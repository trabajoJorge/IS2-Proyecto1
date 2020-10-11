package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Cliente;
import domain.Event;
import businessLogic.BLFacade;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ClientGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonPerfil = null;
	private JButton jButtonMonedero = null;
	private JButton jButtonAnular = null;
	
	private Cliente cli=null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	
	/**
	 * This is the default constructor
	 */
	public ClientGUI(Cliente user, JPanel jContentPane2) {
		super();
		cli = user;
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		initialize(jContentPane2);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(JPanel jContentPane2) {
		// this.setSize(271, 295);
		this.setSize(511, 303);
		this.setContentPane(getJContentPane(jContentPane2));
		this.setTitle("Vista cliente");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(JPanel jContentPane2) {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getBoton4());
			jContentPane.add(getBoton5());
			jContentPane.add(getBoton6());
			jContentPane.add(getPanel());
			jContentPane.add(getBtnNewButton(jContentPane2));
			jContentPane.add(getLblNewLabel());
		}
		return jContentPane;
	}


	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(1, 70, 250, 32);
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeHistory"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//BLFacade facade=MainGUI.getBusinessLogic();
					//Vector<Event> events=facade.getAllEvents();
					JFrame a = new UserBetsHistoryGUI(cli);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
					//jContentPane.setVisible(false);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(1, 102, 250, 32);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("DoBet"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ApostarGUI(new Vector<Event>(), cli);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}

	private JButton getBoton4() {
		if (jButtonPerfil == null) {
			jButtonPerfil = new JButton();
			jButtonPerfil.setBounds(252, 70, 250, 32);
			jButtonPerfil.setText("Ver/Editar Perfil");
			jButtonPerfil.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new VerPerfilClienteGUI(cli);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonPerfil;
	}

	private JButton getBoton5() {
		if (jButtonMonedero == null) {
			jButtonMonedero = new JButton();
			jButtonMonedero.setBounds(252, 102, 250, 32);
			jButtonMonedero.setText("Cargar Monedero");
			jButtonMonedero.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CargarMonederoGUI(cli);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonMonedero;
	}
	
	
	private JButton getBoton6() {
		if (jButtonAnular == null) {
			jButtonAnular = new JButton();
			jButtonAnular.setBounds(1, 134, 250, 32);
			jButtonAnular.setText("Anular apuesta");
			jButtonAnular.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new AnularApuestaGUI(cli);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonAnular;
	}
	
	
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setBounds(226, 6, 78, 23);
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.setBounds(37, 6, 81, 23);
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.setBounds(123, 6, 98, 23);
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(6, 242, 321, 33);
			panel.setLayout(null);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	
	private void redibujar() {
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("DoBet"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeHistory"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	
	private JButton getBtnNewButton(final JPanel jContentPane2) {
		if (btnNewButton == null) {
			btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ClientGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jContentPane2.setVisible(true);
					close1();
				}
			});
			btnNewButton.setBounds(374, 242, 117, 33);
		}
		return btnNewButton;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ClientGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel.setBounds(153, 25, 205, 43);
		}
		return lblNewLabel;
	}
	
	private void close1(){
		this.dispose();
	}
} // @jve:decl-index=0:visual-constraint="0,0"
