package programm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class BookImporter {

    public static List<BookImport> readCsv(String path) throws FileNotFoundException {
        File csvFile = new File(path);
        if (!csvFile.exists()) throw new FileNotFoundException("Csv file not found");
        try {
            List<BookImport> records = new ArrayList<>();
            Scanner scanner = new Scanner(csvFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] values = line.split(";");

                try {
                    String title = values[0];
                    String author = values[1];
                    BookImport book = new BookImport(title, author);
                    records.add(book);
                } catch (IndexOutOfBoundsException exception) {
                    throw new RuntimeException(exception);
                }
            }
            return records;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
