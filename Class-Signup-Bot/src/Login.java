import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import static java.lang.System.out;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import javax.swing.JDesktopPane;

public class Login {

	private JFrame frame;
	private JLabel lblSid;
	private JTextField sidField;
	private JLabel lblPin;
	private JPasswordField pinField;
	
	Core webReq = new Core();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 354, 186);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblSid = new JLabel("SID");
		lblSid.setBounds(10, 33, 46, 14);
		frame.getContentPane().add(lblSid);
		
		sidField = new JTextField();
		sidField.setText("94042XXX");
		sidField.setBounds(66, 30, 86, 20);
		frame.getContentPane().add(sidField);
		sidField.setColumns(10);
		
		lblPin = new JLabel("PIN");
		lblPin.setBounds(10, 98, 46, 14);
		frame.getContentPane().add(lblPin);
		
		pinField = new JPasswordField();
		pinField.setText("password");
		pinField.setBounds(66, 95, 86, 20);
		frame.getContentPane().add(pinField);
		pinField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sid = sidField.getText();
				String pin = pinField.getText();
				try {
					webReq.init(sid, pin);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(186, 59, 89, 23);
		frame.getContentPane().add(btnLogin);
	}
}
