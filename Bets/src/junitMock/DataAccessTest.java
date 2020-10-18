package junitMock;

/**
 * DataAccessTest: Some JUnit example for DataAccess
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.Event;
import domain.Question;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

class DataAccessTest {
	// sut- System Under Test
	private DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	private TestFacadeImplementation testBL = new TestFacadeImplementation();

	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Event ev;

	@Test
	@DisplayName("The event has one question with a queryText")
	void createQuestionDATest1() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure the state of the system (create object in the dabatase)
			ev = testBL.addEvent(queryText, oneDate);
			sut.createQuestion(ev, queryText, betMinimum);

			// invoke System Under Test (sut)
			assertThrows(QuestionAlreadyExist.class, () -> sut.createQuestion(ev, queryText, betMinimum));

		} catch (ParseException | QuestionAlreadyExist e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");
		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean b = testBL.removeEvent(ev);
			assertTrue(b);
		}

	}

	@Test
	@DisplayName("The event has NOT one question with a queryText")
	void createQuestionDATest2() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure the state of the system (create object in the dabatase)
			ev = testBL.addEvent(queryText, oneDate);

			// invoke System Under Test (sut)
			Question q = sut.createQuestion(ev, queryText, betMinimum);

			// verify the results
			assertNotNull(q);
			assertEquals(queryText, q.getQuestion());
			assertEquals(betMinimum, q.getBetMinimum(), 0.05);

		} catch (QuestionAlreadyExist | ParseException e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");

		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean b = testBL.removeEvent(ev);
			assertTrue(b);
		}
	}

	@Test
	@DisplayName("Prueba getClientByUsername")
	void getClientByUsernameTest() {
		sut.doRegister("Juanito034", "Patata", "juan@gmail.com");
		String obtained = sut.getClientByUsername("Juanito034").getUsername();
		String expected = (new Cliente("Juanito034", "Patata", "juan@gmail.com")).getUsername();
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsername1() {
		sut.doRegister("bryanespada", "123", "bryan@gmail.com");
		sut.doRegister("mauricg", "456", "mauri@gmail.com");
		String expected = (new Cliente("bryanespada", "123", "bryan@gmail.com")).getUsername();
		String obtained = (sut.getClientByUsername("bryanespada")).getUsername();
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsername2() {
		sut.doRegister("bryanespada2", "123", "bryan2@gmail.com");
		String expected = null;
		Cliente obtained = (sut.getClientByUsername("mauricg2"));
		assertEquals(expected, obtained);
	}
	
	@Test
	public void getClientByUsername3() {
		Cliente expected = null;
		Cliente obtained = sut.getClientByUsername("bryanespada3");
		assertEquals(expected, obtained);
	}
	@Test
	@DisplayName("Prueba BetsByClient1")
	void betsByClientTest1() {
		try {
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			sut.doRegister("Juanito", "Sasa", "juan@gmail.com");
			Cliente c = sut.getClientByUsername("Juanito");
			c.setSaldo(8000.0f);
			ev = testBL.addEvent(queryText, oneDate);
			Question q = sut.createQuestion(ev, queryText, betMinimum);
			Answer a = new Answer(queryText, betMinimum, q);
			sut.insertAnswer(a);
			ArrayList<Apuesta> expected = new ArrayList<Apuesta>();
			ArrayList<Apuesta> obtained = sut.BetsByClient(c);
			assertEquals(expected.toString(), obtained.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Prueba BetsByClient2")
	void betsByClientTest2() {
		try {
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			sut.doRegister("Juanto", "Sasa", "juan@gmail.com");
			Cliente c = sut.getClientByUsername("Juanto");
			c.setSaldo(8000.0f);
			ev = testBL.addEvent(queryText, oneDate);
			Question q = sut.createQuestion(ev, queryText, betMinimum);
			Answer a = new Answer("otro", betMinimum, q);
			sut.insertAnswer(a);
			ArrayList<Apuesta> expected = new ArrayList<Apuesta>();
			ArrayList<Apuesta> obtained = sut.BetsByClient(c);
			assertEquals(expected.toString(), obtained.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Prueba BetsByClient3")
	void betsByClientTest3() {
		try {
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			sut.doRegister("Juanito1", "Sasa", "juan@gmail.com");
			Cliente c = sut.getClientByUsername("Juanito1");
			c.setSaldo(8000.0f);
			ev = testBL.addEvent(queryText, oneDate);
			Question q = sut.createQuestion(ev, queryText, betMinimum);
			Answer a = new Answer(queryText, betMinimum, q);
			sut.insertAnswer(a);
			sut.createApuesta(a, c, 10.0f, q);
			sut.getAllApuestas();
			Apuesta e = new Apuesta(a, 10.0f, oneDate, c);
			ArrayList<Apuesta> expected = new ArrayList<Apuesta>();
			expected.add(e);
			ArrayList<Apuesta> obtained = sut.BetsByClient(c);
			assertEquals(expected.toString(), obtained.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion1 - BD sin preguntas")
	void getAnswersByQuestionTest1() {
		try {
			Date oneDate = sdf.parse("02/05/2021");
			Event e1 = new Event("Carreras", oneDate);
			Question q1 = new Question("¿Ganador?", 12.0f, e1);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			ArrayList<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			System.out.println("Falla el 1");
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion2 - Pregunta no esta en la BD ")
	void getAnswersByQuestionTest2() {
		try {
			Date oneDate = sdf.parse("02/05/2021");
			Date otherDate = sdf.parse("02/07/2021");
			Event e1 = testBL.addEvent("Carreras", oneDate);
			Question q1 = new Question("¿Ganador?", 12.0f, e1);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			ArrayList<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			System.out.println("Falla el 2");
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion3 - Pregunta no tiene respuesta ")
	void getAnswersByQuestionTest3() {
		try {
			Date oneDate = sdf.parse("02/05/2021");
			Event e1 = testBL.addEvent("Carreras", oneDate);
			Question q1 = sut.createQuestion(e1, "¿Ganador?", 12.0f);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			ArrayList<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			System.out.println("Falla el 3");
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion4 - La pregunta tiene una respuesta")
	void getAnswersByQuestionTest4() {
		try {
			Date oneDate = sdf.parse("02/05/2021");
			Event e1 = testBL.addEvent("Carreras", oneDate);
			Question q1 = sut.createQuestion(e1, "¿Ganador?", 12.0f);
			Answer a11 = new Answer("FC Barcelona", 2.0f, q1);
			sut.insertAnswer(a11);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			expected.add(a11);
			ArrayList<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			System.out.println("Falla el 1");
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion5 - La pregunta tiene varias respuestas")
	void getAnswersByQuestionTest5() {
		try {
			Date oneDate = sdf.parse("02/05/2021");
			Event e1 = testBL.addEvent("Carreras", oneDate);
			Question q1 = sut.createQuestion(e1, "¿Ganador?", 12.0f);
			Answer a11 = new Answer("FC Barcelona", 2.0f, q1);
			Answer a12 = new Answer("Malaga", 20.0f, q1);
			sut.insertAnswer(a11);
			sut.insertAnswer(a12);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			expected.add(a11);
			expected.add(a12);
			ArrayList<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			System.out.println("Falla el 1");
		}
	}

}
