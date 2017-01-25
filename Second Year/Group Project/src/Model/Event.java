package Model;

import java.time.LocalDateTime;

public class Event {
	private int event_id;
	private String date;
	private String name;
	private String description;
	private String location;
	private int creator_id;
	private String creator_type;
	
	public Event( String date, String name, String description, String location, int creator_id, String creator_type){
		this.date = date;
		this.name = name;
		this.description = description;
		this.location = location;
		this.creator_id = creator_id;
		this.creator_type = creator_type;
	}
	
	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}

	public String getCreator_type() {
		return creator_type;
	}

	public void setCreator_type(String creator_type) {
		this.creator_type = creator_type;
	}
}
