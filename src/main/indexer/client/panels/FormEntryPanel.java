package main.indexer.client.panels;

import javax.swing.JPanel;

import main.indexer.client.panels.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;

public class FormEntryPanel extends JPanel implements IndexerDataListener{

	private static final long serialVersionUID = 1L;
	
	private IndexerDataModel model;
	
	public FormEntryPanel(IndexerDataModel model){
		super();
		this.model = model;
		model.addListener(this);
		createComponents();
	}

	private void createComponents(){
		
	}

	public void setBatch(Batch batch){}

	@Override
	public void cellSelect(int row, int col){}

	@Override
	public void dataChange(int row, int col, String data){}
	
}
