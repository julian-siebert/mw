package programm.editor.authors;

import programm.editor.Editor;
import programm.editor.EditorFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

public final class AuthorFrame extends EditorFrame {

    public AuthorFrame(Editor editor) {
        super(editor, "Authors");
        create();
    }

    @Override
    protected void create() {
        setUndecorated(false);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        add(new AuthorFooterPanel(this), BorderLayout.PAGE_END);
        add(new AuthorTablePanel(this), BorderLayout.CENTER);
        setResizable(true);
        setVisible(true);
    }
}
