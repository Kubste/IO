package Model.Interfaces;

import Model.Classes.Copy;
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
	static boolean addCopy(File file, CopyType type, int departmentID){
		Copy copy = new Copy(file.getTotalSpace(), departmentID, file.getAbsolutePath(),type);
		return true;
	}

}