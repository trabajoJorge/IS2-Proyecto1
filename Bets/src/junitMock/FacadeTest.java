package junitMock;

/**
 * FacadeTest: Some JUnit example for Facade
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Answer;
import domain.Cliente;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

class FacadeTest {
	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Event ev;

	@BeforeAll
	public static void ini() {
		// you can parametrize the DataAccess used by BLFacadeImplementation
		DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		sut = new BLFacadeImplementation(da);
		testBL = new TestFacadeImplementation();
	}

	@Test
	@DisplayName("The event has one question with a queryText")
	void createQuestionBLTest1() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure the state of the system (create object in the database)
			ev = testBL.addEvent(queryText, oneDate);
			sut.createQuestion(ev, queryText, betMinimum);

			// invoke System Under Test (sut)
			assertThrows(QuestionAlreadyExist.class, () -> sut.createQuestion(ev, queryText, betMinimum));

		} catch (ParseException | EventFinished | QuestionAlreadyExist e) {
			// if the program goes to this point fail
			fail("No problems should arise: ParseException/EventFinished/QuestionaAlreadyExist");

		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean b = testBL.removeEvent(ev);
			assertTrue(b);
		}
	}

	@Test
	@DisplayName("The event has NOT one question with a queryText")
	void createQuestionBLTest2() {

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

		} catch (ParseException | QuestionAlreadyExist | EventFinished e) {
			// if the program goes to this point fail
			fail("No problems should arise: ParseException/EventFinished/QuestionaAlreadyExist");

		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean b = testBL.removeEvent(ev);
			System.out.println("Finally " + b);
		}
	}

	@Test
	@DisplayName("getAnswersByQuestion2 - Se le arroja al metodo un null como parametro")
	void createQuestionBLMocktest2() {
		List<Answer> expected = null;
		List<Answer> obtained = sut.getAnswersByQuestion(null);
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsernameTest2() {
		Cliente expected = null;
		Cliente obtained = sut.getClientByUsername(null);
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsernameTest3() {
		Cliente expected = null;
		Cliente obtained = sut.getClientByUsername("");
		assertEquals(expected, obtained);
	}
	
	@Test
	public void BetsByClientTest1() {
		Cliente expected = null;
		Cliente obtained = sut.getClientByUsername(null);
		assertEquals(expected, obtained);
	}
}
