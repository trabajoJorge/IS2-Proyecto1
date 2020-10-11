package gui;

import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Answer;
import domain.Cliente;
import domain.Event;
import domain.Question;

public class ApostarGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private JComboBox<Answer> jComboBoxAnswers = new JComboBox<Answer>();
	DefaultComboBoxModel<Answer> modelAnswers = new DefaultComboBoxModel<Answer>();
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField monto = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private Answer selectedAnswer=null;
	
	private Cliente currentClient;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private Question selectedQuestion;
	private Event selectedEvent;

	private JButton jButtonCreate = new JButton("Confirmar apuesta");
//	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonClose = new JButton("Cancelar");
	private final JLabel lblSeleccionarPresgunta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarPresgunta.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblSeleccionarEvento = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarEvento.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel tituloMonto = new JLabel("Monto a apostar");
	private final JLabel minimumCaption = new JLabel();
	private final JTextArea resultDialog = new JTextArea();

	public ApostarGUI(Vector<domain.Event> v, Cliente pcli) {
		try {
			currentClient=pcli;
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {
		this.setSize(new Dimension(743, 359));
		this.setTitle("Crear apuesta");
		jComboBoxEvents.setBounds(460, 50, 250, 20);
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setEnabled(false);
		jComboBoxQuestions.setBounds(460, 93, 250, 20);
		jComboBoxQuestions.setEnabled(false);
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedEvent = (Event) jComboBoxEvents.getSelectedItem();
			
				try {
					BLFacade facade = MainGUI.getBusinessLogic();

					List<Question> questions = facade.getOpenQuestionsByEvent(selectedEvent);

					jComboBoxAnswers.removeAllItems();
					jComboBoxQuestions.removeAllItems();
					System.out.println("Preguntas " + questions);

					for (domain.Question q : questions)
						modelQuestions.addElement(q);
						jComboBoxQuestions.repaint();

					if (questions.size() == 0) {
						jComboBoxQuestions.setEnabled(false);
						jComboBoxAnswers.setEnabled(false);
						checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
						
					} else {
						jComboBoxQuestions.setEnabled(true);
						checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
					}
				} catch (Exception e1) {

					//JOptionPane.showMessageDialog(null, "Error en llenar JComboBoxQuestion");
				}
				
			}
		});
		monto.setHorizontalAlignment(SwingConstants.CENTER);
		monto.setBounds(460, 189, 250, 20);
		monto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setBounds(543, 239, 162, 40);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(40, 239, 155, 40);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		getContentPane().setLayout(null);

		this.getContentPane().add(jButtonClose);
		this.getContentPane().add(jButtonCreate);
		this.getContentPane().add(monto);
		this.getContentPane().add(jComboBoxEvents);
		jCalendar.setBounds(40, 50, 225, 150);

		this.getContentPane().add(jCalendar);
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		lblSeleccionarPresgunta.setBounds(291, 93, 155, 20);
		jButtonCreate.setEnabled(false);
		
		getContentPane().add(lblSeleccionarPresgunta);

		jComboBoxQuestions.setModel(modelQuestions);
		getContentPane().add(jComboBoxQuestions);
		
		jComboBoxQuestions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedQuestion = (Question) jComboBoxQuestions.getSelectedItem();
				
					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						List<Answer> answers = facade.getAnswersByQuestion(selectedQuestion);
						
						jComboBoxAnswers.removeAllItems();
						System.out.println("Respuestas " + answers);

						for (domain.Answer ans : answers)
							modelAnswers.addElement(ans);
							jComboBoxAnswers.repaint();
							
							//JOptionPane.showMessageDialog(null,"# de ans: "+ answers.size());
							
						if (answers.size() == 0) {
							jComboBoxAnswers.setEnabled(false);
							checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
						} else {
							jComboBoxAnswers.setEnabled(true);
							minimumCaption.setText("La apuesta mínima es "+selectedQuestion.getBetMinimum()+" €");
							checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
						}
					} catch (Exception e1) {
						System.out.println("Excepción en la carga del combobox de respuestas.");
						
					}
					
				}
			});
		jComboBoxAnswers.setBounds(460, 139, 250, 20);
		jComboBoxAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = (Answer) jComboBoxAnswers.getSelectedItem();

			}
		});
		jComboBoxAnswers.setEnabled(false);
		jComboBoxAnswers.setModel(modelAnswers);
		getContentPane().add(jComboBoxAnswers);
		
		JLabel lblSeleccionarRespuesta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarRespuesta.text"));
		lblSeleccionarRespuesta.setBounds(291, 140, 155, 20);
		getContentPane().add(lblSeleccionarRespuesta);
		lblSeleccionarEvento.setBounds(293, 50, 155, 20);
		
		getContentPane().add(lblSeleccionarEvento);
		tituloMonto.setBounds(291, 191, 151, 16);
		
		getContentPane().add(tituloMonto);
		minimumCaption.setHorizontalAlignment(SwingConstants.CENTER);
		minimumCaption.setBounds(460, 206, 250, 16);
		
		getContentPane().add(minimumCaption);
		resultDialog.setWrapStyleWord(true);
		resultDialog.setLineWrap(true);
		resultDialog.setEditable(false);
		resultDialog.setBackground(UIManager.getColor("Button.background"));
		resultDialog.setText("");
		resultDialog.setBounds(543, 280, 167, 40);
		
		getContentPane().add(resultDialog);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					jCalendar.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getOpenEvents(firstDay);

						if (events.isEmpty()) {
							jComboBoxEvents.setEnabled(false);
							jComboBoxQuestions.setEnabled(false);
							jComboBoxAnswers.setEnabled(false);
						} else
							jComboBoxEvents.setEnabled(true);
						jComboBoxAnswers.removeAllItems();
						jComboBoxEvents.removeAllItems();
						jComboBoxQuestions.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();


					} catch (Exception e1) {

						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

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
	
	
	private void jButtonCreate_actionPerformed(ActionEvent e) {

		try {
			BLFacade facade = MainGUI.getBusinessLogic();
			// Displays an exception if the query field is empty;
			Float montoIngresadoFloat = Float.parseFloat(monto.getText());
			
			int mensaje = facade.createApuesta(selectedAnswer, currentClient, montoIngresadoFloat, selectedQuestion);
			
			switch (mensaje) {
				case 0: 
						//JOptionPane.showMessageDialog(null, "Bet created!.");
						resultDialog.setText("Bet created!.");
						resultDialog.setForeground(new Color(0,128,0));
						monto.setText("");
						break;
					
				case 1:
						//JOptionPane.showMessageDialog(null, "Error creating bet.");
						resultDialog.setText("Error creating bet.");
						resultDialog.setForeground(new Color(128,0,0));
						break;
					
				case 2:
						//JOptionPane.showMessageDialog(null, "You have no money available in your account.");
						resultDialog.setText("You have no money available in your account.");
						resultDialog.setForeground(new Color(128,0,0));
						break;
						
				case 3:	
						//JOptionPane.showMessageDialog(null, "The bet must be greater than the minimum.");
						resultDialog.setText("The bet must be greater than the minimum.");
						resultDialog.setForeground(new Color(0,0,128));
						break;

				case 4:
						//JOptionPane.showMessageDialog(null, "Only positives numbers.");
						resultDialog.setText("Only positives numbers.");
						resultDialog.setForeground(new Color(0,0,128));
						break;
						
				case 5:
					//JOptionPane.showMessageDialog(null, "This client does not exist in database.");
					resultDialog.setText("This client does not exist in database.");
					resultDialog.setForeground(new Color(128,0,0));
					break;
				}
			
		} catch (java.lang.NumberFormatException e1) {
			//JOptionPane.showMessageDialog(null, "You must enter a valid number.");
			resultDialog.setText("Yoy must enter a valid number.");
			resultDialog.setForeground(new Color(0,0,128));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	public void checkCaption(Event pevento, Question pquestion, Answer panswer) {
		if (selectedEvent == null || selectedQuestion == null || selectedAnswer == null) {
			minimumCaption.setVisible(false); 
			jButtonCreate.setEnabled(false);
			resultDialog.setText("");
		} else { 
			minimumCaption.setVisible(true);
			jButtonCreate.setEnabled(true);
		}
	}
}
