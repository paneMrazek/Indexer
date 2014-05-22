package main.java.indexer.shared.communication.params;

import java.util.List;

/**
 * A class with the username, password, fieldIds and searchValues used to call search.
 *
 */
public class Search_Params extends Params{
	
	private List<String> fieldId;
	private List<String> searchValues;
	/**
	 * @return the fieldId
	 */
	public List<String> getFieldId(){
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(List<String> fieldId){
		this.fieldId = fieldId;
	}
	/**
	 * @return the searchValues
	 */
	public List<String> getSearchValues(){
		return searchValues;
	}
	/**
	 * @param searchValues the searchValues to set
	 */
	public void setSearchValues(List<String> searchValues){
		this.searchValues = searchValues;
	}
	
}
