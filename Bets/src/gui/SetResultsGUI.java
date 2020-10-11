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
import domain.Admin;
import domain.Answer;
import domain.Event;
import domain.Question;

public class SetResultsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private JComboBox<Answer> jComboBoxAnswers = new JComboBox<Answer>();
	DefaultComboBoxModel<Answer> modelAnswers = new DefaultComboBoxModel<Answer>();
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private Answer selectedAnswer=null;
	private Admin currentAdmin= null;
	private Date firstDay=null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private Question selectedQuestion;
	private Event selectedEvent;

	private JButton jButtonCreate = new JButton("Definir resultados"); //$NON-NLS-1$ //$NON-NLS-2$
//	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonClose = new JButton("Cancelar");
	private final JLabel lblSeleccionarPregunta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarPresgunta.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblSeleccionarEvento = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarEvento.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextArea resultDialog = new JTextArea();

	public SetResultsGUI(Vector<Event> v, Admin padmin) {
		try {
			currentAdmin=padmin;
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<Event> v) throws Exception {
				
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
					firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<Event> events = facade.getOpenEvents(firstDay);

						if (events.isEmpty()) {
							jComboBoxEvents.setEnabled(false);
							jComboBoxQuestions.setEnabled(false);;
							jComboBoxAnswers.setEnabled(false);
						} else {
							jComboBoxEvents.setEnabled(true);
						}
						jComboBoxAnswers.removeAllItems();
						jComboBoxEvents.removeAllItems();
						jComboBoxQuestions.removeAllItems();
						System.out.println("Events " + events);

						for (Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();


					} catch (Exception e1) {

						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				paintDaysWithEvents(jCalendar);
			}
		});
		
		this.setSize(new Dimension(575, 359));
		this.setTitle("Definir resultados");
		jComboBoxEvents.setBounds(293, 50, 250, 20);
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setEnabled(false);
		jComboBoxQuestions.setBounds(294, 119, 250, 20);
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

					for (Question q : questions)
						modelQuestions.addElement(q);
						jComboBoxQuestions.setModel(modelQuestions);
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
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setBounds(376, 239, 162, 40);

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
		this.getContentPane().add(jComboBoxEvents);
		jCalendar.setBounds(40, 50, 225, 150);

		this.getContentPane().add(jCalendar);
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		lblSeleccionarPregunta.setBounds(296, 90, 155, 20);
		jButtonCreate.setEnabled(false);
		
		getContentPane().add(lblSeleccionarPregunta);

		jComboBoxQuestions.setModel(modelQuestions);
		getContentPane().add(jComboBoxQuestions);
		
		jComboBoxQuestions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedQuestion = (Question) jComboBoxQuestions.getSelectedItem();
				
					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						List<Answer> answers = facade.getOpenAnswersByQuestion(selectedQuestion);
						
						jComboBoxAnswers.removeAllItems();
						System.out.println("Respuestas " + answers);

						for (Answer ans : answers) {
							modelAnswers.addElement(ans);
						}
							jComboBoxAnswers.setModel(modelAnswers);
							jComboBoxAnswers.repaint();
							
							//JOptionPane.showMessageDialog(null,"# de ans: "+ answers.size());
							
						if (answers.size() == 0) {
							jComboBoxAnswers.setEnabled(false);
							checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
						} else {
							jComboBoxAnswers.setEnabled(true);
							checkCaption(selectedEvent, selectedQuestion, selectedAnswer);
						}
					} catch (Exception e1) {
						System.out.println("Excepción en la carga del combobox de respuestas.");
						
					}
					
				}
			});
		jComboBoxAnswers.setBounds(295, 186, 250, 20);
		jComboBoxAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = (Answer) jComboBoxAnswers.getSelectedItem();

			}
		});
		jComboBoxAnswers.setEnabled(false);
		jComboBoxAnswers.setModel(modelAnswers);
		getContentPane().add(jComboBoxAnswers);
		
		JLabel lblSeleccionarRespuesta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApostarGUI.lblSeleccionarRespuesta.text"));
		lblSeleccionarRespuesta.setBounds(293, 155, 245, 25);
		getContentPane().add(lblSeleccionarRespuesta);
		lblSeleccionarEvento.setBounds(293, 21, 268, 20);
		
		getContentPane().add(lblSeleccionarEvento);
		resultDialog.setWrapStyleWord(true);
		resultDialog.setLineWrap(true);
		resultDialog.setEditable(false);
		resultDialog.setBackground(UIManager.getColor("Button.background"));
		resultDialog.setText("");
		resultDialog.setBounds(367, 281, 167, 40);
		
		getContentPane().add(resultDialog);

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

		/*    PROCEDIMIENTO DE CIERRE
		 * 1. selectedAnswer.setRespuestaGanadora(true) para definir la respuesta ganadora. (el resto ya están a false)
		 * 2. selectedQuestion.setReult(true) para definir la pregunta como resuelta. (por defecto está a false)
		 * 3. selectedEvent.setResuelt(true) para cerrar el evento.
		 * 4. Marcar las apuestas como ganadas o perdidas.
		 * 5. Asignar al monedero de cada cliente los saldos de las apuestas ganadoras
		 * 6. Asignar a la cuenta del sistema los saldos de perdedores y apuestas.
		 * 
		 * Devuelve true si cerró correctamente el eventos y asignó las apuestas
		 * */
		
		try {
			BLFacade facade = MainGUI.getBusinessLogic();
			
			if(facade.definirResultados(selectedEvent, selectedQuestion, selectedAnswer, currentAdmin)) {
				resultDialog.setText("The results have been saved correctly.");
				resultDialog.setForeground(new Color(0,128,0));
				jButtonCreate.setEnabled(false);

				actualizarEvents(firstDay);
				
			} else {
				resultDialog.setText("The results have not been saved correctly.");
				resultDialog.setForeground(new Color(128,0,0));
			}
			
		} catch (Exception e1) {

			resultDialog.setText("Exception problem >> gui/SetResultsGUI.java");
			resultDialog.setForeground(new Color(128,0,0));

		}
	}

	
	public void actualizarEvents (Date firstDay) {
		jComboBoxEvents.removeAllItems();
		try {
			BLFacade facade=MainGUI.getBusinessLogic();
			Vector<Event> eventsVector = facade.getOpenEvents(firstDay);
			ArrayList <Event> events= new ArrayList<Event>(eventsVector);
				for (Event c : events) {
					if(c.getResult()==false) {
						modelEvents.addElement((Event) c);
					}
				}
				jComboBoxEvents.setModel(modelEvents);
				jComboBoxEvents.repaint();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Exception >> AnularApuestaGUI: "+ex.getMessage());
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	
	
	public void checkCaption(Event pevento, Question pquestion, Answer panswer) {
		if (selectedEvent == null || selectedQuestion == null || selectedAnswer == null) {
			jButtonCreate.setEnabled(false);
			resultDialog.setText("");
		} else {
			jButtonCreate.setEnabled(true);
		}
	}
}