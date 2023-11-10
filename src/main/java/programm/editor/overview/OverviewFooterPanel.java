package programm.editor.overview;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import programm.editor.EditorFrame;
import programm.editor.EditorPanel;
import programm.editor.authors.AuthorFrame;
import programm.editor.books.BooksFrame;
import programm.importer.BookImport;
import programm.importer.BookImporter;
import programm.items.Book;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

public final class OverviewFooterPanel extends EditorPanel {

    public OverviewFooterPanel(EditorFrame parent) {
        super(parent, new GridBagLayout());
        create();
    }

    @Override
    protected EditorPanel create() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createAuthorsButton(), gbc);

        gbc.gridy = 1;
        add(createBooksButton(), gbc);

        gbc.gridy = 2;
        add(createImportButton(), gbc);
        return this;
    }

    private JButton createImportButton() {
        JButton button = new JButton("Import CSV");
        button.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file == null) return;
                try {
                    ObjectCollection<BookImport> imports = BookImporter.readCsv(file.getPath());
                    for (BookImport bookImport : imports) {
                        Book.byImport(this.editor.library(), bookImport);
                    }
                } catch (Exception exception) {
                    logger.error("Unable to import books from csv", exception);
                }
            }
        });
        return button;
    }

    private JButton createBooksButton() {
        JButton button = new JButton("Books");
        button.addActionListener(event -> {
            this.parent.setVisible(false);
            this.parent.remove(this);
            new BooksFrame(this.editor);
        });
        return button;
    }

    private JButton createAuthorsButton() {
        JButton button = new JButton("Authors");
        button.addActionListener(event -> {
            this.parent.setVisible(false);
            this.parent.remove(this);
            new AuthorFrame(this.editor);
        });
        return button;
    }
}
