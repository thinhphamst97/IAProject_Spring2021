package listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	String logDirPath;
	//private String relativeWebPath;
	
    public ContextListener() {
    	
    }

    public void contextInitialized(ServletContextEvent sce)  {
    	logDirPath = sce.getServletContext().getInitParameter("logDirPath");
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
        		    	//System.out.println(String.format("Sleep %d seconds...\n.\n.\n.", sleepTime));
        		    	for (int i = 0; i < sleepTime; i++) {
        		    		//System.out.printf("%d.", sleepTime - i);
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
    	// Get absolut web path
    	String relativeWebPath = "";
    	String absoluteWebDiskPath = sce.getServletContext().getRealPath(relativeWebPath);
    	String absoluteMonitorDiskPath = absoluteWebDiskPath + File.separator + "monitor";
    	System.out.println(absoluteMonitorDiskPath);
    	
    	//Check if npm is running
    	String[] cmdArray = new String[] {"ps", "-aux"};
    	String output = Utils.executeCommand(cmdArray);
    	String[] lines = output.split("\n");
    	for (String line : lines) {
    		if (line.toLowerCase().contains("npm")) {
    			// npm is running => do execute "killall -s SIGINT node"
    			cmdArray = new String[] {"killall", "-s", "SIGINT", "node"};
    	    	npmStartProcess = Utils.executeCommandWithCWD(cmdArray, absoluteMonitorDiskPath);
//    			// npm is running => do execute "killall npm"
//    			cmdArray = new String[] {"killall", "npm"};
//    	    	npmStartProcess = Utils.executeCommandWithCWD(cmdArray, absoluteMonitorDiskPath);
//    			// npm is running => do execute "killall /usr/bin/node"
//    			cmdArray = new String[] {"killall", "/usr/bin/node"};
//    	    	npmStartProcess = Utils.executeCommandWithCWD(cmdArray, absoluteMonitorDiskPath);
    		}
    	}
    	

		// npm is not running => execute "npm start"
    	cmdArray = new String[] {"npm", "start"};
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
			final boolean oldStatus = client.isOn();
			boolean switchStatus = false;
			System.out.println("client.getId(): " + client.getId() + ", oldStatus: " + oldStatus);
			// System.out.println(String.format("%d - %s", x.getId(), x.getMac()));
			String ip = Utils.MacToIp(client.getMac());
			if (ip != null) {
				Thread t = new Thread() {
					public void run() {
						boolean switchStatusThread = false;
						int numOfRequests = (int)context.getAttribute("pingRequests");
						HashMap<Boolean, String> result = Utils.getClientInfo(ip, apacheLogPath, numOfRequests);
						if (result.containsKey(true)) {
							if (oldStatus != true) {
								switchStatusThread = true;
							}
							client.setOn(true);
							client.setCurrentIp(ip);
							ImageDTO currentImage = ImageDAO.getImage(result.get(true));
							client.setCurrentImage(currentImage);
						} else {
							if (oldStatus != false) {
								switchStatusThread = true;
							}
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
						
						if (switchStatusThread) {
							String imageName;
							if (client.getCurrentImage() != null && client.isOn())
								imageName = client.getCurrentImage().getName();
							else
								imageName = null;
							writeLogAccess(client.getMac(), client.isOn(), imageName);
						}
					}
				};
				t.start();
				System.out.println("Start thread " + i);
				i++;
				threadList.add(t);
			} else {
				if (oldStatus != false) {
					switchStatus = true;
				}
				client.setOn(false);
				client.setCurrentImage(null);
				client.setCurrentIp(null);
				ClientDAO.updateClient(client.getId(), client.getName(), client.isOn()
						, client.getCurrentIp(), -1);
			}
			
			if (switchStatus) {
				String imageName;
				if (client.getCurrentImage() != null && client.isOn())
					imageName = client.getCurrentImage().getName();
				else
					imageName = null;
				writeLogAccess(client.getMac(), client.isOn(), imageName);
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
    
    private void writeLogAccess(String mac, boolean isOn, String imageName) {
    	String accessLogDirPath = logDirPath + File.separator + "access";
    	String logPath = accessLogDirPath + File.separator + mac + ".log";
    	
    	LocalDateTime now = LocalDateTime.now();
    	String status;
    	if (isOn)
    		status = "on";
    	else
    		status = "off";
    	
    	String content;
    	if (imageName != null && isOn)
    		content = String.format("%s %s %s\n", now, status, imageName);
    	else
    		content = String.format("%s %s\n", now, status);
    	try {
			Files.writeString(Paths.get(logPath), content, StandardOpenOption.WRITE
					, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public static void main(String[] args) {
    	System.out.println(LocalDateTime.parse("2021-04-18T17:49:05.652573").isBefore(LocalDateTime.parse("2021-04-18T17:49:05.652572")));
    	System.out.println(LocalDateTime.parse("2021-04-18T17:49:05.652573").toString());

    }
}
