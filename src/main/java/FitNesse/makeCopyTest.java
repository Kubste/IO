package FitNesse;

import fit.ColumnFixture;
import Model.Classes.*;
import Model.Enums.*;
import Provider.Facades.makeCopy;
import java.util.ArrayList;
import Model.Facades.manageDepartments;

public class makeCopyTest extends ColumnFixture {

    private int userId;
    private int selectedDepartmentId;
    private CopyType selectedCopyType;



    public void setSelectedCopyType(String selectedCopyType) {
        // Convert string to enum
        this.selectedCopyType = CopyType.valueOf(selectedCopyType.toUpperCase());
    }


    public String testCreateCopy() throws Exception {
        try{
            boolean result = makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType);

            return (result) ? "" : "Unexpected Error";
        } catch(Exception e){
            return e.getMessage();
        }

    }
}
