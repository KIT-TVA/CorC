package de.tu_bs.cs.isf.cbc.mutation.util;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class JavaDirectoryLoader {

	public List<File> fileList = new LinkedList<File>();
	public File parentDir;
	public String fileEnding = ".java";

	public File[] loadData(String path) {
		parentDir = new File(path);
		fileList.add(parentDir);
		addAllFilesToList();
		List<File> toRemove = new LinkedList<File>();
		for (File f : fileList) {
			if (!f.getName().contains(fileEnding)) {
				toRemove.add(f);
			}
		}
		fileList.removeAll(toRemove);

		File[] files = new File[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			files[i] = fileList.get(i);
		}

		return files;
	}

	public void addAllFilesToList() {
		List<File> toRemove = new LinkedList<File>();
		List<File> toAdd = new LinkedList<File>();
		for (File f : fileList) {
			if (f.isDirectory()) {
				for (File fnew : f.listFiles()) {
					toAdd.add(fnew);
				}
				toRemove.add(f);
			} else {
				if (!f.getAbsolutePath().contains(fileEnding)) {
					toRemove.add(f);
				}
			}
		}
		while (toAdd.size() != 0) {
			fileList.removeAll(toRemove);
			toRemove.clear();
			fileList.addAll(toAdd);
			toAdd.clear();
			addAllFilesToList();
		}
	}
}
