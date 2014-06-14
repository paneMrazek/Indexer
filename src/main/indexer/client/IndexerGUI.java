package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import main.indexer.client.menus.IndexerButtonMenu;
import main.indexer.client.menus.IndexerMenu;
import main.indexer.client.menus.IndexerButtonMenu.ButtonMenuListener;
import main.indexer.client.menus.IndexerMenu.MenuListener;
import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerProperties;
import main.indexer.client.models.QualityChecker;
import main.indexer.client.panels.ImageViewer;
import main.indexer.client.popups.DownloadBatchWindow;
import main.indexer.client.popups.LoginWindow;
import main.indexer.client.popups.ViewSampleWindow;
import main.indexer.client.popups.DownloadBatchWindow.DownloadBatchWindowListener;
import main.indexer.client.popups.LoginWindow.LoginListener;
import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.DownloadBatch_Params;
import main.indexer.shared.communication.params.GetBatch_Params;
import main.indexer.shared.communication.params.GetProjects_Params;
import main.indexer.shared.communication.params.GetSampleImage_Params;
import main.indexer.shared.communication.params.SubmitBatch_Params;
import main.indexer.shared.communication.results.GetProjects_Result;
import main.indexer.shared.communication.results.SubmitBatch_Result;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Project;
import main.indexer.shared.models.User;

public class IndexerGUI extends JFrame implements LoginListener, MenuListener, ButtonMenuListener, DownloadBatchWindowListener{

	private static final long serialVersionUID = 1L;
	
	private LoginWindow login;
	private IndexerMenu menu;
	private IndexerDataModel model;
    private QualityChecker checker;
	private IndexerButtonMenu buttonToolBar;
	private ImageViewer imageViewer;
	private IndexerFooter footer;
	private JSplitPane splitPane;
	private IndexerProperties properties;
	private boolean hasBatch;
	
	User user;
	
	//Creation
	
	public IndexerGUI(String title){
		super(title);
		hasBatch = false;
	}
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		
		menu = new IndexerMenu();
		this.setJMenuBar(menu);
		menu.addListener(this);
		
		buttonToolBar = new IndexerButtonMenu();
		this.add(buttonToolBar, BorderLayout.NORTH);
		buttonToolBar.addListener(this);
		
		model = new IndexerDataModel();
        checker = new QualityChecker();
		
		imageViewer = new ImageViewer(model);	
		footer = new IndexerFooter(model, checker);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,imageViewer,footer);
		splitPane.setDividerLocation(properties.getProperty("verticalSplitPosition",400));
		this.add(splitPane, BorderLayout.CENTER);
		
		footer.setDividerLocation(properties.getProperty("horizontalSplitPosition",600));
		
		int width = (int) properties.getProperty("guiWidth",
				Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		int height = (int) properties.getProperty("guiHeight",
				Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		this.setSize(width,height);
		
		int x = properties.getProperty("guiX",0);
		int y = properties.getProperty("guiY",0);
		
		this.setLocation(x,y);
	}

	//LoginWindow at start and at logout menu call
	public void start(){
		displayLogin();
	}
	
	private void displayLogin(){
		login = new LoginWindow("Login to Indexer");
		login.setVisible(true);
		login.addListener(this);
	}
	
	public void saveState(){
		if(this.hasBatch){
			properties = new IndexerProperties();
			properties.setProperty("batchId",imageViewer.getBatch().getId());
			properties.setProperty("scale",imageViewer.getScale());
			properties.saveRecords(footer.getRecordValues());
		}
		properties.setProperty("horizontalSplitPosition",footer.getDividerLocation());
		properties.setProperty("verticalSplitPosition",splitPane.getDividerLocation());
		properties.setProperty("guiHeight",this.getHeight());
		properties.setProperty("guiWidth",this.getWidth());
		properties.setProperty("guiX",this.getX());
		properties.setProperty("guiY",this.getY());
		properties.setProperty("inverted",imageViewer.isInverted());
		properties.setProperty("highlight",imageViewer.isHighlightOn());
		try{
			properties.store(new PrintWriter(new File("Users/" + user.getUserName() + ".properties")),"");
		}catch(IOException e){
			e.printStackTrace();
		};
	}
	
	@Override
	public void login(User user){
		properties = new IndexerProperties();
		try{
			properties.load(new FileInputStream(new File("Users/" + user.getUserName() + ".properties")));
		}catch(FileNotFoundException e){
			
		}catch(IOException e){
			e.printStackTrace();
		}
		this.createComponents();
		this.user = user;
		this.setVisible(true);
		int batchId = properties.getProperty("batchId",-1);
		Batch batch;
		if(batchId != -1){
			hasBatch = true;
			batch = ClientCommunicator.getInstance().getBatch(new GetBatch_Params(batchId,
					user.getUserName(),user.getPassword())).getBatch();
			imageViewer.setScale(properties.getProperty("scale",0.7));
			imageViewer.setInverted(properties.getProperty("inverted",false));
			imageViewer.setHighlightOn(properties.getProperty("highlight",true));
			imageViewer.setBatch(batch);
			buttonToolBar.setEnabled(true);
			footer.setBatch(batch);
			imageViewer.setBatch(batch);
			menu.setHasBatch(true);
			properties.updateValues(model,checker,batch.getFields());
		}
	}
	
	@Override
	public void requestBatch(){
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		GetProjects_Result result = ClientCommunicator.getInstance()
				.getProjects(params);
		DownloadBatchWindow window = new DownloadBatchWindow(this,"Web Browser",
				result.getProjects());
		window.addListener(this);
		window.setVisible(true);
	}
	
	@Override
	public void logout(){
		saveState();
		this.setVisible(false);
		displayLogin();
	}
	
	@Override
	public void exit(){
		saveState();
		System.exit(0);
	}
	
	//ButtonMenuListener Methods
	
	@Override
	public void zoomIn(){
		imageViewer.zoomIn();
	}

	@Override
	public void zoomOut(){
		imageViewer.zoomOut();
	}

	@Override
	public void invertImage(){
		imageViewer.invertImage();
	}

	@Override
	public void toggleHighlight(){
		imageViewer.toggleHighlight();
	}

	@Override
	public void save(){
		saveState();
	}

	@Override
	public void submit(){
		SubmitBatch_Params params = new SubmitBatch_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		List<String[]> values = new ArrayList<>();
		for(Object[] record : footer.getRecordValues()){
			values.add((String[]) record);
		}
		params.setRecordValues(values);
		params.setBatchId(imageViewer.getBatch().getId());
		SubmitBatch_Result result = ClientCommunicator.getInstance().submitBatch(params);
		if(result.isError()){
			JOptionPane.showMessageDialog(this, 
					"There was an error submitting your batch",
					"Submit Batch Failed",JOptionPane.ERROR_MESSAGE);
		}else{
			hasBatch = false;
			imageViewer.removeBatch();
			buttonToolBar.setEnabled(false);
			footer.removeBatch();
			imageViewer.removeBatch();
			menu.setHasBatch(false);
		}
	}
	
	//DownloadBatchWindowListener Methods
	
	@Override
	public void downloadSampleImage(Project project){
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(project.getId());
		String url = ClientCommunicator.getInstance().getSampleImage(params).getUrl();
	    ViewSampleWindow window = new ViewSampleWindow(this,"Sample Image from " + project.getTitle(), url);
	    window.setVisible(true);
	}
	
	@Override
	public void downloadBatch(int projectId){
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(projectId);
		Batch batch = ClientCommunicator.getInstance().downloadBatch(params).getBatch();
		buttonToolBar.setEnabled(true);
		footer.setBatch(batch);
		imageViewer.setBatch(batch);
		hasBatch = true;
		menu.setHasBatch(hasBatch);
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
        	saveState();
            System.exit(0);
        }
    };

}
