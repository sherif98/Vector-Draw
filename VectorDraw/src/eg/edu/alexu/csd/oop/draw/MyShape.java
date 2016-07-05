package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

abstract public class MyShape implements Shape, Cloneable {

	protected Point myPosition = new Point();
	protected Map<String, Double> myProperties = new HashMap<>();
	protected Color myColor, myFillColor;

	@Override
	public void setPosition(Point position) {
		if (position == null) {
			myPosition = null;
			return;
		}
		int x = (int) position.x;
		int y = (int) position.y;
		myPosition = new Point(x, y);

	}

	@Override
	public Point getPosition() {
		return myPosition;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		if (properties == null) {
			myProperties = null;
			return;
		}
		myProperties = new HashMap<>();
		for (Map.Entry<String, Double> entry: properties.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() == null) {
				myProperties.put(key, null);
			} else {
				myProperties.put(key, entry.getValue());
			}
		}
	}

	@Override
	public Map<String, Double> getProperties() {
		return myProperties;
	}

	@Override
	public void setColor(Color color) {
		if (color == null) {
			myColor = null;
			return;
		}
		myColor = new Color(color.getRGB());
	}

	@Override
	public Color getColor() {
		return myColor;
	}

	@Override
	public void setFillColor(Color color) {
		if (color == null) {
			myFillColor = null;
			return;
		}

		myFillColor = new Color(color.getRGB());
	}

	@Override
	public Color getFillColor() {
		return myFillColor;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Object cloned = super.clone();
		Shape clonedShape = (Shape) cloned;
		if (clonedShape.getPosition() == null) {
			myPosition = null;
		} else {
			myPosition = new Point(clonedShape.getPosition().x,
					clonedShape.getPosition().y);
		}
		myProperties = new HashMap<>();
		if (clonedShape.getProperties() == null) {
			myProperties = null;
		} else {
			myProperties.putAll(clonedShape.getProperties());
		}
		if (clonedShape.getColor() == null) {
			myColor = null;
		} else {
			myColor = new Color(clonedShape.getColor().getRGB());
		}
		if (clonedShape.getFillColor() == null) {
			myFillColor = null;
		} else {
			myFillColor = new Color(clonedShape.getFillColor().getRGB());
		}
		return cloned;
	}

}
