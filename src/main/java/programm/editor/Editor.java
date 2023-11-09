package programm.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programm.Library;
import programm.editor.overview.OverviewFrame;

public record Editor(Library library) {
    private static final Logger logger = LoggerFactory.getLogger("Editor");

    public void open() {
        logger.info("Opening editor");
        new OverviewFrame(this);
    }
}
