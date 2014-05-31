package main.indexer.shared.communication.results;

import java.util.List;

import main.indexer.shared.models.Field;

/**
 * A class with the fields that is returned from getFields.
 *
 */
public class GetFields_Result extends Result{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Field> fields;

	/**
	 * @return the fields
	 */
	public List<Field> getFields(){
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields){
		this.fields = fields;
	}

	@Override
	public String toString(){
		String ret = "";
		for(Field field : fields){
			ret += field.getProjectId() + "\n" + field.getId() + "\n"
					+ field.getTitle() + "\n";
		}
		return ret;
	}
	
	
	
}
