package LMS;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class Book implements Subject {

    private int bookID;
    private BookInfo bookInfo;
    private boolean isIssued;

    private HoldRequestOperations holdRequestsOperations = new HoldRequestOperations();
    private List<Observer> observers = new ArrayList<>();

    static int currentIdNumber = 0;

    public Book(int id, BookInfo info, boolean issued) {
        this.bookID = (id == -1) ? ++currentIdNumber : id;
        this.bookInfo = info;
        this.isIssued = issued;
    }

    public Book(int id, String t, String s, String a, boolean issued) {
        this(id, new BookInfo(t, s, a), issued);
    }

    // ✅ Observer Pattern methods
    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    // ✅ Print hold requests
    public void printHoldRequests() {
        if (!holdRequestsOperations.holdRequests.isEmpty()) {
            System.out.println("\nHold Requests are: ");

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("No.\t\tBook's Title\t\t\tBorrower's Name\t\t\tRequest Date");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < holdRequestsOperations.holdRequests.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                holdRequestsOperations.holdRequests.get(i).print();
            }
        } else {
            System.out.println("\nNo Hold Requests.");
        }
    }

    public void printInfo() {
        System.out.println(getTitle() + "\t\t\t" + getAuthor() + "\t\t\t" + getSubject());
    }

    // ✅ Setters update BookInfo
    public void setAuthor(String author) {
        this.bookInfo.setAuthor(author);
    }

    public void setSubject(String subject) {
        this.bookInfo.setSubject(subject);
    }

    public void setTitle(String title) {
        this.bookInfo.setTitle(title);
    }

    // ✅ Getters
    public String getTitle() { return bookInfo.getTitle(); }
    public String getSubject() { return bookInfo.getSubject(); }
    public String getAuthor() { return bookInfo.getAuthor(); }

    public boolean getIssuedStatus() { return isIssued; }
    public void setIssuedStatus(boolean s) { isIssued = s; }
    public int getID() { return bookID; }

    public ArrayList<HoldRequest> getHoldRequests() {
        return holdRequestsOperations.holdRequests;
    }

    public static void setIDCount(int n) {
        currentIdNumber = n;
    }

    // ✅ Place book on hold (borrower becomes observer)
    public void placeBookOnHold(Borrower bor) {
        HoldRequest hr = new HoldRequest(bor, this, new Date());

        holdRequestsOperations.addHoldRequest(hr);
        bor.addHoldRequest(hr);

        // ✅ add observer
        addObserver(bor);

        System.out.println("\nThe book " + getTitle() + " has been placed on hold by borrower " + bor.getName() + ".\n");
    }

    // ✅ Make hold request
    public void makeHoldRequest(Borrower borrower) {

        for (int i = 0; i < borrower.getBorrowedBooks().size(); i++) {
            if (borrower.getBorrowedBooks().get(i).getBook() == this) {
                System.out.println("\nYou have already borrowed " + getTitle());
                return;
            }
        }

        for (int i = 0; i < holdRequestsOperations.holdRequests.size(); i++) {
            if (holdRequestsOperations.holdRequests.get(i).getBorrower() == borrower) {
                System.out.println("\nYou already have one hold request for this book.\n");
                return;
            }
        }

        placeBookOnHold(borrower);
    }

    // ✅ Service hold request
    public void serviceHoldRequest(HoldRequest hr) {
        holdRequestsOperations.removeHoldRequest();
        hr.getBorrower().removeHoldRequest(hr);

        // ✅ remove observer after request processed
        removeObserver(hr.getBorrower());
    }

    // ✅ Issue book
    public void issueBook(Borrower borrower, Staff staff) {

        Date today = new Date();
        ArrayList<HoldRequest> hRequests = holdRequestsOperations.holdRequests;

        for (int i = 0; i < hRequests.size(); i++) {
            HoldRequest hr = hRequests.get(i);

            long days = ChronoUnit.DAYS.between(today.toInstant(), hr.getRequestDate().toInstant());
            days = -days;

            if (days > Library.getInstance().getHoldRequestExpiry()) {
                holdRequestsOperations.removeHoldRequest();
                hr.getBorrower().removeHoldRequest(hr);

                // ✅ remove observer if expired
                removeObserver(hr.getBorrower());
            }
        }

        if (isIssued) {
            System.out.println("\nThe book " + getTitle() + " is already issued.");
            System.out.println("Would you like to place the book on hold? (y/n)");

            Scanner sc = new Scanner(System.in);
            String choice = sc.next();

            if (choice.equalsIgnoreCase("y")) {
                makeHoldRequest(borrower);
            }
            return;
        }

        // ✅ If holds exist → only first borrower gets notification
        if (!holdRequestsOperations.holdRequests.isEmpty()) {
            Borrower firstBorrower = holdRequestsOperations.holdRequests.get(0).getBorrower();

            if (borrower != firstBorrower) {
                System.out.println("\nSorry, someone else requested earlier. Wait for your turn.");
                return;
            }

            serviceHoldRequest(holdRequestsOperations.holdRequests.get(0));
        }

        // ✅ Issue
        setIssuedStatus(true);

        Loan loan = new Loan(borrower, this, staff, null, new Date(), null, false);
        Library.getInstance().addLoan(loan);
        borrower.addBorrowedBook(loan);

        System.out.println("\nThe book " + getTitle() + " is successfully issued to " + borrower.getName() + ".");
        System.out.println("\nIssued by: " + staff.getName());
    }

    // ✅ Return book + notify first observer
    public void returnBook(Borrower borrower, Loan l, Staff staff) {

        setIssuedStatus(false);
        l.setReturnedDate(new Date());
        l.setReceiver(staff);

        borrower.removeBorrowedBook(l);
        l.payFine();

        System.out.println("\nThe book " + getTitle() + " is successfully returned by " + borrower.getName() + ".");
        System.out.println("\nReceived by: " + staff.getName());

        // ✅ notify ONLY first borrower in hold queue (extra mark)
        if (!holdRequestsOperations.holdRequests.isEmpty()) {
            Borrower nextBorrower = holdRequestsOperations.holdRequests.get(0).getBorrower();
            nextBorrower.update("Book '" + getTitle() + "' is now available! You are next in queue.");
        }
    }
}
