package programm.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programm.items.Author;
import programm.items.Book;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public final class Database implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger("Database");

    private final Connection connection;
    private final Object[] sec = new Object[0];

    Database(Connection connection) {
        logger.info("Connection established");
        this.connection = connection;
    }

    public Set<Book> getBooks() throws RuntimeException {
        String sql = "SELECT * FROM books;";

        Set<Book> books = new HashSet<>();

        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    Book book = new Book(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("author_id"),
                            result.getInt("borrowed")
                    );
                    books.add(book);
                }
                return books;
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public Set<Author> getAuthors() throws RuntimeException {
        String sql = "SELECT * FROM authors;";

        Set<Author> authors = new HashSet<>();
        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    Author author = new Author(
                            result.getInt("id"),
                            result.getString("name")
                    );
                    authors.add(author);
                }
                return authors;
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void updateBook(Book book) {
        synchronized (sec) {
            deleteBook(book);
            addBook(book);
        }
    }

    public void updateAuthor(Author author) {
        synchronized (sec) {
            deleteAuthor(author);
            addAuthor(author);
        }
    }

    public void addBook(Book book) throws RuntimeException {
        String sql = "INSERT INTO books (id, name, author_id, borrowed) VALUES (?, ?, ?, ?);";
        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, book.getId());
                statement.setString(2, book.getName());
                statement.setInt(3, book.getAuthorId());
                statement.setInt(4, book.getBorrowed());
                statement.execute();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void addAuthor(Author author) throws RuntimeException {
        String sql = "INSERT INTO authors (id, name) VALUES (?, ?);";
        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, author.getId());
                statement.setString(2, author.getName());
                statement.execute();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void deleteBook(Book book) throws RuntimeException {
        String sql = "DELETE FROM books WHERE id=?;";
        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, book.getId());
                statement.execute();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void deleteAuthor(Author author) throws RuntimeException {
        String sql = "DELETE FROM authors WHERE id=?;";
        synchronized (sec) {
            try {
                PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, author.getId());
                statement.execute();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public static Database sqLite(String url) throws RuntimeException {
        try {
            logger.info("Connection to sqlite db " + url);
            logger.trace("Loading org.sqlite.JDBC");
            Class.forName("org.sqlite.JDBC");
            logger.trace("Connecting");
            Connection connection = DriverManager.getConnection(url);
            logger.trace("Initializing");
            return new Database(connection);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void close() {
        try {
            logger.trace("Closing");
            this.connection.close();
            logger.info("Connection closed");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
