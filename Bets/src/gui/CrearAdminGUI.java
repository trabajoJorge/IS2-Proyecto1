package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Software Engineering teachers
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.SystemColor;


public class CrearAdminGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	

    private static BLFacade appFacadeInterface;
    private JTextField textFieldNombre;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public CrearAdminGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle("Crear usuario admin");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JButton atras = new JButton("Cerrar"); //$NON-NLS-1$ //$NON-NLS-2$
			atras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
					}
				}
			);
			
			
			atras.setBounds(6, 233, 129, 29);
			jContentPane.add(atras);
			
			JButton btnCrear = new JButton("Crear"); //$NON-NLS-1$ //$NON-NLS-2$
			btnCrear.setBounds(360, 233, 129, 29);
			jContentPane.add(btnCrear);
			
			JTextArea resultDialog = new JTextArea();
			resultDialog.setWrapStyleWord(true);
			resultDialog.setLineWrap(true);
			resultDialog.setBackground(SystemColor.window);
			resultDialog.setBounds(360, 200, 129, 29);
			jContentPane.add(resultDialog);
			
			JLabel lblNombre = new JLabel("Nombre: ");
			lblNombre.setBounds(21, 29, 178, 16);
			jContentPane.add(lblNombre);
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String ps1= new String(passwordField.getPassword());
					String ps2= new String(passwordField_1.getPassword());
					String em=textFieldEmail.getText();
					
					if(textFieldNombre.getText().length()!=0 && textFieldUsername.getText().length()!=0 && textFieldEmail.getText().length()!=0 & passwordField.getPassword().length!=0) {
						if (verificarPasswords(ps1, ps2)) {
							if (verificarEmail(em)) {
								
								BLFacade facade=MainGUI.getBusinessLogic();
								boolean registrado = facade.createAdmin(textFieldNombre.getText(), textFieldUsername.getText(), textFieldEmail.getText(), new String(passwordField.getPassword()));
								
								if (registrado) {
									resultDialog.setText("Admin created.");
									resultDialog.setForeground(new Color(0,128,0));
									
									//VACÍO LOS CAMPOS
									textFieldNombre.setText("");
									textFieldUsername.setText("");
									textFieldEmail.setText("");
									passwordField.setText("");
									passwordField_1.setText("");
								} else {
									resultDialog.setText("Error creating Admin.");
									resultDialog.setForeground(new Color(128,0,0));
								}
							} else {
								JOptionPane.showMessageDialog(null, "Wrong email address.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Passwords does not match.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Fields can't be empty.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
					}
				}
			});

			JLabel lblUsername = new JLabel("Username: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblUsername.setBounds(21, 57, 178, 16);
			jContentPane.add(lblUsername);
			
			JLabel lblEmail = new JLabel("Email: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblEmail.setBounds(21, 85, 178, 16);
			jContentPane.add(lblEmail);
			
			JLabel lblPass = new JLabel("Password: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblPass.setBounds(21, 113, 129, 16);
			jContentPane.add(lblPass);
			
			textFieldNombre = new JTextField();
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setBounds(211, 24, 278, 26);
			jContentPane.add(textFieldNombre);
			textFieldNombre.setColumns(10);
			textFieldNombre.setEditable(true);
			
			textFieldUsername = new JTextField();
			textFieldUsername.setText((String) null);
			textFieldUsername.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldUsername.setColumns(10);
			textFieldUsername.setBounds(211, 52, 278, 26);
			jContentPane.add(textFieldUsername);
			textFieldUsername.setEditable(true);
			
			textFieldEmail = new JTextField();
			textFieldEmail.setText((String) null);
			textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(211, 80, 278, 26);
			jContentPane.add(textFieldEmail);

			textFieldEmail.setEditable(true);
			
			passwordField = new JPasswordField();
			passwordField.setHorizontalAlignment(SwingConstants.CENTER);
			passwordField.setBounds(211, 108, 278, 26);
			jContentPane.add(passwordField);
			
			passwordField_1 = new JPasswordField();
			passwordField_1.setHorizontalAlignment(SwingConstants.CENTER);
			passwordField_1.setBounds(211, 138, 278, 26);
			jContentPane.add(passwordField_1);
			
			JLabel lblRepassword = new JLabel("Re-Password: ");
			lblRepassword.setBounds(21, 141, 129, 16);
			jContentPane.add(lblRepassword);
		
		}
		return jContentPane;
	}
	

	
	public boolean verificarPasswords(String ppass1, String ppass2) {
		return ppass1.equals(ppass2);
	}
	
	
	
	
	public boolean verificarEmail(String pemail) {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(pemail);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
        
	}

	
	private void close1(){
		this.dispose();
	}	
} // @j200, 129, 29x=0:visual-constraint="0,0"