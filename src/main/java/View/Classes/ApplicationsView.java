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
		System.out.println(STR."Wybrano wniosek o ID: \{this.selectedApplicationId}");
	}

	private boolean chooseAction() {

		System.out.print("\nWybierz opcje: ");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		switch(choice) {
			case 1 -> chooseApplication();
			case 2 -> showApplication();
			case 3 -> {
				provider.acceptApplication(selectedApplicationId);
				System.out.println("Wniosek zostal zaakceptowany");
				this.reset();
			}
            case 4 -> {
                System.out.print("Podaj tresc odmowy: ");
				scanner.nextLine();
				String rejectDescription = scanner.nextLine();
                provider.rejectApplication(selectedApplicationId, rejectDescription);
				System.out.println("Wniosek zostal odrzucony");
				this.reset();
            } case 5 -> this.showAssignedApplicationsIds();
			case 6 -> {
				if(this.selectedApplicationId != -1) System.out.println(STR."Numer ID wybranego wniosku: \{this.selectedApplicationId}");
				else System.out.println("Nie wybrano wniosku");
			}
            case 7 -> {
                return true;
            }default -> System.out.println("Bledna opcja");
        }
		return false;
	}

	private void reset() {
		this.selectedApplicationId = -1;
	}

	private void showAssignedApplicationsIds() {
		System.out.println(STR."\nNumery ID wnoskow przypisanych do uzytkownika: \{this.provider.getLoggedUserFullName()}");
		int i = 0;
		for(int id : this.assignedApplicationIds) {
			if(this.provider.checkApplicationArchivedStatus(id)) System.out.println(STR."\{++i}. ID wniosku: \{id}");
		}
	}

	private void showApplication() {
		if(selectedApplicationId == -1) this.chooseApplication();
		System.out.println(STR."Tresc wniosku o numerze ID: \{selectedApplicationId}");
		System.out.println(provider.getApplicationDetails(selectedApplicationId));
	}

	@Override
	public void render() {
		boolean exit = false;
		System.out.println(STR."\nZalogowany uzytkownik: \{this.provider.getLoggedUserFullName()}");
		while(!exit) {
			System.out.println("\n\nWidok wnioskow\n");
			System.out.println("Wybierz dzialanie: ");
			System.out.println("1. Wybierz wniosek");
			System.out.println("2. Wyswietl zawartosc wniosku");
			System.out.println("3. Zaakceptuj wniosek");
			System.out.println("4. Odrzuc wniosek");
			System.out.println("5. Wyswietl dostpene wnioski");
			System.out.println("6. Wyswietl numer ID wybranego wniosku");
			System.out.println("7. Wyjdz");
			exit = this.chooseAction();
		}
	}

}