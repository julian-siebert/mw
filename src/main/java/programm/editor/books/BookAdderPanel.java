package programm.editor.books;

import programm.editor.EditorFrame;
import programm.editor.EditorPanel;

import javax.swing.*;
import java.awt.*;

public final class BookAdderPanel extends EditorPanel {
    public BookAdderPanel(EditorFrame parent) {
        super(parent, new GridBagLayout());
        create();
    }

    @Override
    protected EditorPanel create() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(new JButton("Test"), gbc);
        return this;
    }
}
