package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Usuario {
	
	public Cliente(String pusername, String ppassword, String pemail) {
		super(pusername, ppassword, pemail);
		this.saldo=(float) 0.00;
		this.tarjeta="";
		this.CC="";
	}
	
	public Cliente(String pusername, String ppassword, String pemail, Float psaldo, String pnombre, String pCC, String ptarjeta) {
		super(pusername, ppassword, pemail, pnombre);
		this.setSaldo(psaldo);
		this.setCC(pCC);
		this.setTarjeta(ptarjeta);
	}

	private String CC;
	private String tarjeta;
	private Float saldo;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apuesta> apuestas=new Vector<Apuesta>();
	
	
	//ACCESO A ATRIBUTOS
	public void setBet (Vector<Apuesta> papuesta) {
		this.apuestas = papuesta;
	}
	
	public Vector<Apuesta> getApuesta(){
		return this.apuestas;
	}
	
	public void setCC (String pCC) {
		this.CC	= pCC;
	}
	
	public String getCC () {
		return this.CC;
	}
	
	public void setTarjeta (String ptarjeta) {
		this.tarjeta = ptarjeta;
	}
	
	public String getTarjeta () {
		return this.tarjeta;
	}
	
	public void setSaldo (Float psaldo) {
		this.saldo = psaldo;
	}
	
	public Float getSaldo () {
		return this.saldo;
	}

	public Apuesta addBet(Apuesta papuesta) {
		apuestas.add(papuesta);
		return papuesta;
	}

}
