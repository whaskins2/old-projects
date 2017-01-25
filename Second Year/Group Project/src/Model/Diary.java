package Model;

public class Diary {
	private int diary_item_id;
	private String date;
	private String item_contents;
	private int creator_id;
	private String creator_type;
	
	public Diary(int diary_item_id, String date, String item_contents, int creator_id, String creator_type){
		this.diary_item_id = diary_item_id;
		this.date = date;
		this.item_contents = item_contents;
		this.creator_id = creator_id;
		this.creator_type = creator_type;
	}
	public Diary(String date, String item_contents, int creator_id, String creator_type){
		this.date = date;
		this.item_contents = item_contents;
		this.creator_id = creator_id;
		this.creator_type = creator_type;
	}
	public int getDiary_item_id() {
		return diary_item_id;
	}

	public void setDiary_item_id(int diary_item_id) {
		this.diary_item_id = diary_item_id;
	}
	
	public String getCreatorType() {
		return creator_type;
	}

	public void setCreatorType(String creator_type) {
		this.creator_type = creator_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItem_contents() {
		return item_contents;
	}

	public void setItem_contents(String item_contents) {
		this.item_contents = item_contents;
	}

	public int getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
}
