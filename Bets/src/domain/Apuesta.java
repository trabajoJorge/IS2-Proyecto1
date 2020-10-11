package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.ResourceBundle;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

//import exceptions.QuestionAlreadyExist;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apuesta extends ApuestaGlobal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Answer answer;
	
	
	public Apuesta(Answer panswer, Float pamount, Date pbetDate, Cliente cliente) {
		super(pamount,  pbetDate,  cliente);
		this.answer = panswer;
	}
	
	
	public Answer getAnswer() {
		return answer;
	}


	public void setAnswer(Answer answer) {
		this.answer = answer;
	}


	public String toString(){
		String strDateFormat = "dd-MM-yyyy";
		SimpleDateFormat fecha = new SimpleDateFormat(strDateFormat);
		Date f= this.getAnswer().getQuestion().getEvent().getEventDate();
		return "Id "+this.getIdBet()+"/"+fecha.format(f)+"/"+this.getAnswer().getQuestion()+"/"+this.getAnswer();
	}


}
