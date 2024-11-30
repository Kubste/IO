package Model.Classes;

import Model.Enums.ApplicationStatus;
import Model.Interfaces.exposeApplications;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Application extends Model implements exposeApplications {

	private final int userID;
	private final int departmentID;
	private ApplicationStatus status;
	private LocalDateTime considerationDate;

	private final String description;
	private String rejectedDescription;

	private boolean isArchived;

	public Application(int userID, int departmentID, String description) {
		super();
		this.userID = userID;
		this.departmentID = departmentID;
		this.status = ApplicationStatus.PENDING;
		this.considerationDate = null;
		this.description = description;
		this.rejectedDescription = null;
		this.isArchived = false;
	}

	public int getUserID() {
		return this.userID;
	}


	public int getDepartmentID() {
		return this.departmentID;
	}
	public String getDescription() {
		return description;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean archived) {
		isArchived = archived;
	}

	@Override
	public ApplicationStatus getApplicationStatus(int applicationID) {
		return this.status;
	}

	@Override
	public void acceptApplication(int applicationID) {
		this.status = ApplicationStatus.ACCEPTED;
		this.isArchived = true;
		dbManager.update(this);
		this.considerationDate = LocalDateTime.now();
	}

	@Override
	public boolean delete(){
		this.isArchived = true;
		return super.delete();
	}
	@Override
	public void rejectApplication(int applicationID, String rejectedDescription) {
		this.status = ApplicationStatus.REJECTED;
		this.rejectedDescription = rejectedDescription;
		this.isArchived = true;
		dbManager.update(this);
		this.considerationDate = LocalDateTime.now();
	}

}