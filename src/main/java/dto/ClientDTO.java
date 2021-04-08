package dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String mac;
	private ImageDTO image;
	private boolean isOn;
	private String currentIp;
	private String currentImageName;
	public ClientDTO(int id, String mac, ImageDTO image) {
		super();
		this.id = id;
		this.mac = mac;
		this.image = image;
	}
	
	public ClientDTO(int id, String mac, boolean isOn, String currentImageName) {
		super();
		this.id = id;
		this.mac = mac;
		this.isOn = isOn;
		this.currentImageName = currentImageName;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	public String getCurrentImageName() {
		return currentImageName;
	}

	public void setCurrentImageName(String currentImageName) {
		this.currentImageName = currentImageName;
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
	public ImageDTO getImage() {
		return image;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}

	
	
}
