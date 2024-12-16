package Model.Classes;

import java.util.ArrayList;

public class Department extends Model {

	private String name;
	private String address;
	private ArrayList<User> officials;
	private ArrayList<User> citizens;
	private int administratorID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<User> getOfficials() {
		return officials;
	}

	public void setOfficials(ArrayList<User> officials) {
		this.officials = officials;
	}

	public ArrayList<User> getCitizens() {
		return citizens;
	}

	public void setCitizens(ArrayList<User> citizens) {
		this.citizens = citizens;
	}

	public int getAdministratorID() {
		return administratorID;
	}

	public void setAdministratorID(int administratorID) {
		this.administratorID = administratorID;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	private boolean isLocked;

	/**
	 * 
	 * @param name
	 * @param address
	 * @param administratorID
	 */
	public Department(String name, String address, int administratorID, ArrayList<User> officials, ArrayList<User> citizens) {
		super();
		this.name = name;
		this.address = address;
		this.administratorID = administratorID;
		this.officials = officials;
		this.citizens = citizens;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}
}