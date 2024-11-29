package Provider.Interfaces;

import Model.Enums.CopyType;

public interface makeCopy {

	/**
	 * 
	 * @param userID
	 * @param selectedDepartmentId
	 * @param selectedCopyType
	 */
	void startCreateCopy(int userID, int selectedDepartmentId, CopyType selectedCopyType);

}