package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class MyLine extends MyShape {

	final static String[] propertyName = { "x2", "y2" };

	public MyLine() {
		myPosition = new Point(0, 0);
		myColor = Color.BLACK;
		myFillColor = Color.WHITE;
		myProperties.put(propertyName[0], 0.0);
		myProperties.put(propertyName[1], 0.0);
	}

	public void draw(Graphics canvas) {
		if (myProperties == null || myPosition == null || myColor == null
				|| myFillColor == null) {
			throw new RuntimeException("one or more property not found");
		}

		int x = 0, y = 0;
		if (myProperties.get(propertyName[0]) == null
				|| myProperties.get(propertyName[1]) == null) {
			throw new RuntimeException("No Property Found");
		}
		x = myProperties.get(propertyName[0]).intValue();
		y = myProperties.get(propertyName[1]).intValue();

		if (canvas instanceof Graphics2D) {
			Graphics2D g2D = (Graphics2D) canvas;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			Line2D line = new Line2D.Double(myPosition.x, myPosition.y, x, y);
			g2D.setStroke(new BasicStroke(2));
			g2D.setColor(myColor);
			g2D.draw(line);
		} else {
			canvas.setColor(myColor);
			canvas.drawLine(myPosition.x, myPosition.y, x, y);
		}
	}

}