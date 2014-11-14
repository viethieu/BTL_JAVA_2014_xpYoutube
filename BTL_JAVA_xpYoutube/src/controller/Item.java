package controller;

public class Item {
	private String title;
	private String date;
	private String duration;
	private String viewCount;
	private String icon;
	private String link;

	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getDuration() {
		return duration;
	}

	public String getViewCount() {
		return viewCount;
	}

	public String geticon() {
		return icon;
	}
	
	public String getLink() {
		return link;
	}

	public void setdate(String date) {
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDuartion(String duration) {
		this.duration = duration;
	}

	public void seticon(String icon) {
		this.icon = icon;
	}

	public void setLink (String link) {
		this.link = link;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
}
