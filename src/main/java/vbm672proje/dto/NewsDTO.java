package vbm672proje.dto;

public class NewsDTO {
	
	
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int d) {
		this.newsId = d;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Boolean getIsShared() {
		return isShared;
	}
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}
	private int newsId;
	private int categoryId;
	private String header;
	private String body;
	private Boolean isShared;

}
