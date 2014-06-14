package main.indexer.client.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.client.models.QualityChecker;
import main.indexer.shared.models.Batch;

public class TableEntryPanel extends JPanel implements IndexerDataListener{
	
	private String[] columnNames;
	private Object[][] rowData;
	private IndexerDataModel model;
	
	QualityChecker checker;

	private static final long serialVersionUID = 1L;
	
	public TableEntryPanel(IndexerDataModel model, QualityChecker checker){
		super(new BorderLayout());
		this.model = model;
		this.checker = checker;
		model.addListener(this);
	}
	
	JTable table;

	public void setBatch(Batch batch){
		
		columnNames = new String[batch.getFields().size() + 1];
		columnNames[0] = "Record Number";
		for(int i = 0; i < batch.getFields().size(); i++){
			columnNames[i+1] = batch.getFields().get(i).getTitle();
		}
		
		rowData = new String[batch.getRecordNum()][batch.getFields().size()+1];
		for(int i = 0; i < batch.getRecordNum(); i++){
			rowData[i][0] = Integer.toString(i+1);
		}
		
		table = new JTable(rowData,columnNames);
		table.setModel(tableModel);
		
		table.setCellSelectionEnabled(true);
		table.getSelectionModel().addListSelectionListener(selectionListener);
		table.getColumnModel().getSelectionModel().addListSelectionListener(selectionListener);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.add(table.getTableHeader(),BorderLayout.PAGE_START);
		this.add(table,BorderLayout.CENTER);
	}
	
	public void removeBatch(){
		this.removeAll();
		this.paintAll(getGraphics());
	}
	
	AbstractTableModel tableModel = new AbstractTableModel(){
		private static final long serialVersionUID = 1L;

		@Override
		public Object getValueAt(int arg0, int arg1){
			return rowData[arg0][arg1];
		}
		
		@Override
		public int getRowCount(){
			return rowData.length;
		}
		
		@Override
		public int getColumnCount(){
			return columnNames.length;
		}
		
		@Override
		public boolean isCellEditable(int row, int col){
			return col >= 1;
		}
		
		@Override
		public String getColumnName(int col) {
	        return columnNames[col];
	    }
		
		@Override
		public void setValueAt(Object value, int row, int col) {
	        rowData[row][col] = value;
	        model.dataChange(row,col-1,(String) value);
	        fireTableCellUpdated(row, col);
	    }
	};
	
	private ListSelectionListener selectionListener = new ListSelectionListener(){

		@Override
		public void valueChanged(ListSelectionEvent e){
			if(!e.getValueIsAdjusting())
				model.cellSelect(table.getSelectedRow(),table.getSelectedColumn()-1);
		}
		
	};

	@Override
	public void cellSelect(int row, int col){
		table.changeSelection(row,col+1,false,false);
	}

	@Override
	public void dataChange(int row, int col, String data){
		rowData[row][col+1] = data;
	}
	
}
