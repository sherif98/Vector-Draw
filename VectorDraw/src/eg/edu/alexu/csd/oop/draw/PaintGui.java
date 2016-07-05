package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PaintGui {

	private JFrame frame;
	private JComboBox<Object> comboBox = new JComboBox<Object>();
	private JComboBox<Object> comboBox_1 = new JComboBox<Object>();
	private Panel panel = new Panel();
	private Panel panel_1 = new Panel();
	private JTextField textField = new JTextField();
	private JTextField textField_1 = new JTextField("Centre is X: 0  Y: 0");
	private JButton btnNewButton = new JButton("Undo");
	private JButton btnNewButton_1 = new JButton("Redo");
	private JButton btnNewButton_2 = new JButton("Save");
	private JButton btnNewButton_3 = new JButton("Load");
	private JButton btnNewButton_4 = new JButton("Remove");
	private JButton btnNewButton_5 = new JButton("Update");
	private JButton btnNewButton_6 = new JButton("");
	private JButton btnNewButton_7 = new JButton("");
	private JButton btnNewButton_8 = new JButton("new shape");
	private static PaintGui guiInstance;

	private PaintGui() throws Exception {
		initialize();
		frame.setVisible(true);
	}
	public static PaintGui getGuiInstance(){
		if(guiInstance == null){
			try {
				guiInstance = new PaintGui() ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return guiInstance ;
	}
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 1038, 702);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel.setBounds(310, 10, 702, 641);
		panel.setBackground(Color.white);
		frame.getContentPane().add(panel);
		comboBox.setBounds(122, 109, 73, 20);
		frame.getContentPane().add(comboBox);
		textField.setBounds(30, 10, 165, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		textField_1.setBounds(30, 65, 165, 33);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		frame.getContentPane().add(textField_1);
		btnNewButton.setBounds(10, 259, 89, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton_1.setBounds(137, 259, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_2.setBounds(10, 293, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_3.setBounds(137, 293, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		btnNewButton_4.setBounds(10, 613, 89, 23);
		frame.getContentPane().add(btnNewButton_4);
		btnNewButton_5.setBounds(106, 613, 89, 23);
		frame.getContentPane().add(btnNewButton_5);
		JLabel lblNewLabel = new JLabel("Shapes List");
		lblNewLabel.setBounds(30, 112, 69, 14);
		frame.getContentPane().add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("Border Color");
		lblNewLabel_1.setBounds(30, 171, 81, 14);
		frame.getContentPane().add(lblNewLabel_1);
		JLabel lblNewLabel_2 = new JLabel("Fill Color");
		lblNewLabel_2.setBounds(33, 209, 57, 14);
		frame.getContentPane().add(lblNewLabel_2);
		btnNewButton_6.setBounds(139, 167, 24, 23);
		frame.getContentPane().add(btnNewButton_6);
		btnNewButton_7.setBounds(139, 205, 24, 23);
		frame.getContentPane().add(btnNewButton_7);
		JLabel lblNewLabel_3 = new JLabel("Select a drawn shape");
		lblNewLabel_3.setBounds(10, 365, 129, 14);
		frame.getContentPane().add(lblNewLabel_3);
		comboBox_1.setBounds(10, 419, 89, 20);
		frame.getContentPane().add(comboBox_1);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(98, 416, 206, 191);
		frame.getContentPane().add(panel_1);
		btnNewButton_8.setBounds(205, 109, 4, 20);
		frame.getContentPane().add(btnNewButton_8);
			
		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = Color.BLACK;
				btnNewButton_6.setBackground(JColorChooser.showDialog(null, "Pick a Color", color));
			}
		});
		btnNewButton_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = Color.BLACK;
				btnNewButton_7.setBackground(JColorChooser.showDialog(null, "Pick a Color", color));

			}
		});
	}

	public void addSupportedShapesToTheBox(ArrayList<String> shapesNames) {
		comboBox.setModel(new DefaultComboBoxModel<>(shapesNames.toArray()));
	}

	public void setComboBoxListener(ItemListener listener) {
		comboBox.addItemListener(listener);
	}

	public void setDrawnShapesComboBoxListener(ItemListener listener) {
		comboBox_1.addItemListener(listener);
	}
	public void setAddNewShapesActionListener(ActionListener listener){
		btnNewButton_8.addActionListener(listener);
	}
	public int getComboBoxCurrentIndex() {
		return comboBox.getSelectedIndex();
	}

	public void setPaintAreaMouseListener(MouseListener listener, MouseMotionListener motionListener) {
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(motionListener);
	}

	public void sendMsg(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public Double getUserInputs(String key) {
		String input;
		input = JOptionPane.showInputDialog(null, "Enter The Shape" + key);
		if (input == null) {
			return 0.0;
		}
		return Double.parseDouble(input);
	}

	public Graphics getGraphics() {
		return panel.getGraphics();
	}

	public Graphics getGraphicsOfSelectionArea() {
		return panel_1.getGraphics();
	}

	public void displayMousePosition(int x, int y) {
		textField.setText(String.format("Pos X %d   Pos Y %d", x, y));
	}

	public void displayTheClickedPosition(int x, int y) {
		textField_1.setText(String.format("Centre is X: %d  Y: %d", x, y));
	}

	public void clearComboBox() {
		comboBox.setSelectedIndex(0);
	}

	public void setUndoRedoListeners(ActionListener listener) {
		btnNewButton.addActionListener(listener);
		btnNewButton_1.addActionListener(listener);
	}

	public Color getBorderColor() {
		Color color = btnNewButton_6.getBackground();
		return color;
	}

	public Color getFillColor() {
		Color color = btnNewButton_7.getBackground();
		return color;
	}

	public Double getShapeNumber() {
		String number = JOptionPane.showInputDialog("enter shape number");
		return Double.parseDouble(number);
	}

	public void addNameToComboBox(int length) {
		ArrayList<String> shapesNumbers = getMeShapes(length);
		comboBox_1.setModel(new DefaultComboBoxModel<>(shapesNumbers.toArray()));
	}

	private ArrayList<String> getMeShapes(int length) {
		ArrayList<String> names = new ArrayList<String>();
		names.add("Non");
		for (int i = 0; i < length; i++) {
			names.add(String.format("Shape %d", i + 1));
		}
		return names;
	}

	public int getDrawnShapesComboBoxIndex() {
		return comboBox_1.getSelectedIndex();
	}

	public void setRemoveButtonListener(ActionListener listener) {
		btnNewButton_4.addActionListener(listener);
	}

	public void setUpdateButtonListener(ActionListener Listener) {
		btnNewButton_5.addActionListener(Listener);
	}

	public void setSaveButtonListener(ActionListener listener) {
		btnNewButton_2.addActionListener(listener);
	}

	public void setLoadButtonListener(ActionListener listener) {
		btnNewButton_3.addActionListener(listener);
	}

	public String getFilePath() {
		String path = JOptionPane.showInputDialog("enter full path");
		return path;
	}
	
}
