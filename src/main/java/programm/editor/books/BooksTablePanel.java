package programm.editor.books;

import programm.editor.EditorFrame;
import programm.editor.EditorPanel;
import programm.items.Book;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class BooksTablePanel extends EditorPanel {
    private final String[] columnNames;
    private final Object[][] data;

    public BooksTablePanel(EditorFrame parent) {
        super(parent, new GridBagLayout());
        this.columnNames = Book.COLUMNS;
        this.data = editor.library().getBookMatrix();
        create();
    }

    @Override
    protected EditorPanel create() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTable table = new JTable();
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.setFillsViewportHeight(true);
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                Point point = event.getPoint();
                int row = table.rowAtPoint(point);
                if (event.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    Book book = editor.library().getBook(id);
                    if (book == null) return;
                    book.setBorrowed(book.isBorrowed() ? 0 : 1);
                    model.setValueAt(book.getBorrowed(), row, 3);
                    editor.library().getDatabase().updateBook(book);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);

        gbc.gridy++;

        add(new BookAdderPanel(parent), gbc);
        return this;
    }
}
