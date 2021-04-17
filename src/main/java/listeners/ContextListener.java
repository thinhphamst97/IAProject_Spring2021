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
	private final int DEFAULTSLEEPTIME = 30; //seconds
	ServletContext context;
	boolean stop = false;
	Thread t2, t3;
	Process npmStartProcess = null;
	
    public ContextListener() {
    	
    }

    public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("Start up application");
    	context = sce.getServletContext();
    	t2 = new Thread() {
    		public void run() {
    			while(true) {
    				try {
	    				if (stop)
	    					break;
	    		    	if (context.getAttribute("sleepTime") == null)
	    		    		context.setAttribute("sleepTime", DEFAULTSLEEPTIME);
	    		    	System.out.println("Start to check clients...");
	    				checkClient(sce);
    					int sleepTime = (int)context.getAttribute("sleepTime");
        		    	System.out.println(String.format("Sleep %d seconds...\n.\n.\n.", sleepTime));
        		    	for (int i = 0; i < sleepTime; i++) {
        		    		System.out.printf("%d.", sleepTime - i);
        		    		Thread.sleep(1 * 1000); //miliseconds
        		    	}
        		    	System.out.println("0");
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    					break;
    				}
    			}
    		}
    	};
    	t2.start();
    	t3 = new Thread() {
    		public void run() {
    	    	npmStart(sce);
    		}
    	};
    	t3.start();
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("Shutdown application");
    	stop = false;
    	try {
    		if (t2.isAlive()) {
    			t2.interrupt();
    			t2.join();
    		}
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	try {
    		if (t3.isAlive()) {
    			t3.interrupt();
    			t3.join();
    		}
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	//npmStartProcess.destroy();
    }
    
    private void npmStart(ServletContextEvent sce) {
    	//Check if npm is running
    	String[] cmdArray = new String[] {"ps", "-aux"};
    	String output = Utils.executeCommand(cmdArray);
    	String[] lines = output.split("\n");
    	for (String line : lines) {
    		if (line.toLowerCase().contains("npm")) {
    			// npm is running => do not execute "npm start"
    			return;
    		}
    	}
    	

		// npm is not running => execute "npm start"
    	String monitorRelativeWebPath = "/monitor";
    	String absoluteMonitorDiskPath = sce.getServletContext().getRealPath(monitorRelativeWebPath);
    	cmdArray = new String[] {"npm", "start"};
    	System.out.println(absoluteMonitorDiskPath);
    	npmStartProcess = Utils.executeCommandWithCWD(cmdArray, absoluteMonitorDiskPath);
    }
    
//    private String multiplyString(String ori, int num) {
//    	String result = "";
//    	for (int i = 0; i < num; i++) {
//    		result += ori;
//    	}
//    	return result;
//    }

    
    private void checkClient(ServletContextEvent sce) {
    	// Set default number of ping requests to application variable 
    	if (context.getAttribute("pingRequests") == null)
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
}
