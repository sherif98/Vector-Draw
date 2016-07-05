package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {
	
	@SuppressWarnings("resource")
	public Shape[] xmlLoad(String fileName) throws Exception {
		if (fileName == null) {
			throw new RuntimeException("No file name found");
		}
		Scanner scan = new Scanner(new File(fileName));
		if (scan.hasNextLine() == false) {
			scan.close(); 
			return null;
		}
		File xmlFile = new File(fileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		ArrayList<Shape> listOfShapes = new ArrayList<>();
		NodeList list;
	//	try {
			list = document.getElementsByTagName("canvas");
//		} catch (RuntimeException e) {
//
//			Shape[] ans = new Shape[0];
//
//			return ans;
//		}

		Node node = list.item(0);

		if (node.getNodeType() == Node.ELEMENT_NODE) {

			Element canvas = (Element) node;
			NodeList shapes = canvas.getChildNodes();

			for (int i = 0; i < shapes.getLength(); i++) {

				Node n = shapes.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) n;

					if (element.getAttribute("nullFlag").equals("true")) {
						listOfShapes.add(null);
						continue;
					}
					Shape shape = (Shape) Class.forName(element.getAttribute("class")).newInstance();

					Map<String, Double> map;
					Map<String, Double> propertyMap = new HashMap<>();
					map = shape.getProperties();

					if (map == null) {
						propertyMap = null;
					} else {

						for (Map.Entry<String, Double> entry : map.entrySet()) {
							if (entry.getValue() == null) {
								propertyMap.put(entry.getKey(), null);
							} else {
								propertyMap.put(entry.getKey(), entry.getValue());
							}
						}
					}

					String flag = element.getAttribute("properties");

					if (flag.equals("false")) {
						propertyMap = null;
					} else {
						for (Map.Entry<String, Double> entry : map.entrySet()) {
							String key = entry.getKey();
							String valueString = element.getAttribute(key);
							if (valueString.equals("null")) {
								propertyMap.put(key, null);
							} else {
								Double value = Double.parseDouble(element.getAttribute(key));
								propertyMap.put(key, value);
							}
						}
					}

					String xString = element.getAttribute("x");
					String yString = element.getAttribute("y");
					if (xString.equals("null") || yString.equals("null")) {
						shape.setPosition(null);
					} else {

						int xPosition = Integer.parseInt(element.getAttribute("x"));
						int yPosition = Integer.parseInt(element.getAttribute("y"));
						shape.setPosition(new Point(xPosition, yPosition));
					}
					Color color, fillColor;

					String colorString = element.getAttribute("color");
					String fillColorString = element.getAttribute("fillColor");

					if (colorString.equals("null")) {
						color = null;
					} else {

						color = new Color(Integer.parseInt(element.getAttribute("color")));
					}

					if (fillColorString.equals("null")) {
						fillColor = null;
					} else {

						fillColor = new Color(Integer.parseInt(element.getAttribute("fillColor")));
					}

					shape.setColor(color);
					shape.setFillColor(fillColor);

					shape.setProperties(propertyMap);

					listOfShapes.add(shape);
				}

			}
		}
		Shape[] ans = new Shape[listOfShapes.size()];
		for (int i = 0; i < listOfShapes.size(); i++) {
			ans[i] = listOfShapes.get(i);
		}
		return ans;
	}
}
