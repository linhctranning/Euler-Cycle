package EulerCycle ;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

class myDraw extends JPanel implements MouseListener , MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private myData data = new myData() ;

	private int sizeLine = 1;
	private int draw = 0 ; //draw line,point,move
	private int x = 0 , y = 0 , r = 15 , r2 = 2*r; //radius..
	//point
	private Point pointBeginLine , point ;
	//index
	private int indexPointBeginLine , indexPointEndLine , indexTemp ;
	private int indexEndPoint ;
	//check
	private boolean checkDrawLine = false  , isFindPoint = true ;
	private boolean drawResultEuler = false , drawResultEulerWay = false ,drawResultComponent = false ;
	private boolean resetGraph = false , reDraw = false;
	//Color
	private Color colorBackGround = Color.lightGray,
				colorIndex = Color.white, colorPoint = Color.black 
				;
	//Arr
	private int arrEulerCycle[] , arrEulerPath[], arrComponent[] , solt  ;
	private int sizeTop;
	public int getSolt() {
		return solt;
	}

	public void setSolt(int solt) {
		this.solt = solt;
	}

	//Result 
	private Color colorResult = Color.orange , colorIndexResult = Color.black , colorResultEulerWay = Color.green  ;
	private int sizeLineResult = 2 ;
	
	public myDraw() {
		init();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(colorBackGround);
		Graphics2D g2d = (Graphics2D) g;
		reDraw(g2d) ;
		
		if(drawResultEuler) {
			drawResultEuler(g2d);
		}
		
		if(drawResultEulerWay) {
			drawResultEulerWay(g2d) ;
		}
		
		if(drawResultComponent) {
			drawResultComponent(g2d);
		}
		
		if(reDraw) {
			reDraw(g2d);
			reDraw = false ;
		}
		
		if(resetGraph) {
			resetGraph(g2d);
			init();
			resetGraph = false ;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(isFindPoint) {
			indexPointBeginLine = indexPointContain(pointBeginLine);
			if(indexPointBeginLine > 0) {
				isFindPoint = false ;
			}
		}
		if(draw == 2 || draw == 1 || indexPointBeginLine >=0) {
			int dx = e.getX() - point.x ;
			int dy = e.getY() - point.y ;
			//movePoint
			if( (draw == 1 || draw == 3) && indexPointBeginLine > 0 ) {
				Ellipse2D.Float el = data.getArrmyPoint().get(indexPointBeginLine).getEl();
				el.x += dx ;
				el.y += dy ;
				data.getArrmyPoint().get(indexPointBeginLine).setEl(el);
			}
			//drawLine
			if(draw == 2 && indexPointBeginLine >= 0) {
				checkDrawLine = true ;
//				setindexPointA
				data.getArrmyLine().get(indexTemp).setIndexPointA(indexPointBeginLine);
				Ellipse2D.Float el = data.getArrmyPoint().get(indexTemp).getEl();
				el.x += dx ;
				el.y += dy ;
				data.getArrmyPoint().get(indexTemp).setEl(el);
			}
			updateLine();
			repaint();
			point.x +=dx;
			point.y +=dy;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(draw == 1) {
			Ellipse2D.Float el = new Ellipse2D.Float(x-r,y-r,r2,r2);
			myPoint mPoint = new myPoint(el);
			data.getArrmyPoint().add(mPoint);
			repaint();
		}
		System.out.println("Clicked"+e.getX()+" "+e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pointBeginLine = e.getPoint() ;
		point = e.getPoint() ;
		e.getPoint();
		e.getPoint();
		data.getArrmyPoint().get(indexTemp).getEl().x = e.getX();
		data.getArrmyPoint().get(indexTemp).getEl().y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		boolean drawAgaine = false ;
		if(checkDrawLine) {
			indexPointEndLine = indexPointContain(new Point(e.getX(),e.getY()));
			if(indexPointEndLine > 0) {
				isFindPoint = false ;
			}
			for (int i = 1; i < data.getArrmyLine().size(); i++) {
				myLine ml = data.getArrmyLine().get(i);
				if( (ml.getIndexPointA() == indexPointBeginLine && ml.getIndexPointB() == indexPointEndLine) ||
						(ml.getIndexPointB() == indexPointBeginLine && ml.getIndexPointA() == indexPointBeginLine)
						) {
					drawAgaine = true ;
					break;
				}
			}
			if(!drawAgaine) {
				addLineToList(indexPointBeginLine, indexPointEndLine);
			}
			checkDrawLine = false ;
		}
		//setIndexPointA
		data.getArrmyLine().get(indexTemp).setIndexPointA(
				data.getArrmyLine().get(indexTemp).getIndexPointB()
				);
		updateLine();
		repaint();
		isFindPoint = true ;
	}
	
	protected int indexPointContain(Point point) {
		for (int i = 1; i < data.getArrmyPoint().size(); i++) {
			if (data.getArrmyPoint().get(i).getEl().getBounds2D()
					.contains(point)) {
				return i;
			}
		}
		return -1;
	}

	// Add line to list line
	protected void addLineToList(int indexPointBeginLine,
			int indexPointEndLine) {
		if(indexPointEndLine > 0) {
			myLine ml = new myLine(creatLine(
					data.getArrmyPoint().get(indexPointBeginLine).getP(), data
							.getArrmyPoint().get(indexPointEndLine).getP()),
					indexPointBeginLine, indexPointEndLine);
			data.getArrmyLine().add(ml);
			repaint();
		}
		}

	public void init() {
		data.getArrmyLine().clear();
		data.getArrmyPoint().clear();
		myPoint p0 = new myPoint(new Ellipse2D.Float(50, 50, 50, 50));
		data.getArrmyPoint().add(p0);
		data.getArrmyLine().add(
				new myLine(creatLine(p0.getP(), p0.getP()), 0, 0));
	}

	private Line2D.Double creatLine(Point p1, Point p2) {
		Line2D.Double l = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
		return l;
	}

	private void updateLine() { // update location line after move point
		for (int i = 0; i < data.getArrmyLine().size(); i++) {
			data.getArrmyLine()
					.get(i)
					.setL(creatLine(
							data.getArrmyPoint()
									.get(data.getArrmyLine().get(i)
											.getIndexPointA()).getP(),
							data.getArrmyPoint()
									.get(data.getArrmyLine().get(i)
											.getIndexPointB()).getP()));
		}
	}

	public void resetGraph(Graphics2D g2d) {
		g2d.setColor(colorBackGround);
		g2d.fillRect(0, 0, 600, 600);
	}

	private void reDraw(Graphics2D g2d) {
		resetGraph(g2d);
		for (int i = 0; i < data.getArrmyLine().size(); i++) {
			data.getArrmyLine()
					.get(i)
					.drawLine(
							g2d,
							data.getArrmyPoint()
									.get(data.getArrmyLine().get(i)
											.getIndexPointA()).getP(),
							data.getArrmyPoint()
									.get(data.getArrmyLine().get(i)
											.getIndexPointB()).getP(),
							 colorPoint, sizeLine);
		}

		// draw point
		for (int i = 1; i < data.getArrmyPoint().size(); i++) {
			data.getArrmyPoint().get(i).draw(g2d, i, colorPoint, colorIndex);
		}
	}

	public void drawResultEuler(Graphics2D g2d) {
		for (int i= indexEndPoint; i > 0; i--) {
			if(arrEulerCycle[i] != 0) {
				myLine ml = new myLine(creatLine(data.getArrmyPoint().get(arrEulerCycle[i])
						.getP(), data.getArrmyPoint().get(arrEulerCycle[i+1]).getP()), arrEulerCycle[i] ,arrEulerCycle[i+1]);

				ml.drawLine(g2d, data.getArrmyPoint().get(arrEulerCycle[i]).getP(), data
						.getArrmyPoint().get(arrEulerCycle[i+1]).getP(), colorResult,
						sizeLineResult);
				
				data.getArrmyPoint().get(arrEulerCycle[i])
				.drawResult(g2d,arrEulerCycle[i], colorResult, colorIndexResult);
			}
		}
	}
	
	public void drawResultEulerWay(Graphics2D g2d) {
		for (int i = indexEndPoint+1; i > 0; i--) {
			if(arrEulerPath[i] != 0) {
				myLine ml = new myLine(creatLine(data.getArrmyPoint().get(arrEulerPath[i])
						.getP(), data.getArrmyPoint().get(arrEulerPath[i+1]).getP()), arrEulerPath[i] ,arrEulerPath[i+1]);

				ml.drawLine(g2d, data.getArrmyPoint().get(arrEulerPath[i]).getP(), data
						.getArrmyPoint().get(arrEulerPath[i+1]).getP(), colorResultEulerWay,
						sizeLineResult);
				
				data.getArrmyPoint().get(arrEulerPath[i])
				.drawResult(g2d,arrEulerPath[i], colorResultEulerWay, colorIndexResult);
			}
		}
	}

	/*
	public void drawResultComponent(Graphics2D g2d) {
		 for (int i = 1; i <=solt; i++) {
			for (int j = 1; j <=sizeTop-1; j++) {
				Random rand = new Random();
				int redValue = rand.nextInt(20 * j) ;
				int greenValue = rand.nextInt(20 * j);
				int blueValue = rand.nextInt(20 * j);
				Color clr = new Color(redValue, greenValue, blueValue);
				if( arrComponent[j] == i ) {
						if( ( arrComponent[j+1] == i) ) {
							myLine ml = new myLine(creatLine(data.getArrmyPoint().get(j)
									.getP(), data.getArrmyPoint().get(j+1).getP()), j,j+1);
							
							ml.drawLine(g2d, data.getArrmyPoint().get(j).getP(), data
										.getArrmyPoint().get(j+1).getP(), clr,
											sizeLineResult);
						}
						data.getArrmyPoint().get(j)
						.drawResult(g2d,j, clr, Color.white);
				}
			}
	}
}
	*/
	public void drawResultComponent(Graphics2D g2d) {
		 for (int i = 1; i <=solt; i++) {
			 Random rand = new Random();
			 int redValue = rand.nextInt(50 * i) ;
			 int greenValue = rand.nextInt(50 * i);
			 int blueValue = rand.nextInt(50 * i);
			 Color clr = new Color(redValue, greenValue, blueValue);
			for (int j = 1; j <=sizeTop-1; j++) {
				if( arrComponent[j] == i ) {
						if( ( arrComponent[j+1] == i) ) {
							myLine ml = new myLine(creatLine(data.getArrmyPoint().get(j)
									.getP(), data.getArrmyPoint().get(j+1).getP()), j,j+1);
							
							ml.drawLine(g2d, data.getArrmyPoint().get(j).getP(), data
										.getArrmyPoint().get(j+1).getP(), clr,
											sizeLineResult);
						}
						data.getArrmyPoint().get(j)
						.drawResult(g2d,j, clr, Color.white);
				}
			}
	}
}
	
	public void write(String path) {
		try {
			path  += ".inp";
			FileOutputStream f = new FileOutputStream(path);
			ObjectOutputStream oStream = new ObjectOutputStream(f);
			oStream.writeObject(data);
			oStream.close();
			JOptionPane.showMessageDialog(null, "Save Graph", "Save Graph",JOptionPane.INFORMATION_MESSAGE );
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Save", "Error Save", JOptionPane.OK_OPTION);
		}
	}
	
	public void readFile(String path) {
		myData data = null ;
		try {
			FileInputStream fi = new FileInputStream(path);
			ObjectInputStream oiStream = new ObjectInputStream(fi);
			data = (myData) oiStream.readObject();
			oiStream.close();
			this.data = data ;
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Open Graph", "Error Open Graph", JOptionPane.OK_OPTION);
		}
	}
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public myData getData() {
		return data;
	}

	public void setData(myData data) {
		this.data = data;
	}

	public int getIndexEndPoint() {
		return indexEndPoint;
	}

	public void setIndexEndPoint(int indexEndPoint) {
		this.indexEndPoint = indexEndPoint;
	}

	public boolean isDrawResultEuler() {
		return drawResultEuler;
	}

	public void setDrawResultEuler(boolean drawResultEuler) {
		this.drawResultEuler = drawResultEuler;
	}

	public int[] getArrEulerCycle() {
		return arrEulerCycle;
	}

	public void setArrEulerCycle(int arrEulerCycle[]) {
		this.arrEulerCycle = arrEulerCycle;
	}

	public boolean isDrawResultEulerWay() {
		return drawResultEulerWay;
	}

	public void setDrawResultEulerWay(boolean drawResultEulerWay) {
		this.drawResultEulerWay = drawResultEulerWay;
	}

	public int[] getarrEulerPath() {
		return arrEulerPath;
	}

	public void setarrEulerPath(int arrEulerPath[]) {
		this.arrEulerPath = arrEulerPath;
	}

	public boolean isResetGraph() {
		return resetGraph;
	}

	public void setResetGraph(boolean resetGraph) {
		this.resetGraph = resetGraph;
	}

	public boolean isReDraw() {
		return reDraw;
	}

	public void setReDraw(boolean reDraw) {
		this.reDraw = reDraw;
	}

	public int[] getArrComponent() {
		return arrComponent;
	}

	public void setArrComponent(int arrComponent[]) {
		this.arrComponent = arrComponent;
	}

	public boolean isDrawResultComponent() {
		return drawResultComponent;
	}

	public void setDrawResultComponent(boolean drawResultComponent) {
		this.drawResultComponent = drawResultComponent;
	}

	public int getSizeTop() {
		return sizeTop;
	}

	public void setSizeTop(int sizeTop) {
		this.sizeTop = sizeTop;
	}
	
}