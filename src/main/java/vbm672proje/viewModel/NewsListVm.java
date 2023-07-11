package vbm672proje.viewModel;

import java.util.List;

import vbm672proje.dto.*;

public class NewsListVm {

	private List<CategoryDTO> listOfCategoryDTOs;
	
	public List<CategoryDTO> getListOfCategoryDTOs() {
		return listOfCategoryDTOs;
	}

	public void setListOfCategoryDTOs(List<CategoryDTO> listOfCategoryDTOs) {
		this.listOfCategoryDTOs = listOfCategoryDTOs;
	}



	private Boolean isCategoryAdmin;
	public Boolean getIsCategoryAdmin() {
		return isCategoryAdmin;
	}

	public void setIsCategoryAdmin(Boolean isCategoryAdmin) {
		this.isCategoryAdmin = isCategoryAdmin;
	}



	private Boolean isSiteAdmin;
	public Boolean getIsSiteAdmin() {
		return isSiteAdmin;
	}

	public void setIsSiteAdmin(Boolean isSiteAdmin) {
		this.isSiteAdmin = isSiteAdmin;
	}

	public List<NewsDTO> getListOfNewsDTOs() {
		return listOfNewsDTOs;
	}

	public void setListOfNewsDTOs(List<NewsDTO> listOfNewsDTOs) {
		this.listOfNewsDTOs = listOfNewsDTOs;
	}

 

	
	private List<NewsDTO> listOfNewsDTOs;
	private int categoryId;
public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}




private String categoryName;
public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}

private String userName;
public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}
	
	
}
