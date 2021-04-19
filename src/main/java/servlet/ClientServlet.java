package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dto.ClientDTO;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PAGE = "client.jsp";
	private final int DEFAULTREQUESTS = 4;
	int numOfRequests;
	String logDirPath;

	public ClientServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logDirPath = getServletContext().getInitParameter("logDirPath");
		// String apacheLogPath = getServletContext().getInitParameter("apacheLogPath");
		if (request.getParameter("numOfRequests") != null) {
			numOfRequests = Integer.parseInt(request.getParameter("numOfRequests"));
		} else {
			numOfRequests = DEFAULTREQUESTS;
		}

//		ArrayList<Thread> threadList = new ArrayList<Thread>();
//		int i = 1;
//		for (ClientDTO client : clientList) {
//			// System.out.println(String.format("%d - %s", x.getId(), x.getMac()));
//			String ip = Utils.MacToIp(client.getMac());
//			if (ip != null) {
//				Thread t = new Thread() {
//					public void run() {
//						HashMap<Boolean, String> result = Utils.getClientInfo(ip, apacheLogPath, numOfRequests);
//						if (result.containsKey(true)) {
//							client.setOn(true);
//							client.setCurrentIp(ip);
//							ImageDTO currentImage = ImageDAO.getImage(result.get(true));
//							client.setCurrentImage(currentImage);
//						} else {
//							client.setOn(false);
//							client.setCurrentImage(null);
//						}
//					}
//				};
//				t.start();
//				System.out.println("Start thread " + i);
//				i++;
//				threadList.add(t);
//			} else {
//				client.setOn(false);
//				client.setCurrentImage(null);
//			}
//		}
//		
//		// Wait for threads to finish their work
//		for (i = 0; i < threadList.size(); i++) {
//			try {
//				threadList.get(i).join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		ArrayList<ClientDTO> clientList = ClientDAO.getAll();
		for (ClientDTO client : clientList) {
			if (client.isOn()) {
				String now = LocalDateTime.now().toString();
				int index = now.indexOf("T") + 1;
				now = now.substring(index, index + "17:49:05".length()) + "</br>"
						+ now.substring(0, "2021-04-18".length());
				client.setLastLoggedOn(now);
			} else {
				String time = getLastLoggedOn(client.getMac());
				client.setLastLoggedOn(time);
			}
		}
		request.setAttribute("clientList", clientList);
		forward(PAGE, request, response);
	}

	private String getLastLoggedOn(String mac) {
		String result = null;
		String accessLogDirPath = "/var/www/html/ltsp/log" + File.separator + "access";
		String logPath = accessLogDirPath + File.separator + mac + ".log";
		String content = null;
		if (Files.exists(Paths.get(logPath))) {
			try {
				content = Files.readString(Paths.get(logPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] lines = content.split("\n");

			// time
			if (lines.length > 0) {
				String temp = lines[lines.length - 1];
				int index = temp.indexOf(" ");
				if (!temp.equals("")) {
					String time = temp.substring(0, index);
					System.out.println(time);
					index = temp.indexOf("T");
					String first = time.substring(index, index + "17:49:05".length());
					String second = "</br>";
					String third = time.substring(0, "2021-04-18".length());
					result = first + second + third;
				}
			}
			// status
//    		temp = temp.substring(index + 1);
//    		index = temp.indexOf(" ");
//    		String status = temp.substring(0, index);

//    		//image name
//    		String imageName = null;
//    		if (status.equals("on")) {
//	    		temp = temp.substring(index + 1);
//	    		imageName = temp;
//    		}
		}
		return result;
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

	public static void main(String[] args) {
		//System.out.println(getLastLoggedOn("00:0c:29:a3:fc:19"));
	}

}
