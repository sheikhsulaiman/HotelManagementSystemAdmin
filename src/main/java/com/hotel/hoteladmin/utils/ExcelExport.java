package com.hotel.hoteladmin.utils;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.FileChooser;

public class ExcelExport<T> {

    public void export(TableView<T> tableView){

        HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
        HSSFSheet hssfSheet=  hssfWorkbook.createSheet("Sheet1");
        HSSFRow firstRow= hssfSheet.createRow(0);

        ///set titles of columns
        for (int i=0; i<tableView.getColumns().size();i++){

            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());

        }


        for (int row=0; row<tableView.getItems().size();row++){

            HSSFRow hssfRow= hssfSheet.createRow(row+1);

            for (int col=0; col<tableView.getColumns().size(); col++){

                Object celValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();

                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (  NumberFormatException e ){

                    hssfRow.createCell(col).setCellValue(celValue.toString());
                }

            }

        }


        //save excel file and close the workbook
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Sheet(*.xls)", "Report.xls"));
            fileChooser.setInitialFileName("Report");
            //fileChooser.setInitialFileName("Report.xls");
            hssfWorkbook.write(new FileOutputStream(fileChooser.showSaveDialog(new Stage())));
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
