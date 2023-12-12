package cz.fio.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class StoreContactService {
    private static final String CSV_SEPARATOR = "";
    private static final String CSV_FILE_PATH = "C:\\Users\\Viktoriia\\Desktop\\TestProject\\test-javista\\contacts.csv";


    public void saveContact(String firstName, String lastName, String email) {
        if (isEmptyStr(firstName) || isEmptyStr(lastName) ||
        isEmptyStr(email)){
            throw new IllegalArgumentException("is Empty");
        }

        try {
            File csvFile = new File(CSV_FILE_PATH);
            boolean isNewFile = !csvFile.exists();

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(csvFile, true), StandardCharsets.UTF_8);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader())) {

                if (isNewFile) {
                    csvPrinter.printRecord();
                }

                if (!isRecordExists(csvFile, firstName, lastName, email)) {
                    csvPrinter.printRecord(firstName, lastName, email);
                    csvPrinter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmptyStr(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isRecordExists(File csvFile, String firstName, String lastName, String email) throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String existingFirstName = record.get(firstName);
                String existingLastName = record.get(lastName);
                String existingEmail = record.get(email);

                if (existingFirstName.equals(firstName) && existingLastName.equals(lastName) && existingEmail.equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }
}
