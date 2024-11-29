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
		System.out.print("Podaj ID urzedu: ");
		Scanner scanner = new Scanner(System.in);
		this.selectedDepartmentId = scanner.nextInt();
		System.out.println("Wybrano urzad o ID: " + this.selectedDepartmentId);
	}

	private void chooseCopyType() {
		System.out.print("Wybierz typ kopii (1 - calosciowa, 2 - przyrostowa): ");
		Scanner scanner = new Scanner(System.in);
		int copyType = scanner.nextInt();
		if(copyType == 1) this.selectedCopyType = CopyType.FULL;
		else if(copyType == 2) this.selectedCopyType = CopyType.INCREMENTAL;
		System.out.println("Wybrano typ kopii: " + selectedCopyType);
	}

	private void submit() {
		this.provider.startCreateCopy(999999, this.selectedDepartmentId, this.selectedCopyType);
	}

	private void reset() {
		// TODO - implement View.Classes.CopyView.reset
		throw new UnsupportedOperationException();
	}

	private void showSelectedParameters() {
		System.out.println("Numer ID wybranego urzedu: " + this.selectedDepartmentId);
		System.out.println("Wybrany typ kopii: " + this.selectedCopyType);
	}

	@Override
	public void render() {
		System.out.println("Widok kopii zapasowej\n");
		System.out.println("Dostepne opcje:");
		System.out.println("1. Wyswietl wybrane opcje");
		System.out.println("2. Zmien wybrany urzad");
		System.out.println("3. Wybierz typ kopii");
		System.out.println("4. Wykonaj kopie zapasowa");
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

	@Override
	public void reRender() {

	}
}