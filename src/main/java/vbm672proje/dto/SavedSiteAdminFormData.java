package vbm672proje.dto;

import java.util.List;

public class SavedSiteAdminFormData {
	
	public List<Integer> getDeletedRowIds() {
		return deletedRowIds;
	}
	public void setDeletedRowIds(List<Integer> deletedRowIds) {
		this.deletedRowIds = deletedRowIds;
	}
	public List<CategoryDTO> getInsertedRows() {
		return insertedRows;
	}
	public void setInsertedRows(List<CategoryDTO> insertedRows) {
		this.insertedRows = insertedRows;
	}
	public List<CategoryDTO> getUpdatedRows() {
		return updatedRows;
	}
	public void setUpdatedRows(List<CategoryDTO> updatedRows) {
		this.updatedRows = updatedRows;
	}
	private List<Integer> deletedRowIds;
	  private List<CategoryDTO> insertedRows;
	  private List<CategoryDTO> updatedRows;
	

	

}
