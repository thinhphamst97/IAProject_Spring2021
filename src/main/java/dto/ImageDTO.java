package dto;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class ImageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String name = null;
	private String type = null;
	private float size = -1;
	private String description = null;
	private boolean isActive = false;
	private Date dateCreated = null;
	private KernelDTO kernel = null;

	public ImageDTO(int id, String name, String type, float size, String description, boolean isActive, Date dateCreated,
			KernelDTO kernel) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.description = description;
		this.isActive = isActive;
		this.dateCreated = dateCreated;
		this.kernel = kernel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public KernelDTO getKernel() {
		return kernel;
	}

	public void setKernel(KernelDTO kernel) {
		this.kernel = kernel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {
		LocalDate now = LocalDate.now();
		System.out.println(Date.valueOf(now).toString());
	}

}
