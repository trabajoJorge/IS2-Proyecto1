package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Admin;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.CuentaGlobal;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	DataAccess dbManager; 
	
	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
			}
	}
	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();
		}
		dbManager=da;
	}	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
	    dbManager.open (false); 
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   };
	
	
   	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open (false); 
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	
    //Devuelve todos los eventos 
	@WebMethod	
	public Vector<Event> getOpenEvents(Date date)  {
		dbManager.open (false); 
		Vector<Event>  events=dbManager.getOpenEvents(date);
		dbManager.close();
		return events;
	}
    
	
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open (false); 
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
		dbManager.open (false); 
		dbManager.initializeDB();
		dbManager.close();
	}
    
    
    public boolean doLogin (String pusername, String ppassword) {
    	dbManager.open (false); 
		boolean existUser = dbManager.doLogin(pusername, ppassword);
		dbManager.close();
    	return existUser;
    }
    
    

	public boolean doRegister(String pusername, String ppassword, String pemail) {
		dbManager.open (false); 
		boolean existUser = dbManager.doRegister(pusername, ppassword, pemail);
		dbManager.close();
		return existUser;
	}
	
	public boolean isAdmin(String pusername, String ppassword) {
		dbManager.open (false); 
		boolean isAdmin = dbManager.isAdmin(pusername, ppassword);
		dbManager.close();
		return isAdmin;
	}



	@Override
	public List<Question> getQuestionsByEvent(Event pevent) {
		dbManager.open (false); 
		List<Question> questions = dbManager.getQuestionsByEvent(pevent);
		dbManager.close();
		return questions;
	}
	
	
	@Override
	public List<Question> getOpenQuestionsByEvent(Event pevent) {
		dbManager.open (false); 
		List<Question> questions = dbManager.getOpenQuestionsByEvent(pevent);
		dbManager.close();
		return questions;
	}


	@Override
	public List<Answer> getAnswersByQuestion(Question pselectedQuestion) {
		dbManager.open (false); 
		List<Answer> answers = dbManager.getAnswersByQuestion(pselectedQuestion);
		dbManager.close();
		return answers;
	}
	

	@Override
	public List<Answer> getOpenAnswersByQuestion(Question pselectedQuestion) {
		dbManager.open (false); 
		List<Answer> answers = dbManager.getOpenAnswersByQuestion(pselectedQuestion);
		dbManager.close();
		return answers;
	}
	

	@Override
	public int createApuesta(Answer selectedAnswer, Cliente pselectedClient, Float pselectedAmount, Question pselectedQuestion) {
		dbManager.open (false); 
		int apuestaCreada = dbManager.createApuesta(selectedAnswer, pselectedClient, pselectedAmount, pselectedQuestion);
		dbManager.close();
		return apuestaCreada;
	}


	@Override
	public Cliente getClientByUsername(String pusername) {
		DataAccess dbManager= new DataAccess();
		Cliente CliDB = dbManager.getClientByUsername(pusername);
		dbManager.close();
		return CliDB;
	}

	@Override 
	public ArrayList <Apuesta> BetsByClient(Cliente a){
		dbManager.open (false); 
		ArrayList<Apuesta> ApuList= dbManager.BetsByClient(a);
		return ApuList;
	}

	public Admin getAdminByUsername(String pusername) {
		DataAccess dbManager= new DataAccess();
		Admin AdmDB = dbManager.getAdminByUsername(pusername);
		dbManager.close();
		return AdmDB;
	}
	

	public List<Event> getAllEvents(){
		DataAccess dbManager = new DataAccess();
		List<Event> r = dbManager.getAllEvents();
		dbManager.close();
		return r;
	}
	
	public boolean insertEvent(Event ev) {
		DataAccess dbManager = new DataAccess();
		return dbManager.insertEvent(ev);
	}
	
	public boolean insertAnswer(Answer an) {
		DataAccess dbManager = new DataAccess();
		return dbManager.insertAnswer(an);
	}


	@Override
	public Float getSaldoEnCuenta(Cliente pcurrentClient) {
		return pcurrentClient.getSaldo();
	}


	@Override
	public boolean definirResultados(Event pselectedEvent, Question pselectedQuestion, Answer pselectedAnswer,Admin pcurrentAdmin) {
		DataAccess dbManager = new DataAccess();
		return dbManager.definirResultados(pselectedEvent, pselectedQuestion, pselectedAnswer, pcurrentAdmin);
	}


	@Override
	public CuentaGlobal getCuentaGlobal() {
		DataAccess dbManager = new DataAccess();
		return dbManager.getCuentaGlobal();
	}


	@Override
	public boolean createAdmin(String pNombre, String pUsername, String pEmail, String pPassword) {
		DataAccess dbManager = new DataAccess();
		boolean registrado = dbManager.createAdmin(pNombre, pUsername, pEmail, pPassword);
		dbManager.close();
		return registrado;
	}
	
	@Override
	public boolean acreditarSaldo(Cliente pClient, float pMonto, int pPin, String pTipo) {
		DataAccess dbManager = new DataAccess();
		boolean acreditado = dbManager.acreditarSaldo(pClient, pMonto, pPin, pTipo);
		dbManager.close();
		return acreditado;
	}

	@Override
	public boolean confirmarEditarPerfilCliente(String pNombre, String pUsername, String pEmail, String pTarjeta, String pCC) {
		DataAccess dbManager = new DataAccess();
		boolean modificado = dbManager.confirmarEditarPerfilCliente(pNombre, pUsername, pEmail, pTarjeta, pCC);
		dbManager.close();
		return modificado;
	}
	
	@Override
	public boolean anularApuesta(Apuesta pApuesta) {
		DataAccess dbManager = new DataAccess();
		boolean anulada = dbManager.anularApuesta(pApuesta);
		dbManager.close();
		return anulada;
	}
	
	@Override
	public boolean updateQuestion(Question q, String r, float f) {
		DataAccess dbManager = new DataAccess();
		boolean actualizada = dbManager.updateQuestion(q, r, f);
		dbManager.close();
		return actualizada;
	}
	
	@Override
	public boolean updateAnswer(Answer selectedAnswer, String respuestaModificada, Float coeficienteFloat) {
		DataAccess dbManager = new DataAccess();
		boolean actualizada = dbManager.updateAnswer(selectedAnswer, respuestaModificada, coeficienteFloat);
		dbManager.close();
		return actualizada;
	}

	@Override
	public boolean deleteAnswer(Answer pselectedAnswer) {
		DataAccess dbManager = new DataAccess();
		boolean anulada = dbManager.deleteAnswer(pselectedAnswer);
		dbManager.close();
		return anulada;
	}


	@Override
	public boolean updateQuestion(Question pselectedQuestion, String ppreguntaModificada, Float papuestaMinimaFloat) {
		DataAccess dbManager = new DataAccess();
		boolean actualizada = dbManager.updateQuestion(pselectedQuestion, ppreguntaModificada, papuestaMinimaFloat);
		dbManager.close();
		return actualizada;
	}

	@Override
	public boolean deleteQuestion(Question pselectedQuestion) {
		DataAccess dbManager = new DataAccess();
		boolean anulada = dbManager.deleteQuestion(pselectedQuestion);
		dbManager.close();
		return anulada;
	}


	@Override
	public boolean updateEvent(Event pselectedEvent, String ptext) {
		DataAccess dbManager = new DataAccess();
		boolean actualizado = dbManager.updateQuestion(pselectedEvent, ptext);
		dbManager.close();
		return actualizado;
	}


	@Override
	public boolean deleteEvent(Event pselectedEvent) {
		DataAccess dbManager = new DataAccess();
		boolean anulado = dbManager.deleteEvent(pselectedEvent);
		dbManager.close();
		return anulado;
	}
	
	
}

