package junitMock;

/**
 * FacadeMockTest: Some JUnit+Mock example for FacadeMock
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.objectdb.o.HST.A;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

class FacadeMockTest {
	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	// sut.createQuestion: The event has one question with a queryText.

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("The event has one question with a queryText")
	void createQuestionBLMockTest1() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class), Mockito.any(String.class),
					Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);

			// invoke System Under Test (sut)
			assertThrows(QuestionAlreadyExist.class, () -> sut.createQuestion(mockedEvent, queryText, betMinimum));

		} catch (ParseException | QuestionAlreadyExist e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");
		}
	}

	@Test
	@DisplayName("The event has NOT a question   with a queryText")
	void createQuestionBLMocktest2() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.doReturn(new Question(queryText, betMinimum, mockedEvent)).when(dataAccess)
					.createQuestion(Mockito.any(Event.class), Mockito.any(String.class), Mockito.any(Integer.class));

			// invoke System Under Test (sut)
			Question q = sut.createQuestion(mockedEvent, queryText, betMinimum);

			// verify the results
			assertNotNull(q);

			ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
			ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);

			Mockito.verify(dataAccess, Mockito.times(1)).createQuestion(eventCaptor.capture(),
					questionStringCaptor.capture(), betMinimunCaptor.capture());

			assertEquals(mockedEvent, eventCaptor.getValue());
			assertEquals(queryText, questionStringCaptor.getValue());
			assertEquals(betMinimum, betMinimunCaptor.getValue());

		} catch (ParseException | QuestionAlreadyExist | EventFinished e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");

		}
	}

	@Test
	@DisplayName("getAnswersByQuestionMock1 - Todo funciona correctamente")
	void createQuestionMock1() {

		try {
			Date oneDate;
			oneDate = sdf.parse("02/05/2021");
			Event e1 = new Event("Carreras", oneDate);
			Question q1 = new Question("¿Ganador?", 12.0f, e1);
			Answer a11 = new Answer("FC Barcelona", 2.0f, q1);
			Answer a12 = new Answer("Malaga", 20.0f, q1);
			ArrayList<Question> ArrayQuestions = new ArrayList<Question>();
			ArrayList<Answer> ArrayAnswers = new ArrayList<Answer>();
			ArrayQuestions.add(q1);
			ArrayAnswers.add(a11);
			ArrayAnswers.add(a12);
			Mockito.doReturn(ArrayQuestions).when(dataAccess).getAllQuestions();
			Mockito.doReturn(true).when(dataAccess).questionExist(q1);
			Mockito.doReturn(ArrayAnswers).when(dataAccess).getAnswersByQuestion(q1);
			List<Answer> expected = ArrayAnswers;
			List<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("getAnswersByQuestionMock3 - La pregunta no esta en la BD")
	void createQuestionMock3() {

		try {
			Date oneDate;
			oneDate = sdf.parse("02/05/2021");
			Event e2 = new Event("Tractores", oneDate);
			Question q2 = new Question("¿Goleador", 20.0f, e2);
			Event e1 = new Event("Carreras", oneDate);
			Question q1 = new Question("¿Ganador?", 12.0f, e1);
			ArrayList<Question> ArrayQuestions = new ArrayList<Question>();
			ArrayQuestions.add(q2);
			Mockito.doReturn(ArrayQuestions).when(dataAccess).getAllQuestions();
			Mockito.doReturn(false).when(dataAccess).questionExist(q1);
			List<Answer> expected = null;
			List<Answer> obtained = sut.getAnswersByQuestion(q1);
			assertEquals(expected, obtained);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("getAnswersByQuestionMock4 - La pregunta no tiene respuestas")
	void getAnswersByQuestionMock4() {
		try {
			Date oneDate;
			oneDate = sdf.parse("02/05/2021");
			Event e1 = new Event("Carreras", oneDate);
			Question q1 = new Question("¿Ganador?", 12.0f, e1);
			ArrayList<Question> ArrayQuestions = new ArrayList<Question>();
			ArrayQuestions.add(q1);
			Mockito.doReturn(ArrayQuestions).when(dataAccess).getAllQuestions();
			Mockito.doReturn(true).when(dataAccess).questionExist(q1);
			ArrayList<Answer> expected = new ArrayList<Answer>();
			ArrayList<Answer> obtained = new ArrayList<Answer>(sut.getAnswersByQuestion(q1));
			assertEquals(expected, obtained);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getClientByUsernameTest1() {
		Cliente expected = new Cliente("bryanespada", "123", "bryan@gmail.com");
		Mockito.doReturn(true).when(dataAccess).clientExist("bryanespada");
		Mockito.doReturn(expected).when(dataAccess).getClientByUsername("bryanespada");
		Cliente obtained = sut.getClientByUsername("bryanespada");
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsernameTest4() {
		Cliente expected = null;
		Mockito.doReturn(false).when(dataAccess).clientExist("bryanespada");
		Cliente obtained = sut.getClientByUsername("mauricg");
		assertEquals(expected, obtained);
	}

	@Test
	public void getClientByUsernameTest5() {
		Cliente expected = null;
		Mockito.doReturn(false).when(dataAccess).clientExist("bryanespada");
		Cliente obtained = sut.getClientByUsername("mauricg");
		assertEquals(expected, obtained);
	}

	@Test
	public void BetsByClientTest2() {
		ArrayList<Apuesta> expected = null;
		Cliente c1 = new Cliente("bryanespada", "123", "bryan@gmail.com");
		Mockito.doReturn(false).when(dataAccess).clientExist(c1.getUsername());
		ArrayList<Apuesta> obtained = sut.BetsByClient(c1);
		assertEquals(expected, obtained);
	}

	@Test
	public void BetsByClientTest3() {
		Cliente expected = null;
		Mockito.doReturn(true).when(dataAccess).clientExist("Jorge");
		Cliente c1 = new Cliente("bryanespada", "123", "bryan@gmail.com");
		Cliente obtained = sut.getClientByUsername("Jorge");
		Mockito.doReturn(true).when(dataAccess).clientExist(c1.getUsername());
		List<Apuesta> apulist = null;
		Mockito.doReturn(apulist).when(dataAccess).getAllApuestas();
		assertEquals(expected, obtained);
	}

	@Test
	public void BetsByClientTest4() {
		Date oneDate;
		try {
			oneDate = sdf.parse("02/05/2021");
			Mockito.doReturn(true).when(dataAccess).clientExist("Jorge");
			Cliente c1 = new Cliente("bryanespada", "123", "bryan@gmail.com");
			Mockito.doReturn(true).when(dataAccess).clientExist(c1.getUsername());
			Event e1=   new Event(queryText, oneDate);
			Question q1= new Question("hola", betMinimum, e1);
			Answer a1= new Answer("sdsad", 10.0f, q1);
			Apuesta apu1= new Apuesta(a1, betMinimum, oneDate, c1);
			Apuesta apu2= new Apuesta(a1, betMinimum+8.0f, oneDate, c1);
			List<Apuesta> apulist = new ArrayList<Apuesta>();
			apulist.add(apu1);
			apulist.add(apu2);
			Mockito.doReturn(apulist).when(dataAccess).getAllApuestas();
			Mockito.doReturn(apulist).when(dataAccess).BetsByClient(c1);
			ArrayList<Apuesta> obtained = sut.BetsByClient(c1);
			ArrayList<Apuesta> expected = listToArrayList(apulist);
			assertEquals(expected, obtained);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	public static ArrayList<Apuesta> listToArrayList(List<Apuesta> myList) {
        ArrayList<Apuesta> arl = new ArrayList<Apuesta>();
        for (Apuesta object : myList) {
            arl.add((Apuesta) object);
        }
        return arl;

    }
}
