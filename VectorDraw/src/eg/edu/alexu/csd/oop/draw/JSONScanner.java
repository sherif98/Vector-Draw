package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONScanner {

	public Shape[] loadJSON(String fileName) throws Exception {
		File f = new File(fileName);
		InputStream inputStream = new FileInputStream(f);
		JSONReader reader = new JSONReader(inputStream);

		ArrayList<Shape> ans = new ArrayList<>();

		ArrayList<JSONObject> listOfShapes = reader.loadAllObjects();

		for (JSONObject element : listOfShapes) {
			Shape shape = (Shape) Class.forName(element.getAttribute("class")).newInstance();

			Map<String, Double> map = shape.getProperties();
			Map<String, Double> properties = new HashMap<>();

			if (map == null) {
				properties = null;
			} else {
				for (Map.Entry<String, Double> entry : map.entrySet()) {
					properties.put(entry.getKey(), entry.getValue());
				}
			}

			String propertiesFlag = element.getAttribute("properties");
			if (propertiesFlag.equals("false")) {
				properties = null;
			} else {

				for (Map.Entry<String, Double> entry : map.entrySet()) {
					String key = entry.getKey();

					if (element.getAttribute(key).equals("null")) {
						properties.put(key, null);
					} else {

						Double value = Double.parseDouble(element.getAttribute(key));
						properties.put(key, value);
					}
				}
			}

			String xString, yString;
			xString = element.getAttribute("x");
			yString = element.getAttribute("y");
			if (xString.equals("null") || yString.equals("null")) {
				shape.setPosition(null);
			} else {

				int xPosition = Integer.parseInt(element.getAttribute("x"));
				int yPosition = Integer.parseInt(element.getAttribute("y"));

				shape.setPosition(new Point(xPosition, yPosition));
			}

			String colorString, fillColorString;
			colorString = element.getAttribute("color");
			if (colorString.equals("null")) {
				shape.setColor(null);
			} else {
				Color color = new Color(Integer.parseInt(element.getAttribute("color")));
				shape.setColor(color);
			}
			fillColorString = element.getAttribute("fillColor");

			if (fillColorString.equals("null")) {
				shape.setFillColor(null);
			} else {

				Color fillColor = new Color(Integer.parseInt(element.getAttribute("fillColor")));
				shape.setFillColor(fillColor);
			}

			shape.setProperties(properties);
			ans.add(shape);

		}

		Shape[] ret = new Shape[ans.size()];
		for (int i = 0; i < ans.size(); i++) {
			ret[i] = ans.get(i);
		}

		reader.close();

		return ret;
	}
}
