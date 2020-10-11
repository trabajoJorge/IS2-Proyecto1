package gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class CreateEventGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private final JLabel lblEligeDia = new JLabel("Elige dia:");
	private final JLabel lblDescripcionDeEvento = new JLabel("Descripcion de evento:");
	private final JTextField textFieldDescripcion = new JTextField();
	private final JButton btnCrearEvento = new JButton("Crear evento");
	private Date firstDay;
	private final JLabel lblNewLabel = new JLabel("Evento creado con éxito");


	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public CreateEventGUI() {
		this.setTitle("Añadir evento");
		textFieldDescripcion.setBounds(275, 75, 315, 20);
		textFieldDescripcion.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	
		this.setTitle("Crear Evento");


		
		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		contentPane.add(jCalendar);
		lblEligeDia.setBounds(40, 25, 78, 14);

		contentPane.add(lblEligeDia);
		lblDescripcionDeEvento.setBounds(275, 50, 193, 14);

		contentPane.add(lblDescripcionDeEvento);

		contentPane.add(textFieldDescripcion);
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//TODO Hay que cambiar esto ya que no deberiamos crearlo de esta forma
				BLFacade facade = MainGUI.getBusinessLogic();
				
				firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				
				System.out.println(firstDay);
				Event NewEv = new Event(textFieldDescripcion.getText(), firstDay);
				boolean save = facade.insertEvent(NewEv);
				
				if(save) {
					lblNewLabel.setText("EVENTO INGRESADO.");
					lblNewLabel.setVisible(true);
				} else {
					lblNewLabel.setText("ERROR AL INGRESAR EVENTO.");
					lblNewLabel.setVisible(true);
				}
			}
		});
		btnCrearEvento.setBounds(275, 120, 315, 57);

		lblNewLabel.setVisible(false);
		
		contentPane.add(btnCrearEvento);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				close1();
			}
		});
		btnCerrar.setBounds(501, 232, 89, 23);
		contentPane.add(btnCerrar);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setBounds(347, 189, 173, 16);
		
		contentPane.add(lblNewLabel);

		
		


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



					System.out.println(firstDay);








					/*
					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarMio.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}
					 */

				}
				//paintDaysWithEvents(jCalendar);
			}
		});

	}

	private void close1(){
		this.dispose();
	}
}
