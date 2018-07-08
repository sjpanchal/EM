package emguiclient;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private int serverId;
	private String username, firstname, lastname, email, phonenumber;
	private Boolean isAdmin, isManager, isEmployee, isOutsideManager, isGroupContact, isOutsideVendor;
	
	public User(int serverid, String username, String firstname, String lastname, String email, String phonenumber, boolean isAdmin, boolean isManager, boolean isEmployee, boolean isOutsideManager, boolean isGroupContact, boolean isOutsideVendor) {
		this.serverId = serverid;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.isAdmin =  isAdmin;
		this.isManager = isManager;
		this.isEmployee = isEmployee;
		this.isOutsideManager = isOutsideManager;
		this.isGroupContact = isGroupContact;
		this.isOutsideVendor = isOutsideVendor;
	}
	
	public User(String username, String firstname, String lastname, String email, String phonenumber, boolean isAdmin, boolean isManager, boolean isEmployee, boolean isOutsideManager, boolean isGroupContact, boolean isOutsideVendor) {
		this.serverId = -1;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.isAdmin =  isAdmin;
		this.isManager = isManager;
		this.isEmployee = isEmployee;
		this.isOutsideManager = isOutsideManager;
		this.isGroupContact = isGroupContact;
		this.isOutsideVendor = isOutsideVendor;
	}
	
	public User(String username, String firstname, String lastname, String email, String phonenumber) {
		this.serverId = -1;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.isAdmin =  false;
		this.isManager = false;
		this.isEmployee = false;
		this.isOutsideManager = false;
		this.isGroupContact = false;
		this.isOutsideVendor = false;
	}

	public User(String[] subCommandSplit) throws UserOperationException {
		if(subCommandSplit.length != 12) {
			throw new UserOperationException("Argument list too short.");
		}
		else {
			this.serverId = -1;
			this.setUsername(subCommandSplit[1]);
			this.setFirstname(subCommandSplit[2]);
			this.setLastname(subCommandSplit[3]);
			this.setEmail(subCommandSplit[4]);
			this.setPhonenumber(subCommandSplit[5]);
			this.setAdmin(Boolean.parseBoolean(subCommandSplit[6]));
			this.setManager(Boolean.parseBoolean(subCommandSplit[7]));
			this.setEmployee(Boolean.parseBoolean(subCommandSplit[8]));
			this.setOutsideManager(Boolean.parseBoolean(subCommandSplit[9]));
			this.setGroupContact(Boolean.parseBoolean(subCommandSplit[10]));
			this.setOutsideVendor(Boolean.parseBoolean(subCommandSplit[11]));
		}
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public boolean isOutsideManager() {
		return isOutsideManager;
	}

	public void setOutsideManager(boolean isOutsideManager) {
		this.isOutsideManager = isOutsideManager;
	}

	public boolean isGroupContact() {
		return isGroupContact;
	}

	public void setGroupContact(boolean isGroupContact) {
		this.isGroupContact = isGroupContact;
	}

	public boolean isOutsideVendor() {
		return isOutsideVendor;
	}

	public void setOutsideVendor(boolean isOutsideVendor) {
		this.isOutsideVendor = isOutsideVendor;
	}
	
	public String newUserInfo() {
		return "?" + this.getUsername() + "?" + this.getFirstname() + "?" + this.getLastname() + "?" + this.getEmail() + "?" + this.getPhonenumber() + "?~~~"; 
	}

	public void updateUserInfo(String[] serverUserUpdate) throws UserOperationException {
		if(serverUserUpdate.length != 12) {
			throw new UserOperationException("Argument list too short.");
		}
		else {
			this.setUsername(serverUserUpdate[1]);
			this.setFirstname(serverUserUpdate[2]);
			this.setLastname(serverUserUpdate[3]);
			this.setEmail(serverUserUpdate[4]);
			this.setPhonenumber(serverUserUpdate[5]);
			this.setAdmin(Boolean.parseBoolean(serverUserUpdate[6]));
			this.setManager(Boolean.parseBoolean(serverUserUpdate[7]));
			this.setEmployee(Boolean.parseBoolean(serverUserUpdate[8]));
			this.setOutsideManager(Boolean.parseBoolean(serverUserUpdate[9]));
			this.setGroupContact(Boolean.parseBoolean(serverUserUpdate[10]));
			this.setOutsideVendor(Boolean.parseBoolean(serverUserUpdate[11]));
		}
	}
	
	
	
	
	private static String getHashedPassword(String passwd) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(passwd.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		while(hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		
		System.err.println("USER PASS md5ed: " + hashtext);
		return hashtext;
	}
}
