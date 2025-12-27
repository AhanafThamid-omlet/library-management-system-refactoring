package LMS;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Book {

    private int bookID;
    private BookInfo bookInfo;
    private boolean isIssued;

    private HoldRequestOperations holdRequestsOperations = new HoldRequestOperations();

    static int currentIdNumber = 0;

    //  Constructor using BookInfo (only ONE)
    public Book(int id, BookInfo info, boolean issued) {
        this.bookID = (id == -1) ? ++currentIdNumber : id;
        this.title = info.getTitle();
        this.subject = info.getSubject();
        this.author = info.getAuthor();
        this.isIssued = issued;
    }

    //  Constructor with direct parameters (calls BookInfo constructor)
    public Book(int id, String t, String s, String a, boolean issued) {
        this(id, new BookInfo(t, s, a), issued);
    }

    // Print hold requests
    public void printHoldRequests() {
        if (!holdRequestsOperations.holdRequests.isEmpty()) {
            System.out.println("\nHold Requests are: ");

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("No.\t\tBook's Title\t\t\tBorrower's Name\t\t\tRequest Date");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < holdRequestsOperations.holdRequests.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                holdRequestsOperations.printRequests();
            }
        } else
            System.out.println("\nNo Hold Requests.");
    }

    // Print book info
    public void printInfo() {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }

    //  Setter methods (needed for updating)
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter methods
    public String getTitle() { return title; }

    public String getSubject() { return subject; }

    public String getAuthor() { return author; }

    public boolean getIssuedStatus() { return isIssued; }

    public void setIssuedStatus(boolean s) { isIssued = s; }

    public int getID() { return bookID; }

    public ArrayList<HoldRequest> getHoldRequests() {
        return holdRequestsOperations.holdRequests;
    }

    public static void setIDCount(int n) {
        currentIdNumber = n;
    }

    //  Place book on hold
    public void placeBookOnHold(Borrower bor) {
        HoldRequest hr = new HoldRequest(bor, this, new Date());

        holdRequestsOperations.addHoldRequest(hr);
        bor.addHoldRequest(hr);

        System.out.println("\nThe book " + title + " has been successfully placed on hold by borrower " + bor.getName() + ".\n");
    }

    //  Make hold request
    public void makeHoldRequest(Borrower borrower) {

        for (int i = 0; i < borrower.getBorrowedBooks().size(); i++) {
            if (borrower.getBorrowedBooks().get(i).getBook() == this) {
                System.out.println("\nYou have already borrowed " + title);
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

    //  Service hold request
    public void serviceHoldRequest(HoldRequest hr) {
        holdRequestsOperations.removeHoldRequest();
        hr.getBorrower().removeHoldRequest(hr);
    }

    // Issue a book
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
            }
        }

        if (isIssued) {
            System.out.println("\nThe book " + title + " is already issued.");
            System.out.println("Would you like to place the book on hold? (y/n)");

            Scanner sc = new Scanner(System.in);
            String choice = sc.next();

            if (choice.equalsIgnoreCase("y")) {
                makeHoldRequest(borrower);
            }
            return;
        }

        if (!holdRequestsOperations.holdRequests.isEmpty()) {
            boolean hasRequest = false;

            for (int i = 0; i < holdRequestsOperations.holdRequests.size() && !hasRequest; i++) {
                if (holdRequestsOperations.holdRequests.get(i).getBorrower() == borrower)
                    hasRequest = true;
            }

            if (hasRequest) {
                if (holdRequestsOperations.holdRequests.get(0).getBorrower() == borrower)
                    serviceHoldRequest(holdRequestsOperations.holdRequests.get(0));
                else {
                    System.out.println("\nSorry some other users have requested for this book earlier than you.");
                    return;
                }
            } else {
                System.out.println("\nSome users have already placed this book on request and you haven't.");
                System.out.println("Would you like to place the book on hold? (y/n)");

                Scanner sc = new Scanner(System.in);
                String choice = sc.next();

                if (choice.equalsIgnoreCase("y")) {
                    makeHoldRequest(borrower);
                }
                return;
            }
        }

        // Issue
        setIssuedStatus(true);

        Loan iHistory = new Loan(borrower, this, staff, null, new Date(), null, false);
        Library.getInstance().addLoan(iHistory);
        borrower.addBorrowedBook(iHistory);

        System.out.println("\nThe book " + title + " is successfully issued to " + borrower.getName() + ".");
        System.out.println("\nIssued by: " + staff.getName());
    }

    // Return a book
    public void returnBook(Borrower borrower, Loan l, Staff staff) {
        l.getBook().setIssuedStatus(false);
        l.setReturnedDate(new Date());
        l.setReceiver(staff);

        borrower.removeBorrowedBook(l);

        l.payFine();

        System.out.println("\nThe book " + l.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
        System.out.println("\nReceived by: " + staff.getName());
    }
}
