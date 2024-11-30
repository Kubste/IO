package View.Classes;

import Provider.Classes.Provider;
import View.Interfaces.IView;

public abstract class View implements IView {

	protected int loggedUserID;
	protected Provider provider;

	public View(Provider provider) {
		this.provider = provider;
		this.loggedUserID = provider.getLoggedUserID();
	}
}