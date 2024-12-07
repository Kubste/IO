package View.Classes;

import Model.Enums.CopyType;
import Provider.Classes.CopyProvider;
import Provider.Classes.Provider;
import java.util.ArrayList;
import java.util.Scanner;

public class CopyView extends View {

	private int selectedDepartmentId = -1;
	private CopyType selectedCopyType;
	private final CopyProvider provider;
	private final ArrayList<Integer> assignedDepartmentIds;

	public CopyView(ArrayList<Integer> assignedDepartmentIds, Provider provider) {
		super(provider);
		this.provider = (CopyProvider) provider;
		this.assignedDepartmentIds = assignedDepartmentIds;
	}

	private void chooseDepartment() {
		System.out.print("\nPodaj ID urzedu: ");
		Scanner scanner = new Scanner(System.in);
		int ID = scanner.nextInt();
		if(this.assignedDepartmentIds.contains(ID)) {
			this.selectedDepartmentId = ID;
			System.out.println("Wybrano urzad o ID: "+this.selectedDepartmentId);
		} else System.out.println("Wybrano bledny numer ID");
	}

	private void chooseCopyType() {
		System.out.println("\n");
		System.out.print("Wybierz typ kopii (1 - calosciowa, 2 - przyrostowa): ");
		Scanner scanner = new Scanner(System.in);
		int copyType = scanner.nextInt();
		if(copyType == 1) this.selectedCopyType = CopyType.FULL;
		else if(copyType == 2) this.selectedCopyType = CopyType.INCREMENTAL;
		System.out.println("Wybrano typ kopii: "+selectedCopyType);
	}

	private void submit() {
		if(this.selectedDepartmentId != -1 && this.selectedCopyType != null) {
			this.provider.startCreateCopy(this.loggedUserID, this.selectedDepartmentId, this.selectedCopyType);
			System.out.println("\nTworzenie kopii...");
		} else System.out.println("Nie wybrano poprawnych parametrow");

	}

	private void reset() {
		this.selectedCopyType = null;
		this.selectedDepartmentId  = -1;
	}

	private void showSelectedParameters() {
		System.out.println("\n");
		if(this.selectedDepartmentId == -1) System.out.println("Nie wybrano urzedu");
		else System.out.println("Numer ID wybranego urzedu: "+this.selectedDepartmentId);
		if(this.selectedCopyType == null) System.out.println("Nie wybrano typu kopii");
		else System.out.println("Wybrany typ kopii: "+this.selectedCopyType);
	}

	private void showAssignedDepartmentsIDs() {
		System.out.println("Numery ID urzedow przypisanych do uzytkownika: "+this.provider.getLoggedUserFullName());
		int i = 0;
		for(int id : assignedDepartmentIds) System.out.println(++i + ". ID urzedu: "+id);
	}

	private void callOperation(int choice) {
		switch(choice) {
			case 1 -> this.showSelectedParameters();
			case 2 -> this.chooseDepartment();
			case 3 -> this.chooseCopyType();
			case 4 -> this.submit();
			case 5 -> this.showAssignedDepartmentsIDs();
			case 6 -> this.reset();
			default -> System.out.println("Bledna opcja");
		}
	}

	private int chooseAction() {
		System.out.print("\nWybierz opcje: ");

		Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
	}

	@Override
	public void render() {
		System.out.println("\nZalogowany uzytkownik: "+this.provider.getLoggedUserFullName());
		while(true) {
			System.out.println("\n\nWidok kopii zapasowej\n");
			System.out.println("Dostepne opcje:");
			System.out.println("1. Wyswietl wybrane parametry");
			System.out.println("2. Wybierz urzad");
			System.out.println("3. Wybierz typ kopii");
			System.out.println("4. Wykonaj kopie zapasowa");
			System.out.println("5. Wyswietl przypisane numery ID urzedow");
			System.out.println("6. Reset wybranych parametrow");
			System.out.println("7. Wyjdz");
			int choice = chooseAction();
			if(choice == 7) break;
			callOperation(choice);
		}
	}
}