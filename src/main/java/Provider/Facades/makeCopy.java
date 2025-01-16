package Provider.Facades;

import Model.Classes.*;
import Model.Enums.CopyType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import Model.Facades.manageDepartments;
import Model.Facades.exposeApplications;
import Model.Facades.manageCopies;

public abstract class makeCopy {


	public static boolean checkFreeSpace(){
		File disk = new File("C:\\");
		long freeSpaceBytes = disk.getFreeSpace();
		long freeSpaceMB = freeSpaceBytes / (1024 * 1024);
		return freeSpaceMB >= 50;
	}

	public static void sendMail(int receiverId, String message){
		System.out.println("Wysyłanie maila o treści: " + message  + " do użytkownika o id " + receiverId);
	}


	/**
	 * @param userID
	 * @param selectedDepartmentId
	 * @param selectedCopyType
	 */
	public static boolean startCreateCopy(int userID, int selectedDepartmentId, CopyType selectedCopyType) throws Exception {

		ArrayList<Department> assignedDepartments = manageDepartments.getAssignedDepartments(userID);
		ArrayList<Integer> assignedDepartmentIds = (ArrayList<Integer>) assignedDepartments.stream().map(Department::getId).collect(Collectors.toList());


		if(!assignedDepartmentIds.contains(selectedDepartmentId)){
			throw new NoSuchElementException("Provided department is not assigned to this user!");
		}

		manageDepartments.lockDepartment(selectedDepartmentId);

		boolean isFreeSpace = checkFreeSpace();
		if(!isFreeSpace){
			sendMail(userID, "Brak wolnego miejsca na dysku do stworzenia kopii");
			manageDepartments.unlockDepartment(selectedDepartmentId);
			throw new Exception("No free space left");
		}

		ArrayList<User> citizens = manageDepartments.getAllCitizens(selectedDepartmentId);
		ArrayList<User> officials = manageDepartments.getAllOfficials(selectedDepartmentId);
		ArrayList<Application> applications = exposeApplications.getApplicationsFromDepartment(selectedDepartmentId);

		File copyFile = new File("./" +  UUID.randomUUID().toString() + ".txt");

		if(selectedCopyType == CopyType.FULL){
			try (FileWriter writer = new FileWriter(copyFile)) {

				writer.write("Citizens:\n");
				for (User citizen : citizens) {
					writer.write(citizen + "\n");
				}

				writer.write("\nOfficials:\n");
				for (User official : officials) {
					writer.write(official + "\n");
				}

				writer.write("\nApplications:\n");
				for (Application application : applications) {
					writer.write(application + "\n");
				}

				manageCopies.addCopy(copyFile, selectedCopyType, selectedDepartmentId);
				System.out.println("Dane zostały zapisane do pliku: " + copyFile.getAbsolutePath());
			} catch (IOException e) {
				System.out.println("Wystąpił błąd podczas zapisywania do pliku: " + e.getMessage());
			}
		}
		else{
			Copy lastCopy = manageCopies.getLastCopyForDepartment(selectedDepartmentId);
			if(lastCopy == null) return false;

			copyFile = new File(lastCopy.getPath());


			try {
				List<String> existingLines = Files.readAllLines(copyFile.toPath());
				Set<String> existingEntries = new HashSet<>(existingLines);

				try (FileWriter writer = new FileWriter(copyFile, true)) {
					writer.write("\nMissing Citizens:\n");
					for (User citizen : citizens) {
						if (!existingEntries.contains(citizen.toString())) {
							writer.write(citizen + "\n");
						}
					}

					writer.write("\nMissing Officials:\n");
					for (User official : officials) {
						if (!existingEntries.contains(official.toString())) {
							writer.write(official + "\n");
						}
					}

					writer.write("\nMissing Applications:\n");
					for (Application application : applications) {
						if (!existingEntries.contains(application.toString())) {
							writer.write(application + "\n");
						}
					}

					manageCopies.addCopy(copyFile, selectedCopyType, selectedDepartmentId);
					System.out.println("Brakujące dane zostały dopisane do pliku: " + copyFile.getAbsolutePath());
				}
			} catch (IOException e) {
				System.out.println("Wystąpił błąd podczas odczytu lub zapisu pliku: " + e.getMessage());
			}
		}

		sendMail(userID, "Zapisano kopię danych z urzędu");
		manageDepartments.unlockDepartment(selectedDepartmentId);
		manageCopies.addCopy(copyFile,selectedCopyType, selectedDepartmentId);

		return true;
	}
}