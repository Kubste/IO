package Model.Interfaces;

import Model.Classes.Copy;
import Model.Classes.DBManager;
import Model.Enums.CopyType;
import java.io.File;

public interface manageCopies {


	// boolean deleteCopy(int id);


//	static boolean addCopy(File file, CopyType type, int departmentID){
//		Copy copy = new Copy(file.getTotalSpace(), departmentID, file.getAbsolutePath(),type);
//		return true;
//	}

	static boolean addCopy(File file, CopyType copyType, int departmentID) {
		DBManager.getInstance().getDatabase().addCopy(new Copy(file.getTotalSpace(), departmentID, file.getPath(), copyType));
		return true;
	}

}