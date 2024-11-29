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
	public Department(int id, String name, String address, int administratorID) {
		super();
		this.name = name;
		this.address = address;
		this.administratorID = administratorID;
		this.officials = new ArrayList<>();
		this.citizens = new ArrayList<>();
	}

	@Override
	public ArrayList<Department> getAssignedDepartments(User user) {
		return null;
	}
}