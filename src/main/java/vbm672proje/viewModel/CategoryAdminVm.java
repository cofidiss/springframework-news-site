package vbm672proje.viewModel;

import java.util.List;

import vbm672proje.dto.*;

public class CategoryAdminVm {
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<NewsDTO> getListOfNewsDTOs() {
		return listOfNewsDTOs;
	}

	public void setListOfNewsDTOs(List<NewsDTO> listOfNewsDTOs) {
		this.listOfNewsDTOs = listOfNewsDTOs;
	}

	private String userName;
	
	private String categoryName;
		
	public List<CategoryDTO> getListOfCategoryDTOs() {
		return listOfCategoryDTOs;
	}

	public void setListOfCategoryDTOs(List<CategoryDTO> listOfCategoryDTOs) {
		this.listOfCategoryDTOs = listOfCategoryDTOs;
	}
private int categoryId;
	public int getCategoryId() {
	return categoryId;
}

public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}

	private List<NewsDTO> listOfNewsDTOs;
	private List<CategoryDTO> listOfCategoryDTOs;
}
