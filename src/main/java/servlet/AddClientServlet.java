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

@WebServlet("/AddClientServlet")
public class AddClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "addclient.jsp";

	public AddClientServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = null;
		String name = request.getParameter("name");
		String mac = request.getParameter("mac").toLowerCase();
		if (mac != null && !mac.equals("")) {
			if (name != null && !name.equals("")) {
				if (Utils.checkMacAddressFormat(mac)) {
					String result = ClientDAO.addClient(name, mac);
					if (result.equals("true")) {
						request.setAttribute("result", "true");
						
						// Add this client to macDeployClientList context attriubte
						@SuppressWarnings("unchecked")
						ArrayList<ClientDTO> macDeployClientList = (ArrayList<ClientDTO>)
								getServletContext().getAttribute("macDeployClientList");
						ClientDTO newClient = ClientDAO.getClient(ClientDAO.getHighestId());
						macDeployClientList.add(newClient);
						getServletContext().setAttribute("macDeployClientList", macDeployClientList);
						
					} else {
						error = result;
					}
				} else {
					error = "Wrong MAC address format<br>\n";
					error += "Example: a1:b2:c3:d4:5f:6e<br>\n";
				}
			} else {
				error = "Client name cannot be empty";
			}
		} else {
			error = "MAC address cannot be empty";
		}
		if (error != null)
			request.setAttribute("result", error);
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
