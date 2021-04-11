package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dao.ImageDAO;
import dao.KernelDAO;
import dto.ImageDTO;
import dto.KernelDTO;
import utils.Utils;

@WebServlet("/DeleteImageServlet")
public class DeleteImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "deleteimageexample.jsp";

	public DeleteImageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String generalImageDirPath = getServletContext().getInitParameter("generalImageDirPath");
		int imageId;
		if (request.getParameter("imageId") != null) {
			boolean result;
			imageId = Integer.parseInt(request.getParameter("imageId"));

			// Remove reference(s) between client(s) and the image
			result = ClientDAO.removeReference(imageId);
			if (result) {
				// Get image information
				ImageDTO image = ImageDAO.getImage(imageId);
				if (image != null) {
					KernelDTO kernel = image.getKernel();
					String imageName = image.getName();
					String imageDirPath = generalImageDirPath + File.separator + imageName;
					boolean end = false;

					// Both Windows and Linux: Remove the image directory in
					// /var/www/html/ltsp/image/<imageName>
					String[] cmdArray = new String[] { "rm", "-r", imageDirPath };
					String output = Utils.executeCommand(cmdArray);
					if (output.toLowerCase().contains("cannot remove")) {
						request.setAttribute("deleteImageResult", output);
						end = true;
					}

					if (end == false) {
						if (image.getType().equalsIgnoreCase("windows")) {
							// Windows: Do nothing

						} else if (image.getType().equalsIgnoreCase("linux")) {
							// remove the "file system" file in NFS directory
							String nfsDirPath = getServletContext().getInitParameter("nfsDirPath");
							String fileSystemPath = String.format("%s/%s.img", nfsDirPath, imageName);
							cmdArray = new String[] { "rm", fileSystemPath };
							output = Utils.executeCommand(cmdArray);
							if (output.toLowerCase().contains("cannot remove")) {
								request.setAttribute("deleteImageResult", output);
								end = true;
							}
						} else {
							// Unknown image type
							request.setAttribute("deleteImageResult", "Unknown image type");
							end = true;
						}
					}

					if (end == false) {
						// Both Windows and Linux: Remove the image in DB
						if (ImageDAO.deleteImage(imageId)) {
							// only linux remove the kernel in DB (because windows use only 1 wimboot for
							// all images)
							if (image.getType().equalsIgnoreCase("linux")
									&& KernelDAO.deleteKernel(kernel.getId()) == false) {
								String error = "Cannot delete the kernel information in database";
								request.setAttribute("deleteImageResult", error);
							} else {
								request.setAttribute("deleteImageResult", "true");
							}
						} else {
							String error = "Cannot delete the image information in database";
							request.setAttribute("deleteImageResult", error);
						}
					}
				} else {
					// There is no image with this image id
					String error = String.format("There is no image with this imageId = %d", imageId);
					request.setAttribute("deleteImageResult", error);
				}

			} else {
				String error = String
						.format("Cannot remove reference(s) between client(s) and the image (image id = %d)", imageId);
				request.setAttribute("deleteImageResult", error);
			}

		} else {
			request.setAttribute("deleteImageResult", "imageId is null");
		}
		forward(PAGE, request, response);
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
