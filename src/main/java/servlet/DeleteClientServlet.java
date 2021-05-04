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

@WebServlet("/DeleteClientServlet")
public class DeleteClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "MainServlet?action=Client";
	//private final String PAGE = "deleteclientexample.jsp";

	public DeleteClientServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("clientId");
		System.out.println("clientId: " + idString);
		if (idString != null && !idString.equals("")) {
			int clientId = Integer.parseInt(idString);
			ClientDTO client = ClientDAO.getClient(clientId);
			if (!client.isOn()) {
				if (ClientDAO.deleteClient(clientId)) {
					request.setAttribute("deleteClientResult", "true");
					
					/* Remove deleted client from macDeployClientList context attriubte */
					@SuppressWarnings("unchecked")
					ArrayList<ClientDTO> macDeployClientList = (ArrayList<ClientDTO>)
							getServletContext().getAttribute("macDeployClientList");
					// Get deleted client object
					ClientDTO deletedClient = null;
					for (ClientDTO macDeployClient : macDeployClientList) {
						if (macDeployClient.getId() == clientId) {
							deletedClient = macDeployClient;
							// Remove deleted client object
							macDeployClientList.remove(deletedClient);
							break;
						}
					}
					getServletContext().setAttribute("macDeployClientList", macDeployClientList);
					
				}
				else
					request.setAttribute("deleteClientResult",
							String.format("Cannot delete client, client name: %s", client.getName()));
			} else {
				request.setAttribute("deleteClientResult",
						String.format("This client is online, cannot delete, client name: %s", client.getName()));
			}
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
