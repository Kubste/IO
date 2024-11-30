package Model.Classes;

import Model.Enums.CopyType;
import Model.Interfaces.manageCopies;

public class Copy extends Model implements manageCopies {

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

//	/**
//	 *
//	 * @param file
//	 * @param type
//	 * @param departmentID
//	 */
//	public Copy(int id, File file, CopyType type, int departmentID) {
//		super(id);
//		this.
//	}

}