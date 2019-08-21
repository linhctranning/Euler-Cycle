package EulerCycle;

import java.util.ArrayList;

import org.w3c.dom.traversal.NodeIterator;


public class myEuler {
	private int a[][];
	private int size=0;//numtop
	private ArrayList<myPoint> arrmyPoint = new ArrayList<myPoint>();
	private ArrayList<myLine> arrmyLine = new ArrayList<myLine>();
	private int Queue[] , arrNotConsider[] ;
	private final int  MAX  = 50 ;
	private int solt ; //numComponentConnected
	public int getSolt() {
		return solt;
	}

	public void setSolt(int solt) {
		this.solt = solt;
	}

	private int topOdd ;
	private String showInfo = "";
	private int arrEulerCycle[] ,  arrEulerPath[] , arrComponent[] ;
	
	public myEuler() {
	}
	
	public void input() {
		size = arrmyPoint.size() ; 
		arrNotConsider = new int[size];
		Queue = new int[size] ;
		a = new int[size][size];
		arrEulerCycle = new int[MAX];
		arrEulerPath = new int[MAX] ;
		arrComponent = new int[MAX] ;
		for (int i = 1; i < arrmyLine.size(); i++) {
			a[arrmyLine.get(i).getIndexPointA()][arrmyLine.get(i).getIndexPointB()] = 1;
			a[arrmyLine.get(i).getIndexPointB()][arrmyLine.get(i).getIndexPointA()] = 1;
		}
		print(a);
	}
	
	private void InitComponentConnect() {
		int topNum = size - 1;
		for (int i = 1; i < topNum; i++) {
			arrNotConsider[i] = 0;
		}
		solt = 0 ;
	}
	
	public int numEdge() {
		int numEdge = 0 ;
		for (int i = 1; i < a.length; i++) {
			int sum = 0 ;
			for (int j = 1; j < a.length; j++) {
				sum += a[i][j] ;
			}
			numEdge += sum ;
		}
		numEdge = numEdge / 2 ;
		return numEdge ;
	}
	
	private void ResultComponentConnect(int arrNotConsider[],int n ,int solt ) {
		if (solt == 1) {
			System.out.println("Do thi lien thong");
			showInfo += "Graph interconnected" +"\n" ; 
		}
		for (int i = 1; i <=solt; i++) {
			System.out.println("Thanh phan lien thong thu "+i);
			showInfo += "\n" + "Connected Component " + i + " is " +"\n" ;
			for (int j = 1; j <=n; j++) {
				if(arrNotConsider[j] == i) {
					System.out.print(j);
					showInfo += j + " " ;
				}
			}
			System.out.println();
		}
	}
	
	private void BFSComponentConnect(int G[][],int n ,int i ,int solt,int arrNotConsider[],int Queue[]) {
		int u , startQ , endQ  ;
		startQ = endQ = 1;
		Queue[endQ] = i;
		arrNotConsider[i] = solt;
		arrComponent[i] = solt ;
		while(startQ <= endQ) {
			u = Queue[startQ] ; //getTop
			startQ += 1;
			for (int j = 1; j <=n; j++) {
				if(G[u][j]==1 && arrNotConsider[j]==0) {
					endQ += 1; //
					Queue[endQ] = j;
					arrNotConsider[j] = solt ;
					arrComponent[j] = solt;
				}
			}
		}
	}
	
	public void findComponentConnect() {
		InitComponentConnect();
		for (int i = 1; i <= size-1; i++) {
			if(arrNotConsider[i] == 0) {
				solt += 1;
				BFSComponentConnect(a,size-1,i,solt,arrNotConsider, Queue);
			}
		}
		ResultComponentConnect(arrNotConsider,size-1, solt);
	}
	
	private int topDegree(int i) {
		int sum = 0;
		if(i < 0 && i > size) return -1;
		for (int j = 1; j < a.length ; j++) {
			sum+=a[i][j] ;
		}
		return sum  ;
	}
	
	private boolean checkConnected() {
		InitComponentConnect();
		for (int i = 1; i <= size-1; i++) {
			if(arrNotConsider[i] == 0) {
				solt += 1;
				BFSComponentConnect(a,size-1,i,solt,arrNotConsider, Queue);
			}
		}
		if(solt == 1) return true ;
		return false ;
	}
	
	private boolean checkEulerCycle() {
		boolean isTopDegree = true ;
		boolean checkConnected = checkConnected();
		for (int i = 1; i <= size-1; i++) {
			if(topDegree(i) % 2 != 0) isTopDegree = false;
		}
		if(checkConnected && isTopDegree) return true ;
		return false ;
	}
	
	public void eulerCycle() {
		if (checkEulerCycle()) {
			int u = 1; // get top
			int v , x , top , numarrVisit;
			int stack[] ;
			stack  = new int[MAX] ;
			top = 1;
			stack[top] = u ; //insert u into stack
			numarrVisit = 0;
			do {
				v = stack[top] ; //get topTop in stack
				x = 1;
				while(x <=size-1 && a[v][x] == 0) //find adjoin with v
					x++;
				if(x > size-1) {
					numarrVisit++;
					arrEulerCycle[numarrVisit] = v ; //save v into arrEulerCycle
					top--;
				}else { // x adjoin with v
					top++;
					stack[top] = x ;
					//delete edge v x 
					a[v][x] = 0; 
					a[x][v] = 0;
				}
			}while(top!=0);
			System.out.println("--EulerCycle--");
			showInfo += "EulerCycle : ->" ;
			for (int i = numarrVisit; i > 0 ; i--) {
				System.out.print(arrEulerCycle[i]+" ");
				showInfo += " " + arrEulerCycle[i] + " " ;
			}
		}else {
			showInfo += "Graph not have EulerCycle" +"\n"+"\n" ;
			System.out.println("Do thi khong co chu trinh Euler");
			eulerPath();
		}
	}
	
	private void indexTopOdd() {
		for (int i = 1; i < a.length; i++) {
			int sum = 0;
			for (int j = 1; j < a.length; j++)
				sum += a[i][j] ;
			if(sum%2 != 0) {
				topOdd = i ;
			}
		}
	}
	
	private boolean checkEulerPath() {
		int dem = 0  ;
		for (int i = 1; i < a.length; i++) {
			int sum = 0;
			for (int j = 1; j < a.length; j++)
				sum += a[i][j] ;
			if(sum%2 != 0) {
				dem++ ;
			}
		}
		if(dem == 2) return true ;
		return false ;
	}
	
	public void eulerPath() {
		if(checkEulerPath()) {
			indexTopOdd();
			int v , x , top , numArrVisit ;
			int stack[] ;
			stack = new int[MAX];
			top = 1;
			stack[top] = topOdd; // add topOdd into stack
			numArrVisit = 0 ;
			do {
				v = stack[top]; //get Top from stack 
				x = 1;  // find Top x adjoin v 
				while(x <= size -1 && a[v][x]==0)
					x++;
				if(x > size-1) {
					numArrVisit++;
					arrEulerPath[numArrVisit] = v ;//save v into arrEulerPath
					top--;
				}else {
					top++;
					stack[top] = x ;
					a[v][x] = 0 ;
					a[x][v] = 0;
				}
			}while(top != 0);
			System.out.println("--EulerWay--");
			showInfo += "EulerWay : ->" ;
			for (int i = numArrVisit; i > 0; i--) {
				System.out.print( arrEulerPath[i] );
				showInfo += " " + arrEulerPath[i] + " " ;
			}
		}else {
			System.out.println("Khong co duong di euler");
 			showInfo += "\n" + "\n" + "Graph not have EulerWay" ;
		}
	}
	
 	private void print(int a[][]) {
		for (int i = 1; i < a.length ; i++) {
			for (int k = 1; k < a.length; k++) {
				System.out.print(a[i][k]);
			}
			System.out.println();
		}
	}
	
	public int[][] getA() {
		return a;
	}
	public void setA(int a[][]) {
		this.a = a;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public ArrayList<myPoint> getArrmyPoint() {
		return arrmyPoint;
	}

	public void setArrmyPoint(ArrayList<myPoint> arrmyPoint) {
		this.arrmyPoint = arrmyPoint;
	}

	public ArrayList<myLine> getArrmyLine() {
		return arrmyLine;
	}

	public void setArrmyLine(ArrayList<myLine> arrmyLine) {
		this.arrmyLine = arrmyLine;
	}
	
	public int[] getArrNotConsider() {
		return arrNotConsider;
	}

	public void setArrNotConsider(int[] arrNotConsider) {
		this.arrNotConsider = arrNotConsider;
	}

	public String getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}

	public int[] getArrEulerCycle() {
		return arrEulerCycle;
	}

	public void setArrEulerCycle(int arrEulerCycle[]) {
		this.arrEulerCycle = arrEulerCycle;
	}

	public int[] getarrEulerPath() {
		return arrEulerPath;
	}

	public void setarrEulerPath(int arrEulerPath[]) {
		this.arrEulerPath = arrEulerPath;
	}

	public int[] getArrComponent() {
		return arrComponent;
	}

	public void setArrComponent(int arrComponent[]) {
		this.arrComponent = arrComponent;
	}

}
