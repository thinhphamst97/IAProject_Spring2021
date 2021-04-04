package dto;

import java.io.Serializable;

public class InitrdDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int imageId;
	public InitrdDTO(int id, int imageId) {
		super();
		this.id = id;
		this.imageId = imageId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
}
