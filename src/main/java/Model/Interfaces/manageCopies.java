package Model.Interfaces;

import Model.Enums.CopyType;
import java.io.File;

public interface manageCopies {

	/**
	 * 
	 * @param id
	 */
	boolean deleteCopy(int id);

	/**
	 * 
	 * @param file
	 * @param type
	 * @param departmentID
	 */
	boolean addCopy(File file, CopyType type, int departmentID);

}