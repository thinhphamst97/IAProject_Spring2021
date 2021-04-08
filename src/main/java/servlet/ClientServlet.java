package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dto.ClientDTO;
import utils.Utils;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "clientexample.jsp";

	public ClientServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String apacheLogPath = getServletContext().getInitParameter("apacheLogPath");

		ArrayList<ClientDTO> clientList = ClientDAO.getAll();
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		int i = 1;
		for (ClientDTO client : clientList) {
			// System.out.println(String.format("%d - %s", x.getId(), x.getMac()));
			String ip = Utils.MacToIp(client.getMac());
			if (ip != null) {
				Thread t = new Thread() {
					public void run() {
						HashMap<Boolean, String> result = Utils.getClientInfo(ip, apacheLogPath);
						if (result.containsKey(true)) {
							client.setOn(true);
							client.setCurrentIp(ip);
							client.setCurrentImageName(result.get(true));
						} else {
							client.setOn(false);
							client.setCurrentImageName(null);
						}
					}
				};
				t.start();
				System.out.println("Start thread " + i);
				i++;
				threadList.add(t);
			} else {
				client.setOn(false);
				client.setCurrentImageName(null);
			}
		}
		
		// Wait for threads to finish their work
		for (i = 0; i < threadList.size(); i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("clientList", clientList);
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