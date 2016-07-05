package eg.edu.alexu.csd.oop.draw;

import java.util.LinkedList;

public class MyLinkedList extends LinkedList<Object>{
	
	private static final long serialVersionUID = 1L;
	public void deleteAllAfter(int index){
		while(this.size() > index){
			this.removeLast() ; 
		}
	}
}
