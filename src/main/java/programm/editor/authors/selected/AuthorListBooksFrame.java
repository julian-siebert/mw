package programm.editor.authors.selected;

import programm.editor.Editor;
import programm.editor.EditorFrame;
import programm.editor.EditorPanel;

import programm.editor.authors.AuthorFrame;
import programm.items.Author;
import programm.items.Book;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class AuthorListBooksFrame extends EditorFrame {
    private final Author author;

    public AuthorListBooksFrame(Editor editor, Author author) {
        super(editor, "Author " + author.getName());
        this.author = author;
        create();
    }

    @Override
    protected void create() {
        setUndecorated(false);
        setResizable(true);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        add(new EditorPanel(this, new GridBagLayout()) {
            @Override
            protected EditorPanel create() {
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.EAST;
                add(createBackButton(() -> {
                    parent.setVisible(false);
                    parent.remove(this);
                    new AuthorFrame(editor);
                }), gbc);
                return this;
            }
        }.create(), BorderLayout.PAGE_END);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTable table = new JTable();

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.setFillsViewportHeight(true);

        DefaultTableModel model = new DefaultTableModel(getBookMatrix(), new String[]{
                "ID",
                "Title",
                "Borrowed"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);


        JPanel panel = new JPanel(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, gbc);
        add(panel, BorderLayout.CENTER);

        setResizable(true);
        setVisible(true);
    }

    private Object[][] getBookMatrix() {
        List<Book> bookList = getBooks();
        Object[][] books = new Object[bookList.size()][];

        Iterator<Book> iterator = bookList.stream()
                .sorted(Comparator.comparing(Book::getId)).iterator();

        int x = 0;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            books[x] = new Object[] {
                    book.getId(),
                    book.getName(),
                    book.getBorrowed()
            };
            x++;
        }
        return books;
    }

    private List<Book> getBooks() {
        return editor.library().getBooks().stream()
                .filter(book -> this.author.getBooks().contains(book.getId()))
                .toList();
    }
}
