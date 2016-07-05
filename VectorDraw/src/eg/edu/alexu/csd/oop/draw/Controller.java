package eg.edu.alexu.csd.oop.draw;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class Controller {
	private int posX, posY;
	private PaintEngine paintEngineObj;
	private PaintGui gui;
	private List<Class<? extends Shape>> classesList;
	private Shape shapeToBeDrawn;
	private Shape selectedShape;

	public Controller() {
		this.paintEngineObj = PaintEngine.getEngineInstance();
		this.gui = PaintGui.getGuiInstance();
	}

	public void start() {
		getShapes();
		gui.setComboBoxListener(new ComboBoxHandler());
		gui.setPaintAreaMouseListener(new MouseHandler(), new MouseHandler());
		gui.setUndoRedoListeners(new ButtonHandler());
		gui.setDrawnShapesComboBoxListener(new DrwanShapesComboBoxHandler());
		gui.setRemoveButtonListener(new RemoveButtonListenser());
		gui.setUpdateButtonListener(new UpdateButtonListener());
		gui.setSaveButtonListener(new SaveButtonListener());
		gui.setLoadButtonListener(new LoadButtonListener());
		gui.setAddNewShapesActionListener(new PluginButtonReader());
	}

	private void getShapes() {
		classesList = paintEngineObj.getSupportedShapes();
		classesList.add(0, null);
		ArrayList<String> shapesNames = new ArrayList<String>();
		for (Class<?> i : classesList) {
			if (i == null) {
				shapesNames.add("");
				continue;
			}
			shapesNames.add(i.getSimpleName());
		}
		gui.addSupportedShapesToTheBox(shapesNames);
	}

	private void getTheShapeProperties() {
		Map<String, Double> map = shapeToBeDrawn.getProperties();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			entry.setValue(gui.getUserInputs(entry.getKey()));
		}
		shapeToBeDrawn.setProperties(map);
		shapeToBeDrawn.setPosition(new Point(posX, posY));
		shapeToBeDrawn.setFillColor(gui.getFillColor());
		shapeToBeDrawn.setColor(gui.getBorderColor());
		shapeToBeDrawn.draw(gui.getGraphics());
		paintEngineObj.addShape(shapeToBeDrawn);
		gui.addNameToComboBox(paintEngineObj.getShapes().length);
		gui.clearComboBox();

	}

	private class ComboBoxHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if (arg0.getStateChange() == ItemEvent.SELECTED) {
				int index = gui.getComboBoxCurrentIndex();
				if (index == 0) {
					return;
				}
				try {
					Shape instance = classesList.get(index).newInstance();
					shapeToBeDrawn = instance;
					getTheShapeProperties();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent arg0) {
			posX = arg0.getX();
			posY = arg0.getY();
			gui.displayTheClickedPosition(posX, posY);
		}

		public void mouseMoved(MouseEvent arg0) {
			gui.displayMousePosition(arg0.getX(), arg0.getY());
		}
	}

	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonName = ((JButton) e.getSource()).getText();
			if (buttonName.equals("Undo")) {
				paintEngineObj.undo();
			} else {
				paintEngineObj.redo();
			}
			gui.addNameToComboBox(paintEngineObj.getShapes().length);
			gui.getGraphics().clearRect(0, 0, 702, 641);
			paintEngineObj.refresh(gui.getGraphics());
			gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
		}
	}

	private class DrwanShapesComboBoxHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			Shape clonedShape = null;
			if (e.getStateChange() == ItemEvent.SELECTED) {
				int index = gui.getDrawnShapesComboBoxIndex();
				if (index == 0) {
					selectedShape = null;
					gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
					return;
				}
				Shape[] drawnShapes;
				drawnShapes = paintEngineObj.getShapes();
				selectedShape = drawnShapes[index - 1];
				try {
					clonedShape = (Shape) selectedShape.clone();
					clonedShape.setPosition(new Point(103, 95));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
				clonedShape.draw(gui.getGraphicsOfSelectionArea());
			}
		}
	}

	private class RemoveButtonListenser implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedShape == null) {
				gui.sendMsg("None Selected");
				return;
			}
			paintEngineObj.removeShape(selectedShape);
			gui.addNameToComboBox(paintEngineObj.getShapes().length);
			gui.getGraphics().clearRect(0, 0, 702, 641);
			paintEngineObj.refresh(gui.getGraphics());
			gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
		}
	}

	private class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedShape == null) {
				gui.sendMsg("None Selected");
				return;
			}
			Shape newShape = null;
			try {
				newShape = (Shape) selectedShape.clone();
				Map<String, Double> map = newShape.getProperties();
				for (Map.Entry<String, Double> entry : map.entrySet()) {
					entry.setValue(gui.getUserInputs(entry.getKey()));
				}
				newShape.setProperties(map);
				paintEngineObj.updateShape(selectedShape, newShape);
				gui.getGraphics().clearRect(0, 0, 702, 641);
				paintEngineObj.refresh(gui.getGraphics());
				gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
				gui.addNameToComboBox(paintEngineObj.getShapes().length);

			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = gui.getFilePath();
			paintEngineObj.save(path);
		}

	}

	private class LoadButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = gui.getFilePath();
			paintEngineObj.load(path);
			gui.getGraphics().clearRect(0, 0, 702, 641);
			paintEngineObj.refresh(gui.getGraphics());
			gui.getGraphicsOfSelectionArea().clearRect(0, 0, 206, 191);
			gui.addNameToComboBox(paintEngineObj.getShapes().length);

		}
	}

	private class PluginButtonReader implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String message = System.getProperty("java.class.path") ; 
			gui.sendMsg(message);
//			String path = gui.getFilePath();
//			File plugin = new File(path);
//			String classpath = System.getProperty("java.class.path");
//			String pathSeparator = System.getProperty("path.separator");
//			String[] allNames = classpath.split(pathSeparator);
//			for (int i = 0; i < allNames.length; i++) {
//				File classPath = new File(allNames[i]);
//				try {
//					Files.copy(plugin.toPath(), classPath.toPath());
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//			classesList.clear();
//			getShapes();
		}

	}

}
