package Model.Classes;

import Model.Enums.CopyType;
import Model.Interfaces.manageCopies;
import java.io.File;

public class Copy extends Model implements manageCopies {

	private long size;
	private int departmentID;
	private String path;
	private CopyType type;

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

	@Override
	public boolean deleteCopy(int id) {
		return false;
	}

	@Override
	public boolean addCopy(File file, CopyType type, int departmentID) {
		return false;
	}
}