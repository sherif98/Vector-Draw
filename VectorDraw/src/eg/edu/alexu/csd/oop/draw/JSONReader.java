package eg.edu.alexu.csd.oop.draw;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class JSONReader extends BufferedReader {

	public JSONReader(InputStream stream) {
		super(new InputStreamReader(stream));
	}

	private int next() {
		try {
			int ret = super.read();
			return  ret;
		} catch (Exception e) {
			throw new InputMismatchException();
		}
	}
	
	private void goToObjects() {
		int ret = next();
		char c = (char) ret;
		while (c != '{' && ret >= 0) {
			ret = next();
			c = (char) ret;
		}
	}

	private String nextObjectString() {
		String ans = "";
		int ret = next();
		char c = (char) ret;
		while (c != '{' && ret >= 0) {
			ret = next();
			c = (char) ret;
		}
		while (c != '}' && ret >= 0) {
			ret = next();
			c = (char) ret;
			ans += c;
		}
		return ans;
	}

	private Map<String, String> nextObject(String object) {
		Map<String, String> ans = new HashMap<>();
		object = object.replaceAll("[\"{} ]", "");
		String[] parameter = object.split(",");
		for (String x : parameter) {
			String[] pair = x.split(":");
			if (pair.length >= 2) {
				ans.put(cleanAdd(pair[0]), cleanAdd(pair[1]));
			}
		}
		return ans;
	}

	private String cleanAdd(String s) {
		s = s.replaceAll("[{}]", "");
		s = s.replaceAll("[\", :]", "");
		s = s.replaceAll("\\s+", "");
		return s;
	}

	public ArrayList<JSONObject> loadAllObjects() {
		ArrayList<JSONObject> listOfObjects = new ArrayList<>();
		goToObjects();
		String objectString = nextObjectString();
		while (objectString != null && objectString.length() != 0
				&& objectString != "") {
			Map<String, String> map = nextObject(objectString);
			JSONObject x = new JSONObject(map);
			listOfObjects.add(x);
			objectString = nextObjectString();
		}
		
		return listOfObjects;
	}

}
