package Model.Classes;

import java.time.LocalDateTime;

public abstract class Model {

	private static int idCounterApp = 0;
	private static int idCounterUs = 0;
	private static int idCounterDep = 0;
	private static int idCounterCop = 0;


	protected int id;
	protected LocalDateTime deleted_at;

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public static void resetIdCounters() {
		idCounterApp = 0;
		idCounterUs = 0;
		idCounterDep = 0;
		idCounterCop = 0;
	}

	protected LocalDateTime created_at;
	protected LocalDateTime updated_at;
	protected static final DBManager dbManager = DBManager.getInstance();

	public Model() {
        this.assignID();
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
		this.deleted_at = null;
	}

	protected LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public boolean save() {
		this.created_at = getCurrentTime();
		dbManager.save(this);
		return true;
	}

	public boolean delete() {
		this.deleted_at = LocalDateTime.now();
		return dbManager.delete(this);
	}

	public boolean update() {
		this.updated_at = LocalDateTime.now();
		dbManager.update(this);
		return true;
	}

	public void setUpdated_at() {
		this.updated_at = LocalDateTime.now();
	}

	public void assignID() {
        switch(this) {
            case Application ignored -> this.id = idCounterApp++;
            case User ignored -> this.id = idCounterUs++;
            case Department ignored -> this.id = idCounterDep++;
            case Copy ignored -> this.id = idCounterCop++;
            default -> {
            }
        }
	}
}