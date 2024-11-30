package View.Classes;

import Model.Enums.CopyType;
import Provider.Classes.CopyProvider;
import Provider.Classes.Provider;
import java.util.ArrayList;
import java.util.Scanner;

public class CopyView extends View {

	private int selectedDepartmentId;
	private CopyType selectedCopyType;
	private CopyProvider provider;
	private ArrayList<Integer> assignedDepartmentIds;

	public CopyView(Provider provider) {
		super(provider);
	}

	private void chooseDepartment() {
		System.out.print("\nPodaj ID urzedu: ");
		Scanner scanner = new Scanner(System.in);
		this.selectedDepartmentId = scanner.nextInt();
		System.out.println(STR."Wybrano urzad o ID: \{this.selectedDepartmentId}");
	}

	private void chooseCopyType() {
		System.out.println("\n");
		System.out.print("Wybierz typ kopii (1 - calosciowa, 2 - przyrostowa): ");
		Scanner scanner = new Scanner(System.in);
		int copyType = scanner.nextInt();
		if(copyType == 1) this.selectedCopyType = CopyType.FULL;
		else if(copyType == 2) this.selectedCopyType = CopyType.INCREMENTAL;
		System.out.println(STR."Wybrano typ kopii: \{selectedCopyType}");
	}

	private void submit() {
		System.out.println("\n");
		System.out.println("Tworzenie kopii...");
		this.provider.startCreateCopy(this.loggedUserID, this.selectedDepartmentId, this.selectedCopyType);
	}

	private void reset() {
		this.selectedCopyType = null;
		this.selectedDepartmentId  = -1;
	}

	private void showSelectedParameters() {
		System.out.println("\n");
		System.out.println(STR."Numer ID wybranego urzedu: \{this.selectedDepartmentId}");
		System.out.println(STR."Wybrany typ kopii: \{this.selectedCopyType}");
	}

	@Override
	public void render() {

		System.out.println("\n\n");
		System.out.println("Widok kopii zapasowej\n");
		System.out.println("Dostepne opcje:");
		System.out.println("1. Wyswietl wybrane opcje");
		System.out.println("2. Wybierz urzad");
		System.out.println("3. Wybierz typ kopii");
		System.out.println("4. Wykonaj kopie zapasowa");
		System.out.println("5. Reset wybranych opcji");
		System.out.print("Wybierz opcje: ");

		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch(choice) {
			case 1 -> this.showSelectedParameters();
			case 2 -> this.chooseDepartment();
			case 3 -> this.chooseCopyType();
			case 4 -> this.submit();
		}
	}

}