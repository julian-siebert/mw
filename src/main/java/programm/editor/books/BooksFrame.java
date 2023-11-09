package programm.editor.books;

import programm.editor.Editor;
import programm.editor.EditorFrame;

import java.awt.BorderLayout;

public final class BooksFrame extends EditorFrame {
    public BooksFrame(Editor editor) {
        super(editor, "Books");
        create();
    }

    @Override
    protected void create() {
        setUndecorated(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        add(new BooksFooterPanel(this), BorderLayout.PAGE_END);
        add(new BooksTablePanel(this), BorderLayout.CENTER);
        setResizable(true);
        setVisible(true);
    }
}
