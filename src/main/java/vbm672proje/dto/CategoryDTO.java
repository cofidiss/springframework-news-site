package vbm672proje.dto;

import java.util.List;

public class CategoryDTO {
	
	



	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getStringOfAdminsUserNames() {
		return stringOfAdminsUserNames;
	}
	public void setStringOfAdminsUserNames(String stringOfAdminsUserNames) {
		this.stringOfAdminsUserNames = stringOfAdminsUserNames;
	}
	public Integer getStringOfCategoryAdminsPersonIds() {
		return stringOfCategoryAdminsPersonIds;
	}
	public void setStringOfCategoryAdminsPersonIds(Integer stringOfCategoryAdminsPersonIds) {
		this.stringOfCategoryAdminsPersonIds = stringOfCategoryAdminsPersonIds;
	}
	private String categoryName;
	private int categoryId;
	private String stringOfAdminsUserNames;
	private Integer stringOfCategoryAdminsPersonIds;
}
