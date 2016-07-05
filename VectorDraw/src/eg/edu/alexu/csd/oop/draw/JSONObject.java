package eg.edu.alexu.csd.oop.draw;
import java.util.Map;

public class JSONObject {

	private Map<String, String> myMap;

	public JSONObject(Map<String, String> myMap) {
		this.myMap = myMap;
	}

	public String getAttribute(String attribute) {
		return myMap.get(attribute);
	}

	public String toString() {
		return myMap.toString();
	}

}
