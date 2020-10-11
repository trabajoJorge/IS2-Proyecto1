package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
/**
 * @author Software Engineering teachers
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Apuesta;
import domain.Cliente;
import javax.swing.JComboBox;

public class AnularApuestaGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	JLabel success = new JLabel("");
	
	private Cliente cl;

	private JComboBox<Apuesta> jComboBoxApuestas = new JComboBox<Apuesta>();
	DefaultComboBoxModel<Apuesta> modelApuestas = new DefaultComboBoxModel<Apuesta>();

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	
	/**
	 * This is the default constructor
	 */
	public AnularApuestaGUI(Cliente pcl) {
		cl=pcl;
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
		this.setSize(495, 214);
		this.setContentPane(getJContentPane());
		this.setTitle("Anular apuesta");
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
			jContentPane.add(getLblNewLabel());
			
			JButton jButtonCerrar = new JButton("Cancelar"); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
				}
			});
			jButtonCerrar.setBounds(22, 150, 117, 29);
			jContentPane.add(jButtonCerrar);
			
			JLabel lblNewLabel = new JLabel("Se muestran únicamente las apuestas pendientes.");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(88, 57, 324, 16);
			jContentPane.add(lblNewLabel);
			
			
			actualizarApuestas();
			
			
			JButton btnAnular = new JButton("Anular");
			btnAnular.setBounds(353, 150, 117, 29);
			jContentPane.add(btnAnular);
			btnAnular.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BLFacade facade=MainGUI.getBusinessLogic();
					Apuesta a = (Apuesta)jComboBoxApuestas.getSelectedItem();
					if (facade.anularApuesta(a)) {
						success.setText("Apuesta anulada.");
						success.setForeground(new Color(0,128,0));
						actualizarApuestas();;
					} else {
						success.setText("Problema de anular.");
						success.setForeground(new Color(128,0,0));
						
					}
				}
			});

			
			jComboBoxApuestas.setBounds(22, 78, 448, 27);
			jContentPane.add(jComboBoxApuestas);
			success.setHorizontalAlignment(SwingConstants.CENTER);
			success.setBounds(167, 155, 174, 16);
			jContentPane.add(success);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		return jContentPane;
	}
	

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel("Anular apuesta");
			jLabelSelectOption.setBounds(142, 6, 223, 29);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	
	public void actualizarApuestas () {
		jComboBoxApuestas.removeAllItems();
		try {
			BLFacade facade=MainGUI.getBusinessLogic();
			ArrayList <Apuesta> apuestas_by_client= facade.BetsByClient(cl);
				for (Apuesta c : apuestas_by_client) {
					if(c.getEstado()==0) {
						modelApuestas.addElement(c);
					}
				}
				jComboBoxApuestas.setModel(modelApuestas);
				jComboBoxApuestas.repaint();
		} catch(Exception ex) {
			success.setText("Exception >> AnularApuestaGUI: "+ex.getMessage());
			success.setForeground(new Color(128,0,0));
		}
	}
	
	

	private void close1(){
		this.dispose();
	}	
} // @jve:decl-index=0:visual-constraint="0,0"