package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;

@WebServlet("/DeleteClientServlet")
public class DeleteClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private final String PAGE = "MainServlet?action=Client";
	private final String PAGE = "deleteclientexample.jsp";

    public DeleteClientServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idString = request.getParameter("clientId");
		if (idString != null && !idString.equals("")) {
			int clientId = Integer.parseInt(idString);
			if (ClientDAO.deleteClient(clientId))
				request.setAttribute("deleteClientResult", "true");
			else
				request.setAttribute("deleteClientResult", String.format("Cannot delete client, clientId: %d", clientId));
		}
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
