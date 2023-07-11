package vbm672proje.viewModel;

import java.util.List;

import vbm672proje.dto.*;

public class SiteAdminVm {

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<CategoryDTO> getListOfCategoryDTOs() {
		return listOfCategoryDTOs;
	}
	public void setListOfCategoryDTOs(List<CategoryDTO> listOfCategoryDTOs) {
		this.listOfCategoryDTOs = listOfCategoryDTOs;
	}
	private String userName;
	private List<CategoryDTO> listOfCategoryDTOs;
	
	private List<String> listOfNonCatetegoryAdminsUserNames;

	public List<String> getListOfNonCatetegoryAdminsUserNames() {
		return listOfNonCatetegoryAdminsUserNames;
	}
	public void setListOfNonCatetegoryAdminsUserNames(List<String> listOfNonCatetegoryAdminsUserNames) {
		this.listOfNonCatetegoryAdminsUserNames = listOfNonCatetegoryAdminsUserNames;
	}


	
}
