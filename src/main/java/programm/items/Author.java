package programm.items;

import programm.Library;

import java.util.HashSet;
import java.util.Set;

public final class Author {
    public static final String[] COLUMNS = {"ID", "Title", "Books"};
    private static final Library library = Library.getInstance();

    private int id;
    private String name;
    private final Set<Integer> books;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
        this.books = new HashSet<>();
    }

    public Set<Integer> getBooks() {
        return books;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
