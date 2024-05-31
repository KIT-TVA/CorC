package de.kit.tva.lost.models;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import de.kit.tva.lost.models.LostParser.ProgramContext;

public class ParseTreeGenerator {
    private ProgramContext tree;

    public ParseTreeGenerator() {
	this.tree = null;
    }

    public boolean generateParseTree(String code) {
	LostLexer lexer = new LostLexer(CharStreams.fromString(code));
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	LostParser parser = new LostParser(tokens);
	parser.removeErrorListeners();
	parser.addErrorListener(TranslatorErrorListenerModel.getInstance());
	this.tree = parser.program();
	return !TranslatorErrorListenerModel.getInstance().errorOccurred();
    }

    public ProgramContext get() {
	return tree;
    }
}
