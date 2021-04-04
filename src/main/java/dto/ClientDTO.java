package dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mac;
	private String name;
	private String ip;
	private boolean isOn;
	private int currentImageId;
	
	public ClientDTO(String mac, String name, String ip, boolean isOn, int currentImageId) {
		super();
		this.mac = mac;
		this.name = name;
		this.ip = ip;
		this.isOn = isOn;
		this.currentImageId = currentImageId;
	}
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isOn() {
		return isOn;
	}
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public int getCurrentImageId() {
		return currentImageId;
	}

	public void setCurrentImageId(int currentImageId) {
		this.currentImageId = currentImageId;
	}
	
}
