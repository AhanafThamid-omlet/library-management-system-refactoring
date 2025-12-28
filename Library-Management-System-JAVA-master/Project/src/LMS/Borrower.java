package LMS;

import java.io.*;
import java.util.*;

public class Borrower extends Person implements Observer {

    private ArrayList<Loan> borrowedBooks;
    private ArrayList<HoldRequest> onHoldBooks;

    public Borrower(int id, String name, String address, int phoneNum) {
        super(id, name, address, phoneNum);
        borrowedBooks = new ArrayList<>();
        onHoldBooks = new ArrayList<>();
    }

    @Override
    public void update(String message) {
        System.out.println("\n📢 Notification for " + getName() + ": " + message);
    }

    @Override
    public void printInfo() {
        super.printInfo();
        printBorrowedBooks();
        printOnHoldBooks();
    }

    public void printBorrowedBooks() {
        if (!borrowedBooks.isEmpty()) {
            System.out.println("\nBorrowed Books are: ");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < borrowedBooks.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                borrowedBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        } else {
            System.out.println("\nNo borrowed books.");
        }
    }

    public void printOnHoldBooks() {
        if (!onHoldBooks.isEmpty()) {
            System.out.println("\nOn Hold Books are: ");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < onHoldBooks.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                onHoldBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        } else {
            System.out.println("\nNo On Hold books.");
        }
    }

    public void updateBorrowerInfo() throws IOException {

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nDo you want to update " + getName() + "'s Name ? (y/n)");
        String choice = sc.next();
        if (choice.equalsIgnoreCase("y")) {
            System.out.println("\nType New Name: ");
            setName(reader.readLine());
        }

        System.out.println("\nDo you want to update " + getName() + "'s Address ? (y/n)");
        choice = sc.next();
        if (choice.equalsIgnoreCase("y")) {
            System.out.println("\nType New Address: ");
            setAddress(reader.readLine());
        }

        System.out.println("\nDo you want to update " + getName() + "'s Phone Number ? (y/n)");
        choice = sc.next();
        if (choice.equalsIgnoreCase("y")) {
            System.out.println("\nType New Phone Number: ");
            setPhone(sc.nextInt());
        }

        System.out.println("\nBorrower is successfully updated.");
    }

    public void addBorrowedBook(Loan iBook) {
        borrowedBooks.add(iBook);
    }

    public void removeBorrowedBook(Loan iBook) {
        borrowedBooks.remove(iBook);
    }

    public void addHoldRequest(HoldRequest hr) {
        onHoldBooks.add(hr);
    }

    public void removeHoldRequest(HoldRequest hr) {
        onHoldBooks.remove(hr);
    }

    public ArrayList<Loan> getBorrowedBooks() {
        return borrowedBooks;
    }

    public ArrayList<HoldRequest> getOnHoldBooks() {
        return onHoldBooks;
    }
}
