package View.Classes;

import Provider.Classes.ApplicationsProvider;
import Provider.Classes.Provider;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationsView extends View {

	private int selectedApplicationId = -1;
	private final ArrayList<Integer> assignedApplicationIds;
	private final ApplicationsProvider provider;

	public ApplicationsView(ArrayList<Integer> assignedApplicationIds, Provider provider) {
		super(provider);
		this.assignedApplicationIds = assignedApplicationIds;
		this.provider = (ApplicationsProvider) provider;
	}

	private void chooseApplication() {
		System.out.print("Podaj ID wniosku: ");
		Scanner scanner = new Scanner(System.in);
		this.selectedApplicationId = scanner.nextInt();
		System.out.println("Wybrano wniosek o ID: " + this.selectedApplicationId);
	}

	private void acceptApplication() {
		provider.acceptApplication(selectedApplicationId);
		System.out.println("Wniosek zostal zaakceptowany");
		this.provider.sendMail(this.provider.getApplicant(this.selectedApplicationId), "Wniosek zaakceptowany");
		this.reset();
	}

	private void rejectApplication() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Podaj tresc odmowy: ");
		//scanner.nextLine();
		String rejectDescription = scanner.nextLine();
		provider.rejectApplication(selectedApplicationId, rejectDescription);
		System.out.println("Wniosek zostal odrzucony");
		this.provider.sendMail(this.provider.getApplicant(this.selectedApplicationId), "Wniosek odrzucony, uzasadnienie odrzucenia: " + rejectDescription);
		this.reset();
	}

	private void callOperation(int choice) {
		switch(choice) {
			case 1 -> chooseApplication();
			case 2 -> showApplication();
			case 3 -> acceptApplication();
			case 4 -> rejectApplication();
			case 5 -> showAssignedApplicationsIds();
			case 6 -> showApplicationID();
			default -> System.out.println("Bledna opcja");
		}
	}

	private int chooseAction() {

		System.out.print("\nWybierz opcje: ");
		Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
	}

	private void reset() {
		this.selectedApplicationId = -1;
	}

	private void showAssignedApplicationsIds() {
		System.out.println("\nNumery ID wnoskow przypisanych do uzytkownika: " + this.provider.getLoggedUserFullName());
		int i = 0;
		for(int id : this.assignedApplicationIds) {
			if(this.provider.checkApplicationArchivedStatus(id)) System.out.println(++i + " ID wniosku: "+id);
		}
	}

	private void showApplication() {
		if(selectedApplicationId == -1) this.chooseApplication();
		System.out.println("Tresc wniosku o numerze ID: "+selectedApplicationId);
		System.out.println(provider.getApplicationDetails(selectedApplicationId));
	}

	private void showApplicationID() {
		if(this.selectedApplicationId != -1) System.out.println("Numer ID wybranego wniosku: " + this.selectedApplicationId);
		else System.out.println("Nie wybrano wniosku");
	}

	@Override
	public void render() {
		System.out.println("\nZalogowany uzytkownik: "+this.provider.getLoggedUserFullName());
		while(true) {
			System.out.println("\n\nWidok wnioskow\n");
			System.out.println("Wybierz dzialanie: ");
			System.out.println("1. Wybierz wniosek");
			System.out.println("2. Wyswietl zawartosc wniosku");
			System.out.println("3. Zaakceptuj wniosek");
			System.out.println("4. Odrzuc wniosek");
			System.out.println("5. Wyswietl dostepne wnioski");
			System.out.println("6. Wyswietl numer ID wybranego wniosku");
			System.out.println("7. Wyjdz");
			int userChoice = chooseAction();
			if(userChoice == 7) break;
			else callOperation(userChoice);
		}
	}

}