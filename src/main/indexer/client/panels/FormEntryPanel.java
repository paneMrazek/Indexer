package main.indexer.client.panels;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.client.models.QualityChecker;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;

public class FormEntryPanel extends JSplitPane implements IndexerDataListener, QualityChecker.QualityCheckerListener {

	private static final long serialVersionUID = 1L;
	
	private IndexerDataModel model;
	private FormEntryInput selected;
	
	private JList<Integer> list;

    private List<FormEntryInput> inputs;

    private Object[][] data;
    private boolean[][] invalid;
	
	
	public FormEntryPanel(IndexerDataModel model, QualityChecker checker){
		super(JSplitPane.HORIZONTAL_SPLIT);
		inputs = new ArrayList<>();
		this.model = model;
        model.addListener(this);
        checker.addListener(this);
	}
	
	public Object[][] getRecordValues(){
		return data;
	}

	public void setBatch(Batch batch){
        String[] fieldNames = new String[batch.getFields().size()];
		for(int i = 0; i < batch.getFields().size(); i++){
			fieldNames[i] = batch.getFields().get(i).getTitle();
		}
		
		data = new String[batch.getRecordNum()][batch.getFields().size()];
        invalid = new boolean[batch.getRecordNum()][batch.getFields().size()];

        Integer[] recordNums = new Integer[batch.getRecordNum()];
		for(int i = 0; i < recordNums.length; i++)
			recordNums[i] = i+1;
		
		list = new JList<>(recordNums);
		list.addListSelectionListener(selectionListener);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(list);
		this.setLeftComponent(listScroller);

        JPanel panel = new JPanel(new SpringLayout());
		JScrollPane panelScroller = new JScrollPane(panel);
		
		this.setRightComponent(panelScroller);
		this.setDividerLocation(30);
		
		for(int i = 0; i < fieldNames.length; i++){
			String fieldName = fieldNames[i];
			JLabel lable = new JLabel(fieldName, JLabel.TRAILING);
            panel.add(lable);
            FormEntryInput textField = new FormEntryInput(lable, i);
            textField.addFocusListener(focusAdapter);
            inputs.add(textField);
            panel.add(textField);			
		}
		
		//Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                                        fieldNames.length, 2, 	//rows, cols
                                        20, 20,        			//initX, initY
                                        40, 20);       			//xPad, yPad
	}
	
	public void removeBatch(){
		this.removeAll();
		this.paintAll(this.getGraphics());
	}
	
	private void updateFields(){
		for(FormEntryInput input : inputs){
			String value = (String) data[list.getSelectedIndex()][input.getIndex()];
			if(data != null)
			    input.setText(value);
            if(invalid[list.getSelectedIndex()][input.getIndex()])
                input.setBackground(new Color(0xFF0000));
            else
                input.setBackground(Color.WHITE);
		}
	}
	
	private ListSelectionListener selectionListener = new ListSelectionListener(){

		@Override
		public void valueChanged(ListSelectionEvent e){
			if(!e.getValueIsAdjusting() && list != null && selected != null){
				model.cellSelect(list.getSelectedIndex(), selected.getIndex());
			}
			updateFields();
		}
		
	};
	
	private FocusAdapter focusAdapter = new FocusAdapter(){
		public void focusGained(FocusEvent e) {
			selected = (FormEntryInput) e.getSource();
			model.cellSelect(list.getSelectedIndex(), selected.getIndex());
		}
		
		public void focusLost(FocusEvent e) {
			FormEntryInput input = (FormEntryInput) e.getSource();
			model.dataChange(list.getSelectedIndex(), input.getIndex(),input.getText());
		}
	};
	

	@Override
	public void cellSelect(int row, int col){
		list.setSelectedIndex(row);
		inputs.get(col).requestFocusInWindow();
	}

	@Override
	public void dataChange(int row, int col, String data){
		this.data[row][col] = data;
		updateFields();
	}

    @Override
    public void setInvalid(int col, int row, boolean invalid) {
        this.invalid[col][row] = invalid;
    }

    private class FormEntryInput extends JTextField{
		
		private static final long serialVersionUID = 1L;
		
		private int index;

		public FormEntryInput(JLabel lable, int index){
			lable.setLabelFor(this);
			this.setMinimumSize(new Dimension(40,20));
            this.setPreferredSize(new Dimension(80,20));
            this.setMaximumSize(new Dimension(200,20));
            this.setIndex(index);
		}

		public int getIndex(){
			return index;
		}
		public void setIndex(int index){
			this.index = index;
		}
	}
	
}
