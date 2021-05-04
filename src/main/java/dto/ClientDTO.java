package dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String mac;
	private boolean isOn;
	private String currentIp;
	private ImageDTO currentImage;
	private String lastLoggedOn;
	private ImageDTO macDeployImage;
	
	public ClientDTO(int id, String mac, ImageDTO image) {
		super();
		this.id = id;
		this.mac = mac;
		this.currentImage = image;
		this.macDeployImage = null;
	}
	
	public ClientDTO(int id, String name, String mac, boolean isOn, String currentIp, ImageDTO currentImage) {
		super();
		this.id = id;
		this.name = name;
		this.mac = mac;
		this.isOn = isOn;
		this.currentIp = currentIp;
		this.currentImage = currentImage;
		this.macDeployImage = null;
	}

	public ImageDTO getMacDeployImage() {
		return macDeployImage;
	}

	public void setMacDeployImage(ImageDTO macDeployImage) {
		this.macDeployImage = macDeployImage;
	}

	public String getLastLoggedOn() {
		return lastLoggedOn;
	}

	public void setLastLoggedOn(String lastLoggedOn) {
		this.lastLoggedOn = lastLoggedOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageDTO getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(ImageDTO currentImage) {
		this.currentImage = currentImage;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}

	
	
}
