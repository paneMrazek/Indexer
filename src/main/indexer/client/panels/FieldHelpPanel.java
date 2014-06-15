package main.indexer.client.panels;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.JEditorPane;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;

public class FieldHelpPanel extends JEditorPane implements IndexerDataListener{

	private static final long serialVersionUID = 1L;
	
	private List<Field> fields;
	
	public FieldHelpPanel(IndexerDataModel model){
		super();
		this.setContentType("text/html");
		model.addListener(this);
	}

	public void setBatch(Batch batch){
		fields = batch.getFields();
	}
	
	public void removeBatch(){
		this.removeAll();
		fields = null;
		this.setText("");
	}

	@Override
	public void cellSelect(int row, int col){
		try{
			if(col >= 0)
				this.setPage(new URL(fields.get(col).getHelpFile()));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void dataChange(int row, int col, String data){}
	
}
