package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImageDAO;

@WebServlet("/UpdateStatusImageServlet")
public class UpdateStatusImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "MainServlet?action=ImageList";

	public UpdateStatusImageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] idList = request.getParameterValues("id[]");
		String[] statusList = request.getParameterValues("imageStatus[]");
		if (idList != null && statusList != null) {
			for (int i = 0; i < idList.length; i++) {
				int id = Integer.parseInt(idList[i]);
				boolean status = "true".equalsIgnoreCase(statusList[i]) ? true : false;
				if (ImageDAO.updateStatusImage(id, status) == false) {
					System.out.println(String.format("Failed to update: id = %d, status = %s", id, status));
					request.setAttribute("updateStatusImageResult", true);
				}
			}
		}
		request.setAttribute("updateStatusImageResult", true);
		forward(PAGE, request, response); return;
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
