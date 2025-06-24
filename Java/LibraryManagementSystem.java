import java.util.*;

// Book class
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issue() { isIssued = true; }
    public void returnBook() { isIssued = false; }

    public String toString() {
        return bookId + " - " + title + " by " + author + (isIssued ? " [Issued]" : " [Available]");
    }
}

// User class
class User {
    private String userId;
    private String name;
    private List<Book> issuedBooks = new ArrayList<>();

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }

    public void issueBook(Book book) {
        issuedBooks.add(book);
        book.issue();
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
        book.returnBook();
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public String toString() {
        return userId + " - " + name + " | Books issued: " + issuedBooks.size();
    }
}

// Library class
class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, User> users = new HashMap<>();

    public void addBook(String id, String title, String author) {
        books.put(id, new Book(id, title, author));
    }

    public void addUser(String id, String name) {
        users.put(id, new User(id, name));
    }

    public void issueBook(String bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);
        if (book == null || user == null) {
            System.out.println("Invalid book or user ID.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Book is already issued.");
            return;
        }
        user.issueBook(book);
        System.out.println("Book issued successfully.");
    }

    public void returnBook(String bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);
        if (book == null || user == null || !user.getIssuedBooks().contains(book)) {
            System.out.println("Invalid return attempt.");
            return;
        }
        user.returnBook(book);
        System.out.println("Book returned successfully.");
    }

    public void displayBooks() {
        System.out.println("Books in library:");
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    public void displayUsers() {
        System.out.println("Users in system:");
        for (User user : users.values()) {
            System.out.println(user);
        }
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("B1", "Java Basics", "James Gosling");
        library.addBook("B2", "Data Structures", "Robert Lafore");
        library.addUser("U1", "Alice");
        library.addUser("U2", "Bob");

        library.displayBooks();
        library.displayUsers();

        library.issueBook("B1", "U1");
        library.returnBook("B1", "U1");

        library.displayBooks();
        library.displayUsers();
    }
}
