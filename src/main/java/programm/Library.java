package programm;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programm.store.Database;
import programm.editor.Editor;
import programm.items.Author;
import programm.items.Book;

import javax.swing.UIManager;
import java.awt.EventQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class Library {
    private static final Logger logger = LoggerFactory.getLogger("Library");
    private static Library instance;

    private final ObjectSet<Book> books;
    private final ObjectSet<Author> authors;
    private final Database database;
    private final Editor editor;

    public Library(String databasePath) {
        instance = this;
        this.books = new ObjectArraySet<>();
        this.authors = new ObjectArraySet<>();
        this.database = Database.sqLite("jdbc:sqlite:" + databasePath);
        this.editor = new Editor(this);
    }

    public int generateAuthorId() {
        AtomicInteger uniqueId = new AtomicInteger();

        int x = this.authors.size();

        do {
            x++;
            uniqueId.set(x);
        } while (authors.stream().anyMatch(author -> author.getId() == uniqueId.get()) && uniqueId.get() != 0);

        return uniqueId.get();
    }

    public int generateBookId() {
        AtomicInteger uniqueId = new AtomicInteger();

        int x = this.books.size();
        do {
            x++;
            uniqueId.set(x);
        } while (books.stream().anyMatch(book -> book.getId() == uniqueId.get()) && uniqueId.get() != 0);

        return uniqueId.get();
    }

    public Author getAuthor(int id) {
        return this.authors.stream()
                .filter(author -> author.getId() == id)
                .findAny().orElse(null);
    }

    public Author getAuthor(String name) {
        return this.authors.stream()
                .filter(author -> author.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public Book getBook(String name) {
        return this.books.stream()
                .filter(book -> book.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public Book getBook(int id) {
        return this.books.stream()
                .filter(book -> book.getId() == id)
                .findAny().orElse(null);
    }

    public void syncBooks() {
        synchronized (this.books) {
            try {
                this.books.clear();
                for (Book book : database.getBooks()) {
                    Author author = getAuthor(book.getAuthorId());
                    author.getBooks().add(book.getId());
                    this.books.add(book);
                }
            } catch (Exception exception) {
                logger.error("Cannot register all books", exception);
            }
        }
    }

    public void syncAuthors() {
        synchronized (this.authors) {
            try {
                this.authors.clear();
                this.authors.addAll(database.getAuthors());
            } catch (Exception exception) {
                logger.error("Cannot register all authors", exception);
            }
        }
    }

    public void start() {
        logger().info("Starting program");

        syncAuthors();
        syncBooks();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            System.out.println("Setting look");
            exception.printStackTrace();
        }
        EventQueue.invokeLater(this.editor::open);
    }

    public void stop() {
        logger().info("Stopping program");
        this.database.close();
    }

    public ObjectSet<Book> getBooks() {
        return books;
    }

    public ObjectSet<Author> getAuthors() {
        return authors;
    }

    public Object[][] getBookMatrix() {
        Object[][] books = new Object[this.books.size()][];

        Iterator<Book> iterator = this.books.stream()
                .sorted(Comparator.comparing(Book::getId)).iterator();

        int x = 0;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            books[x] = new Object[] {
                    book.getId(),
                    book.getName(),
                    book.getAuthorId(),
                    book.getBorrowed()
            };
            x++;
        }
        return books;
    }

    public Object[][] getAuthorMatrix() {
        Object[][] books = new Object[this.authors.size()][];

        Iterator<Author> iterator = this.authors.stream()
                .sorted(Comparator.comparing(Author::getId)).iterator();

        int x = 0;
        while (iterator.hasNext()) {
            Author author = iterator.next();
            books[x] = new Object[] {
                    author.getId(),
                    author.getName(),
                    author.getBooks().size()
            };
            x++;
        }
        return books;
    }

    public Database getDatabase() {
        return this.database;
    }

    public static Logger logger() {
        return logger;
    }

    public static Library getInstance() {
        return instance;
    }
}
