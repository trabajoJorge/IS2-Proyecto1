package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.Admin;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.CuentaGlobal;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	@WebMethod public Vector<Event> getEvents(Date date);
	
	@WebMethod public Vector<Event> getOpenEvents(Date date);
	
	@WebMethod public Vector<Date> getEventsMonth(Date date);

	@WebMethod public void initializeBD();

	@WebMethod public boolean doLogin(String username, String password);

	@WebMethod public boolean doRegister(String username, String password, String email);
	
	@WebMethod public boolean isAdmin(String username, String password);

	@WebMethod public List<Question> getQuestionsByEvent(Event event);

	@WebMethod public List<Question> getOpenQuestionsByEvent(Event event);

	@WebMethod public List<Answer> getAnswersByQuestion(Question selectedQuestion);

	@WebMethod public List<Answer> getOpenAnswersByQuestion(Question selectedQuestion);
	
	@WebMethod public int createApuesta(Answer selectedAnswer, Cliente selectedClient, Float selectedAmount, Question selectedQuestion);

	@WebMethod public Cliente getClientByUsername(String us);

	@WebMethod public Admin getAdminByUsername(String us);
	
	@WebMethod public ArrayList <Apuesta> BetsByClient(Cliente a);
	
	@WebMethod public boolean insertAnswer(Answer an);

	@WebMethod public List<Event> getAllEvents();
	
	@WebMethod public boolean insertEvent(Event ev);

	@WebMethod public Float getSaldoEnCuenta(Cliente currentClient);

	@WebMethod boolean definirResultados(Event selectedEvent, Question selectedQuestion, Answer selectedAnswer, Admin currentAdmin);

	@WebMethod public CuentaGlobal getCuentaGlobal();

	@WebMethod public boolean createAdmin(String pNombre, String pUsername, String pEmail, String pPassword);
	
	@WebMethod public boolean acreditarSaldo(Cliente pClient, float pMonto, int pin, String tipo);

	@WebMethod public boolean confirmarEditarPerfilCliente(String pNombre, String pUsername, String pEmail, String pTarjeta, String pCC);

	@WebMethod public boolean anularApuesta(Apuesta papuesta);

	@WebMethod public boolean updateQuestion(Question q, String r, float f);

	@WebMethod public boolean updateAnswer(Answer selectedAnswer, String respuestaModificada, Float coeficienteFloat);

	@WebMethod public boolean deleteAnswer(Answer selectedAnswer);

	@WebMethod public boolean updateQuestion(Question selectedQuestion, String preeguntaModificada, Float apuestaMinimaFloat);

	@WebMethod public boolean deleteQuestion(Question selectedQuestion);

	@WebMethod public boolean updateEvent(Event selectedEvent, String text);

	@WebMethod public boolean deleteEvent(Event selectedEvent);
	
}
