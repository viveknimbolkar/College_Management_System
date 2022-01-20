package com.tools;

import com.constant.AllConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.stream.Stream;

public class CreatePDF {
    /*
    This method create a pdf file having some client data.
    We are using iText API to do this stuff.
     */
    public void createClientPDFFile(String clientName, String[] clientData, File saveAs) throws Exception{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(saveAs));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
        Chunk collegeName = new Chunk("Sipna College Of Engineering & Technology",font);
        document.add(collegeName);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(clientName+" details"));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table,clientData,clientName);

        document.add(table);
        document.close();
    }

    private static void addRows(PdfPTable table, String[] clientData, String clientName) {
        AllConstants allConstants = new AllConstants();

        //if client name is admin the run this loop
        switch (clientName){
            case "Admin":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.ADMIN_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;

            case "Student":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.STUDENT_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;

            case "Employee":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.EMPLOYEE_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;
        }
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Details")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
