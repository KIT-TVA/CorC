package de.tu_bs.cs.isf.cbc.mutation.feature;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IMutationReductionTechnique;

public class NPercent implements IMutationReductionTechnique {

	public long randomSeed;
	public int percentage;
	
	public NPercent(long randomSeed, int percentage) {
		this.randomSeed = randomSeed;
		this.percentage = percentage;
	}
	
	@Override
	public File[] reduceMutants(File[] files) {
		int resultLength = files.length * (percentage / 100);
		java.util.Random rng = new java.util.Random(randomSeed);
		List<File> fileList = new LinkedList<File>();
		for (File f: files) {
			fileList.add(f);
		}
		while(fileList.size() >= resultLength) {
			fileList.remove(rng.nextInt(fileList.size()));
		}
		File[] finalfiles = new File[resultLength];
		for (int i = 0; i < resultLength; i++) {
			finalfiles[i] = fileList.get(i);
		}
		return finalfiles;
	}

}
