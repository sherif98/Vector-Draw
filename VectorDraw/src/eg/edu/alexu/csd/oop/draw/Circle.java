package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Circle extends MyShape {
	final static String[] propertyName = { "Radius" };

	public Circle() {
		myProperties.put(propertyName[0], 0.0);
		myColor = Color.BLACK;
		myFillColor = Color.WHITE;
	}

	@Override
	public void draw(Graphics canvas) {
		
		if (myPosition == null || myProperties == null || myColor == null || myFillColor == null) {
			throw new RuntimeException ("one or more property not found");
		}
		
		if (canvas instanceof Graphics2D) {
			Graphics2D g2D = (Graphics2D) canvas;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			double radius = 0;
			if (myProperties.get(propertyName[0]) == null) {
				throw new RuntimeException("No Radius Found");
			}
			radius = myProperties.get(propertyName[0]).doubleValue();
			
			if (myPosition == null) {
				throw new RuntimeException ("No position found");
			}
			
			double x = myPosition.x;
			double y = myPosition.y;
			Ellipse2D circle = new Ellipse2D.Double(x, y, radius, radius);
			
			if (myFillColor == null || myColor == null) {
				throw new RuntimeException ("No color or fill color found");
			}
			g2D.setPaint(myFillColor);
			g2D.fill(circle);

			g2D.setColor(myColor);
			g2D.setStroke(new BasicStroke(2));
			g2D.draw(circle);

		}

		else {
			int radius = 0;
			if (myProperties.get(propertyName[0]) != null) {
				radius = myProperties.get(propertyName[0]).intValue();
			} else {
				throw new RuntimeException("No Radius Found");
			}
			canvas.setColor(myFillColor);
			canvas.fillOval(myPosition.x, myPosition.y, radius, radius);

			canvas.setColor(myColor);
			canvas.drawOval(myPosition.x, myPosition.y, radius, radius);
		}
	}
}
