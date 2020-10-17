package dataAccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

//import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.CuentaGlobal;
import domain.Event;
import domain.Question;
import domain.Usuario;
//import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		open(initializeMode);
	}

	public void open(boolean initializeMode) {
		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccess() {
		new DataAccess(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atletico-Athletic", UtilDate.newDate(year, month, 17));
			Question q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);

			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "AlavÃ©s-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "EspaÃ±ol-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-LeganÃ©s", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "AtlÃ©tico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "AlavÃ©s-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "EspaÃ±ol-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "MÃ¡laga-Valencia", UtilDate.newDate(year, month, 28));
			Event ev18 = new Event(18, "Girona-LeganÃ©s", UtilDate.newDate(year, month, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month, 28));

			Question q2 = ev1.addQuestion("Â¿QuiÃ©n meterÃ¡ el primer gol?", 2);
			Question q3 = ev11.addQuestion("Â¿QuiÃ©n ganarÃ¡ el partido?", 1);
			Question q4 = ev11.addQuestion("Â¿CuÃ¡ntos goles se marcarÃ¡n?", 2);
			Question q5 = ev17.addQuestion("Â¿QuiÃ©n ganarÃ¡ el partido?", 1);
			Question q6 = ev17.addQuestion("Â¿HabrÃ¡ goles en la primera parte?", 2);

			Answer ans01 = q1.addAnswer("Gana AtlÃ©tico", (float) 1.5);
			Answer ans02 = q1.addAnswer("Gana Athletic", (float) 2.0);
			Answer ans03 = q1.addAnswer("Empate", (float) 3.5);
			Answer ans04 = q1.addAnswer("Sin goles", (float) 2.5);

			Answer ans05 = q2.addAnswer("Jugador 1", (float) 4.5);
			Answer ans06 = q2.addAnswer("Jugador 2", (float) 1.6);
			Answer ans07 = q2.addAnswer("Jugador 3", (float) 6.0);
			Answer ans08 = q2.addAnswer("Jugador 4", (float) 2.5);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.persist(ans01);
			db.persist(ans02);
			db.persist(ans03);
			db.persist(ans04);

			db.persist(ans05);
			db.persist(ans06);
			db.persist(ans07);
			db.persist(ans08);

			// USUARIOS CLIENTES
			Cliente cli01 = new Cliente("user1", "pass1", "user1@mail.com", (float) 10.11, "Usuario1 Apellido1",
					"ES12 3445 4567 2320 3456 8654 1234", "1245 6543 3456 8766");
			Cliente cli02 = new Cliente("user2", "pass2", "user2@mail.com", (float) 20.22, "Usuario2 Apellido2",
					"ES23 3445 4567 2320 3456 8654 1234", "2345 6543 3456 8766");
			Cliente cli03 = new Cliente("user3", "pass3", "user3@mail.com", (float) 30.33, "Usuario3 Apellido3",
					"ES34 3445 4567 2320 3456 8654 1234", "3445 6543 3456 8766");
			Cliente cli04 = new Cliente("user4", "pass4", "user4@mail.com", (float) 40.44, "Usuario4 Apellido4",
					"ES45 3445 4567 2320 3456 8654 1234", "4545 6543 3456 8766");
			Cliente cli05 = new Cliente("user5", "pass5", "user5@mail.com", (float) 50.55, "Usuario5 Apellido5",
					"ES56 3445 4567 2320 3456 8654 1234", "5645 6543 3456 8766");
			Cliente cli = new Cliente("u", "", "user@mail.com", (float) 10.11, "Usuario1 Apellido1",
					"ES12 3445 4567 2320 3456 8654 1234", "1245 6543 3456 8766");

			db.persist(cli);
			db.persist(cli02);
			db.persist(cli03);
			db.persist(cli04);
			db.persist(cli05);

			// USUARIOS ADMINISTRADORES
			Admin adm = new Admin("", "admin@mail.com", "");
			Admin adm01 = new Admin("admin1", "admin1@mail.com", "pass1");
			Admin adm02 = new Admin("admin2", "admin2@mail.com", "pass2");

			db.persist(adm);
			db.persist(adm01);
			db.persist(adm02);

			// CUENTA GLOBAL
			CuentaGlobal cg = new CuentaGlobal(1, (float) 1100.0);
			db.persist(cg);

			// PUESTAS DE PRUEBA EN CLIENTE 1
			Apuesta apu01 = ans01.addBet((float) 10.0, cli01);
			Apuesta apu02 = ans02.addBet((float) 20.0, cli01);
			Apuesta apu03 = ans03.addBet((float) 30.0, cli01);
			Apuesta apu04 = ans04.addBet((float) 40.0, cli01);

			cli01.addBet(apu01);
			cli01.addBet(apu02);
			cli01.addBet(apu03);
			cli01.addBet(apu04);

			cg.addBet(apu01);
			cg.addBet(apu02);
			cg.addBet(apu03);
			cg.addBet(apu04);

			db.persist(apu01);
			db.persist(apu02);
			db.persist(apu03);
			db.persist(apu04);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	public boolean insertAnswer(Answer pan) {
		System.out.println(">> DataAccess: insertAnswer=> question= " + pan.getQuestion().getQuestion() + " answer="
				+ pan.getAnswerText());

		try {
			Question q = db.find(Question.class, pan.getQuestion());

			db.getTransaction().begin();
			Answer ans = q.addAnswer(pan);
			// db.persist(q);
			db.persist(ans); // db.persist(q) not required when CascadeType.PERSIST is added in questions
								// property of Event class
								// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean insertEvent(Event pev) {
		System.out
				.println(">> DataAccess: insertEvent=> event= " + pev.getDescription() + " date=" + pev.getEventDate());

		try {
			db.getTransaction().begin();
			db.persist(pev);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Vector<Event> getEvents(Date date) {

		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public Vector<Question> getQuestions(Event pevent) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Question> res = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1", Question.class);
		query.setParameter(1, pevent);
		List<Question> questions = query.getResultList();
		for (Question q : questions) {
			System.out.println(q.toString());
			res.add(q);
		}
		return res;
	}

	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public boolean doLogin(String pusername, String ppassword) {

		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.password=?2",
				Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<Usuario> usuarios = query.getResultList();

		if (usuarios.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean doRegister(String pusername, String ppassword, String pemail) throws RollbackException {

		// Verifico que el usuario no exista
		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.email=?2",
				Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, pemail);
		List<Usuario> usuarios = query.getResultList();
		if (usuarios.isEmpty()) {
			db.getTransaction().begin();
			Cliente c = new Cliente(pusername, ppassword, pemail);
			db.persist(c);
			db.getTransaction().commit();
			System.out.println("Usuario registrado ");
			return true;
		} else {
			return false;
		}
	}

	public boolean isAdmin(String pusername, String ppassword) {
		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.password=?2",
				Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<Usuario> usuarios = query.getResultList();

		for (Usuario u : usuarios) {
			return (u instanceof Admin);
		}

		if (usuarios instanceof Admin) {
			return true;
		} else {
			return false;
		}
	}

	public List<Event> getAllEvents() {
		// TODO Conseguir todos los eventos

		TypedQuery<Event> query = db.createQuery("SELECT e FROM Event e ORDER BY e.eventNumber", Event.class);

		List<Event> eventos = query.getResultList();

		return eventos;
	}

	public List<Question> getQuestionsByEvent(Event pevent) {
		// TODO Devuelve las preguntas que tengas como evento @event

		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> evList = query.getResultList();

		if (evList.isEmpty()) {
			System.out.println("No events for that date");
			return null;
		} else {
			ArrayList<Question> result = new ArrayList<Question>();
			for (Event ev : evList)
				if (ev.getEventNumber().equals(pevent.getEventNumber()))
					for (Question q : ev.getQuestions())
						result.add(q);
			return result;

		}

	}

	public List<Question> getOpenQuestionsByEvent(Event pevent) {

		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.result=?1", Event.class);
		query.setParameter(1, false);
		List<Event> evList = query.getResultList();

		if (evList.isEmpty()) {
			System.out.println("No events for that date");
			return null;
		} else {
			ArrayList<Question> result = new ArrayList<Question>();
			for (Event ev : evList) {
				if (ev.getEventNumber().equals(pevent.getEventNumber())) {
					for (Question q : ev.getQuestions()) {
						if (!q.getResult()) {
						}
					}
				}
			}
			return result;
		}

	}

	public List<Answer> getAnswersByQuestion(Question p) {
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q", Question.class);
		List<Question> qList = query.getResultList();

		if (qList.isEmpty()) {
			System.out.println("No questions in BD");
			return null;
		} else {
			ArrayList<Answer> result = new ArrayList<Answer>();
			Vector<Answer> respuestas = new Vector<Answer>();
			boolean pregIguales;
			for (Question q : qList) {
				pregIguales = q.getQuestionNumber().equals(p.getQuestionNumber());
				if (pregIguales) {
					respuestas = q.getAnswers();
					for (Answer ans : respuestas) {
						ans.toString();
						result.add(ans);
					}
				}
			}
			return result;
		}
	}

	public List<Answer> getOpenAnswersByQuestion(Question pselectedQuestion) {
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.questionNumber=?1",
				Question.class);
		query.setParameter(1, pselectedQuestion.getQuestionNumber());
		List<Question> qList = query.getResultList();

		if (qList.isEmpty()) {
			System.out.println("No questions for that event");
			return null;
		} else {
			ArrayList<Answer> result = new ArrayList<Answer>();
			for (Question q : qList)
				for (Answer ans : q.getAnswers()) {
					if (ans.getActiva() == true) {
						ans.toString();
						result.add(ans);
					}
				}
			return result;

		}

	}

	public int createApuesta(Answer pselectedAnswer, Cliente pselectedClient, Float pselectedAmount,
			Question pselectedQuestion) {
		// VALIDACIÃ“N DE NÃšMERO POSITIVO
		if (pselectedAmount < 0) {

			// 4 - NÃšMERO NEGATIVO
			return 4;
		} else {

			// VALIDACIÃ“N DE MONTO MAYOR AL MÃ�NIMO
			if (pselectedAmount < pselectedQuestion.getBetMinimum()) {

				// 3 - NO ALCANZA APUESTA MÃ�NIMA
				return 3;

			} else {

				Cliente clientdb = db.find(Cliente.class, pselectedClient.getUsername());

				// VALIDACIÃ“N DE SALDO EN CUENTA
				if (pselectedAmount >= clientdb.getSaldo()) {
					// 2 - FALTA DE SALDO
					return 2;

				} else {

					System.out.println(">> DataAccess: createApuesta=> answer= " + pselectedAnswer + " client= "
							+ clientdb.getUsername() + " amount=" + pselectedAmount + "â‚¬");

					try {
						db.getTransaction().begin();
						CuentaGlobal cuentaGlobal = db.find(CuentaGlobal.class, 1);
						Apuesta ap = pselectedAnswer.addBet(pselectedAmount, clientdb);
						cuentaGlobal.addBet(ap);
						db.persist(ap);

						cuentaGlobal.setSaldoGlobal(cuentaGlobal.getSaldoGlobal() + pselectedAmount);
						clientdb.setSaldo(clientdb.getSaldo() - pselectedAmount);
						// JOptionPane.showMessageDialog(null, cl.getSaldo());
						db.persist(clientdb);
						db.getTransaction().commit();

						// 0 - APUESTA CREADA
						return 0;

					} catch (Exception ex) {

						// 1 - ERROR DE INGRESO DE APUESTA
						return 1;
					}

				}

			}
		}

	}

	public Admin getAdminByUsername(String pusername) {
		System.out.println(">> DataAccess: getAdminByUsername");

		TypedQuery<Admin> query = db.createQuery("SELECT adm FROM Admin adm", Admin.class);
		List<Admin> admList = query.getResultList();

		// ArrayList<Admin> result = new ArrayList<Admin>();
		for (Admin a : admList) {
			if (a.getUsername().equals(pusername))
				return a;
		}
		return null;

	}

	public ArrayList<Apuesta> BetsByClient(Cliente a) { // Questions by event
		TypedQuery<Apuesta> query = db.createQuery("SELECT q FROM Apuesta q", Apuesta.class);
		List<Apuesta> ApuList = query.getResultList();
		ArrayList<Apuesta> BetList;
		if (ApuList.isEmpty()) {
			System.out.println("No bets for that client");
			return null;
		} else {

			// JOptionPane.showMessageDialog(null, "ApuList1; "+ApuList.size());
			BetList = new ArrayList<Apuesta>();
			for (Apuesta q : ApuList) {
				if (q.getCliente().getUsername().equals(a.getUsername())) {
					BetList.add(q);
					// JOptionPane.showMessageDialog(null, "ApuList;
					// "+q.getCliente().getUsername());
				}
			}

			return BetList;
		}
	}

	public boolean insertSomething(Object something) {
		try {
			db.getTransaction().begin();
			db.persist(something);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public Vector<Event> getOpenEvents(Date date) {
		System.out.println(">> DataAccess: getOpenEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.result=?2",
				Event.class);
		query.setParameter(1, date);
		query.setParameter(2, false);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public CuentaGlobal getCuentaGlobal() {
		CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
		return cg;
	}

	public boolean createAdmin(String pNombre, String pUsername, String pEmail, String pPassword) {

		System.out.println(">> DataAccess: createAdmin=> username= " + pUsername);

		try {
			Admin adm = new Admin(pNombre, pUsername, pEmail, pPassword);
			db.getTransaction().begin();
			db.persist(adm);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean definirResultados(Event pselectedEvent, Question pselectedQuestion, Answer pselectedAnswer,
			Admin pcurrentAdmin) {

		db.getTransaction().begin();
		Answer ganadora = db.find(Answer.class, pselectedAnswer);
		ganadora.setRespuestaGanadora(true); // 1.
		db.getTransaction().commit();

		// TODAS LAS RESPUESTAS POSIBLES DE ESA PREGUNTA
		TypedQuery<Answer> query0 = db.createQuery("SELECT ans FROM Answer ans WHERE ans.question=?1", Answer.class);
		query0.setParameter(1, pselectedQuestion);
		db.getTransaction().begin();
		ArrayList<Answer> ArrayListRespuestas = new ArrayList<Answer>(query0.getResultList());
		db.getTransaction().commit();

		CuentaGlobal cuentaGlobal = db.find(CuentaGlobal.class, 1);

		try {

			db.getTransaction().begin();
			for (Answer ans : ArrayListRespuestas) {
				TypedQuery<Apuesta> query1 = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1",
						Apuesta.class);
				query1.setParameter(1, ans);
				ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(query1.getResultList());
				if (ArrayListApuestas.isEmpty()) {
					System.out.println("No bets for this answer.");
				} else {
					for (Apuesta apu : ArrayListApuestas) {

						// FILTRA QUE NO ESTÃ‰ ANULADA
						if (apu.getEstado() != 2) {

							if (apu.getAnswer().getAnswerNumber() == pselectedAnswer.getAnswerNumber()) {
								apu.setEstado(3);// SET ACIERTO

								// ACREDITA SALDO CLIENTE
								Cliente cliente = apu.getCliente();
								float saldoCliente = cliente.getSaldo();
								float total = saldoCliente + apu.getAmount() * pselectedAnswer.getCoeficienteGanancia();

								System.out.println("\nAcredita al cliente " + cliente.getUsername() + " un total de "
										+ apu.getAmount() * pselectedAnswer.getCoeficienteGanancia() + "â‚¬ ("
										+ apu.getAmount() + "â‚¬ x " + pselectedAnswer.getCoeficienteGanancia() + ")");

								cuentaGlobal.setSaldoGlobal(cuentaGlobal.getSaldoGlobal()
										- apu.getAmount() * pselectedAnswer.getCoeficienteGanancia());
								cliente.setSaldo(total);

							} else {

								apu.setEstado(1);// SET FALLO
							}

						} // filtro anulada
					} // para cada apuesta
				} // existen apuestas
			} // para cada respueta de la pregunta

			// CIERRO LA PREGUNTA
			Question q = db.find(Question.class, pselectedQuestion);

			q.setResult(true);
			System.out
					.println("\n// Apuestas sobre pregunta '" + q.getQuestion() + "' resueltas.\nPregunta cerrada. //");
			db.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DataAccess >> Asignar Resultados y Pagos >> Catch: " + e.getMessage()); // FIX-ME!
																															// Comentar
																															// la
																															// lÃ­nea
			return false;
		}

		// CIERRE DE EVENTO
		try {

			// SI NO HAY PREGUNTAS ABIERTAS, CIERRA EVENTO
			Event ev = db.find(Event.class, pselectedEvent);

			db.getTransaction().begin();
			TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1 AND q.result=?2",
					Question.class);
			query.setParameter(1, ev);
			query.setParameter(2, false);

			ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(query.getResultList());

			// SOLO CIERRA EL EVENTO SI NO TIENE
			// PREGUNTAS EN ESTADO PENDIENTE
			// JOptionPane.showMessageDialog(null, "ArrayListQuestions:
			// "+ArrayListQuestions.isEmpty()); //FIX-ME! Comentar la lÃ­nea
			if (ArrayListQuestions.isEmpty()) {
				ev.setResult(true);
				db.getTransaction().commit();
			}
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DataAccess >> Cerrar evento >> Catch");
			return false;
		}
	}

	public boolean acreditarSaldo(Cliente pClient, float pMonto, int pPin, String pTipo) {

		if (pTipo == "t") {
			// SIMULA UNA TRANSACCIÃ“N POR BANCO
			if (pPin == 123) {
				db.getTransaction().begin();
				Cliente cli = db.find(Cliente.class, pClient);
				cli.setSaldo(cli.getSaldo() + pMonto);
				db.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} else {
			// SIMULA UNA TRANSACCIÃ“N CON TARJETA DE CRÃ‰DITO Y ENVÃ�A NÃšMERO DE TARJETA Y
			// PIN
			db.getTransaction().begin();
			Cliente cli = db.find(Cliente.class, pClient);
			cli.setSaldo(cli.getSaldo() + pMonto);
			db.getTransaction().commit();
			return true;
		}
	}

	public boolean confirmarEditarPerfilCliente(String pNombre, String pUsername, String pEmail, String pTarjeta,
			String pCC) {
		try {
			db.getTransaction().begin();
			Cliente cliente = db.find(Cliente.class, pUsername);
			cliente.setName(pNombre);
			cliente.setEmail(pEmail);
			cliente.setTarjeta(pTarjeta);
			cliente.setCC(pCC);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean anularApuesta(Apuesta pApuesta) {
		try {
			db.getTransaction().begin();
			Apuesta apuesta = db.find(Apuesta.class, pApuesta);
			Cliente cliente = db.find(Cliente.class, pApuesta.getCliente());
			CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
			apuesta.setEstado(4);
			cliente.setSaldo(cliente.getSaldo() + pApuesta.getAmount());
			cg.setSaldoGlobal(cg.getSaldoGlobal() - pApuesta.getAmount());

			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Cliente getClientByUsername(String pusername) {
		System.out.println(">> DataAccess: getClientByUsername");
		TypedQuery<Cliente> query = db.createQuery("SELECT cli FROM Cliente cli", Cliente.class);
		List<Cliente> cliList = query.getResultList();
		for (Cliente c : cliList) {
			if (c.getUsername().equals(pusername))
				return c;
		}
		return null;
	}

	public boolean updateQuestion(Question pselectedQuestion, String r, float f) {
		try {
			db.getTransaction().begin();
			Question qselected = db.find(Question.class, pselectedQuestion);
			qselected.setQuestion(r);
			qselected.setBetMinimum(f);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean updateAnswer(Answer pselectedAnswer, String prespuestaModificada, Float pcoeficienteGanancia) {
		try {
			db.getTransaction().begin();
			Answer ansselected = db.find(Answer.class, pselectedAnswer);
			ansselected.setAnswerText(prespuestaModificada);
			ansselected.setCoeficienteGanancia(pcoeficienteGanancia);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteAnswer(Answer pselectedAnswer) {
		try {
			db.getTransaction().begin();
			Answer ansselected = db.find(Answer.class, pselectedAnswer);
			TypedQuery<Apuesta> query1 = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2",
					Apuesta.class);
			query1.setParameter(1, ansselected);
			query1.setParameter(2, 0); // Apuestas en estado 0 - pendientes
			ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(query1.getResultList());
			if (ArrayListApuestas.isEmpty()) {
				System.out.println("\nSin apuestas en esta respuesta.");
			} else {
				for (Apuesta apu : ArrayListApuestas) {
					Apuesta apuesta = db.find(Apuesta.class, apu);
					Cliente cliente = db.find(Cliente.class, apu.getCliente());
					CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
					apuesta.setEstado(2);
					cliente.setSaldo(cliente.getSaldo() + apu.getAmount());
					cg.setSaldoGlobal(cg.getSaldoGlobal() - apu.getAmount());

					System.out.println("\nApuesta id " + apuesta.getIdBet() + " anulada.");
				}
			}

			ansselected.setActiva(false);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteQuestion(Question pselectedQuestion) {
		try {
			db.getTransaction().begin();
			Question questionSelected = db.find(Question.class, pselectedQuestion);

			TypedQuery<Answer> queryAnswer = db
					.createQuery("SELECT ans FROM Answer ans WHERE ans.question=?1 AND ans.activa=?2", Answer.class);
			queryAnswer.setParameter(1, questionSelected);
			queryAnswer.setParameter(2, true);
			ArrayList<Answer> ArrayListAnswers = new ArrayList<Answer>(queryAnswer.getResultList());
			/* ACÃ� TENEMOS TODAS LAS RESPUESTAS DE ESTA PREGUNTA */
			if (ArrayListAnswers.isEmpty()) {
				System.out.println("\nSin respuestas en esta pregunta.");
			} else {
				for (Answer ans : ArrayListAnswers) {
					TypedQuery<Apuesta> queryApuesta = db.createQuery(
							"SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2", Apuesta.class);
					queryApuesta.setParameter(1, ans);
					queryApuesta.setParameter(2, 0); // Apuestas en estado 0 - pendientes
					ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(queryApuesta.getResultList());
					if (ArrayListApuestas.isEmpty()) {
						System.out.println("\nSin apuestas en esta respuesta.");
					} else {
						for (Apuesta apu : ArrayListApuestas) {
							Apuesta apuesta = db.find(Apuesta.class, apu);
							Cliente cliente = db.find(Cliente.class, apu.getCliente());
							CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
							apuesta.setEstado(2);
							cliente.setSaldo(cliente.getSaldo() + apu.getAmount());
							cg.setSaldoGlobal(cg.getSaldoGlobal() - apu.getAmount());

							System.out.println("\nApuesta id " + apuesta.getIdBet() + " anulada.");
						}
					}
					ans.setActiva(false);
				}
			}

			questionSelected.setResult(true);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean updateQuestion(Event pselectedEvent, String ptext) {
		try {
			db.getTransaction().begin();
			Event eventselected = db.find(Event.class, pselectedEvent);
			eventselected.setDescription(ptext);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteEvent(Event pselectedEvent) {
		try {

			db.getTransaction().begin();
			Event eventSelected = db.find(Event.class, pselectedEvent);
			TypedQuery<Question> queryQuestion = db
					.createQuery("SELECT q FROM Question q WHERE q.event=?1 AND q.result=?2", Question.class);
			queryQuestion.setParameter(1, eventSelected);
			queryQuestion.setParameter(2, false);
			ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(queryQuestion.getResultList());
			if (ArrayListQuestions.isEmpty()) {
				System.out.println("\nSin preguntas en este evento.");
			} else {
				for (Question questionSelected : ArrayListQuestions) {

					TypedQuery<Answer> queryAnswer = db.createQuery(
							"SELECT ans FROM Answer ans WHERE ans.question=?1 AND ans.activa=?2", Answer.class);
					queryAnswer.setParameter(1, questionSelected);
					queryAnswer.setParameter(2, true);
					ArrayList<Answer> ArrayListAnswers = new ArrayList<Answer>(queryAnswer.getResultList());
					/* ACÃ� TENEMOS TODAS LAS RESPUESTAS DE ESTA PREGUNTA */
					if (ArrayListAnswers.isEmpty()) {
						System.out.println("\nSin respuestas en esta pregunta.");
					} else {
						for (Answer ans : ArrayListAnswers) {
							TypedQuery<Apuesta> queryApuesta = db.createQuery(
									"SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2", Apuesta.class);
							queryApuesta.setParameter(1, ans);
							queryApuesta.setParameter(2, 0); // Apuestas en estado 0 - pendientes
							ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(queryApuesta.getResultList());
							if (ArrayListApuestas.isEmpty()) {
								System.out.println("\nSin apuestas en esta respuesta.");
							} else {
								for (Apuesta apu : ArrayListApuestas) {
									Apuesta apuesta = db.find(Apuesta.class, apu);
									Cliente cliente = db.find(Cliente.class, apu.getCliente());
									CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
									apuesta.setEstado(2);
									cliente.setSaldo(cliente.getSaldo() + apu.getAmount());
									cg.setSaldoGlobal(cg.getSaldoGlobal() - apu.getAmount());

									System.out.println("\nApuesta id " + apuesta.getIdBet() + " anulada.");
								}
							}
							ans.setActiva(false);
						}
					}

					questionSelected.setResult(true);
				}
			}

			eventSelected.setResult(true);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}