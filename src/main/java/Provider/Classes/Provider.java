package Provider.Classes;

import Model.Classes.Model;
import View.Classes.View;
import View.Interfaces.IView;
import Model.Classes.User;
import org.apache.commons.lang3.NotImplementedException;

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


	protected void createView() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	protected void sendMail(String receiver, String message) {
		System.out.println("Wiadomosc: " + message + " zostala wyslana do: " + receiver);
	}

	public int getLoggerUserID() {
		return loggedUser.getID();
	}

}