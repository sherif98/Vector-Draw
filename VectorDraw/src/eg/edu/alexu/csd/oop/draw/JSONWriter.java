package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class JSONWriter {

	public void SaveJSON(String fileName, Shape[] shapes) throws Exception {

		File f = new File(fileName);
		OutputStream outputStream = new FileOutputStream(f);

		PrintWriter out = new PrintWriter(outputStream);
		out.println("{");

		for (int i = 0; i < shapes.length - 1; i++) {
			String result = convertToJSON(shapes[i]);
			out.println(result + ",");
		}

		String last = convertToJSON(shapes[shapes.length - 1]);
		out.println(last);
		out.println("}");

		out.close();

	}

	private String convertToJSON(Shape shape) {
		String ans = "";
		String className = shape.getClass().getSimpleName();
		className = addQuots(className);

		ans += "\t" + className;

		ans += " : {";

		ans += "\n";

		String classPath = shape.getClass().getName();
		classPath = addQuots(classPath);

		ans += "\t\t\"class\" : " + classPath + ",";

		ans += "\n";

		Map<String, Double> map = shape.getProperties();
		String propertiesFlag, properties;
		properties = addQuots("properties");

		if (map == null) {
			propertiesFlag = addQuots("false");
			ans += "\t\t" + properties + " : " + propertiesFlag + ',' + '\n';

		} else {

			propertiesFlag = "true";
			propertiesFlag = addQuots(propertiesFlag);
			ans += "\t\t" + properties + " : " + propertiesFlag + ',' + '\n';
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				String key = entry.getKey();

				String value;
				if (entry.getValue() == null) {
					value = "null";
				} else {
					value = String.valueOf(entry.getValue());
				}
				
				key = addQuots(key);

				value = addQuots(value);

				ans += "\t\t";
				ans += key + " : " + value + ",";
				ans += "\n";
			}
		}

		String xPosition, yPosition;
		if (shape.getPosition() == null) {
			xPosition = "null";
			yPosition = "null";
		} else {
			xPosition = String.valueOf(shape.getPosition().x);
			yPosition = String.valueOf(shape.getPosition().y);
		}

		xPosition = addQuots(xPosition);
		yPosition = addQuots(yPosition);

		ans += "\t\t\"x\" : " + xPosition + ",";
		ans += "\n";
		ans += "\t\t\"y\" : " + yPosition + ",";

		ans += "\n";

		String color, fillColor;
		if (shape.getColor() == null) {
			color = "null";
		} else {
			color = String.valueOf(shape.getColor().getRGB());
		}

		if (shape.getFillColor() == null) {
			fillColor = "null";
		} else {

			fillColor = String.valueOf(shape.getFillColor().getRGB());
		}

		color = addQuots(color);
		fillColor = addQuots(fillColor);

		ans += "\t\t\"color\" : " + color + ",";
		ans += "\n";
		ans += "\t\t\"fillColor\" : " + fillColor;

		ans += "\n";

		ans += "\t}";

		return ans;
	}

	private String addQuots(String s) {
		return "\"" + s + "\"";
	}

}
