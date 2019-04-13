package ch.cemil.info.yoklama.apis.excel;

import ch.cemil.info.yoklama.services.AttendanceService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleExcelCRUD {

    @Autowired
    private AttendanceService attendanceService;
    private final String spreadsheetId = "1XYolzsgMyPuzL_FPRsfyEQSu8MwStarQ010YRihNVOU";
    private Sheets service = null;
    private ExcelHelper helper = null;
    private List<List<Object>> data = null;


    public GoogleExcelCRUD(){
        try {
            helper = new ExcelHelper();
            this.setFormatOfSpreadSheet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setData (List<List<Object>> pData){
        this.data = pData;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public AppendValuesResponse write () throws IOException{
        service = this.getService();

        ValueRange requestBody = new ValueRange();
        requestBody.setValues(this.getData());


        Sheets.Spreadsheets.Values.Append request =
                service.spreadsheets().values().append(spreadsheetId, "A2", requestBody);

        request.setValueInputOption("RAW");

        AppendValuesResponse cmil =  request.execute();
        return cmil;
    }


    public BatchUpdateValuesResponse update() throws IOException {
        List<List<Object>> allData = this.getAll().getValues();
        service = this.getService();

        List<ValueRange> datas = new ArrayList<>();
        ValueRange valueRange = new ValueRange();
        valueRange.setValues(this.getData());
        valueRange.setRange("A1:Z");

        datas.add(valueRange);

        BatchUpdateValuesRequest requestUpdateBody = new BatchUpdateValuesRequest();
        requestUpdateBody.setValueInputOption("RAW");
        requestUpdateBody.setData(datas);

        Sheets.Spreadsheets.Values.BatchUpdate updateRequest =
                service.spreadsheets().values().batchUpdate(spreadsheetId, requestUpdateBody);

        return updateRequest.execute();
    }

    public void setHeaders(List<Object> list) throws IOException {
        String range = "A1:Z1";

        // How the input data should be interpreted.
        String valueInputOption = "RAW";
        List<List<Object>> rows = new ArrayList<>();
        rows.add(list);
        // fields will be replaced:
        ValueRange requestBody = new ValueRange();
        requestBody.setValues(rows);

        Sheets sheetsService = this.getService();
        Sheets.Spreadsheets.Values.Update request =
                sheetsService.spreadsheets().values().update(spreadsheetId, range, requestBody);
        request.setValueInputOption(valueInputOption);

        UpdateValuesResponse response = request.execute();

    }

    public ValueRange getAll() throws IOException{
        service = this.getService();
        // A:E means get all data rows between A and E
        String range = "A2:Z";
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response;
    }

    private Sheets getService() throws IOException {
        return GoogleExcelApi.getSheetsService();
    }

    public void setFormatOfSpreadSheet () throws IOException {
        service = this.getService();

        List<Request> requests = helper.setPageRules();
        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        BatchUpdateSpreadsheetResponse result = service.spreadsheets()
                .batchUpdate(spreadsheetId, body)
                .execute();
        System.out.printf("%d cells updated.", result.getReplies().size());
    }


}
