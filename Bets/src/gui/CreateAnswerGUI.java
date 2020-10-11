package gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Answer;
import domain.Event;
import domain.Question;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;

public class CreateAnswerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JPanel contentPane;
	
	

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private final JLabel lblEligeDia = new JLabel("Elige dia del evento:");
	private Date firstDay;
	private JComboBox<Event> comboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	private final JLabel lblPregunta = new JLabel("Pregunta:");
	private final JLabel lblEvento = new JLabel("Evento: ");
	private  JComboBox<Question> comboBox = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	private final JLabel lblPronostico = new JLabel("Respuesta:");
	private final JTextField textField = new JTextField();
	private final JButton btnAadirPronostico = new JButton("Añadir respuesta");
	private final JLabel labelExito = new JLabel("AÑADIDO CON EXITO");
	private JTextField coeficiente;
	private float coeficienteFloat;
	
	



	/**
	 * Create the frame.
	 */
	public CreateAnswerGUI() {
		textField.setBounds(275, 162, 326, 38);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		coeficiente = new JTextField();
		coeficiente.setBounds(512, 206, 89, 26);
		contentPane.add(coeficiente);
		coeficiente.setColumns(10);
		labelExito.setHorizontalAlignment(SwingConstants.CENTER);
		labelExito.setVisible(false);



		this.setTitle("Añadir respuesta ");


		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		contentPane.add(jCalendar);
		lblEligeDia.setBounds(40, 25, 145, 14);

		contentPane.add(lblEligeDia);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close1();
			}
		});
		btnCerrar.setBounds(40, 286, 89, 23);
		contentPane.add(btnCerrar);
		comboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Event e = (Event) comboBoxEvents.getSelectedItem();
				
				System.out.println(e);
				
				try {
					
					
					Collection<Question> q = e.getQuestions();
					comboBox.removeAllItems();
					
					System.out.println("Hemos sacado estas preguntas de la query: " + q);
					
					for (Question question : q) {
						if (question.getResult()==false) {
							modelQuestions.addElement(question);
						}
					}
					
					comboBox.setModel(modelQuestions);
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("POR EL CATCH");
					return;
				}
				
				
				
				
				
			}
		});
		comboBoxEvents.setBounds(275, 50, 326, 20);
		
		contentPane.add(comboBoxEvents);
		lblPregunta.setBounds(275, 81, 71, 14);
		
		contentPane.add(lblPregunta);
		lblEvento.setBounds(275, 25, 71, 14);
		
		contentPane.add(lblEvento);
		comboBox.setBounds(275, 106, 326, 20);
		
		contentPane.add(comboBox);
		lblPronostico.setBounds(275, 137, 71, 14);
		
		contentPane.add(lblPronostico);
		
		contentPane.add(textField);
		btnAadirPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Question q = (Question) comboBox.getSelectedItem();
				
				if (textField.getText().length()!=0) {
					
					try {
						coeficienteFloat = Float.parseFloat(coeficiente.getText());
					
						Answer a = new Answer(textField.getText(), coeficienteFloat, q);
						
						BLFacade facade = MainGUI.getBusinessLogic();
						
						boolean save = facade.insertAnswer(a);
						
						if(save) {
							labelExito.setText("RESPUESTA INGRESADA.");
							labelExito.setVisible(true);
							textField.setText("");
						} else {
							labelExito.setText("ERROR AL INGRESAR RESPUESTA.");
							labelExito.setVisible(true);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Coeficiente de ganancia debe ser número.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Fields can't be empty.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		btnAadirPronostico.setBounds(465, 286, 136, 23);
		
		contentPane.add(btnAadirPronostico);
		labelExito.setForeground(new Color(0,128,0));
		labelExito.setBounds(456, 260, 145, 14);
		
		contentPane.add(labelExito);
		
		JLabel lblCoeficienteDeGanancia = new JLabel("Coeficiente de ganancia:");
		lblCoeficienteDeGanancia.setBounds(275, 212, 197, 14);
		contentPane.add(lblCoeficienteDeGanancia);
		
		
		


		jCalendar.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if (evt.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) evt.getNewValue());
				} else if (evt.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) evt.getNewValue();
					jCalendar.setCalendar(calendarMio);
					firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					comboBoxEvents.removeAllItems();
					System.out.println(firstDay);

					BLFacade facade = MainGUI.getBusinessLogic();
					
					Collection<Event> eventos = facade.getOpenEvents(firstDay);

					for (Event event : eventos) {
						modelEvents.addElement(event);
					}
					comboBoxEvents.setModel(modelEvents);

				}
				paintDaysWithEvents(jCalendar);
			}
		});


	}
	
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		Vector<Date> dates=facade.getEventsMonth(jCalendar.getDate());
			
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		//int today=calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:dates){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
	 		calendar.set(Calendar.DAY_OF_MONTH, 1);
	 		calendar.set(Calendar.MONTH, month);
	 	
	}
	

	private void close1(){
		this.dispose();
	}
}
