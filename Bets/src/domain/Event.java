package domain;

import java.io.Serializable;
import java.util.Date;
//import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//import exceptions.QuestionAlreadyExist;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer eventNumber;
	private String description; 
	private Date eventDate;
	private boolean result;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Question> questions=new Vector<Question>();

	
	public Event(Integer eventNumber, String description,Date eventDate) {
		this.eventNumber = eventNumber;
		this.description = description;
		this.eventDate=eventDate;
		this.result=false;
	}
	
	public Event(String description,Date eventDate) {
		this.description = description;
		this.eventDate=eventDate;
		this.result=false;
	}

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}
	
	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public void setResult(boolean pResult) {
		this.result = pResult;
	}
	
	public boolean getResult() {
		return this.result;
	}
	
	public String toString(){
		return description+"";
	}
	
	public Question addQuestion(String question, float betMinimum)  {
        Question q=new Question(question,betMinimum, this);
        questions.add(q);
        return q;
	}

	public boolean DoesQuestionExists(String question)  {	
		for (Question q:this.getQuestions()){
			if (q.getQuestion().compareTo(question)==0)
				return true;
		}
		return false;
	}
		

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventNumber != other.eventNumber)
			return false;
		return true;
	}
	
	
	
	

}
