package gameClient;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class MyPanel extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passwordLabel ;
	private static JTextField passwordField;
	private static JButton button;
	static Thread trd;

	public MyPanel(Ex2 ex) {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(350 , 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);

		userLabel = new JLabel("User ID :");
		userLabel.setBounds(10 ,20 ,80 ,25);
		panel.add(userLabel);
		userText = new JTextField(20);
		userText.setBounds(100,20,165,25);
		panel.add(userText);

		passwordLabel = new JLabel("Level");
		passwordLabel.setBounds(10,50,80,25);
		panel.add(passwordLabel);
		passwordField = new JTextField(20);
		passwordField.setBounds(100,50,165,25);
		panel.add(passwordField);

		button = new JButton("Start");
		button.setBounds(10,80,80,25);
		panel.add(button);
		button.addActionListener(this);


		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		String idString = userText.getText();
		long id= Long.parseLong(idString);
		if(id == 323266254 || id == 305035453) {
			String text = passwordField.getText();
			Ex2.scenario_num = Integer.parseInt(text);
			System.out.println("Good Luck");
			trd.start();
		}else System.out.println("The ID is not valid , please insert the right ID number");
	}


}
