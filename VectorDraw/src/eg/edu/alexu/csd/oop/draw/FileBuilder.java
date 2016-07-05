package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileBuilder {
	public void xmlSave(String fileName, Shape[] shapes) throws Exception {

		if (fileName == null) {
			throw new RuntimeException("No file name found");
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		/* starting Node */
		Element canvas = document.createElement("canvas");
		document.appendChild(canvas);

		for (int i = 0; i < shapes.length; i++) {
			Element element;
			Attr nullFlag = document.createAttribute("nullFlag");
			if (shapes[i] == null) {
				element = document.createElement("nullShape");
				nullFlag.setValue("true");
				element.setAttributeNode(nullFlag);
				canvas.appendChild(element);
			} else {
				element = document.createElement(shapes[i].getClass().getSimpleName());
				
				nullFlag.setValue("false");
				element.setAttributeNode(nullFlag);
				Attr className = document.createAttribute("class");
				className.setValue(shapes[i].getClass().getName());
				element.setAttributeNode(className);

				Attr properties = document.createAttribute("properties");

				if (shapes[i].getProperties() == null) {
					properties.setValue("false");
				} else {
					properties.setValue("true");

					for (Map.Entry<String, Double> entry : shapes[i].getProperties().entrySet()) {

						Attr x = document.createAttribute(entry.getKey());
						if (entry.getValue() == null) {
							x.setValue("null");
						} else {
							x.setValue(entry.getValue().toString());
						}
						element.setAttributeNode(x);
					}
				}

				element.setAttributeNode(properties);

				Attr color = document.createAttribute("color");

				if (shapes[i].getColor() == null) {
					color.setValue("null");
				} else {

					color.setValue(String.valueOf(shapes[i].getColor().getRGB()));
				}
				element.setAttributeNode(color);

				Attr fillColor = document.createAttribute("fillColor");

				if (shapes[i].getFillColor() == null) {
					fillColor.setValue("null");
				} else {
					fillColor.setValue(String.valueOf(shapes[i].getFillColor().getRGB()));
				}
				element.setAttributeNode(fillColor);

				Attr xPosition = document.createAttribute("x");
				Attr yPosition = document.createAttribute("y");
				if (shapes[i].getPosition() == null) {
					xPosition.setValue("null");

					yPosition.setValue("null");

					element.setAttributeNode(xPosition);
					element.setAttributeNode(yPosition);

				} else {
					xPosition.setValue(String.valueOf(shapes[i].getPosition().x));
					element.setAttributeNode(xPosition);

					yPosition.setValue(String.valueOf(shapes[i].getPosition().y));
					element.setAttributeNode(yPosition);
				}
				canvas.appendChild(element);
			}
		}

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();

		DOMSource source = new DOMSource(document);

		StreamResult streamResult = new StreamResult(new File(fileName));

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(source, streamResult);
	}
}
