package eg.edu.alexu.csd.oop.draw;

public class Memento {
	private String command ;
	private Shape oldShape ;
	private Shape newShape ;
	private int index ; 
	private boolean canUndo ; 
	public Memento(String command , Shape oldShape , Shape newShape , int index , boolean canUndo){
		this.command = command ;
		this.oldShape = oldShape ; 
		this.newShape = newShape ;
		this.index = index ;
		this.canUndo = canUndo ; 
	}
	public Shape getOldShape (){
		return oldShape ; 
	}
	public Shape getNewShape(){
		return newShape ;
	}
	public String getCommand(){
		return command ;
	}
	public int getIndex(){
		return index ; 
	}
	public boolean getFlag(){
		return canUndo ; 
	}
}
