package EulerCycle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class myFrame extends JFrame implements ActionListener{

	/**
	 * Title : EulerCycle
	 * Author: Quach Vu Linh B1505888	
	 */
	private static final long serialVersionUID = 1L;
	
	myEuler Euler ;
	//draw 
	private JPanel drawPanel = new JPanel() ;
	private JButton btnRun , btnFind , btnNew , btnPoint , btnLine , btnMove , btnSave , btnOpen ;
	//graph
	private myDraw mdraw = new myDraw() ;
	//log
	private JTextArea textLog ;
	//width , hieght
	int WIDTH ,HEIGHT ; 
	
	public myFrame(String title) {
		setTitle(title);
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout(5,5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add content
		add(createSelectPanel(),BorderLayout.WEST) ;
		add(createPaintPanel(),BorderLayout.CENTER) ;
		add(createLogPanel(),BorderLayout.PAGE_END) ;
 		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel createSelectPanel() {
		JPanel panel = new JPanel(new BorderLayout()) ;
		JPanel panelTop = new JPanel(new GridLayout(4,1,5,5));
		
		JPanel panelRunTemp = new JPanel(new GridLayout(1,2,15,5));
		panelRunTemp.setBorder(new EmptyBorder(0,25,0,40));
		panelRunTemp.add(btnRun = createButton("Run")) ;
		JPanel panelRun = new JPanel(new BorderLayout());
		panelRun.setBorder(new TitledBorder("Euler Cycle"));
		panelRun.add(panelRunTemp) ;
		
		JPanel panelConnectTemp = new JPanel(new GridLayout(1,2,15,5)) ;
		panelConnectTemp.setBorder(new EmptyBorder(0,25,0,40));
		panelConnectTemp.add(btnFind=createButton("Find")) ;
		JPanel panelConnect = new JPanel(new BorderLayout()) ;
		panelConnect.setBorder(new TitledBorder("Connect Component"));
		panelConnect.add(panelConnectTemp);
		
		panelTop.add(panelRun) ;
		panelTop.add(panelConnect);
		
		panel.add(panelTop,BorderLayout.PAGE_START) ;
		panel.setBorder(new EmptyBorder(0,5,0,0));
		WIDTH = (int) panel.getPreferredSize().getWidth() ;
		HEIGHT = (int) panel.getPreferredSize().getHeight();
		return panel ;
	}
	
	private JPanel createPaintPanel() {
		drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.Y_AXIS));
		drawPanel.setBorder(new TitledBorder(""));
		drawPanel.setBackground(null);
		
		Icon icon ;
		String link = "/icon/" ;
		
		icon = getIcon(link + "New.png") ;
		drawPanel.add(btnNew = createButtonImage(icon, "New Graph")) ;
		
		icon = getIcon(link + "Point.png") ;
		drawPanel.add(btnPoint = createButtonImage(icon, "Draw Point")) ;
		
		icon = getIcon(link + "Line.png") ;
		drawPanel.add(btnLine = createButtonImage(icon, "Draw Line")) ;
		
		icon = getIcon(link + "Move.png") ;
		drawPanel.add(btnMove = createButtonImage(icon, "Move Point")) ;
		
		icon = getIcon(link + "Save.png") ;
		drawPanel.add(btnSave = createButtonImage(icon, "Save Graph")) ;
		
		icon = getIcon(link + "Open.png") ;
		drawPanel.add(btnOpen = createButtonImage(icon, "Open Graph")) ;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(drawPanel,BorderLayout.WEST) ;
		panel.add(mdraw,BorderLayout.CENTER);
		return panel ;
	}
	
	private JPanel createLogPanel() {
		textLog = new JTextArea("Path: ") ;
		textLog.setRows(10);
		textLog.setEditable(false);
		JScrollPane scrollPath = new JScrollPane(textLog);
//		JScrollPane scroll = new JScrollPane(tableLog = createTable() );
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Log Information"));
		panel.add(scrollPath,BorderLayout.PAGE_START);
//		panel.add(scroll,BorderLayout.CENTER) ;
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		return panel ;
	}
	
	private JButton createButton(String lable) {
		JButton button = new JButton(lable);
		button.addActionListener(this);
		return button ;
	}
	
	private JButton createButtonImage(Icon icon , String toolTip) {
		JButton button = new JButton(icon) ;
		button.setMargin(new Insets(0,0,0,0));
		button.addActionListener(this);
		button.setToolTipText(toolTip);
		return button ;
	}
	
	private ImageIcon getIcon(String link) {
		return new ImageIcon(getClass().getResource(link));
	}
	
	//Action
	
	private void actionUpdate() {
		resetEuler();
		setDrawResult(false);
		reDraw();
		clearLog();
	}
	
	private void reDraw() {
		mdraw.setReDraw(true);
		mdraw.repaint();
	}
	
	private void actionNew() {
		setDrawResult(false);
		mdraw.setResetGraph(true);
		mdraw.repaint();
		mdraw.init();
		clearLog();
	}
	
	private void actionDrawPoint() {
		mdraw.setDraw(1);
		setDrawResult(false);
	}
	
	private void actionDrawMove() {
		mdraw.setDraw(3);
		setDrawResult(false);
	}
	
	private void actionDrawLine() {
		mdraw.setDraw(2);
		setDrawResult(false);
	}
	
	private void clearLog() {
		textLog.setText("........");
	}
	
	private void setDrawResult(boolean check) {
		mdraw.setDrawResultEuler(check);
		mdraw.setDrawResultEulerWay(check);
		mdraw.setDrawResultComponent(check);
	}
	
	private void setBeginEndPoint() {
		mdraw.setIndexEndPoint(Euler.numEdge());
	}
	
	private void resetEuler() {
		Euler = new myEuler();
		Euler.setArrmyPoint(mdraw.getData().getArrmyPoint());
		Euler.setArrmyLine(mdraw.getData().getArrmyLine());
		Euler.input();
	}
	
	private void actionEuler() {
		resetEuler();
		mdraw.setDrawResultComponent(false);
		setBeginEndPoint();
		Euler.eulerCycle();
		
		mdraw.setDrawResultEuler(true);
		mdraw.setArrEulerCycle(Euler.getArrEulerCycle());
		
		mdraw.setDrawResultEulerWay(true);
		mdraw.setarrEulerPath(Euler.getarrEulerPath());
		
		textLog.setText(Euler.getShowInfo());
		mdraw.repaint();
	}
	
	private void actionFindComponentConnect() {
		resetEuler();
		mdraw.setDrawResultEuler(false);
		mdraw.setDrawResultEulerWay(false);
//		setBeginEndPoint();
		Euler.findComponentConnect();
		
		mdraw.setSizeTop(Euler.getSize());
		mdraw.setSolt(Euler.getSolt());
		mdraw.setDrawResultComponent(true);
		
		mdraw.setArrComponent(Euler.getArrComponent());
		
		
		textLog.setText(Euler.getShowInfo());
		mdraw.repaint();
	}
	
	private void actionSave() {
		resetEuler();
		
		JFileChooser fChooser = new JFileChooser();
		fChooser.setDialogTitle("Save Graph");
		int select = fChooser.showSaveDialog(this);
		
		if(select == 0) {
			String path = fChooser.getSelectedFile().getPath();
			mdraw.write(path) ;
		}
	}
	
	private void actionOpen() {
		JFileChooser fChooser = new JFileChooser();
		fChooser.setDialogTitle("Open Graph");
		int select = fChooser.showSaveDialog(this);
		
		if(select == 0) {
			String path = fChooser.getSelectedFile().getPath();
			mdraw.readFile(path) ;
			actionUpdate();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand() ;
		
		if(e.getSource() == btnNew) {
			actionNew() ;
		}
		if(e.getSource() == btnPoint) {
			actionDrawPoint();
		}
		if(e.getSource() == btnMove) {
			actionDrawMove() ;
		}
		if(e.getSource() == btnLine) {
			actionDrawLine();
		}
		if(e.getSource() == btnRun) {
			actionEuler();
		}
		if(e.getSource() == btnFind) {
			actionFindComponentConnect();
		}
		if(e.getSource() == btnSave) {
			actionSave();
		}
		if(e.getSource() == btnOpen) {
			actionOpen();
		}
	}

}
