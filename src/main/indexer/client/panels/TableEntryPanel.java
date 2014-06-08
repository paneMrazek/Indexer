package main.indexer.client.panels;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

import main.indexer.shared.models.Batch;

public class TableEntryPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public TableEntryPanel(){
		super(new FlowLayout(FlowLayout.LEFT));
	}
	
	JTable table;

	public void setBatch(Batch batch){
		Object[] columnNames = new String[batch.getFields().size() + 1];
		columnNames[0] = "Record Number";
		for(int i = 0; i < batch.getFields().size(); i++){
			columnNames[i+1] = batch.getFields().get(i).getTitle();
		}
		
		Object[][] rowData = new String[batch.getRecordNum()][batch.getFields().size()+1];
		for(int i = 0; i < batch.getRecordNum(); i++){
			rowData[i][0] = Integer.toString(i+1);
		}
		
		table = new JTable(rowData,columnNames);
		this.add(table);
		
	}
	
}
