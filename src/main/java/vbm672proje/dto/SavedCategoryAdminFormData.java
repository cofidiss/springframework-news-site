package vbm672proje.dto;

import java.util.List;

public class SavedCategoryAdminFormData {
	
	
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public List<Integer> getDeletedRowIds() {
		return deletedRowIds;
	}
	public void setDeletedRowIds(List<Integer> deletedRowIds) {
		this.deletedRowIds = deletedRowIds;
	}
	public List<NewsDTO> getInsertedRows() {
		return insertedRows;
	}
	public void setInsertedRows(List<NewsDTO> insertedRows) {
		this.insertedRows = insertedRows;
	}
	public List<NewsDTO> getUpdatedRows() {
		return updatedRows;
	}
	public void setUpdatedRows(List<NewsDTO> updatedRows) {
		this.updatedRows = updatedRows;
	}
	private List<Integer> deletedRowIds;
	  private List<NewsDTO> insertedRows;
	  private List<NewsDTO> updatedRows;
	  private int categoryId;

}
