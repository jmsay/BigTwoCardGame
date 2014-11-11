package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Menu extends JPanel {
	private JButton play,credits, help, quit, back, mode, ai, color, startButton, mm, createGame, joinGame, nextButton;
	private JLabel menu, nameplate, howToPlayFrame, settingsFrame, creditsFrame, aiDifficulty, gameConnectionPanel;
	
	private JLabel notProceed, statusLabel, availableServersLabel;
	private JTextField playerNameTextField, ipAddressTextField, serverNameTextField, portTextField;
	private JButton connectionButton, createButton, joinButton;
	private ArrayList<JLabel> playersLabelList;
	private ArrayList<JButton> servers;
	
	
	private ImageIcon menuBG = new ImageIcon("images/bg/bg.gif");
	private ImageIcon playImage = new ImageIcon("images/buttons/play.gif");
	private ImageIcon creditsImage = new ImageIcon("images/buttons/credits.gif");
	private ImageIcon helpImage = new ImageIcon("images/buttons/help.gif");
	private ImageIcon quitImage = new ImageIcon("images/buttons/quit.gif");
	private ImageIcon color1Image = new ImageIcon("images/buttons/black.png");
	private ImageIcon color2Image = new ImageIcon("images/buttons/white.png");
	private ImageIcon ai1Image = new ImageIcon("images/buttons/Ea.png");
	private ImageIcon ai2Image = new ImageIcon("images/buttons/Mo.png");
	private ImageIcon ai3Image = new ImageIcon("images/buttons/Ha.png");
	private ImageIcon minMax = new ImageIcon("images/buttons/minmax.png");
	private ImageIcon minMaxAB = new ImageIcon("images/buttons/minmaxWithAB.png");
	
	
	private ImageIcon notReadyImage = new ImageIcon("images/labels/notReady.png");
	private ImageIcon hostNotFoundImage  = new ImageIcon("images/labels/hostNotFound.png");
	private ImageIcon hostConnectedImage = new ImageIcon("images/labels/hostConnected.png");
	private ImageIcon disconnectImage = new ImageIcon("images/buttons/disconnect.png");
	private ImageIcon hostDisconnectedImage = new ImageIcon("images/labels/disconnected.png");
	private ImageIcon connectImage = new ImageIcon("images/buttons/connect.png");
	private ImageIcon noAvailableServers = new ImageIcon("images/labels/noAvailableServers.png");
	

	
	
	
	public Menu(){
		setLayout(null);
		setBounds(0, 0, 1000, 600);
		setBackground(Color.WHITE);
		initClass();
		initComponents();
		hideAll();
	}
	private void initClass(){
		menu = new JLabel(new ImageIcon("images/bg/mmbc.gif"));
		nameplate = new JLabel(new ImageIcon("na/images/bg/link369.png"));
		howToPlayFrame = new JLabel(new ImageIcon("images/bg/howToPlayFrame.png"));
		creditsFrame = new JLabel(new ImageIcon("images/bg/creditsFrame.png"));
		settingsFrame = new JLabel(new ImageIcon("images/bg/settingsFrame.png"));
		availableServersLabel = new JLabel(new ImageIcon("images/labels/availableServers.png"));
		gameConnectionPanel = new JLabel(new ImageIcon("images/bg/gameConnection.png"));
		playerNameTextField = new JTextField("Player Name");
		ipAddressTextField = new JTextField("host");
		serverNameTextField = new JTextField("Server Name");
		portTextField = new JTextField("Port Number");
		
		connectionButton = new JButton(connectImage);
		createButton = new JButton(new ImageIcon("images/buttons/create.png"));
		joinButton = new JButton(new ImageIcon("images/buttons/join.png"));
		aiDifficulty = new JLabel(new ImageIcon("images/bg/AI.png"));
		notProceed = new JLabel(new ImageIcon("images/labels/notProceed.png"));
		statusLabel = new JLabel(new ImageIcon("images/labels/notReady.png"));
		
		
		playersLabelList = new ArrayList<JLabel>();
		playersLabelList.add(new JLabel());
		playersLabelList.add(new JLabel());
		playersLabelList.add(new JLabel());
		playersLabelList.add(new JLabel());
		
		servers = new ArrayList<JButton>();
		
		back = new JButton(new ImageIcon("images/buttons/back.png"));
		startButton = new JButton(new ImageIcon("images/buttons/start.png"));
		nextButton = new JButton(new ImageIcon("images/buttons/next.png"));
		play = new JButton();
		help = new JButton();
		credits = new JButton();
		quit = new JButton();		
		mode = new JButton();
		color = new JButton();
		ai = new JButton();
		mm = new JButton();
		createGame = new JButton(new ImageIcon("images/buttons/createGame.png"));
		joinGame = new JButton(new ImageIcon("images/buttons/joinGame.png"));
	}

	private void initComponents(){
		menu.setBounds(0, 0, 1000, 600);
		add(menu);
		
		nameplate.setBounds(100,100,768,236);
		menu.add(nameplate);
		
		howToPlayFrame.setBounds(78,66,912,535);
		menu.add(howToPlayFrame);
		

		gameConnectionPanel.setBounds(0,0,1000,600);
		menu.add(gameConnectionPanel);
		
		creditsFrame.setBounds(89,62,738,538);	
		menu.add(creditsFrame);	
		
		back.setBounds(24, 342,150,150);
		menu.add(back);	
		
		play.setBounds(187, 342,150,150);
		play.setIcon(playImage);
		menu.add(play);		
		
		help.setBounds(350, 342,150,150);
		help.setIcon(helpImage);
		menu.add(help);
		
		credits.setBounds(510, 342,150,150);
		credits.setIcon(creditsImage);
		menu.add(credits);
		
		quit.setBounds(670, 342,150,150);
		quit.setIcon(quitImage);
		menu.add(quit);		

		settingsFrame.setBounds(12,68,966,266);
		menu.add(settingsFrame);
		
		availableServersLabel.setBounds(550, 250, 300, 80);;
		menu.add(availableServersLabel, 0);
		
		
		ipAddressTextField.setBounds(180, 200, 150, 50);
		menu.add(ipAddressTextField, 0);

		serverNameTextField.setBounds(180, 200, 150, 50);
		menu.add(serverNameTextField, 0 );
		
		portTextField.setBounds(180, 300, 150, 50);
		menu.add(portTextField, 0);
		
		playerNameTextField.setBounds(180, 400, 150, 50);
		menu.add(playerNameTextField, 0);
		
		
		
		connectionButton.setBounds(180, 330, 150, 50);
		menu.add(connectionButton);
		
		createButton.setBounds(370, 500, 80, 30);
		menu.add(createButton, 0);
		
		joinButton.setBounds(860, 500, 80, 30);
		menu.add(joinButton, 0);
		
		statusLabel.setBounds(180, 400, 150, 150);
		statusLabel.setIcon(hostDisconnectedImage);
		menu.add(statusLabel, 0);
		
		
		createGame.setBounds(50, 50, 400, 100);
		menu.add(createGame, 0);
		
		joinGame.setBounds(550, 50, 400, 100);
		menu.add(joinGame, 0);
		
		
		playersLabelList.get(0).setBounds(410, 110, 150, 300);
		menu.add(playersLabelList.get(0), 0);
		playersLabelList.get(1).setBounds(410, 140, 150, 300);
		menu.add(playersLabelList.get(1), 0);
		playersLabelList.get(2).setBounds(410, 170, 150, 300);
		menu.add(playersLabelList.get(2), 0);
		playersLabelList.get(3).setBounds(410, 200, 150, 300);
		menu.add(playersLabelList.get(3), 0);
		
		
		for(int i = 0; i < servers.size(); i++){
			servers.get(i).setBounds(550, 200 + i*50, 150, 300);
		}
		
		
		color.setBounds(670, 180,150,150);
		color.setIcon(color1Image);
		menu.add(color);
		
		mm.setBounds(670, 342,150,150);
		mm.setIcon(minMax);
		menu.add(mm);
		
		startButton.setBounds(830, 342,150,150);
		menu.add(startButton);	
		
		nextButton.setBounds(830, 342,150,150);
		menu.add(nextButton, 0);	
		

		notProceed.setBounds(830, 342, 150, 150);
		menu.add(notProceed);
		
		
		
		ai.setBounds(350, 342,150,150);
		ai.setIcon(ai1Image);
		menu.add(ai);		
		
		aiDifficulty.setBounds(187+15, 342-150,729,254);
		menu.add(aiDifficulty);
		
		
	}
	public void hideAIDifficulty(){
		ai.setVisible(false);
		aiDifficulty.setVisible(false);
		color.setVisible(false);
		mm.setVisible(false);
	}
	public void hideAll(){
		hideMenu();
		howToPlayFrame.setVisible(false);
		gameConnectionPanel.setVisible(false);
		creditsFrame.setVisible(false);
		settingsFrame.setVisible(false);
		availableServersLabel.setVisible(false);
		playerNameTextField.setVisible(false);
		ipAddressTextField.setVisible(false);
		
		portTextField.setVisible(false);
		serverNameTextField.setVisible(false);
		
		connectionButton.setVisible(false);
		createButton.setVisible(false);
		joinButton.setVisible(false);
		mode.setVisible(false);
		ai.setVisible(false);
		back.setVisible(false);
		startButton.setVisible(false);
		nextButton.setVisible(false);
		notProceed.setVisible(false);
		statusLabel.setVisible(false);
		
		
		playersLabelList.get(0).setVisible(false);
		playersLabelList.get(1).setVisible(false);
		playersLabelList.get(2).setVisible(false);
		playersLabelList.get(3).setVisible(false);
		
		createGame.setVisible(false);
		joinGame.setVisible(false);
		hideAIDifficulty();
	}
	public void hideMenu(){
		nameplate.setVisible(false);
		play.setVisible(false);
		help.setVisible(false);
		credits.setVisible(false);
		quit.setVisible(false);
	}
	public void setHelp(){
		hideMenu();
		howToPlayFrame.setVisible(true);
		back.setVisible(true);		
	}
	public void setCredits(){
		hideMenu();
		creditsFrame.setVisible(true);
		back.setVisible(true);		
	}
	public void setSettings(){
		hideAll();
		mode.setVisible(true);
		settingsFrame.setVisible(true);
		statusLabel.setVisible(true);
	
		playersLabelList.get(0).setVisible(true);
		playersLabelList.get(1).setVisible(true);
		playersLabelList.get(2).setVisible(true);
		playersLabelList.get(3).setVisible(true);
		
		back.setVisible(true);		
		
	}
	public void setMenu(){
		menu.setIcon(menuBG);
		hideAll();
		nameplate.setVisible(true);
		play.setVisible(true);
		help.setVisible(true);
		credits.setVisible(true);
		quit.setVisible(true);
	}
	
	public void setGameConnection(){
		hideMenu();
		
		createGame.setVisible(true);
		joinGame.setVisible(true);
		gameConnectionPanel.setVisible(true);
	}
	
	public void setJoinGamePanel(){
		ipAddressTextField.setBounds(750, 200, 200, 50);
		ipAddressTextField.setVisible(true);
		ipAddressTextField.setText("Enter IP Address");
		ipAddressTextField.setEditable(true);
		serverNameTextField.setBounds(550, 200, 150, 50);
		serverNameTextField.setVisible(true);
		//serverNameTextField.setText("Enter Server's Computer Name");
		serverNameTextField.setText("MervilleProject");
		serverNameTextField.setEditable(true);
		portTextField.setBounds(550, 300, 150, 50);
		//temporary
		portTextField.setText("1234");
		portTextField.setVisible(true);
		playerNameTextField.setBounds(550, 400, 150, 50);
		playerNameTextField.setVisible(true);
		joinButton.setVisible(true);
	}
	
	public void setCreateGamePanel(){
		ipAddressTextField.setBounds(250, 200, 200, 50);
		ipAddressTextField.setVisible(true);
		ipAddressTextField.setEditable(false);
		serverNameTextField.setBounds(50, 200, 150, 50);
		serverNameTextField.setVisible(true);
		serverNameTextField.setEditable(false);
		portTextField.setBounds(50, 300, 150, 50);
		portTextField.setText("1234");
		portTextField.setVisible(true);
		playerNameTextField.setBounds(50, 400, 150, 50);
		playerNameTextField.setVisible(true);
		createButton.setVisible(true);
		
	}
	
	//getIcons
	public ImageIcon getPlayImage(){
		return playImage;
	}
	public ImageIcon getCreditsImage(){
		return creditsImage;
	}
	public ImageIcon getHelpImage(){
		return helpImage;
	}
	public ImageIcon getQuitImage(){
		return quitImage;
	}
	
	public ImageIcon getColor1Image() {
		return color1Image;
	}
	public ImageIcon getColor2Image() {
		return color2Image;
	}
	public ImageIcon getAi1Image() {
		return ai1Image;
	}
	public ImageIcon getAi2Image() {
		return ai2Image;
	}
	public ImageIcon getAi3Image() {
		return ai3Image;
	}
	public ImageIcon getMinMax() {
		return minMax;
	}
	public ImageIcon getMinMaxAB() {
		return minMaxAB;
	}
	
	//getButtons
	public JButton getPlay() {
		return play;
	}
	public JButton getCredits(){
		return credits;
	}
	public JButton getHelp(){
		return help;
	} 
	public JButton getQuit(){
		return quit;
	}
	public JButton getBack(){
		return back;
	}
	public JButton getMode(){
		return mode;
	}
	public JButton getColor(){
		return color;
	}
	public JButton getAI(){
		return ai;
	}
	public JButton getStartButton(){
		return startButton;
	}
	public JButton getConnectionButton(){
		return connectionButton;
	}
	
	public JButton getmm(){
		return mm;
	}
	public JButton getCreateGame(){
		return createGame;
	}
	public JButton getJoinGame(){
		return joinGame;
	}
	
	public JButton getCreateButton(){
		return createButton;
	}
	
	public JButton getJoinButton(){
		return joinButton;
	}
	
	//getLabels
	public JLabel getMenu() {
		return menu;
	}

	public JLabel getNotProceed(){
		return notProceed;
	}
	
	public JLabel getStatusLabel(){
		return statusLabel;
	}
	
	
	public ArrayList<JLabel> getPlayersLabelList(){
		return playersLabelList;
	}
	
	public ImageIcon getNotReadyImage() {
		return notReadyImage;
	}
	public ImageIcon getHostNotFoundImage() {
		// TODO Auto-generated method stub
		return hostNotFoundImage;
	}
	public ImageIcon getHostConnectedImage() {
		// TODO Auto-generated method stub
		return hostConnectedImage;
	}
	public ImageIcon getDisconnectImage() {
		// TODO Auto-generated method stub
		return disconnectImage;
	}
	public ImageIcon getHostDisconnectedImage() {
		// TODO Auto-generated method stub
		return hostDisconnectedImage;
	}
	public ImageIcon getConnectImage() {
		// TODO Auto-generated method stub
		return connectImage;
	}
	
	public JTextField getHostTextField(){
		return ipAddressTextField;
	}
	public JTextField getNameTextField(){
		return playerNameTextField;
	}
	public JTextField getPortTextField(){
		return portTextField;
	}
	public JButton getNextButton(){
		return nextButton;
	}
	
	public JTextField getServerNameTextField(){
		return serverNameTextField;
	}
	public JTextField getIPAddressTextField() {
		// TODO Auto-generated method stub
		return ipAddressTextField;
	}
}
