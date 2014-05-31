package main.indexer.shared.communication.results;

/**
 * A class with the imageUrl that is returned from getSampleImage.
 *
 */
public class GetSampleImage_Result extends Result{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
