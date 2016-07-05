package eg.edu.alexu.csd.oop.draw;


public class CareTaker {
	 private MyLinkedList savedMementos = new MyLinkedList() ;
	 public void addMemento(Memento memento){
		 savedMementos.add(memento);
	 }
	 public Memento getMememto(int index){
		 return (Memento) savedMementos.get(index) ;
	 }
	 public void deleteAllAfter(int deleteFromThisIndex){
		 savedMementos.deleteAllAfter(deleteFromThisIndex);
	 }
	 public void clearAll(){
		 savedMementos.clear(); 
	 }
	 public int checkSavedNumber(){
		 if(savedMementos.size() > 20){
			 savedMementos.removeFirst() ; 
			 return -1 ; 
		 }
		 return 0 ; 
	 }
}
