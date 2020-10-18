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
	@DisplayName("Prueba BetsByClient")
	void betsByClientTest() {
		try {
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			sut.doRegister("Juanito0634", "Patata", "juan@gmail.com");
			Cliente c = sut.getClientByUsername("Juanito0634");
			c.setSaldo(8000.0f);
			ev = testBL.addEvent(queryText, oneDate);
			Question q = sut.createQuestion(ev, queryText, betMinimum);
			Answer a = new Answer(queryText, betMinimum, q);
			sut.insertAnswer(a);
			sut.createApuesta(a, c, betMinimum, q);
			Apuesta e = new Apuesta(a, betMinimum, oneDate, c);
			ArrayList<Apuesta> expected = new ArrayList<Apuesta>();
			expected.add(e);
			ArrayList<Apuesta> obtained = sut.BetsByClient(c);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@DisplayName("Prueba getAnswersByQuestion")
	void getAnswersByQuestionTest() {
		try {
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			sut.doRegister("Juanito0634", "Patata", "juan@gmail.com");
			Cliente c = sut.getClientByUsername("Juanito0634");
			c.setSaldo(8000.0f);
			ev = testBL.addEvent(queryText, oneDate);
			Question q = sut.createQuestion(ev, queryText, betMinimum);
			Answer a = new Answer(queryText, betMinimum, q);
			sut.insertAnswer(a);
			ArrayList<Answer> expected= new ArrayList<Answer>();
			expected.add(a);
			ArrayList<Answer> obtained= sut.getAnswersByQuestion(q);
			assertEquals(expected, obtained);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
