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
import dao.ImageDAO;
import dto.ClientDTO;
import dto.ImageDTO;
import utils.Utils;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PAGE = "client.jsp";
	private final int DEFAULTREQUESTS = 4;
	int numOfRequests;

	public ClientServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String apacheLogPath = getServletContext().getInitParameter("apacheLogPath");
		if (request.getParameter("numOfRequests") != null) {
			numOfRequests = Integer.parseInt(request.getParameter("numOfRequests"));
		} else {
			numOfRequests = DEFAULTREQUESTS;
		}

		ArrayList<ClientDTO> clientList = ClientDAO.getAll();
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		int i = 1;
		for (ClientDTO client : clientList) {
			// System.out.println(String.format("%d - %s", x.getId(), x.getMac()));
			String ip = Utils.MacToIp(client.getMac());
			if (ip != null) {
				Thread t = new Thread() {
					public void run() {
						HashMap<Boolean, String> result = Utils.getClientInfo(ip, apacheLogPath, numOfRequests);
						if (result.containsKey(true)) {
							client.setOn(true);
							client.setCurrentIp(ip);
							ImageDTO currentImage = ImageDAO.getImage(result.get(true));
							client.setCurrentImage(currentImage);
						} else {
							client.setOn(false);
							client.setCurrentImage(null);
						}
					}
				};
				t.start();
				System.out.println("Start thread " + i);
				i++;
				threadList.add(t);
			} else {
				client.setOn(false);
				client.setCurrentImage(null);
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
