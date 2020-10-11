package domain;

import java.io.*;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question;
	private Float betMinimum;
	private boolean result;  
	@XmlIDREF
	private Event event;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Answer> answers=new Vector<Answer>();

	public Question(Integer queryNumber, String query, Float betMinimum, Event event) {
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.result=false;
	}
	
	public Question(String query, Float betMinimum,  Event event) {
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.result = false;
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public Float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(Float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public boolean getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(boolean result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}




	public String toString(){
		return question+"";
	}

	
	
	
	public void setAnswers (Vector<Answer> pans) {
		this.answers = pans;
	}
	
	public Vector<Answer> getAnswers(){
		return this.answers;
	}

	public Answer addAnswer(String string, Float d) {
		Answer ans = new Answer(string, d, this);
		answers.add(ans);
		return ans;
	}

	public Answer addAnswer(Answer pan) {
		answers.add(pan);
		return pan;
	}




	
}