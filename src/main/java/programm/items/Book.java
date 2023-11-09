package programm.items;

import programm.Library;
import programm.importer.BookImport;

public final class Book {
    public static final String[] COLUMNS = {"ID", "Title", "Author", "Borrowed"};

    private int id;
    private String name;
    private int authorId;
    private int borrowed;

    public Book(int id, String name, int authorId, int borrowed) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.borrowed = borrowed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public boolean isBorrowed() {
        return borrowed != 0;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public static void byImport(Library library, BookImport bookImport) {
        String title = bookImport.title();
        String authorName = bookImport.author();
        Author author = library.getAuthor(authorName);
        if (author == null) {
            author = new Author(library.generateAuthorId(), authorName);
            library.getAuthors().add(author);
            library.getDatabase().addAuthor(author);
        }
        Book book = library.getBook(title);
        if (book == null) {
            book = new Book(library.generateBookId(), title, author.getId(), 0);
            library.getBooks().add(book);
            library.getDatabase().addBook(book);
        }
    }
}
