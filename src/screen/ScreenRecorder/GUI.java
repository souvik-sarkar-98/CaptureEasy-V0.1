package screen.ScreenRecorder;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JRadioButton;

public class GUI {

	private JFrame frame;
	private JPanel buttonPanel;
	private JButton button_2;
	private JButton button_1;
	private JPanel panel;
	private JTextArea textArealabel;
	private JTextField textField;
	private JLabel lblEnterFileName;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 251, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		textArealabel = new JTextArea();
		textArealabel.setRows(1);
		textArealabel.setMargin(new Insets(5, 5, 5, 5));
		textArealabel.setLineWrap(true);
		textArealabel.setForeground(Color.RED);
		textArealabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		textArealabel.setBorder(UIManager.getBorder("TextArea.border"));
		textArealabel.setEditable(false);
		@SuppressWarnings("unused")
		int a=textArealabel.getRows();
		textArealabel.setBackground(UIManager.getColor("CheckBox.background"));
		textArealabel.setColumns(20);
		textArealabel.setText("a");
		panel.add(textArealabel);
		
		rdbtnNewRadioButton = new JRadioButton("");
		panel.add(rdbtnNewRadioButton);
		
		radioButton = new JRadioButton("");
		panel.add(radioButton);
		
		radioButton_1 = new JRadioButton("");
		panel.add(radioButton_1);
		
		lblEnterFileName = new JLabel("Enter File Name: ");
		lblEnterFileName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblEnterFileName);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(textField);
		textField.setColumns(12);
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		
		button_1 = new JButton("New button");
		buttonPanel.add(button_1);
		
		button_2 = new JButton("New button");
		buttonPanel.add(button_2);
	}

}
