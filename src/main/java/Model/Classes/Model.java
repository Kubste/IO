package Model.Classes;

import java.time.LocalDateTime;

public abstract class Model {

	private static int idCounterApp = 0;
	private static int idCounterUs = 0;
	private static int idCounterDep = 0;
	private static int idCounterCop = 0;
	protected int id;
	protected LocalDateTime deleted_at;
	protected LocalDateTime created_at;
	protected LocalDateTime updated_at;
	protected static final DBManager dbManager = DBManager.getInstance();

	public Model() {
        this.assignID();
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
		this.deleted_at = null;
	}

	public int getId() {
		return id;
	}

	public boolean save() {
		dbManager.save(this);
		this.updated_at = LocalDateTime.now();
		return true;
	}

	public boolean delete() {
		this.deleted_at = LocalDateTime.now();
		return dbManager.delete(this);
	}

	public boolean update() {
		this.updated_at = LocalDateTime.now();
		return dbManager.update(this);
	}

	private void assignID() {
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