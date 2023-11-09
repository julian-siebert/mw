package programm.editor;

import programm.editor.overview.OverviewFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class EditorFrame extends JFrame {
    private static EditorFrame current;

    protected final Editor editor;

    public EditorFrame(Editor editor, String title) {
        super(title);
        current = this;
        this.editor = editor;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (getSelf() instanceof OverviewFrame) {
                    current = null;
                    System.exit(0);
                    return;
                }
                current.setVisible(false);
                OverviewFrame frame = new OverviewFrame(editor);
            }
        });
    }

    protected abstract void create();

    private EditorFrame getSelf() {
        return this;
    }
}
