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
    private String selectedCopyType;


    public String testCreateCopy() throws Exception {

        CopyType copyTypeEnumValue = CopyType.valueOf(selectedCopyType);
        try{
            boolean result = makeCopy.startCreateCopy(userId, selectedDepartmentId, copyTypeEnumValue);

            return (result) ? "" : "Unexpected Error";
        } catch(Exception e){
            return e.getMessage();
        }

    }
}
