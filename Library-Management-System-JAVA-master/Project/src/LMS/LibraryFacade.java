package LMS;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class LibraryFacade {

    private Library lib;

    public LibraryFacade() {
        lib = Library.getInstance();
    }

    // ✅ setup library defaults
    public void setupLibrary() {
        lib.setFine(20);
        lib.setRequestExpiry(7);
        lib.setReturnDeadline(5);
        lib.setName("FAST Library");
    }

    // ✅ DB connect
    public Connection connectDatabase() {
        return lib.makeConnection();
    }

    // ✅ Load DB data
    public void loadDatabase(Connection con) throws Exception {
        lib.populateLibrary(con);
    }

    // ✅ Save DB data
    public void saveDatabase(Connection con) throws Exception {
        lib.fillItBack(con);
    }

    // ✅ Login
    public Person login() {
        return lib.login();
    }

    // ✅ Search books
    public ArrayList<Book> searchBooks() throws IOException {
        return lib.searchForBooks();
    }

    // ✅ Find Borrower
    public Borrower findBorrower() {
        return lib.findBorrower();
    }

    // ✅ Find Clerk
    public Clerk findClerk() {
        return lib.findClerk();
    }

    // ✅ Compute Fine
    public double computeFine(Borrower borrower) {
        return lib.computeFine2(borrower);
    }

    // ✅ Create People
    public void createBorrower() {
        lib.createPerson('b');
    }

    public void createClerk() {
        lib.createPerson('c');
    }

    public void createLibrarian() {
        lib.createPerson('l');
    }

    // ✅ Create Book
    public void createBook(String title, String subject, String author) {
        lib.createBook(title, subject, author);
    }

    // ✅ Remove Book
    public void removeBook(Book book) {
        lib.removeBookfromLibrary(book);
    }

    // ✅ View all books
    public void viewAllBooks() {
        lib.viewAllBooks();
    }

    // ✅ View loan history
    public void viewHistory() {
        lib.viewHistory();
    }
}
