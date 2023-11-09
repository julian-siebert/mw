package programm.editor.overview;

import programm.editor.Editor;
import programm.editor.EditorFrame;

import java.awt.*;

public final class OverviewFrame extends EditorFrame {

    public OverviewFrame(Editor editor) {
        super(editor, "Library");
        create();
    }

    @Override
    protected void create() {
        setUndecorated(false);
        setResizable(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        add(new OverviewFooterPanel(this), BorderLayout.CENTER);
        setResizable(true);
        setVisible(true);
    }
}
