package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import main.indexer.client.menus.IndexerButtonMenu;
import main.indexer.client.menus.IndexerMenu;
import main.indexer.client.menus.IndexerButtonMenu.ButtonMenuListener;
import main.indexer.client.menus.IndexerMenu.MenuListener;
import main.indexer.client.popups.DownloadBatchWindow;
import main.indexer.client.popups.LoginWindow;
import main.indexer.client.popups.ViewSampleWindow;
import main.indexer.client.popups.DownloadBatchWindow.DownloadBatchWindowListener;
import main.indexer.client.popups.LoginWindow.LoginListener;
import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.DownloadBatch_Params;
import main.indexer.shared.communication.params.GetProjects_Params;
import main.indexer.shared.communication.params.GetSampleImage_Params;
import main.indexer.shared.communication.results.GetProjects_Result;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Project;
import main.indexer.shared.models.User;

public class IndexerGUI extends JFrame implements LoginListener, MenuListener, ButtonMenuListener, DownloadBatchWindowListener{

	private static final long serialVersionUID = 1L;
	
	private LoginWindow login;
	private IndexerMenu menu;
	private IndexerButtonMenu buttonToolBar;
	private ImageViewer imageViewer;
	private IndexerFooter footer;
	
	User user;
	
	//Creation
	
	public IndexerGUI(String title){
		super(title);
		this.createComponents();
	}
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		
		menu = new IndexerMenu();
		this.setJMenuBar(menu);
		menu.addListener(this);
		
		buttonToolBar = new IndexerButtonMenu();
		this.add(buttonToolBar, BorderLayout.NORTH);
		buttonToolBar.addListener(this);
		
		imageViewer = new ImageViewer();
		//this.add(imageViewer, BorderLayout.CENTER);
	
		footer = new IndexerFooter();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,imageViewer,footer);
		splitPane.setDividerLocation(400);
		this.add(splitPane, BorderLayout.CENTER);
		
		
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
	
	@Override
	public void login(User user){
		this.user = user;
		this.setVisible(true);
	}
	
	@Override
	public void requestBatch(){
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		GetProjects_Result result = ClientCommunicator.getInstance()
				.getProjects(params);
		DownloadBatchWindow window = new DownloadBatchWindow("Web Browser",
				result.getProjects());
		window.addListener(this);
		window.setVisible(true);
	}
	
	@Override
	public void logout(){
		this.setVisible(false);
		displayLogin();
	}
	
	//ButtonMenuListener Methods
	
	@Override
	public void zoomIn(){}

	@Override
	public void zoomOut(){}

	@Override
	public void invertImage(){}

	@Override
	public void toggleHighlight(){}

	@Override
	public void save(){}

	@Override
	public void submit(){}
	
	//DownloadBatchWindowListener Methods
	
	@Override
	public void downloadSampleImage(Project project){
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(project.getId());
		String url = ClientCommunicator.getInstance().getSampleImage(params).getUrl();
	    ViewSampleWindow window = new ViewSampleWindow("Sample Image from " + project.getTitle(), url);
	    window.setVisible(true);
	}
	
	@Override
	public void downloadBatch(int projectId){
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(projectId);
		Batch batch = ClientCommunicator.getInstance().downloadBatch(params).getBatch();
		byte[] image = ClientCommunicator.getInstance().downloadFile(batch.getImageURL());
		buttonToolBar.setEnabled(true);
		footer.setBatch(batch,image);
		imageViewer.setBatch(batch,image);
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };

}
