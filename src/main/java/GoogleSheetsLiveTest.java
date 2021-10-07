
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CopyPasteRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;


public class GoogleSheetsLiveTest {

    private static Sheets sheetsService;

    // this id can be replaced with your spreadsheet id
    // otherwise be advised that multiple people may run this test and update the public spreadsheet
    private static final String SPREADSHEET_ID = "19t0R6XnessAwnADpHB8RSkmelRyIBv_-VExsplhmylg";

    public static void main(String ar[]) throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
        String range ="Sheet1!A1:J10";
       ValueRange res = sheetsService.spreadsheets().values()
               .get(SPREADSHEET_ID,range)
               .execute();
       List<List<Object>> values = res.getValues();
       if (values == null || values.isEmpty())
       {
           System.out.println("Not Found");
       }
       else {
           for(int i=0;i<10;i++) {
               System.out.print(i +" : ");
               for (int j = 0; j < 10; j++)
                   System.out.print(values.get(i).get(j) + " ");
               System.out.println();
           }
       }
          /* for (List row : values)
               System.out.println(row.get(0)+" "+row.get(1)+" "+row.get(2)+" "+row.get(3)+" "+row.get(4)+" "+row.get(5)+" "+row.get(6)+" "+row.get(7).getClass().getSimpleName());*/
    }
  }