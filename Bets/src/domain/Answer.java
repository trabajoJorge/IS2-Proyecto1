package domain;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Answer implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer answerNumber;
	private String answerText;
	private boolean activa;
	private Float coeficienteGanancia;
	private boolean respuestaGanadora;
	@XmlIDREF
	private Question question;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apuesta> apuestas=new Vector<Apuesta>();

	
	public Answer(String panswerText, Float i, Question pquestion) {
		this.answerText=panswerText;
		this.coeficienteGanancia=i;
		this.question = pquestion;
		this.activa = true;
		this.respuestaGanadora=false;
	}

	public void setBet (Vector<Apuesta> papuesta) {
		this.apuestas = papuesta;
	}
	
	public Vector<Apuesta> getApuesta(){
		return this.apuestas;
	}
	
	public void setAnswerNumber(int panswerNumber) {
		this.answerNumber=panswerNumber;
	}
	
	public int getAnswerNumber() {
		return this.answerNumber;
	}
	
	public void setCoeficienteGanancia(Float pcoeficienteGanancia) {
		this.coeficienteGanancia= pcoeficienteGanancia;
	}
	
	public Float getCoeficienteGanancia() {
		return this.coeficienteGanancia;
	}
	
	public void setAnswerText(String panswerText) {
		this.answerText=panswerText;
	}
	
	public String getAnswerText() {
		return this.answerText;
	}
	
	public void setQuestion(Question pquestion) {
		this.question=pquestion;
	}
	
	public Question getQuestion() {
		return this.question;
	}
	
	public void setRespuestaGanadora(boolean pRespuestaGanadora) {
		this.respuestaGanadora = pRespuestaGanadora;
	}
	
	public boolean getRespuestaGanadora() {
		return this.respuestaGanadora;
	}
	
	public void setActiva(boolean pActiva) {
		this.activa = pActiva;
	}
	
	public boolean getActiva() {
		return this.activa;
	}


	public String toString(){
		return answerText+"";
	}


	public Apuesta addBet(Float pselectedAmount, Cliente pselectedClient) {
		
		Date today=Calendar.getInstance().getTime();
		Apuesta ap = new Apuesta(this, pselectedAmount, today, pselectedClient);
		apuestas.add(ap);
		return ap;
		
	}
	

	
}