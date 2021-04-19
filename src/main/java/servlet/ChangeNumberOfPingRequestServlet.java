package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangeNumberOfPingRequestServlet")
public class ChangeNumberOfPingRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String PAGE = "MainServlet?action=Dashboard";

    public ChangeNumberOfPingRequestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HAHAHAHAHAHa");
		String norString = request.getParameter("numberOfRequests");
		if (norString != null && norString.matches("([0-9])+")) {
			int numberOfRequest = Integer.parseInt(norString);
			getServletContext().setAttribute("pingRequests", numberOfRequest);
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
	
	public static void main(String[] args) {
		System.out.println("12321321".matches("([0-9])+"));
	}

}
