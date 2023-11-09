package programm.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programm.Library;

import java.lang.instrument.Instrumentation;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger("Bootstrap");

    public static void main(String... args) {
        try {
            String databasePath;

            try {
                databasePath = args[0];
            } catch (IndexOutOfBoundsException exception) {
                logger.error("Invalid syntax. Please add argument <databasePath>");
                System.exit(1);
                return;
            }

            Library library = new Library(databasePath);
            library.start();
            Runtime.getRuntime().addShutdownHook(new Thread(library::stop));
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
}
