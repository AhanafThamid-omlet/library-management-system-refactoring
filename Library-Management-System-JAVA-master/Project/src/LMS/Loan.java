package LMS;

import java.util.Date;
import java.util.Scanner;

public class Loan {

    private Borrower borrower;
    private Book book;

    private Staff issuer;
    private Date issuedDate;

    private Date dateReturned;
    private Staff receiver;

    private boolean finePaid;

    // ✅ Strategy reference
    private FinePolicy finePolicy;

    public Loan(Borrower bor, Book b, Staff i, Staff r, Date iDate, Date rDate, boolean fPaid) {
        borrower = bor;
        book = b;
        issuer = i;
        receiver = r;
        issuedDate = iDate;
        dateReturned = rDate;

        finePaid = fPaid;

        // ✅ Default Fine Policy
        finePolicy = new StandardFinePolicy();
    }

    // ✅ Allow changing strategy dynamically
    public void setFinePolicy(FinePolicy finePolicy) {
        this.finePolicy = finePolicy;
    }

    // ✅ Getter functions
    public Book getBook() { return book; }
    public Staff getIssuer() { return issuer; }
    public Staff getReceiver() { return receiver; }
    public Date getIssuedDate() { return issuedDate; }
    public Date getReturnDate() { return dateReturned; }
    public Borrower getBorrower() { return borrower; }
    public boolean getFineStatus() { return finePaid; }

    // ✅ Setter functions
    public void setReturnedDate(Date dReturned) { dateReturned = dReturned; }
    public void setFineStatus(boolean fStatus) { finePaid = fStatus; }
    public void setReceiver(Staff r) { receiver = r; }

    // ✅ Fine calculation is now delegated to Strategy
    public double computeFine() {
        return finePolicy.calculateFine(this);
    }

    // ✅ Fine payment processing
    public void payFine() {

        double totalFine = computeFine();

        if (totalFine > 0) {
            System.out.println("\nTotal Fine generated: Rs " + totalFine);
            System.out.println("Do you want to pay? (y/n)");

            Scanner input = new Scanner(System.in);
            String choice = input.next();

            setFineStatus(choice.equalsIgnoreCase("y"));
        }
        else {
            System.out.println("\nNo fine is generated.");
            setFineStatus(true);
        }
    }

    // ✅ Renew loan
    public void renewIssuedBook(Date iDate) {
        issuedDate = iDate;

        System.out.println("\nThe deadline of the book " + getBook().getTitle() + " has been extended.");
        System.out.println("Issued Book is successfully renewed!\n");
    }
}
