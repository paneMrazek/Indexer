package main.indexer.shared.communication.params;

public class GetBatch_Params extends Params{

	private static final long serialVersionUID = 1L;
	
	private int batchId;

	public GetBatch_Params(int batchId, String username, String password){
		super(username,password);
		this.batchId = batchId;
	}

	public int getBatchId(){
		return batchId;
	}

	public void setBatchId(int batchId){
		this.batchId = batchId;
	}
	
}
