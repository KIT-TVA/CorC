package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;

import de.tu_bs.cs.isf.cbc.mutation.feature.Mutator;

public class MutationInformation {
    private Mutator mutator;

    public MutationInformation(Mutator mutator) {
	this.mutator = mutator;
    }

    public boolean save() throws CoreException {
	var fileContent = "";
	var folder = this.mutator.getMutationFolder();
	var infoFile = folder.getFile("mutation_infos.txt");
	for (var mutation : mutator.getMutationInfos()) {
	    fileContent += mutation.mutantName + "\n \tOriginal: " + mutation.mutation.originalLine + "\n\tMutated: "
		    + mutation.mutation.newLine + "\n\n";
	}
	if (infoFile.exists()) {
	    infoFile.delete(false, null);
	}
	InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
	infoFile.create(inputStream, false, null);
	return true;
    }

}
