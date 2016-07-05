package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Ellipse extends MyShape {

	final static String[] propertyName = { "Width", "Height" };

	public Ellipse() {
		myPosition = new Point(0, 0);
		myColor = Color.BLACK;
		myFillColor = Color.WHITE;
		myProperties.put(propertyName[0], 0.0);
		myProperties.put(propertyName[1], 0.0);
	}

	@Override
	public void draw(Graphics canvas) {

		int width = 0, height = 0;
		if (myProperties == null || myPosition == null || myColor == null
				|| myFillColor == null) {
			throw new RuntimeException("one or more property not found");
		}
		if (myProperties.get(propertyName[0]) == null
				|| myProperties.get(propertyName[1]) == null) {
			throw new RuntimeException("No Properites Found");
		}
		width = myProperties.get(propertyName[0]).intValue();
		height = myProperties.get(propertyName[1]).intValue();

		if (canvas instanceof Graphics2D) {
			Graphics2D g2D = (Graphics2D) canvas;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2D.setPaint(myFillColor);
			g2D.setStroke(new BasicStroke(2));
			Ellipse2D ellipse = new Ellipse2D.Double(myPosition.x,
					myPosition.y, width, height);
			g2D.fill(ellipse);

			g2D.setColor(myColor);
			g2D.draw(ellipse);
		} else {
			canvas.setColor(myFillColor);
			canvas.fillOval(myPosition.x, myPosition.y, width, height);

			canvas.setColor(myColor);
			canvas.drawOval(myPosition.x, myPosition.y, width, height);
		}

	}
}
