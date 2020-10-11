package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class ApuestaGlobal {

	
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private int idBet;
	private Cliente cliente;
	private Float amount; 
	private Date betDate;
	private int estado;
	
	public ApuestaGlobal(Float pamount, Date pbetDate, Cliente cliente) {
		this.amount=pamount;
		this.betDate=pbetDate;
		this.cliente = cliente;
		this.estado = 0; //todas las apuestas se crean en estado pendiente.
	}
	
	public void setIdBet(int pIdBet) {
		this.idBet = pIdBet;
	}
	
	public int getIdBet() {
		return this.idBet;
	}
	

	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public void setAmount(Float pamount) {
		this.amount = pamount;
	}
	
	public Float getAmount() {
		return this.amount;
	}
	
	public void setBetDate(Date pdate) {
		this.betDate = pdate;
	}
	
	public Date getBetDate() {
		return this.betDate;
	}
	

	public void setEstado(int pEstado) {
		this.estado = pEstado;
	}
	
	public int getEstado() {
		return this.estado;
	}
	
	


	
	
	
	
}
