package main.indexer.client.models;

import main.indexer.shared.models.Field;

import java.util.ArrayList;
import java.util.List;

public class IndexerDataModel{
	
	private List<IndexerDataListener> listeners;
	
	public IndexerDataModel(){
		listeners = new ArrayList<>();
	}
	
	public void addListener(IndexerDataListener listener){
		listeners.add(listener);
	}
	
	public void cellSelect(int row, int col){
		if(row < 0 || col < 0)
			return;
		for(IndexerDataListener listener : listeners){
			listener.cellSelect(row,col);
		}
	}
	
	public void dataChange(int row, int col, String data){
		for(IndexerDataListener listener : listeners){
			listener.dataChange(row,col,data);
		}
	}
	
	public void updateData(String[][] recordValues,QualityChecker checker, List<Field> fields){
		for(int j = 0; j < fields.size(); j++){
            checker.fieldchange(fields.get(j).getKnownData());
			for(int i = 0; i < recordValues.length; i++){
                if(recordValues[i] != null && j < recordValues[i].length)
				    dataChange(i,j,recordValues[i][j]);
			}
		}
	}
	
	public interface IndexerDataListener{
		public void cellSelect(int row, int col);
		public void dataChange(int row, int col, String data);
	}

}
