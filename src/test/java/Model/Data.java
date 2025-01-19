package Model;

import Model.Classes.Application;
import Model.Classes.Copy;
import Model.Classes.Department;
import Model.Classes.User;
import Model.Enums.AccessLevel;
import Model.Enums.CopyType;

import java.util.ArrayList;

public class Data {

    public static ArrayList<User> getSampleUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Jan", "Kowalski", AccessLevel.CITIZEN, "123@gmail.com", "password", "username", 1));
        users.add(new User("Jakub", "Nowak", AccessLevel.OFFICIAL, "456@gmail.com", "password1", "username1", 2));
        users.add(new User("Jan", "Kowalski", AccessLevel.ADMIN, "789@gmail.com", "password2", "username2", 3));

        return users;
    }

    public static ArrayList<Copy> getSampleCopies() {
        ArrayList<Copy> copies = new ArrayList<>();
        copies.add(new Copy(12345, 0, "path1", CopyType.FULL));
        copies.add(new Copy(67890, 0, "path1", CopyType.FULL));
        copies.add(new Copy(111213, 0, "path1", CopyType.FULL));

        return copies;
    }

    public static ArrayList<Application> getSampleApplications() {
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(new Application(0, 0, "description"));
        applications.add(new Application(1, 1, "description1"));
        applications.add(new Application(2, 2, "description2"));

        return applications;
    }

    public static ArrayList<Department> getSampleDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        departments.add(new Department("Department1", "Address1", 1, new ArrayList<>(), new ArrayList<>()));
        departments.add(new Department("Department2", "Address2", 2, new ArrayList<>(), new ArrayList<>()));
        departments.add(new Department("Department3", "Address3", 3, new ArrayList<>(), new ArrayList<>()));
        return departments;
    }
}
