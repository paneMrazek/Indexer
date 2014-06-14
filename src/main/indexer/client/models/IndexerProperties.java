package main.indexer.client.models;

import main.indexer.shared.models.Field;

import java.util.List;
import java.util.Properties;

public class IndexerProperties extends Properties{

	private static final long serialVersionUID = 1L;
	
	public int getProperty(String key, int defaultValue){
		String property = getProperty(key,"null");
		if(property.equals("null"))
			return defaultValue;
		else
			return Integer.parseInt(property);
	}
	
	public double getProperty(String key, double defaultValue){
		String property = getProperty(key,"null");
		if(property.equals("null"))
			return defaultValue;
		else
			return Double.parseDouble(property);
	}
	
	public boolean getProperty(String key, boolean defaultValue){
		String property = getProperty(key,"null");
		if(property.equals("null"))
			return defaultValue;
		else
			return Boolean.parseBoolean(property);
	}
	
	public void setProperty(String key, int value){
		this.setProperty(key,Integer.toString(value));
	}
	
	public void setProperty(String key, double value){
		this.setProperty(key,Double.toString(value));
	}

	public void setProperty(String key, boolean value){
		this.setProperty(key,Boolean.toString(value));
	}

	public void saveRecords(Object[][] recordValues){
		String valueString = "";
		for(int i = 0; i < recordValues.length; i++){
			for(int j = 0; j < recordValues[i].length; j++){
				String s = (String) recordValues[i][j];
				valueString += s == null ? "" : s;
				if(j != recordValues[i].length)
					valueString += ",";
			}
			valueString += ";";
		}
		this.setProperty("values",valueString);
	}

	public void updateValues(IndexerDataModel model,QualityChecker checker,List<Field> fields){
		String valueString = this.getProperty("values","");
		if(!valueString.equals("")){
			String[] split = valueString.split(";");
			String[][] recordValues = new String[split.length][split[0].split(",").length];
			for(int i = 0; i < split.length; i++){
				recordValues[i]	= split[i].split(",");
			}
			model.updateData(recordValues,checker,fields);
		}
	}
	
}
