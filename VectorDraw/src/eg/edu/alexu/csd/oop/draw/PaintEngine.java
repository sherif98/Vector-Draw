package eg.edu.alexu.csd.oop.draw;


import java.awt.Graphics;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PaintEngine implements DrawingEngine {

	private MyLinkedList shapeList ; 
	private Originator originator ;
	private CareTaker careTaker ;
	private int current = 0 ;
	private int topIndex = 0 ;  
	private static PaintEngine EngineInstance ;
	
	private PaintEngine(){
		shapeList = new MyLinkedList() ; 
		careTaker = new CareTaker() ;
		originator = new Originator() ;
	}
	
	public static PaintEngine getEngineInstance(){
		if(EngineInstance == null){
			EngineInstance = new PaintEngine() ;
		}
		return EngineInstance ;
	}
	
	@Override
	public void refresh(Graphics canvas) {
		try{
			Shape [] arr = this.getShapes() ;
			for(Shape i : arr){
				i.draw(canvas);
			}
		}catch(Exception e){
			
		}
		
	}

	@Override
	public void addShape(Shape shape) {
		shapeList.add(shape) ; 
		careTaker.deleteAllAfter(current);
		careTaker.addMemento(originator.storeInMemento("add", shape, null, -1 , true));
		current ++ ; 
	//	current = current + careTaker.checkSavedNumber() ; 
		topIndex = current ; 
	}

	@Override
	public void removeShape(Shape shape) {
		int index = shapeList.indexOf(shape) ; 
		if(index == -1){
			throw new RuntimeException() ; 
		}
		shapeList.remove(shape) ; 
		careTaker.deleteAllAfter(current);
		careTaker.addMemento(originator.storeInMemento("remove", shape , null, index , true));
		current ++ ; 
	//	current = current + careTaker.checkSavedNumber() ; 
		topIndex = current ; 
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		int index = shapeList.indexOf(oldShape) ; 
		if(index == -1){
			throw new RuntimeException() ;
		}
		shapeList.remove(oldShape) ; 
		shapeList.add(index, newShape);
		careTaker.deleteAllAfter(current);
		careTaker.addMemento(originator.storeInMemento("update", oldShape, newShape, index , true));
		current ++ ; 
	//	current = current + careTaker.checkSavedNumber() ; 
		topIndex = current ; 
	}

	@Override
	public Shape[] getShapes() {
		if (shapeList == null) {
			Shape[] ans = new Shape[0];
			return ans;
		}
		Shape [] list = new Shape[shapeList.size()] ;
		for(int i = 0 ; i < shapeList.size() ; i++){
			list[i] = (Shape) shapeList.get(i) ; 
		}
		return list ;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		java.util.List<Class<? extends Shape>> list = new LinkedList<Class<? extends Shape>>();
	//	list.add(Circle.class);
		list.add(Rectangle.class); 
		list.add(Ellipse.class) ; 
		list.add(Square.class) ;
		list.add(MyLine.class) ;
		try{
			ClassFinder find = new ClassFinder() ;
			Set<Class<? extends Shape>>set = find.getClasses() ; 
			java.util.Iterator<Class<? extends Shape>> itr = set.iterator();
			while (itr.hasNext()) {
				list.add(itr.next());
			}
		}catch(Exception e ){
			
		}
		return list;
	}

	@Override
	public void undo() {
		if(current == 0){
			throw new RuntimeException() ; 
		}
		
		current -- ; 
		Memento currentMemento = careTaker.getMememto(current) ;
		if(currentMemento.getFlag() == false){
			current ++ ; 
			throw new RuntimeException() ; 
		}
		String command = originator.restoreMementoCommand(currentMemento) ;
		
		if(command == "remove"){
			int index = originator.restoreMementoIndex(currentMemento) ; 
			Shape shape = originator.restoreMementoOldShape(currentMemento) ;
			shapeList.add(index, shape);
		}
		else if(command == "update"){
			int index = originator.restoreMementoIndex(currentMemento) ;
			Shape oldShape = originator.restoreMementoOldShape(currentMemento) ;
			shapeList.remove(index) ; 
			shapeList.add(index , oldShape) ; 
		}
		else if(command == "add"){
			shapeList.remove(originator.restoreMementoOldShape(currentMemento)) ;
		}
	}

	@Override
	public void redo() {
		if(current == topIndex){
			throw new RuntimeException() ; 
		}
		Memento currentMemento = careTaker.getMememto(current) ;
		String command = originator.restoreMementoCommand(currentMemento) ;
		
		if(command == "remove"){
			int index = originator.restoreMementoIndex(currentMemento) ;
			shapeList.remove(index) ; 
		}
		else if(command == "update"){
			int index = originator.restoreMementoIndex(currentMemento) ;
			Shape newShape = originator.restoreMementoNewShape(currentMemento) ;
			shapeList.remove(index) ; 
			shapeList.add(index , newShape) ; 
		}
		else if(command == "add"){
			shapeList.add(originator.restoreMementoOldShape(currentMemento)) ; 
		}
		current ++ ; 
	}

	@Override
	public void save(String path) {
		if(path.toUpperCase().trim().endsWith(".XML")){
			FileBuilder saveMyXml = new FileBuilder() ;
			try {
				saveMyXml.xmlSave(path , this.getShapes());
			} catch (Exception e1) {
				throw new RuntimeException(); 
			}
			
		}
		else if (path.toUpperCase().trim().endsWith(".JSON")){
			JSONWriter saveMyJson = new JSONWriter();
			try {
				saveMyJson.SaveJSON(path, this.getShapes());
			} catch (Exception e1) {
				throw new RuntimeException(); 
			}
		}
		
	}
	@Override
	public void load(String path) {
		Shape [] myShapes = null ;
		if(path.toUpperCase().trim().endsWith(".XML")){
			XmlReader loader = new XmlReader() ; 
			try {
				myShapes = loader.xmlLoad(path) ;
			} catch (Exception e) {
			}
		}
		else if(path.toUpperCase().trim().endsWith(".JSON")){
			JSONScanner loader = new JSONScanner() ;
			try {
				myShapes = loader.loadJSON(path) ;
			} catch (Exception e) {
			}
		}
			try {
				careTaker.clearAll();
				int size = this.getShapes().length ; 
				for(int i = 0 ; i < size ; i++){
					shapeList.removeLast() ; 
					current = 0 ; 
					topIndex = 0 ; 
				}
				if (myShapes == null) {
					return ;
				}
				for(Shape i : myShapes){
					shapeList.add(i) ; 
					careTaker.addMemento(originator.storeInMemento("add", i , null, -1 , false));
					current ++ ;
					topIndex = current ; 
				}
				
			} catch (Exception e1) {
				 
			} 
		}
}
