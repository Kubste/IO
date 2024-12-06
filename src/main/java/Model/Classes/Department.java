package Model.Classes;

import Model.Interfaces.getDepartments;

import java.util.ArrayList;

public class Department extends Model implements getDepartments {

	private String name;
	private String address;
	private ArrayList<User> officials;
	private ArrayList<User> citizens;
	private int administratorID;
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
}