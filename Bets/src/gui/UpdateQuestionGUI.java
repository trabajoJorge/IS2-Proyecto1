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
import domain.Event;
import domain.Question;

public class UpdateQuestionGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField preguntaModificar = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private Date firstDay;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private Question selectedQuestion;
	private Event selectedEvent;

	private JButton jButtonCreate = new JButton("Modificar pregunta");
	private JButton jButtonClose = new JButton("Cancelar");
	private JButton jButtonDelete = new JButton("Eliminar pregunta");
	private JButton jButtonDeleteConfirm = new JButton("Confirmar eliminar?");
	private final JLabel lblSeleccionarPresgunta = new JLabel("Seleccionar pregunta"); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblSeleccionarEvento = new JLabel("Seleccionar evento"); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblRespuestaModificar = new JLabel("Pregunta a modificar"); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextArea resultDialog = new JTextArea();
	private final JLabel lblCoeficienteDeGanancia = new JLabel("Apuesta mínima");
	private final JTextField apuestaMinima = new JTextField();

	public UpdateQuestionGUI(Vector<Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<Event> v) throws Exception {
		this.setSize(new Dimension(743, 379));
		this.setTitle("Editar pregunta");
		jComboBoxEvents.setBounds(460, 50, 250, 20);
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setEnabled(false);
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedEvent = (Event) jComboBoxEvents.getSelectedItem();
			
				try {
					BLFacade facade = MainGUI.getBusinessLogic();

					List<Question> questions = facade.getOpenQuestionsByEvent(selectedEvent);

					jComboBoxQuestions.removeAllItems();
					System.out.println("Preguntas " + questions);

					for (Question q : questions){
						modelQuestions.addElement(q);
					}
					
					jComboBoxQuestions.repaint();

					if (questions.size() == 0) {
						jComboBoxQuestions.setEnabled(false);
						checkCaption(selectedEvent, selectedQuestion);
						habilitarCopia(false);
						
					} else {
						jComboBoxQuestions.setEnabled(true);
						checkCaption(selectedEvent, selectedQuestion);
						habilitarCopia(true);
					}
				} catch (Exception e1) {

					//JOptionPane.showMessageDialog(null, "Error en llenar JComboBoxQuestion");
				}
				
			}
		});
		
		preguntaModificar.setEditable(false);
		preguntaModificar.setHorizontalAlignment(SwingConstants.CENTER);
		preguntaModificar.setBounds(460, 143, 250, 20);
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setBounds(548, 311, 162, 40);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(40, 311, 155, 40);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		getContentPane().setLayout(null);

		jButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonDelete_actionPerformed(e);
			}
		});

		jButtonDeleteConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonDeleteConfirm_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose);
		this.getContentPane().add(jButtonCreate);
		this.getContentPane().add(preguntaModificar);
		this.getContentPane().add(jComboBoxEvents);
		jCalendar.setBounds(40, 50, 225, 150);

		this.getContentPane().add(jCalendar);
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		lblSeleccionarPresgunta.setBounds(291, 93, 155, 20);
		jButtonCreate.setEnabled(false);
		
		getContentPane().add(lblSeleccionarPresgunta);

		
		jComboBoxQuestions.setBounds(460, 94, 250, 20);
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedQuestion = (Question) jComboBoxQuestions.getSelectedItem();
				checkCaption(selectedEvent, selectedQuestion);
				habilitarCopia(true);
				jButtonDeleteConfirm.setVisible(false);
				jButtonDelete.setVisible(true);
			}
		});
		jComboBoxQuestions.setEnabled(false);
		jComboBoxQuestions.setModel(modelQuestions);
		getContentPane().add(jComboBoxQuestions);
		lblSeleccionarEvento.setBounds(293, 50, 155, 20);
		
		getContentPane().add(lblSeleccionarEvento);
		lblRespuestaModificar.setBounds(291, 145, 151, 16);
		
		getContentPane().add(lblRespuestaModificar);
		resultDialog.setWrapStyleWord(true);
		resultDialog.setLineWrap(true);
		resultDialog.setEditable(false);
		resultDialog.setBackground(UIManager.getColor("Button.background"));
		resultDialog.setText("");
		resultDialog.setBounds(460, 262, 250, 37);
		
		getContentPane().add(resultDialog);
		lblCoeficienteDeGanancia.setBounds(291, 184, 167, 16);
		
		getContentPane().add(lblCoeficienteDeGanancia);
		apuestaMinima.setEditable(false);
		apuestaMinima.setHorizontalAlignment(SwingConstants.CENTER);
		apuestaMinima.setBounds(460, 180, 250, 20);
		
		getContentPane().add(apuestaMinima);
		

		jButtonDelete.setEnabled(false);
		jButtonDelete.setBounds(284, 311, 162, 40);
		getContentPane().add(jButtonDelete);
		jButtonDeleteConfirm.setEnabled(false);
		jButtonDeleteConfirm.setVisible(false);
		jButtonDeleteConfirm.setBounds(284, 311, 162, 40);
		getContentPane().add(jButtonDeleteConfirm);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
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
							jButtonDelete.setEnabled(false);
							jButtonDeleteConfirm.setEnabled(false);
							jComboBoxEvents.setEnabled(false);
							jComboBoxQuestions.setEnabled(false);
						} else {
							jComboBoxEvents.setEnabled(true);
						}
						
						jComboBoxQuestions.removeAllItems();
						jComboBoxEvents.removeAllItems();
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

	
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		Vector<Date> dates=facade.getEventsMonth(jCalendar.getDate());
			
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
	 	for (Date d:dates){

	 		calendar.setTime(d);
	 		System.out.println(d);

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
			Float apuestaMinimaFloat = Float.parseFloat(apuestaMinima.getText());
			String preguntaModificada = preguntaModificar.getText();
			
			if (facade.updateQuestion(selectedQuestion, preguntaModificada, apuestaMinimaFloat)) {
				resultDialog.setText("The question was updated.");
				resultDialog.setVisible(true);
				resultDialog.setForeground(new Color(0,128,0));
				actualizarEvents(firstDay);
			} else {
				resultDialog.setText("The question was not updated.");
				resultDialog.setVisible(true);
				resultDialog.setForeground(new Color(128,0,0));
			}	
		} catch (java.lang.NumberFormatException e1) {
			//JOptionPane.showMessageDialog(null, "You must enter a valid number.");
			resultDialog.setText("You must enter a valid number.");
			resultDialog.setForeground(new Color(128,0,0));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void jButtonDelete_actionPerformed(ActionEvent e) {
		jButtonDelete.setVisible(false);
		jButtonDelete.setEnabled(false);
		jButtonDeleteConfirm.setVisible(true);
		jButtonDeleteConfirm.setEnabled(true);
	}
	
	private void jButtonDeleteConfirm_actionPerformed(ActionEvent e) {
		BLFacade facade = MainGUI.getBusinessLogic();
		if (facade.deleteQuestion(selectedQuestion)) {
			actualizarEvents(firstDay);
			resultDialog.setText("The answer was deleted.");
			resultDialog.setForeground(new Color(0,128,0));
			resultDialog.setVisible(true);
		} else {
			resultDialog.setText("The answer was not deleted.");
			resultDialog.setForeground(new Color(128,0,0));
			resultDialog.setVisible(true);
		}
	}
	
	public void checkCaption(Event pevento, Question pquestion) {
		if (selectedEvent == null || selectedQuestion == null) {
			jButtonCreate.setEnabled(false);
			jButtonDelete.setEnabled(false);
			resultDialog.setText("");
			apuestaMinima.setText("");
			preguntaModificar.setText("");
			apuestaMinima.setEditable(false);
			preguntaModificar.setEditable(false);
		} else { 
			jButtonCreate.setEnabled(true);
			jButtonDelete.setEnabled(true);
		}
	}
	
	public void habilitarCopia(boolean habilitar) {
		if (habilitar) {
			try {
				apuestaMinima.setText(selectedQuestion.getBetMinimum()+"");
				preguntaModificar.setText(selectedQuestion.getQuestion());
				apuestaMinima.setEditable(true);
				preguntaModificar.setEditable(true);
			} catch (Exception ex) {
				System.out.println("Catch de habilitarCopia");
			}
		} else {
			apuestaMinima.setText("");
			preguntaModificar.setText("");
			apuestaMinima.setEditable(false);
			preguntaModificar.setEditable(false);
		}
	}
}
