package main.indexer.client.panels;

import java.awt.Color;

import javax.swing.JPanel;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;

public class ImageNavPanel extends JPanel implements IndexerDataListener{

    public ImageNavPanel(IndexerDataModel model){
		super();
		model.addListener(this);
		this.setBackground(new Color(128,128,128));
		createComponents();
	}

	private void createComponents(){
		
	}

	public void setBatch(Batch batch){
		
	}
	
	public void removeBatch(){
		this.removeAll();
	}

	@Override
	public void cellSelect(int row, int col){}

	@Override
	public void dataChange(int row, int col, String data){}
	
}
