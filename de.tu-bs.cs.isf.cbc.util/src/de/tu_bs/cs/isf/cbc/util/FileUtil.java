package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;

public class FileUtil implements IFileUtil{
	
	static String applicationUri;	

	public FileUtil(String applicationUri) {
		this.applicationUri = applicationUri;
	}
	
	public static void setApplicationUri(URI applicationUri) {
		FileUtil.applicationUri = applicationUri.toString();
	}

	public File getClassFile(String className) {
		URI uriTrimmed = URI.createPlatformResourceURI(applicationUri).trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				return traverseFolders(project, className);

			}
		}
		return null;
	}

	private File traverseFolders(IContainer folder, String className) {
		try {
			IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					File foundFile = traverseFolders((IContainer) resource, className);
					if (foundFile != null) {
						return foundFile;
					}
				} else if (resource instanceof IFile) {

					final IFile file = (IFile) resource;
					if (file.getName().equals(className + ".java")) {
						return file.getLocation().toFile();
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> readFileInList(String path) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);//teilt jede Zeile ein und teilt somit auch ensures in mehrere Teile
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public String getProjectLocation(String uriString) {
		return getProjectLocationS(uriString).getLocation().toPortableString();
	}
	
	public static IProject getProjectLocationS(String uriPath) {
		if (uriPath == null || uriPath.length() == 0) return null;
		uriPath = uriPath.substring(1, uriPath.length());
		int positionOfSlash = uriPath.indexOf('/') + 1;
		String projectName = uriPath.substring(0, positionOfSlash-1);
		uriPath = uriPath.substring(positionOfSlash, uriPath.length());
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getFile(new Path(uriPath)).exists() && p.getName().equalsIgnoreCase(projectName)) {
				thisProject = p;
				break;
			}
		}
//			if (thisProject.getName().contains("Userstudy")) {
//			File diagramFile = new File(thisProject.getLocation() + "/" + uriPath);
//			File diagramFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".diagram");
//			File cbcFile = new File(thisProject.getLocation() + "/" + uriPath.substring(0, uriPath.indexOf(".")) + ".cbcmodel");
//			File cbcFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".cbcmodel");
//			proofCounter++;
//			try {
//				IWorkspace workspace = ResourcesPlugin.getWorkspace();
//				Files.copy(diagramFile.toPath(), diagramFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
//				Files.copy(cbcFile.toPath(), cbcFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);  
//				IPath iLocation = Path.fromOSString(diagramFileCopy.getAbsolutePath()); 
//				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
//				ifile.refreshLocal(0, null);
//				iLocation = Path.fromOSString(cbcFileCopy.getAbsolutePath()); 
//				ifile = workspace.getRoot().getFileForLocation(iLocation);
//				ifile.refreshLocal(0, null);
//			} catch (IOException | CoreException e) {
//				e.printStackTrace();
//			}
//		}
		return thisProject;
	}
	
	public static String getProjectLocation(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);
		IProject project = getProjectLocationS(uriPath);
		return project == null ? "" : project.getLocation().toPortableString();
	}
	
	public static IProject getProject(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);
		return getProjectLocationS(uriPath);
	}
	
	public static IProject getProjectFromFileInProject(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);
		if (uriPath == null) {
			uriPath = uri.toString();
		}
		uriPath = uriPath.substring(1, uriPath.length());
		int positionOfSlash = uriPath.indexOf('/') + 1;
		uriPath = uriPath.substring(positionOfSlash, uriPath.length());
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getFile(new Path(uriPath)).exists()) {
				thisProject = p;
			}
		}
		return thisProject;
	}
	
	public static IProject getProjectFromProjectPath(String path) {
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path)) {
				thisProject = p;
			}
		}
		return thisProject;
	}
	
	public File writeFile(String problem, String location, boolean override, AbstractStatement statement, String subProofName) {
		FileNameManager manager = new FileNameManager();
		String keyFileName = manager.getFileName(problem, location, statement, subProofName);
		
		File keyFile = new File(location + keyFileName + ".key");

		File keyHelperFile = new File(location + "/helper.key");
		
		if (!keyFile.exists() || override) {
			if (!keyHelperFile.exists()) {
				try {
					keyHelperFile.getParentFile().mkdirs();
					keyHelperFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return createFile(keyFile, problem);
		}
		return null;
	}

	private File createFile(File file, String content) {
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(content);

			bw.close();

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(file.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String generateComposedClass(String project, String composedClassName, String className, String content, String contentOriginal) {
		File generatedClass = new File(getProjectLocation(project) + "/src_gen/" + composedClassName + ".java");
		File originalClass = new File(getProjectLocation(project) + "/src-orig/" + className + ".java");
		if (!className.contentEquals(composedClassName)) {
			createFile(originalClass, contentOriginal);
		}
		File generatedFile = createFile(generatedClass, content);
		return generatedFile.getName().substring(0, generatedFile.getName().indexOf("."));
	}

	private String getLastSegment(String uri) {
		return URI.createURI(uri).trimFileExtension().lastSegment();
	}
	
	private String trimSegment(String uriString, int number) {
		URI uri = URI.createURI(uriString);
		List<String> segments = new ArrayList<String>(Arrays.asList(uri.segments()));
		segments.remove(number);
		return String.join("/", segments);
	}
	
	private String trimLastSegment(String uriString) {
		URI uri = URI.createURI(uriString);
		return trimSegment(uriString, uri.segmentCount() - 1);
	}
	
	public String getLocationString(String uri) {
		String uriWithoutProjectAndFileName = trimLastSegment(trimSegment(uri, 0));
		return getProjectLocation(uri) + "/" + uriWithoutProjectAndFileName + "/prove" + getLastSegment(uri);
	}
	    
    public static Collection<Resource> getCbCClasses(IProject p) {
        final List<IFile> files = getFiles(p, ".cbcclass");
        final List<Resource> cbcClassList = new ArrayList<Resource>();
        final ResourceSet rSet = new ResourceSetImpl();
        for (final IFile file : files) {
             final Resource resource = getResourceFromFile(file, rSet);
             if (resource != null) {
            	 cbcClassList.add(resource);
             }
        }
        return cbcClassList;
     }
	  
    public static List<IFile> getJavaFilesFromProject(IProject p) {
        return getFiles(p, ".java");
     }
    
    public static List<IFile> getFiles(IContainer p, String fileExtension) {
        final List<IFile> files = new ArrayList<IFile>();
        try {
             final IResource[] members = p.members();
             for (final IResource resource : members) {
                  if (resource instanceof IContainer) {
                	  files.addAll(getFiles((IContainer) resource, fileExtension));
                  } else if (resource instanceof IFile) {
                      final IFile file = (IFile) resource;
                      if (file.getName().endsWith(fileExtension)) {
                    	  files.add(file);
                      }
                  }
             }
        } catch (final CoreException e) {
                 e.printStackTrace();
        }
        return files;
     }
    
    public static List<IFile> getFilesFromProject(IProject p, String fileExtension) {
        return getFiles(p, fileExtension);
     }
	 
    public static Resource getResourceFromFile(IFile file,
            ResourceSet resourceSet) {
    	// Get the URI of the model file.
        final URI resourceURI = getFileURI(file, resourceSet);
        // Demand load the resource for this file.
        Resource resource = resourceSet.getResource(resourceURI, true);
    	return resource;
    }
 
    private static URI getFileURI(IFile file, ResourceSet resourceSet) {
       final String pathName = file.getLocation().toPortableString();
       URI resourceURI = URI.createFileURI(pathName);
       resourceURI = resourceSet.getURIConverter().normalize(resourceURI);
       return resourceURI;
    }

}
