package main.indexer.client.panels;

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
		for(IndexerDataListener listener : listeners){
			listener.cellSelect(row,col);
		}
	}
	
	public void dataChange(int row, int col, String data){
		for(IndexerDataListener listener : listeners){
			listener.dataChange(row,col,data);
		}
	}
	
	public interface IndexerDataListener{
		public void cellSelect(int row, int col);
		public void dataChange(int row, int col, String data);
	}
}
