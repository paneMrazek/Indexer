package main.java.indexer.shared.communication.results;

public class GetSampleImage_Result extends Result{
	
	private String url;

	/**
	 * @return the url
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/** 
	 * 
	 */
	@Override
	public String toString(){
		if(isError())
			return super.toString();
		else
			return url;
	}
}
