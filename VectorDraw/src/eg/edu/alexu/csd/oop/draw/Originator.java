package eg.edu.alexu.csd.oop.draw;

public class Originator {
	
	public Memento storeInMemento(String command , Shape oldShape , Shape newShape , int index , boolean canUndo){
		return new Memento(command , oldShape , newShape, index , canUndo) ; 
	}
	public String restoreMementoCommand(Memento memento){
		return memento.getCommand() ;
	}
	public Shape restoreMementoOldShape(Memento memento){
		return memento.getOldShape() ;
	}
	public Shape restoreMementoNewShape(Memento memento){
		return memento.getNewShape() ; 
	}
	public int restoreMementoIndex(Memento memento){
		return memento.getIndex() ;
	}
	public boolean restoreMementoFlag(Memento memento){
		return memento.getFlag() ; 
	}
}
