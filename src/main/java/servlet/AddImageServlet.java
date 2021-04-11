package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImageDAO;
import dao.KernelDAO;
import utils.Utils;

@WebServlet("/AddImageServlet")
public class AddImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //private final String UPLOAD_DIRECTORY = "upload";
    private final String PAGE = "addimage.jsp";

    public AddImageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        float size = 0;
    	int addResult;
        String imageName = request.getParameter("imageName");
        String imageDescription = request.getParameter("imageDescription");     
        String generalImageDirPath = getServletContext().getInitParameter("generalImageDirPath");
        String httpDirPath = getServletContext().getInitParameter("httpDirPath");
        String imageDirPath = generalImageDirPath + File.separator + imageName;
        String tempImagePath = httpDirPath + File.separator + "tmp";
        
        if (imageName == null || imageName.equals("")) {
        	request.setAttribute("result", "Image name cannot be empty");
            forward(PAGE, request, response); return; 
        }
        if (!imageName.matches("(\\w)+")) {
        	request.setAttribute("result", "Image name can only contains alphanumeric & underscore");
            forward(PAGE, request, response); return; 
        }
        String type = request.getParameter("type");
        if ("windows".equalsIgnoreCase(type)) {
        	// Check condition
            File imageDir = new File(imageDirPath);
            if (!imageDir.exists()) {
            	
            } else {
            	request.setAttribute("result", "Image name is not available");
                forward(PAGE, request, response); return; 
            }
            
        	int wimbootId = Integer.parseInt(getServletContext().getInitParameter("wimbootId"));
            String bcdPath = request.getParameter("bcdPath");
            String bootSdiPath = request.getParameter("bootSdiPath");
            String bootWimPath = request.getParameter("bootWimPath");
            if (bcdPath.equals("") || !Files.exists(Paths.get(bcdPath))) {
                request.setAttribute("result", "bcd path is invalid");
                imageDir.delete();
                forward(PAGE, request, response); return; 
            }
            if (bootSdiPath.equals("") || !Files.exists(Paths.get(bootSdiPath))) {
                request.setAttribute("result", "boot.sdi path is invalid");
                imageDir.delete();
                forward(PAGE, request, response); return; 
            }
            if (bootWimPath.equals("") || !Files.exists(Paths.get(bootWimPath))) {
                request.setAttribute("result", "boot.wim path is invalid");
                imageDir.delete();
                forward(PAGE, request, response); return; 
            }
            
            // Mkdir and copy files
            System.out.println("Before");
            imageDir.mkdir();
            System.out.println("After: " + imageDirPath);
            Files.copy(Paths.get(bcdPath), Paths.get(imageDirPath + File.separator + "bcd"));
            Files.copy(Paths.get(bootSdiPath), Paths.get(imageDirPath + File.separator + "boot.sdi"));
            Files.copy(Paths.get(bootWimPath), Paths.get(imageDirPath + File.separator + "boot.wim"));
			//Chmod the files
			Utils.executeCommand(new String[] {"chmod", "755", imageDirPath, "-R"});
            
            //Calculate image size
            String[] paths = new String[4];
            paths[0] = generalImageDirPath + File.separator + "wimboot";
            paths[1] = imageDirPath + File.separator + "bcd";
            paths[2] = imageDirPath + File.separator + "boot.sdi";
            paths[3] = imageDirPath + File.separator + "boot.wim";
            size = Utils.getImageSize(paths);
            
            //Add information to database
            addResult = ImageDAO.addImage(imageName, "windows", size, imageDescription, false, Date.valueOf(LocalDate.now()), wimbootId);
            if (addResult != 1) {
                request.setAttribute("result", "Fail to add image");
                forward(PAGE, request, response); return; 
            }
        } else if ("linux".equalsIgnoreCase(type)) {
        	String kernelName = request.getParameter("kernelName");
        	String logFilePath = getServletContext().getInitParameter("logDirPath") + File.separator + imageName + ".log";
        	String linkFilePath = tempImagePath + File.separator + imageName + ".img";
        	if (kernelName == null || kernelName.equals("")) {
                request.setAttribute("result", "Kernel name cannot be empty");
                forward(PAGE, request, response); return; 
        	}
            
            String vmdkPath = request.getParameter("vmdkPath");
            if (vmdkPath.equals("") || !Files.exists(Paths.get(vmdkPath))) {
                request.setAttribute("result", "VMDK path is invalid");
                forward(PAGE, request, response); return; 
            }
            Files.createSymbolicLink(Paths.get(linkFilePath), Paths.get(vmdkPath));
            
            //Execute ltsp commands
            String[] cmdArray = new String[]{"/bin/sh", "-c", "ltsp image " + tempImagePath + File.separator + imageName + ".img"};//new String[]{"ltsp", "image", };
            String output = Utils.executeCommand(cmdArray, logFilePath);
            if (output.indexOf("To update the iPXE menu") != -1) {
            	//Successful
            	String ltspImageDirPath = "/srv/tftp/ltsp/" + imageName;
            	String ltspInitrdPath = ltspImageDirPath + File.separator + "initrd.img";
            	String ltspVmlinuzPath = ltspImageDirPath + File.separator + "vmlinuz";
            	if (Files.exists(Paths.get(ltspImageDirPath)) && Files.isDirectory(Paths.get(ltspImageDirPath))) {
            		if (Files.exists(Paths.get(ltspInitrdPath))) {
                		if (Files.exists(Paths.get(ltspVmlinuzPath))) {
                			//Move full image directory (contains initrd.img and vmlinuz) to /var/www/html/ltsp/image/
                			Files.move(Paths.get(ltspImageDirPath), Paths.get(imageDirPath));
                			//Chmod the files
                			Utils.executeCommand(new String[] {"chmod", "755", imageDirPath, "-R"});
                			
                            //Calculate image size
                            String[] paths = new String[4];
                            paths[0] = generalImageDirPath + File.separator + "ltsp.img";
                            paths[1] = imageDirPath + File.separator + "vmlinuz";
                            paths[2] = imageDirPath + File.separator + "initrd.img";
                            paths[3] = "/srv/ltsp/images/" + imageName + ".img";
                            size = Utils.getImageSize(paths);
                            
                            //Add information to database
                            addResult = KernelDAO.addKernel(kernelName);
                            if (addResult != 1) {
                            	Files.delete(Paths.get(linkFilePath));
                                request.setAttribute("result", "Fail to add kernel");
                                forward(PAGE, request, response); return; 
                            }
                            addResult = ImageDAO.addImage(imageName, "linux", size, imageDescription, false, Date.valueOf(LocalDate.now()), KernelDAO.getHighestId());
                            if (addResult != 1) {
                            	Files.delete(Paths.get(linkFilePath));
                                request.setAttribute("result", "Fail to add image");
                                forward(PAGE, request, response); return; 
                            }
                        	Files.delete(Paths.get(linkFilePath));
                		} else {
                        	Files.delete(Paths.get(linkFilePath));
                            request.setAttribute("result", "File " + ltspVmlinuzPath + " does not exist");
                            forward(PAGE, request, response); return; 
                		}
            		} else {
                    	Files.delete(Paths.get(linkFilePath));
                        request.setAttribute("result", "File " + ltspInitrdPath + " does not exist");
                        forward(PAGE, request, response); return; 
            		}
            	} else {
                	Files.delete(Paths.get(linkFilePath));
                    request.setAttribute("result", "Directory " + ltspImageDirPath + " does not exist");
                    forward(PAGE, request, response); return; 
            	}
            } else {
            	//Fail
                //Remove link file
            	Files.delete(Paths.get(linkFilePath));
            	String command = cmdArray[0] + cmdArray[1] + cmdArray[2];
                request.setAttribute("result", String.format("Failed to run command \"%s\". Read the log file at %s",
                		command, logFilePath));
                forward(PAGE, request, response); return; 
            }
        } else {
            request.setAttribute("result", "Unknown image type");
            forward(PAGE, request, response); return; 
        }
        request.setAttribute("result", "true");
        forward(PAGE, request, response); return; 
    }
    
    private void forward(String PAGE, HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
        request.getRequestDispatcher(PAGE).forward(request, response);
        return;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
