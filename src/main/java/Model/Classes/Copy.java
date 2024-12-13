package Model.Classes;

import Model.Enums.CopyType;
import Model.Facades.manageCopies;

public class Copy extends Model {

	private final long size;

	public int getDepartmentID() {
		return departmentID;
	}

	private final int departmentID;

	public String getPath() {
		return path;
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


}