package main.indexer.client.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.client.models.QualityChecker;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;

public class TableEntryPanel extends JPanel implements IndexerDataListener, QualityChecker.QualityCheckerListener {
	
	private String[] columnNames;
	private Object[][] rowData;
	private IndexerDataModel model;
	
	private QualityChecker checker;
    private List<Field> fields;

    private boolean[][] invalid;

	private static final long serialVersionUID = 1L;
	
	public TableEntryPanel(IndexerDataModel model, QualityChecker checker){
		super(new BorderLayout());
		this.model = model;
		this.checker = checker;
		model.addListener(this);
        checker.addListener(this);
	}

	JTable table = new JTable();

	public void setBatch(Batch batch){
		this.fields = batch.getFields();

		columnNames = new String[batch.getFields().size() + 1];
		columnNames[0] = "Record Number";
		for(int i = 0; i < batch.getFields().size(); i++){
			columnNames[i+1] = batch.getFields().get(i).getTitle();
		}

        invalid = new boolean[batch.getRecordNum()][batch.getFields().size()+1];
        rowData = new String[batch.getRecordNum()][batch.getFields().size()+1];
		for(int i = 0; i < batch.getRecordNum(); i++){
			rowData[i][0] = Integer.toString(i+1);
		}
		
		table = new JTable(rowData,columnNames);
		table.setModel(tableModel);
		table.setCellSelectionEnabled(true);
        table.addMouseListener(mouseAdapter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 1; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(renderer);
        }
		
		this.add(table.getTableHeader(),BorderLayout.PAGE_START);
		this.add(table,BorderLayout.CENTER);
	}
	
	public void removeBatch(){
		this.removeAll();
		this.paintAll(getGraphics());
	}
	
	private AbstractTableModel tableModel = new AbstractTableModel(){
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

    private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(invalid == null)
                return c;
            if(invalid[row][column]){
                if(isSelected)
                    c.setBackground(new Color(255,57,50,128));
                else
                    c.setBackground(new Color(0xFF0000));
            }else{
                if(isSelected)
                    c.setBackground(new Color(173,216,230,225));
                else
                    c.setBackground(new Color(0xFFFFFF));
            }
            return c;
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter(){
        public void mouseClicked (MouseEvent e) {
            final int row = table.rowAtPoint(e.getPoint());
            final int col = table.columnAtPoint(e.getPoint());
            if(e.getButton() == MouseEvent.BUTTON3 && invalid[row][col]){
                final String knownData = fields.get(col-1).getKnownData();
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("See Suggestions");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checker.findSuggestions((String) rowData[row][col], knownData, row, col-1);
                    }
                });
                popup.add(menuItem);
                popup.show(e.getComponent(),e.getX(), e.getY());
            }else if(e.getButton() == MouseEvent.BUTTON1){
                model.cellSelect(row,col-1);
            }
        }
    };

	@Override
	public void cellSelect(int row, int col){
		table.changeSelection(row,col+1,false,false);
	}

	@Override
	public void dataChange(int row, int col, String data){
		rowData[row][col+1] = data;
        checker.fieldChange(this.fields.get(col).getKnownData());
        checker.isValidEntry(row, col, data);
	}

    @Override
    public void setInvalid(int row, int col, boolean invalid) {
        if(this.invalid != null){
            this.invalid[row][col + 1] = invalid;
            table.repaint();
        }
    }
}
