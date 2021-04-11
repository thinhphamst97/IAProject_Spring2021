package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import utils.Utils;

@WebServlet("/AddClientServlet")
public class AddClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PAGE = "addclient.jsp";
	
    public AddClientServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error;
		String mac = request.getParameter("mac");
		if (mac != null ) {
			if (Utils.checkMacAddressFormat(mac)) {
				String result = ClientDAO.addClient(mac);
				if (result.equals("true")) {
					request.setAttribute("result", "true");
				} else {
					error = result;
					request.setAttribute("result", error);
				}
			} else {
				error = "Wrong MAC address format<br>\n";
				error += "Example: a1:b2:c3:d4:5f:6e<br>\n";
				request.setAttribute("result", error);
			}
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
