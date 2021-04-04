package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImageDAO;
import dto.ImageDTO;

@WebServlet("/ImageListServlet")
public class ImageListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String PAGE = "imagelist.jsp";

	public ImageListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<ImageDTO> imageList = ImageDAO.getImageList();
		if (imageList != null) {
			request.setAttribute("imageList", (imageList));
			forward(PAGE, request, response); return;
		} else {
			PrintWriter out = response.getWriter();
			out.println("Empty image list");
		}
	}

	private void forward(String PAGE, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(PAGE).forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
