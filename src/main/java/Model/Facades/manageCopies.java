package Model.Facades;

import Model.Classes.Copy;
import Model.Classes.DBManager;
import Model.Enums.CopyType;
import java.io.File;

public abstract class manageCopies {


	// boolean deleteCopy(int id);


//	static boolean addCopy(File file, CopyType type, int departmentID){
//		Copy copy = new Copy(file.getTotalSpace(), departmentID, file.getAbsolutePath(),type);
//		return true;
//	}



	public static Copy getLastCopyForDepartment(int departmentId){
		return DBManager.getInstance().getDatabase().getAllCopies().stream().filter(c -> c.getDepartmentID() == departmentId).reduce((first, second) -> second)
				.orElse(null);
	}

	static boolean addCopy(File file, CopyType copyType, int departmentID) {
		DBManager.getInstance().getDatabase().addCopy(new Copy(file.getTotalSpace(), departmentID, file.getPath(), copyType));
		return true;
	}

}