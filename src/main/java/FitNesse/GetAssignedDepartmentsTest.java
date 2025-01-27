package FitNesse;

import Model.Classes.Department;
import fit.ColumnFixture;
import Model.Facades.manageDepartments;
import java.util.ArrayList;

public class GetAssignedDepartmentsTest extends ColumnFixture {

    public int administratorID;

    public int getAssignedDepartments(){
        ArrayList<Department> assignedDepartment =  manageDepartments.getAssignedDepartments(administratorID);
        return (assignedDepartment != null)  ? assignedDepartment.size() : 0;
    }
}
