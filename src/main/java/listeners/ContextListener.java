package listeners;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.ClientDAO;
import dao.ImageDAO;
import dto.ClientDTO;
import dto.ImageDTO;
import utils.Utils;

@WebListener
public class ContextListener implements ServletContextListener {
	private final int DEFAULTPINGREQUESTS = 4;
    public ContextListener() {
    	
    }

    public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("Start up application");
    	// Set default number of ping requests to application variable 
    	ServletContext context = sce.getServletContext();
    	context.setAttribute("pingRequests", DEFAULTPINGREQUESTS);
    	
		String apacheLogPath = context.getInitParameter("apacheLogPath");
		

		ArrayList<ClientDTO> clientList = ClientDAO.getAll();
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		int i = 1;
		for (ClientDTO client : clientList) {
			System.out.println("client.getId(): " + client.getId());
			// System.out.println(String.format("%d - %s", x.getId(), x.getMac()));
			String ip = Utils.MacToIp(client.getMac());
			if (ip != null) {
				Thread t = new Thread() {
					public void run() {
						int numOfRequests = (int)context.getAttribute("pingRequests");
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
						int imageId;
						if(client.getCurrentImage() != null)
							imageId = client.getCurrentImage().getId();
						else
							imageId = -1;
						ClientDAO.updateClient(client.getId(), client.getName(), client.isOn()
								, client.getCurrentIp(), imageId);
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
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("Shutdown application");
    }
	
}
