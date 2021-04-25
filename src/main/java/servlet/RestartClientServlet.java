package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dto.ClientDTO;
import utils.Utils;

@WebServlet("/RestartClientServlet")
public class RestartClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "MainServlet?action=Client";

    public RestartClientServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String restartResult = "false";
		String restartClient = request.getParameter("restartClient");
		String restartAll = request.getParameter("restartAll");
		if (restartClient != null && restartClient.equals("true")) {
			int clientId = Integer.parseInt(request.getParameter("clientId"));
			ClientDTO client = ClientDAO.getClient(clientId);
			if (client.isOn()) {
				if (client.getCurrentImage().getType().equals("linux")) {
					// Restart linux
					Utils.sshExecute(client.getCurrentIp(), "root", "lehieu123", "init 6");
					restartResult = "true";
				} else if (client.getCurrentImage().getType().equals("windows")) {
					
				}
			}
		} else if (restartAll != null && restartAll.equals("true")) {
			ArrayList<ClientDTO> clientList = ClientDAO.getAll();
			for (ClientDTO client : clientList) {
				if (client.isOn()) {
					if (client.getCurrentImage().getType().equals("linux")) {
						// Restart linux
						Utils.sshExecute(client.getCurrentIp(), "root", "lehieu123", "init 6");
						restartResult = "true";
					} else if (client.getCurrentImage().getType().equals("windows")) {
						
					}
				}
			}
		}
		request.setAttribute("restartResult", restartResult);
		forward(PAGE, request, response);
	}

	private void forward(String PAGE, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(PAGE).forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
