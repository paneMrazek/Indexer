package main.indexer.client.panels;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
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
    private QualityChecker checker;
	private FormEntryInput selected;
	private JList<Integer> list;
    private List<FormEntryInput> inputs;
    private Object[][] data;
    private boolean[][] invalid;
	
	
	public FormEntryPanel(IndexerDataModel model, QualityChecker checker){
		super(JSplitPane.HORIZONTAL_SPLIT);
		inputs = new ArrayList<>();
		this.model = model;
        this.checker = checker;
        model.addListener(this);
        checker.addListener(this);
	}
	
	public Object[][] getRecordValues(){
		return data;
	}

	public void setBatch(Batch batch){
		
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
		
		for(int i = 0; i < batch.getFields().size(); i++){
            Field field = batch.getFields().get(i);
			String fieldName = field.getTitle();
			JLabel lable = new JLabel(fieldName, JLabel.TRAILING);
            panel.add(lable);
            FormEntryInput textField = new FormEntryInput(lable, i,field.getKnownData());
            textField.addFocusListener(focusAdapter);
            inputs.add(textField);
            panel.add(textField);			
		}
		
		//Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                                        batch.getFields().size(), 2, 	//rows, cols
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
            if(invalid[list.getSelectedIndex()][input.getIndex()]) {
                input.setBackground(new Color(0xFF0000));
                input.addMouseListener(mouseAdapter);
            }else{
                input.setBackground(Color.WHITE);
                if(input.getMouseListeners().length > 0)
                    input.removeMouseListener(mouseAdapter);
            }
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

    private MouseAdapter mouseAdapter = new MouseAdapter(){
        public void mouseClicked (MouseEvent e) {
            if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
                final int row = list.getSelectedIndex();
                final int col = ((FormEntryInput) e.getComponent()).getIndex();
                final String knownData = ((FormEntryInput) e.getComponent()).getKnownData();
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("See Suggestions");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checker.findSuggestions((String) data[row][col], knownData, row, col);
                    }
                });
                popup.add(menuItem);
                popup.show(e.getComponent(),e.getX(), e.getY());
            }
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
        private String knownData;

		public FormEntryInput(JLabel lable, int index, String knownData){
			lable.setLabelFor(this);
			this.setMinimumSize(new Dimension(40,20));
            this.setPreferredSize(new Dimension(80,20));
            this.setMaximumSize(new Dimension(200,20));
            this.setIndex(index);
            this.setKnownData(knownData);
		}

		public int getIndex(){
			return index;
		}
		public void setIndex(int index){
			this.index = index;
		}

        public String getKnownData() {
            return knownData;
        }
        public void setKnownData(String knownData) {
            this.knownData = knownData;
        }
    }
	
}
