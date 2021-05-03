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

@WebServlet("/ShutdownClientServlet")
public class ShutdownClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "MainServlet?action=Client";
	private final String proxyIp = "192.168.67.2";
	private final String proxyUser = "ssh";
	private final String proxyPass = "ssh";
	private final String peUser = "administrator";
	private final String pePass = "lehieu123";
	private final String linuxUser = "root";
	private final String linuxPass = "lehieu123";

    public ShutdownClientServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String shutdownResult = "false";
		String shutdownClient = request.getParameter("shutdownClient");
		String shutdownAll = request.getParameter("shutdownAll");
		if (shutdownClient != null && shutdownClient.equals("true")) {
			int clientId = Integer.parseInt(request.getParameter("clientId"));
			ClientDTO client = ClientDAO.getClient(clientId);
			if (client.isOn()) {
				if (client.getCurrentImage().getType().equals("linux")) {
					// Shutdown linux
					Utils.sshExecute(client.getCurrentIp(), linuxUser, linuxPass, "init 0");
				} else if (client.getCurrentImage().getType().equals("windows")) {
					// Shutdown windows
					Utils.shutdownWinPE(client.getCurrentIp(), peUser, pePass, proxyIp, proxyUser, proxyPass);
				}
				shutdownResult = "true";
			}
			request.setAttribute("shutdownID", clientId);
		} else if (shutdownAll != null && shutdownAll.equals("true")) {
			ArrayList<ClientDTO> clientList = ClientDAO.getAll();
			for (ClientDTO client : clientList) {
				if (client.isOn()) {
					if (client.getCurrentImage().getType().equals("linux")) {
						// Shutdown linux
						Utils.sshExecute(client.getCurrentIp(), linuxUser, linuxPass, "init 0");
					} else if (client.getCurrentImage().getType().equals("windows")) {
						Utils.shutdownWinPE(client.getCurrentIp(), peUser, pePass, proxyIp, proxyUser, proxyPass);
					}
					shutdownResult = "true";
				}
			}
		}
		request.setAttribute("shutdownResult", shutdownResult);
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
