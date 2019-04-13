package ch.cemil.info.yoklama.apis.excel;

import com.google.api.services.sheets.v4.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExcelHelper {



    public List<Request> setPageRules () {
        List<GridRange> ranges = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(0)
                .setEndRowIndex(111)
                .setStartColumnIndex(0)
                .setEndColumnIndex(14)
        );

        return Arrays.asList(
                new Request().setAddConditionalFormatRule(new AddConditionalFormatRuleRequest()
                        .setRule(new ConditionalFormatRule()
                                .setRanges(ranges)
                                .setBooleanRule(new BooleanRule()
                                        .setCondition(new BooleanCondition()
                                                .setType("TEXT_CONTAINS")
                                                .setValues(Collections.singletonList(
                                                        new ConditionValue().setUserEnteredValue(
                                                                "TRUE")
                                                ))
                                        )
                                        .setFormat(new CellFormat().setBackgroundColor(
                                                new Color().setRed(0.4f).setGreen(1f).setBlue(0.4f)
                                        ))
                                )
                        )
                        .setIndex(0)
                ),
                new Request().setAddConditionalFormatRule(new AddConditionalFormatRuleRequest()
                        .setRule(new ConditionalFormatRule()
                                .setRanges(ranges)
                                .setBooleanRule(new BooleanRule()
                                        .setCondition(new BooleanCondition()
                                                .setType("TEXT_CONTAINS")
                                                .setValues(Collections.singletonList(
                                                        new ConditionValue().setUserEnteredValue(
                                                                "FALSE")
                                                ))
                                        )
                                        .setFormat(new CellFormat().setBackgroundColor(
                                                new Color().setRed(1f).setGreen(0.4f).setBlue(0.4f)
                                        ))
                                )
                        )
                        .setIndex(0)
                ),
                /*
                new Request().setUpdateBorders( new UpdateBordersRequest()
                        .setRange(new GridRange()
                                .setSheetId(0)
                                .setStartRowIndex(0)
                                .setEndRowIndex(2)
                                .setStartColumnIndex(0)
                                .setEndColumnIndex(6)
                        )
                        .setInnerHorizontal(new Border()
                                .setStyle("SOLID")
                                .setWidth(1)
                                .setColor(new Color().setBlue(0.0f).setGreen(0.0f).setRed(0.0f))
                        )
                ),

                new Request().setRepeatCell(new RepeatCellRequest()
                        .setRange(new GridRange()
                                .setSheetId(0)
                                .setStartRowIndex(0)
                                .setEndRowIndex(2)
                                .setStartColumnIndex(0)
                                .setEndColumnIndex(6)
                        )
                        .setCell(new CellData().setUserEnteredFormat(
                                        new CellFormat()
                                        .setBackgroundColor(new Color().setBlue(0.0f).setGreen(0.0f).setRed(0.0f))
                                        .setHorizontalAlignment("CENTER")
                                        .setTextFormat(new TextFormat()
                                            .setForegroundColor(new Color().setBlue(1.0f).setGreen(1.0f).setRed(1.0f))
                                                .setFontSize(12)
                                                .setBold(true)
                                        )
                                )
                        )
                        .setFields("userEnteredFormat(backgroundColor,textFormat,horizontalAlignment)")
                ),
                */
                new Request().setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
                        .setProperties(new SheetProperties()
                                .setSheetId(0)
                                        .setGridProperties(new GridProperties().setFrozenRowCount(1))
                                )
                                .setFields("gridProperties.frozenRowCount")
                )


        );

    }
}
