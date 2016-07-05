package eg.edu.alexu.csd.oop.draw;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.dir"));
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			throw new RuntimeException(e1);
		}
		Controller ctrl = new Controller();
		ctrl.start();

	}
}
