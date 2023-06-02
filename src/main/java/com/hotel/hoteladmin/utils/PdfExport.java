package com.hotel.hoteladmin.utils;

import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hoteladmin.utils.pricechart.PriceChart;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class PdfExport {
    public static void printInvoice(int invoiceNo,int bookingId,int roomNo,int userId,String checkInDate,String checkOutDate,String payType,String payStatus,String roomService,String poolAccess,String carParking){

        ArrayList<String> list = DButils.getBookingDetails(bookingId);

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf Document(*.pdf)", "Invoice.pdf"));
            fileChooser.setInitialFileName("Invoice_"+bookingId);
            //fileChooser.setInitialFileName("Report.xls");
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(fileChooser.showSaveDialog(new Stage())));


        String path = "D:/Project/Receipt/Invoice.pdf";

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        float col = 280f;
        float columnWidth[] = {col,col};

        Table table = new Table(columnWidth);

        table.setBackgroundColor(new DeviceRgb(0,128,0))
                .setFontColor(Color.WHITE)
                .setBorder(Border.NO_BORDER)
                .setMarginBottom(40f);
        table.addCell(new Cell().add("INVOICE")
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(30f)
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("BigMind Hotel\n#1234 Dhaka\ncontact@bigmindhotel.com")
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginRight(10f));

        float colWidth[] = {80,300,100,80};

        Table customerInformationTable = new Table(colWidth);
        customerInformationTable.addCell(new Cell(0,2)
                .add("Customer Information")
                .setBold()
                .setBorder(Border.NO_BORDER));
        customerInformationTable.addCell(new Cell().add("Invoice No.").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT).setMarginRight(5f));
        customerInformationTable.addCell(new Cell().add(Integer.toString(invoiceNo)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontColor(new DeviceRgb(0,128,0)));
        customerInformationTable.addCell(new Cell().add("Name"));
        customerInformationTable.addCell(new Cell().add(DButils.getUserName(userId)));
        customerInformationTable.addCell(new Cell().add("ID").setTextAlignment(TextAlignment.CENTER));
        customerInformationTable.addCell(new Cell().add(Integer.toString(userId)).setTextAlignment(TextAlignment.CENTER));


        Table bookingInformationTable = new Table(colWidth);
        bookingInformationTable.addCell(new Cell(0,2)
                .add("Booking Information")
                .setBold()
                .setBorder(Border.NO_BORDER)
                        .setMarginBottom(5f)
                ).setMarginTop(50f);
        bookingInformationTable.addCell(new Cell().add("Booking No.").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT).setMarginRight(5f));
        bookingInformationTable.addCell(new Cell().add(Integer.toString(bookingId)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontColor(new DeviceRgb(0,128,0)));

        float detailcolwidth[] = {120f,160f,200f,80f};
        Table listBookingTable = new Table(detailcolwidth);

        listBookingTable.setMarginTop(20f);
        listBookingTable.addCell(new Cell().add("Room No."));
        listBookingTable.addCell(new Cell().add(Integer.toString(roomNo)));
        listBookingTable.addCell(new Cell().add("Check In"));
        listBookingTable.addCell(new Cell().add(checkInDate));
        listBookingTable.addCell(new Cell().add("Room Type."));
        listBookingTable.addCell(new Cell().add(DButils.getRoomType(list.get(0))));
        listBookingTable.addCell(new Cell().add("Check Out"));
        listBookingTable.addCell(new Cell().add(checkOutDate));
        listBookingTable.addCell(new Cell().add("Room Service"));
        listBookingTable.addCell(new Cell().add(roomService));
        listBookingTable.addCell(new Cell(2,0).add("Payment Status").setVerticalAlignment(VerticalAlignment.MIDDLE));
        listBookingTable.addCell(new Cell(2,0).add(list.get(5)).setVerticalAlignment(VerticalAlignment.MIDDLE));
        listBookingTable.addCell(new Cell().add("Pool Access"));
        listBookingTable.addCell(new Cell().add(poolAccess));
        listBookingTable.addCell(new Cell().add("Car Parking"));
        listBookingTable.addCell(new Cell().add(carParking));
        listBookingTable.addCell(new Cell().add("Total"));
            //PriceChart.calculatePrice(list.get(0), LocalDate.parse( list.get(2)),LocalDate.parse( list.get(3)), list.get(6), list.get(7), list.get(8));
        listBookingTable.addCell(new Cell().add("$ "+PriceChart.calculatePrice(Integer.toString(roomNo),LocalDate.parse( checkInDate),LocalDate.parse( checkOutDate), roomService, carParking, poolAccess)));


        Paragraph date = new Paragraph();
        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String formattedDateTime = formatter.format(dateTime);
        date.add(formattedDateTime);
        date.setFixedPosition(40f,40f,500f);


        document.add(table);
        document.add(customerInformationTable);
        document.add(bookingInformationTable);
        document.add(listBookingTable);
        document.add(date);
        document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
