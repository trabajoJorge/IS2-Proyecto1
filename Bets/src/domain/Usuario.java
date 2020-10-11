package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	protected String username;
	protected String name;
	protected String password;
	protected String email;

	
	//CONSTRUCTOR DE REGISTRO
	public Usuario (String pusername, String pemail, String ppassword) {
		super();
		this.username	= pusername;
		this.password	= ppassword;
		this.email		= pemail;
	}
	
	public Usuario (String pname, String pusername, String pemail, String ppassword) {
		super();
		this.username	= pusername;
		this.password	= ppassword;
		this.email		= pemail;
		this.name 		= pname;
	}
	
	
	//ACCESO A ATRIBUTOS
	public void setUsername(String pusername) {
		this.username	= pusername;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setName(String pname) {
		this.name	= pname;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPassword(String ppassword) {
		this.password	= ppassword;
	}

	public String getPassword() {
		return this.password;
	}

	public void setEmail(String pemail) {
		this.email	= pemail;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	
}