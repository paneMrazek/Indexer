package main.java.indexer.shared.communication.params;

/**
 * A class with the username, password, fieldIds and searchValues used to call search.
 *
 */
public class Search_Params extends Params{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] fieldId;
	private String[] searchValues;
	/**
	 * @return the fieldId
	 */
	public String[] getFieldId(){
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String[] fieldId){
		this.fieldId = fieldId;
	}
	/**
	 * @return the searchValues
	 */
	public String[] getSearchValues(){
		return searchValues;
	}
	/**
	 * @param searchValues the searchValues to set
	 */
	public void setSearchValues(String[] searchValues){
		this.searchValues = searchValues;
	}
	
}
