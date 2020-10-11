package domain;

import javax.persistence.Entity;

@Entity
public class Admin extends Usuario {
	
	private boolean isAdmin;
	
	
	public Admin(String pusername, String ppassword, String pemail) {
		super(pusername, ppassword, pemail);
		this.isAdmin = true;
	}
	
	public Admin(String pnombre, String pusername, String pemail, String ppassword) {
		super(pnombre, pusername, pemail, ppassword);
		this.isAdmin = true;
	}

	
	public boolean getIsAdmin () {
		return this.isAdmin;
	}

}
