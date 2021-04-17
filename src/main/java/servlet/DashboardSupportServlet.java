package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dao.ImageDAO;
import dto.ClientDTO;

@WebServlet("/DashboardSupportServlet")
public class DashboardSupportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "dashboardsupport.jsp";

	public DashboardSupportServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dashboardSupportResult = "unknown result";
		boolean error = false;

		// Get client information
		ArrayList<ClientDTO> clientList = ClientDAO.getAll();
		int numberOfClients = clientList.size();
		int numberOfOnlineClients = 0;
		int numberOfWindowsClients = 0;
		int numberOfLinuxClients = 0;
		for (int i = 0; i < numberOfClients; i++) {
			if (error)
				break;
			System.out.println(clientList.get(i).getCurrentIp());
			System.out.println(clientList.get(i).getId());
			System.out.println(clientList.get(i).getMac());
			System.out.println(clientList.get(i).getName());
			System.out.println(clientList.get(i).isOn());
			if (clientList.get(i).isOn()) {
				numberOfOnlineClients += 1;
				if (clientList.get(i).getCurrentImage() != null) {
					if (clientList.get(i).getCurrentImage().getType().equals("windows"))
						numberOfWindowsClients += 1;
					else if (clientList.get(i).getCurrentImage().getType().equals("linux"))
						numberOfLinuxClients += 1;
					else {
						dashboardSupportResult = "Unknown image type: " + clientList.get(i).getCurrentImage().getType();
						error = true;
					}
				}
			}
		}

		// Get image information
		if (!error) {
			int numberOfImages = ImageDAO.getNumberOfImages();
			if (numberOfImages != -1) {
				dashboardSupportResult = String.format("%d %d %d %d %d", numberOfClients, numberOfImages, numberOfOnlineClients
																		, numberOfWindowsClients, numberOfLinuxClients);
			} else {
				dashboardSupportResult = "Cannot get number of images";
				error = true;
			}
		}
		request.setAttribute("dashboardSupportResult", dashboardSupportResult);
		request.setAttribute("error", error);
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
