package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;

import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

public class VerifyThread extends Thread {

	List<IFile> fileList;
	
	public VerifyThread(List<IFile> fileList) {
		super();
		this.fileList = fileList;
	}
	
	@Override
	public void run() {
		for (IFile iFile : fileList) {
			File file = iFile.getLocation().toFile();
			ProveWithKey.proveWithKey(file, null);
		}
    }

}
