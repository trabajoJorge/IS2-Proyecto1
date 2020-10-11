package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import domain.CuentaGlobal;


public class MovimientosCuentaGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	

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
	public MovimientosCuentaGUI() {
		super();
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
		this.setSize(696, 392);
		this.setContentPane(getJContentPane());
		this.setTitle("Movimientos de Cuenta");
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
			
			JButton closeUBH = new JButton("Cerrar"); //$NON-NLS-1$ //$NON-NLS-2$
			closeUBH.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
					}
				}
			);
			closeUBH.setBounds(292, 335, 117, 29);
			jContentPane.add(closeUBH);
			
			TextArea textArea = new TextArea();
			textArea.setEditable(false);
			textArea.setBounds(43, 45, 614, 284);
			BLFacade facade=MainGUI.getBusinessLogic();
			CuentaGlobal cg = facade.getCuentaGlobal();
			String texto="";
			String strDateFormat = "dd-MM-yyyy";
			ArrayList <Apuesta> A_apuestas_by_client= new ArrayList<Apuesta>(cg.getBets());
			String estado=null;
			
				for (Apuesta c : A_apuestas_by_client) {
					SimpleDateFormat fecha = new SimpleDateFormat(strDateFormat);
					
					if (c.getEstado()==0) {
						estado="Pendiente";
						texto=texto+"Apuesta #"+c.getIdBet()+"("+estado+") \t "+c.getCliente().getName()+"("+c.getCliente().getUsername()+") \t "+fecha.format(c.getBetDate())+"\t\t+"+c.getAmount()+"\n";
					}
					if (c.getEstado()==1) {
						estado="Perdida";
						texto=texto+"Apuesta #"+c.getIdBet()+"("+estado+") \t "+c.getCliente().getName()+"("+c.getCliente().getUsername()+") \t "+fecha.format(c.getBetDate())+"\t\t+"+c.getAmount()+"\n";
					}
					if (c.getEstado()==2) {
						estado="Anulada p/ adm";
						texto=texto+"Apuesta #"+c.getIdBet()+"("+estado+") \t "+c.getCliente().getName()+"("+c.getCliente().getUsername()+") \t "+fecha.format(c.getBetDate())+"\t\t-"+c.getAmount()+"\n";
					}
					if (c.getEstado()==3) {
						estado="Ganada";
						texto=texto+"Apuesta #"+c.getIdBet()+"("+estado+") \t "+c.getCliente().getName()+"("+c.getCliente().getUsername()+") \t "+fecha.format(c.getBetDate())+"\t\t-"+c.getAmount()*c.getAnswer().getCoeficienteGanancia()+"\n";	
					}
					if (c.getEstado()==4) {
						estado="Anulada p/ cli";
						texto=texto+"Apuesta #"+c.getIdBet()+"("+estado+") \t "+c.getCliente().getName()+"("+c.getCliente().getUsername()+") \t "+fecha.format(c.getBetDate())+"\t\t-"+c.getAmount()+"\n";
					}

					
				}
				
				
				/*System.out.println("Partido: "+ A_apuestas_by_client.get(i).getIdBet());
				System.out.println("Cantidad Apostada: "+ A_apuestas_by_client.get(i).getAmount());
				System.out.println("Pronostico: "+ A_apuestas_by_client.get(i).getAnswer());
				System.out.println("Fecha: "+ A_apuestas_by_client.get(i).getBetDate());*/
				textArea.setText(texto);
			
			jContentPane.add(textArea);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		return jContentPane;
	}
	

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			BLFacade facade=MainGUI.getBusinessLogic();
			CuentaGlobal cg = facade.getCuentaGlobal();
			float monto= cg.getSaldoGlobal();
			String texto= "Saldo de Cuenta "+monto+"€";
			jLabelSelectOption = new JLabel(texto);
			jLabelSelectOption.setBounds(234, 10, 223, 29);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	

	private void close1(){
		this.dispose();
	}	
	
} // @jve:decl-index=0:visual-constraint="0,0"