package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Event;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Admin adm=null;;
	private JPanel jContentPane = null;
	private JButton jButtonCrearEvento = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonSetResults = null;
	private JButton jButtonVerMovimientosCuenta=null;
	private JButton jButtonCrearAdmin=null;
	private JButton JButtonEditarPregunta = null;
	private JButton JButtonEditarEvento = null;
	private JButton JButtonInsertarRespuesta = null;
	private JButton JButtonEditarRespuesta = null;

	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	

	
	public AdminGUI(Admin admin, JPanel jContentPane2) {
		super();
		getContentPane().setLayout(null);

		adm=admin;
		
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	


	
	
	private void initialize(JPanel jContentPane2) {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane(jContentPane2));
		this.setTitle("Vista admin");
	}


	
	private JPanel getJContentPane(final JPanel jContentPane2) {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(JButtonCrearEvento());
			jContentPane.add(JButtonConsultarPregunta());
			jContentPane.add(JButtonCrearPregunta());
			jContentPane.add(JButtonDefinirResultados());
			jContentPane.add(JButtonVerMovimientosCuenta());
			jContentPane.add(JButtonCrearAdmin());
			jContentPane.add(JButtonEditarPregunta());
			jContentPane.add(JButtonEditarEvento());
			jContentPane.add(JButtonInsertarRespuesta());
			jContentPane.add(JButtonEditarRespuesta());
			jContentPane.add(getPanel());
			
					
			JButton JButtonCerrar = new JButton("Logout");
			JButtonCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jContentPane2.setVisible(true);
					close1();
				}
			});
			JButtonCerrar.setBounds(372, 232, 117, 30);
			jContentPane.add(JButtonCerrar);
			
			JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.lblNewLabel.text"));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel.setBounds(197, 22, 109, 16);
			jContentPane.add(lblNewLabel);
		}
		return jContentPane;
	}



	private JButton JButtonCrearEvento() {
		if (jButtonCrearEvento == null) {
			jButtonCrearEvento = new JButton();
			jButtonCrearEvento.setBounds(6, 67, 242, 32);
			jButtonCrearEvento.setText("Crear Evento");
			jButtonCrearEvento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateEventGUI();
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonCrearEvento;
	}
	
	private JButton JButtonCrearPregunta() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(6, 99, 242, 32);
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	
	private JButton JButtonDefinirResultados() {
		if (jButtonSetResults == null) {
			jButtonSetResults = new JButton();
			jButtonSetResults.setBounds(6, 163, 242, 32);
			jButtonSetResults.setText("Definir resultados");
			jButtonSetResults.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new SetResultsGUI(new Vector<Event>(),adm);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonSetResults;
	}
	

	
	private JButton JButtonConsultarPregunta() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(6, 195, 242, 32);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}
	
	private JButton JButtonVerMovimientosCuenta() {
		if (jButtonVerMovimientosCuenta == null) {
			jButtonVerMovimientosCuenta = new JButton();
			jButtonVerMovimientosCuenta.setBounds(246, 163, 242, 32);
			jButtonVerMovimientosCuenta.setText("Movimientos de cuenta");
			jButtonVerMovimientosCuenta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new MovimientosCuentaGUI();
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonVerMovimientosCuenta;
	}
	
	private JButton JButtonCrearAdmin() {
		if (jButtonCrearAdmin == null) {
			jButtonCrearAdmin = new JButton();
			jButtonCrearAdmin.setBounds(246, 195, 242, 32);
			jButtonCrearAdmin.setText("Crear usuario admin");
			jButtonCrearAdmin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CrearAdminGUI();
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return jButtonCrearAdmin;
	}
	

	private JButton JButtonEditarEvento() {
		if (JButtonEditarEvento == null) {
			JButtonEditarEvento = new JButton();
			JButtonEditarEvento.setBounds(246, 67, 242, 32);
			JButtonEditarEvento.setText("Editar Evento");
			JButtonEditarEvento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new UpdateEventGUI(new Vector<Event>());
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return JButtonEditarEvento;
	}

	private JButton JButtonEditarPregunta() {
		if (JButtonEditarPregunta == null) {
			JButtonEditarPregunta = new JButton();
			JButtonEditarPregunta.setBounds(246, 99, 242, 32);
			JButtonEditarPregunta.setText("Editar Pregunta");
			JButtonEditarPregunta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new UpdateQuestionGUI(new Vector<Event>());
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return JButtonEditarPregunta;
	}

	

	private JButton JButtonEditarRespuesta() {
		if (JButtonEditarRespuesta == null) {
			JButtonEditarRespuesta = new JButton();
			JButtonEditarRespuesta.setBounds(246, 131, 242, 32);
			JButtonEditarRespuesta.setText("Editar Respuesta");
			JButtonEditarRespuesta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new UpdateAnswerGUI(new Vector<Event>());
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return JButtonEditarRespuesta;
	}

	

	private JButton JButtonInsertarRespuesta() {
		if (JButtonInsertarRespuesta == null) {
			JButtonInsertarRespuesta = new JButton();
			JButtonInsertarRespuesta.setBounds(6, 131, 242, 32);
			JButtonInsertarRespuesta.setText("Crear Respuesta");
			JButtonInsertarRespuesta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateAnswerGUI();
					a.setLocationRelativeTo(null);
					a.setVisible(true);
				}
			});
		}
		return JButtonInsertarRespuesta;
	}

	
	
	
	
	
	
	
	
	
	//ROUNDS//
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setBounds(230, 5, 78, 23);
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
			rdbtnNewRadioButton_1.setBounds(41, 5, 81, 23);
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
			rdbtnNewRadioButton_2.setBounds(127, 5, 98, 23);
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
			panel.setBounds(6, 229, 350, 33);
			panel.setLayout(null);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
	
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	
	private void close1(){
		this.dispose();
	}
} // @jve:decl-index=0:visual-constraint="0,0"

