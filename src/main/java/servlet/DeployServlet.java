/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.ImageDAO;
import dto.ImageDTO;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ThinhPH
 */
@WebServlet("/DeployServlet")
public class DeployServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String DEPLOY_SINGLE_OS = "deploysingleos.jsp";
    private final String DEPLOY_MULTIPLE_OS = "deploymultipleos.jsp";
    private final String DEPLOY_OS_WITHIN_CLIENTMAC = "deploywithinclientmac.jsp";

    public DeployServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("option") != null) {
            int option = Integer.valueOf(request.getParameter("option"));
            //option 0: single OS
            //option 1: multiple OS
            //option 2: OS within Client's MAC
            switch (option) {
                case 0:
                    ArrayList<ImageDTO> listForSingleOS = ImageDAO.getAll();
                    request.setAttribute("imageList", listForSingleOS);
                    //Nếu request chứa selectImage --> trả về selectImage
                    if (request.getParameter("selectImage") != null && !request.getParameter("selectImage").equalsIgnoreCase("-1")) {
                        //Nếu selectImage == -1 thì nó là dòng text default --> bỏ qua
                        ServletContext context = getServletContext();
                        String imagePath = context.getInitParameter("generalImageDirPath");
                        int id = Integer.valueOf(request.getParameter("selectImage"));
                        ImageDTO image = ImageDAO.getImageDetails(id);
                        if (image.getType().equalsIgnoreCase("windows")) {
                            String kernelPath = String.format("%s/wimboot", imagePath);
                            String fileSystemPath = String.format("%s/%s/boot.wim", imagePath, image.getName());
                            ArrayList<String> initrdPathList = new ArrayList<String>();
                            initrdPathList.add(String.format("%s/%s/bcd", imagePath, image.getName()));
                            initrdPathList.add(String.format("%s/%s/boot.sdi", imagePath, image.getName()));
                            request.setAttribute("image", image);
                            request.setAttribute("kernelPath", kernelPath);
                            request.setAttribute("fileSystemPath", fileSystemPath);
                            request.setAttribute("initrdPathList", initrdPathList);
                        } else if (image.getType().equalsIgnoreCase("linux")) {
        					String nfsDirPath = getServletContext().getInitParameter("nfsDirPath");
                            String kernelPath = String.format("%s/%s/vmlinuz", imagePath, image.getName());
                            String fileSystemPath = String.format("%s/%s.img", nfsDirPath, image.getName());
                            ArrayList<String> initrdPathList = new ArrayList<String>();
                            initrdPathList.add(String.format("%s/%s/initrd.img", imagePath, image.getName()));
                            initrdPathList.add(String.format("%s/ltsp.img", imagePath));
                            request.setAttribute("image", image);
                            request.setAttribute("kernelPath", kernelPath);
                            request.setAttribute("fileSystemPath", fileSystemPath);
                            request.setAttribute("initrdPathList", initrdPathList);
                        }
                    }
                    //Nếu request chứa ID Deploy 
                    if (request.getParameter("idDeploy") != null) {
                    	String menuDirPath = getServletContext().getInitParameter("menuDirPath");
                        int id = Integer.valueOf(request.getParameter("idDeploy"));
                        ImageDTO image = ImageDAO.getImage(id);
                        String imageName = image.getName();
                        String imageType = image.getType();
                        String menu = Utils.createMenu(menuDirPath, imageName, imageType);
                        Files.deleteIfExists(Paths.get("/srv/tftp/ltsp/ltsp.ipxe"));
                        Files.writeString(Paths.get("/srv/tftp/ltsp/ltsp.ipxe"), menu, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                    }
                    request.setAttribute("result", "true");
                    forward(DEPLOY_SINGLE_OS, request, response); return;
                case 1:
//                    ArrayList<ImageDTO> listForMultipleOS = ImageDAO.getImageActiveList();
//                    if (listForMultipleOS != null) {
//                        request.setAttribute("imageList", (listForMultipleOS));
//                        forward(DEPLOY_MULTIPLE_OS, request, response); return;
//                    }
//                    if (request.getParameter("deployMultipleOS") != null && request.getParameter("deployMultipleOS").equalsIgnoreCase("true")) {
//                        for (ImageDTO x : listForMultipleOS) {
//                            System.out.println("DEPLOY CAI NAY NE! ID= " + x.getId());
//                        }
//                    }
                    break;
                case 2:
                	forward(DEPLOY_OS_WITHIN_CLIENTMAC, request, response); return;
            }

        }
    }

	private void forward(String PAGE, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(PAGE).forward(request, response);
		return;
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
