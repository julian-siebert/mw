package programm.editor.authors;

import programm.editor.EditorFrame;
import programm.editor.EditorPanel;
import programm.editor.authors.selected.AuthorListBooksFrame;
import programm.items.Author;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class AuthorTablePanel extends EditorPanel {
    private final String[] columnNames;
    private final Object[][] data;

    public AuthorTablePanel(EditorFrame parent) {
        super(parent, new GridBagLayout());
        this.columnNames = Author.COLUMNS;
        this.data = editor.library().getAuthorMatrix();
        create();
    }

    @Override
    protected EditorPanel create() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTable table = new JTable();
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.setFillsViewportHeight(true);
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                Point point = event.getPoint();
                int row = table.rowAtPoint(point);
                if (event.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    Author author = editor.library().getAuthor(id);
                    if (author == null) return;
                    parent.setVisible(false);
                    parent.remove(getSelf());
                    new AuthorListBooksFrame(editor, author);
                }
            }
        });
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
        return this;
    }

    private AuthorTablePanel getSelf() {
        return this;
    }
}
