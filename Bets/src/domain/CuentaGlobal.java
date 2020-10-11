package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CuentaGlobal {

	//ATRIBUTOS
	@Id
	private int idCuenta;
	private float saldoGlobal;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apuesta> apuestas=new Vector<Apuesta>();
	
	
	//CONSTRUCTORES
	
	public CuentaGlobal() {
		
	}
	
	public CuentaGlobal(int pId, float pSaldoGlobal) {
		this.idCuenta=pId;
		this.saldoGlobal=pSaldoGlobal;
	}
	
	//MANEJO DE ATRIBUTOS
	
	public int getIdCuenta() {
		return this.idCuenta;
	}
	
	public void setIdCuenta(int pidCuenta) {
		this.idCuenta = pidCuenta;
	}
	
	
	public float getSaldoGlobal() {
		return this.saldoGlobal;
	}

	public void setSaldoGlobal(float pSaldoGlobal) {
		this.saldoGlobal = pSaldoGlobal;
	}
	
	public void setBets (Vector<Apuesta> papuesta) {
		this.apuestas = papuesta;
	}
	
	public Vector<Apuesta> getBets(){
		return this.apuestas;
	}
	
	public Apuesta addBet(Apuesta papuesta) {
		apuestas.add(papuesta);
		return papuesta;
	}
}
