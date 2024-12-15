package Model.Classes;

import Model.Enums.CopyType;

public class Copy extends Model {

	private final long size;
	private final int departmentID;
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