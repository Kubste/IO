package View.Classes;

import Provider.Classes.ApplicationsProvider;
import Provider.Classes.Provider;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationsView extends View {

	private int selectedApplicationId;
	private ArrayList<Integer> assignedApplicationIds;
	private ApplicationsProvider provider;

	public ApplicationsView(ArrayList<Integer> assignedApplicationIds, Provider provider) {
		super(provider);
		this.assignedApplicationIds = assignedApplicationIds;
	}

	private void chooseApplication() {
		System.out.print("Podaj ID wniosku: ");
		Scanner scanner = new Scanner(System.in);
		this.selectedApplicationId = scanner.nextInt();
		System.out.println("Wybrano urzad o ID: " + this.selectedApplicationId);
	}

	private void chooseAction() {

		System.out.print("\nWybierz opcje: ");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		scanner.nextInt();
		switch(choice) {
			case 1 -> chooseApplication();
			case 2 -> showApplication();
			case 3 -> provider.acceptApplication(selectedApplicationId);
            case 4 -> {
                System.out.print("Podaj tresc odmowy: ");
                provider.rejectApplication(selectedApplicationId, scanner.nextLine());
            }
        }
	}

	private void reset() {
		this.selectedApplicationId = -1;
	}

	private void showApplication() {
		if(selectedApplicationId == -1) this.chooseApplication();
		else {
			System.out.println("Zawartosc wniosku o numerze ID: " + selectedApplicationId);
			System.out.println(provider.getApplicationDetails(selectedApplicationId));
		}
	}

	@Override
	public void render() {
		System.out.println("Widok wnioskow\n");
		System.out.println("Wybierz dzialanie: ");
		System.out.println("1. Wybierz wniosek");
		System.out.println("2. Wyswietl zawartosc wniosku");
		System.out.println("3. Zaakceptuj wniosek");
		System.out.println("4. Odrzuc wniosek");
		this.chooseAction();
	}

}