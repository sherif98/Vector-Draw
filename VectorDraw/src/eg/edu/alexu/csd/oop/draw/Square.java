package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

public class Square extends MyShape {

	final static String[] propertyName = { "Length" };

	public Square() {
		myPosition = new Point(0, 0);
		myColor = Color.BLACK;
		myFillColor = Color.WHITE;
		myProperties.put(propertyName[0], 0.0);
	}

	@Override
	public void draw(Graphics canvas) {
		
		if (myProperties == null || myPosition == null || myColor == null
				|| myFillColor == null) {
			throw new RuntimeException("one or more property not found");
		}
		
		int length = 0;
		if (myProperties.get(propertyName[0]) == null) {
			throw new RuntimeException("No Properites Found");
		}
		length = myProperties.get(propertyName[0]).intValue();

		if (canvas instanceof Graphics2D) {
			Graphics2D g2D = (Graphics2D) canvas;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2D.setPaint(myFillColor);
			g2D.setStroke(new BasicStroke(2));
			Rectangle2D Square = new Rectangle2D.Double(myPosition.x,
					myPosition.y, length, length);
			g2D.fill(Square);

			g2D.setColor(myColor);
			g2D.draw(Square);
		} else {
			canvas.setColor(myFillColor);
			canvas.fillRect(myPosition.x, myPosition.y, length, length);
			canvas.setColor(myColor);
			canvas.drawRect(myPosition.x, myPosition.y, length, length);
		}

	}
}
