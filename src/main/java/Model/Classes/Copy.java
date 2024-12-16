package Model.Classes;

import Model.Enums.CopyType;

public class Copy extends Model {

	private long size;

	public int getDepartmentID() {
		return departmentID;
	}

	private int departmentID;

	public void setSize(long size) {
		this.size = size;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public String getPath() {
		return path;
	}

	public long getSize() {
		return size;
	}

	public CopyType getType() {
		return type;
	}

	private final String path;
	private final CopyType type;

	/**
	 * 
	 * @param size
	 * @param departmentID
	 * @param path
	 * @param type
	 */
	public Copy(long size, int departmentID, String path, CopyType type) {
		super();
		this.size = size;
		this.departmentID = departmentID;
		this.path = path;
		this.type = type;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public String getPath() {
		return path;
	}
}