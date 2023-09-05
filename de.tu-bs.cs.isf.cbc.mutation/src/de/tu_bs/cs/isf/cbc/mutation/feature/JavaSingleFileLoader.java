package de.tu_bs.cs.isf.cbc.mutation.feature;
import java.io.File;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IDataLoader;

public class JavaSingleFileLoader implements IDataLoader{

	@Override
	public File[] loadData(String path) {
		File[] files = new File[1];
		files[0] = new File(path);
		return files;
	}

}
