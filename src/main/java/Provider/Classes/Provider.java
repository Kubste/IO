package Provider.Classes;

import Model.Classes.Model;
import View.Interfaces.IView;
import Model.Classes.User;
import java.util.*;

public abstract class Provider {

	protected ArrayList<Model> supportedModels = new ArrayList<>();
	protected User loggedUser;

	/**
	 * 
	 * @param selectedView
	 */
	protected boolean checkViewArgs(IView selectedView) {
		// TODO - implement Provider.Classes.Provider.checkViewArgs
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param args
	 */
	protected IView createView(Object... args) {

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