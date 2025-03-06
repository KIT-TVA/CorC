package de.tu_bs.cs.isf.cbc.tool.proofs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.Predicate;
import de.tu_bs.cs.isf.cbc.util.PredicateDefinition;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser.SelectionInfo;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public interface ProofGraphStrategy {

	default List<Set<SelectionInfo>> generatePredicateInfo(IKeYProof proof) {

		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		String preFormula = Parser.getConditionFromCondition(proof.getStatement().getPreCondition().getName());
		String postFormula = Parser.getConditionFromCondition(proof.getStatement().getPostCondition().getName());

		for (Predicate pred : this.readPredicates(proof.getDiagram())) {
			if (preFormula.contains(pred.name) || postFormula.contains(pred.name)) {
				for (PredicateDefinition def : pred.definitions) {
					if (!def.presenceCondition.equals("true")) {
						IFeatureModel featureModel = FeatureModelManager
								.load(Paths.get(project.getLocation() + "/model.xml"));

						PresenceConditionParser parser = new PresenceConditionParser(def.presenceCondition,
								featureModel.getFeatureOrderList());
						List<Set<SelectionInfo>> conditions = parser.parseCondition();

						return conditions;

					}
				}
			}
		}

		return new ArrayList<Set<SelectionInfo>>();
	}

	default void findPaths(ProofGraph graph, ProofNode node, List<List<String>> paths, List<String> localPathList) {
		if (graph.getAdjacencyList().get(node).isEmpty()) {
			paths.add(List.copyOf(localPathList));
			return;
		}

		for (ProofNode cn : graph.getAdjacencyList().get(node)) {
			localPathList.add(cn.getFeature());
			findPaths(graph, cn, paths, localPathList);
			localPathList.remove(cn.getFeature());
		}
	}

	default void findForks(ProofGraph graph, ProofNode node, List<List<String>> paths, List<String> localPathList) {
		for (ProofNode cn : graph.getAdjacencyList().get(node)) {
			localPathList.add(cn.getFeature());
			findForks(graph, cn, paths, localPathList);
			localPathList.remove(cn.getFeature());
		}

		if (graph.getAdjacencyList().get(node).size() >= 2) {
			paths.add(List.copyOf(localPathList));
		}

		if (graph.getAdjacencyList().get(node).isEmpty()) {
			return;
		}

	}

	private List<Predicate> readPredicates(Diagram diagram) {
		URI uri = diagram.eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		FileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		String filePath = project.getRawLocation() + "/predicates.def";

		List<Predicate> readPredicates = fileHandler.readPredicates(filePath);

		return readPredicates;
	}
}
