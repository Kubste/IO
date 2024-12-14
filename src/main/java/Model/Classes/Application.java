package Model.Classes;

import Model.Enums.ApplicationStatus;

import java.time.LocalDateTime;

public class Application extends Model {

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

	public ApplicationStatus getStatus() {
		return this.status;
	}

	public void setAcceptParams() {
		this.isArchived = true;
		this.considerationDate = LocalDateTime.now();
	}

	public void setRejectParams(String description) {
		this.isArchived = true;
		this.considerationDate = LocalDateTime.now();
		this.rejectedDescription = description;
		this.update();
	}


	@Override
	public boolean delete(){
		this.isArchived = true;
		return super.delete();
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.userID, this.description, this.status);
	}
}