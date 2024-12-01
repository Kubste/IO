package Provider.Classes;

import View.Classes.View;
import View.Interfaces.IView;
import Model.Classes.User;
import java.util.*;

public abstract class Provider {

	protected ArrayList<Class<? extends View>> supportedViews = new ArrayList<>();
	protected User loggedUser;

	public Provider(User loggedUser){
		this.loggedUser = loggedUser;
	}

	/**
	 *
	 * @param selectedView
	 */
	protected void checkView(IView selectedView) {
		if(!supportedViews.contains(selectedView.getClass())){
			throw new UnsupportedOperationException();
		}
	}


	public void createView() {
		throw new UnsupportedOperationException();
	}

	public int getLoggedUserID() {
		return this.loggedUser.getID();
	}

	public String getLoggedUserUsername() {
		return this.loggedUser.getUsername();
	}

	public String getLoggedUserFullName() {
		return this.loggedUser.getFirstName() + this.loggedUser.getLastName();
	}

}