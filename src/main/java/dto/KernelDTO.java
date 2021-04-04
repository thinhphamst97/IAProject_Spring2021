package dto;

import java.io.Serializable;

public class KernelDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	
	public KernelDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
}
