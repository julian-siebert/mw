package programm.editor.books;

import programm.editor.EditorFrame;
import programm.editor.EditorPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public final class BooksFooterPanel extends EditorPanel {
    public BooksFooterPanel(EditorFrame parent) {
        super(parent, new GridBagLayout());
        create();
    }

    @Override
    protected EditorPanel create() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(createBackButton(), gbc);
        return this;
    }
}
