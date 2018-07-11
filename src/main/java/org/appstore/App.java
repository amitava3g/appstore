package org.appstore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app", catalog = "appstore")
public class App {

	@Id
	@Column(name = "id")
	private String id;
	private String date;
	private String description;
	private String type;
	
	public App() {
		
	}
	
	public App(String id, String date, String description, String type) {
		super();
		this.id = id;
		this.date = date;
		this.description = description;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
