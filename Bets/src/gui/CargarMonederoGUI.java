package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Cliente;

import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Font;


public class CargarMonederoGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	private Cliente cl;

    private static BLFacade appFacadeInterface;
    private JTextField pin;
    private JTextField montoaRecargar;

	private JComboBox<String> ComboBoxPago= new JComboBox<String>();
	DefaultComboBoxModel<String> modelComboBoxPago = new DefaultComboBoxModel<String>();
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public CargarMonederoGUI(Cliente pcl) {
		super();
		this.cl=pcl;
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
		this.setSize(672, 202);
		this.setContentPane(getJContentPane());
		this.setTitle("Cargar Monedero");
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
			
			

			BLFacade facade=MainGUI.getBusinessLogic();
			
			Cliente clientedb = facade.getClientByUsername(cl.getUsername());
			
			atras.setBounds(6, 140, 142, 29);
			jContentPane.add(atras);
			
			JButton btnAceptar = new JButton("Confirmar pago"); //$NON-NLS-1$ //$NON-NLS-2$
			btnAceptar.setBounds(524, 140, 142, 29);
			jContentPane.add(btnAceptar);

			pin = new JTextField();
			pin.setBounds(594, 57, 61, 26);
			jContentPane.add(pin);
			pin.setColumns(10);
			pin.setVisible(false);
			
			JLabel pinlabel = new JLabel("CVC:");
			pinlabel.setBounds(569, 62, 43, 16);
			jContentPane.add(pinlabel);
			
			JLabel lblTarjeta = new JLabel("Seleccione medio de pago:"); //$NON-NLS-1$ //$NON-NLS-2$
			lblTarjeta.setBounds(25, 62, 178, 16);
			jContentPane.add(lblTarjeta);
			pinlabel.setVisible(false);

			modelComboBoxPago.addElement(clientedb.getCC());
			modelComboBoxPago.addElement("VISA "+clientedb.getTarjeta());
			ComboBoxPago.setModel(modelComboBoxPago);
			
			ComboBoxPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ComboBoxPago.getSelectedItem().equals("VISA "+clientedb.getTarjeta())) {
						pinlabel.setVisible(true);
						pin.setVisible(true);
						pin.setText("123");
					} else {
						pinlabel.setVisible(false);
						pin.setVisible(false);
					}
				}
			});
			ComboBoxPago.setBounds(200, 58, 357, 27);
			jContentPane.add(ComboBoxPago);
			
			
			JLabel lblNewLabel_1 = new JLabel("Monto a recargar:");
			lblNewLabel_1.setBounds(82, 99, 110, 16);
			jContentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("Saldo actual: "+clientedb.getSaldo()+"€");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel_2.setBounds(210, 6, 247, 39);
			jContentPane.add(lblNewLabel_2);
			
			montoaRecargar = new JTextField();
			montoaRecargar.setHorizontalAlignment(SwingConstants.CENTER);
			montoaRecargar.setBounds(200, 94, 130, 26);
			jContentPane.add(montoaRecargar);
			montoaRecargar.setColumns(10);
			
			JLabel success = new JLabel("");
			success.setHorizontalAlignment(SwingConstants.CENTER);
			success.setBounds(219, 146, 232, 16);
			jContentPane.add(success);
			
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (ComboBoxPago.getSelectedItem().equals("VISA "+clientedb.getTarjeta()) && pin.getText().length()!=3) {
							JOptionPane.showMessageDialog(null, "El CVC debe tener tres números (XXX).");
						} else {
							if (montoaRecargar.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Debe ingresar un monto a acreditar.");
							} else {
								if (ComboBoxPago.getSelectedItem().equals("VISA "+clientedb.getTarjeta())) {
									BLFacade facade = MainGUI.getBusinessLogic();
									float monto = Float.parseFloat(montoaRecargar.getText());
									int pinint = Integer.parseInt(pin.getText());
									boolean result =  facade.acreditarSaldo(clientedb, monto, pinint, "t");
									if (result) {
										success.setForeground(new Color(0,128,0));
										success.setText("Saldo acreditado.");
									} else {
										success.setForeground(new Color(128,0,0));
										success.setText("Error de transacción.");
									}
								} else {
									BLFacade facade = MainGUI.getBusinessLogic();
									float monto = Float.parseFloat(montoaRecargar.getText());
									boolean result =  facade.acreditarSaldo(clientedb, monto, 0, "c");
									if (result) {
										success.setForeground(new Color(0,128,0));
										success.setText("Saldo acreditado.");
									} else {
										success.setForeground(new Color(128,0,0));
										success.setText("Error de transacción.");
									}
								}
								Cliente clienteActualizado= facade.getClientByUsername(clientedb.getUsername());
								lblNewLabel_2.setText("Saldo actual: "+clienteActualizado.getSaldo()+"€");
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Exception >> CargarMonederoGUI");
					}
				}
			});
		}

		return jContentPane;
	}

	private void close1(){
		this.dispose();
	}	
} // @jve:decl-index=0:visual-constraint="0,0"