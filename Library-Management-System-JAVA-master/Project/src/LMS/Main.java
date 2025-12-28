package LMS;

// Including Header Files.
import java.io.*;
import java.util.*;
import java.sql.*;

public class Main {

    // Clearing Required Area of Screen
    public static void clrscr() {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

    // Asking for Input as Choice
    public static int takeInput(int min, int max) {

        String choice;
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter Choice: ");
            choice = input.next();

            if ((!choice.matches(".*[a-zA-Z]+.*")) && (Integer.parseInt(choice) > min && Integer.parseInt(choice) < max)) {
                return Integer.parseInt(choice);
            } else
                System.out.println("\nInvalid Input.");
        }
    }

    // Functionalities of all Persons
    public static void allFunctionalities(Person person, int choice, LibraryFacade facade) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int input = 0;

        // Search Book
        if (choice == 1) {
            facade.searchBooks();
        }

        // Do Hold Request
        else if (choice == 2) {
            ArrayList<Book> books = facade.searchBooks();

            if (books != null) {
                input = takeInput(-1, books.size());
                Book b = books.get(input);

                if ("Clerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
                    Borrower bor = facade.findBorrower();
                    if (bor != null)
                        b.makeHoldRequest(bor);
                } else {
                    b.makeHoldRequest((Borrower) person);
                }
            }
        }

        // View borrower's personal information
        else if (choice == 3) {
            if ("Clerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
                Borrower bor = facade.findBorrower();
                if (bor != null)
                    bor.printInfo();
            } else {
                person.printInfo();
            }
        }

        // Compute Fine of a Borrower
        else if (choice == 4) {
            if ("Clerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
                Borrower bor = facade.findBorrower();
                if (bor != null) {
                    double totalFine = facade.computeFine(bor);
                    System.out.println("\nYour Total Fine is : Rs " + totalFine);
                }
            } else {
                double totalFine = facade.computeFine((Borrower) person);
                System.out.println("\nYour Total Fine is : Rs " + totalFine);
            }
        }

        // Check hold request queue of a book
        else if (choice == 5) {
            ArrayList<Book> books = facade.searchBooks();
            if (books != null) {
                input = takeInput(-1, books.size());
                books.get(input).printHoldRequests();
            }
        }

        // Issue a Book
        else if (choice == 6) {
            ArrayList<Book> books = facade.searchBooks();
            if (books != null) {
                input = takeInput(-1, books.size());
                Book b = books.get(input);

                Borrower bor = facade.findBorrower();
                if (bor != null) {
                    b.issueBook(bor, (Staff) person);
                }
            }
        }

        // Return a Book
        else if (choice == 7) {
            Borrower bor = facade.findBorrower();
            if (bor != null) {
                bor.printBorrowedBooks();
                ArrayList<Loan> loans = bor.getBorrowedBooks();

                if (!loans.isEmpty()) {
                    input = takeInput(-1, loans.size());
                    Loan l = loans.get(input);

                    l.getBook().returnBook(bor, l, (Staff) person);
                } else {
                    System.out.println("\nThis borrower " + bor.getName() + " has no book to return.");
                }
            }
        }

        // Renew a Book
        else if (choice == 8) {
            Borrower bor = facade.findBorrower();
            if (bor != null) {
                bor.printBorrowedBooks();
                ArrayList<Loan> loans = bor.getBorrowedBooks();

                if (!loans.isEmpty()) {
                    input = takeInput(-1, loans.size());
                    loans.get(input).renewIssuedBook(new java.util.Date());
                } else {
                    System.out.println("\nThis borrower " + bor.getName() + " has no issued book which can be renewed.");
                }
            }
        }

        // Add new Borrower
        else if (choice == 9) {
            facade.createBorrower();
        }

        // Update Borrower's Personal Info
        else if (choice == 10) {
            Borrower bor = facade.findBorrower();
            if (bor != null)
                bor.updateBorrowerInfo();
        }

        // Add new Book
        else if (choice == 11) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\nEnter Title:");
            String title = reader.readLine();

            System.out.println("\nEnter Subject:");
            String subject = reader.readLine();

            System.out.println("\nEnter Author:");
            String author = reader.readLine();

            facade.createBook(title, subject, author);
        }

        // Remove a Book
        else if (choice == 12) {
            ArrayList<Book> books = facade.searchBooks();
            if (books != null) {
                input = takeInput(-1, books.size());
                facade.removeBook(books.get(input));
            }
        }

        // Change a Book's Info
        else if (choice == 13) {
            ArrayList<Book> books = facade.searchBooks();

            if (books != null) {
                input = takeInput(-1, books.size());

                Book selectedBook = books.get(input);
                Book.BookUpdater updater = selectedBook.new BookUpdater();
                updater.changeBookInfo(selectedBook);
            }
        }

        // View clerk's personal information
        else if (choice == 14) {
            Clerk clerk = facade.findClerk();
            if (clerk != null)
                clerk.printInfo();
        }

        System.out.println("\nPress any key to continue..\n");
        scanner.next();
    }

    public static void main(String[] args) {

        Scanner admin = new Scanner(System.in);

        // ✅ Using Facade instead of Library directly
        LibraryFacade facade = new LibraryFacade();
        facade.setupLibrary();

        Connection con = facade.connectDatabase();

        if (con == null) {
            System.out.println("\nError connecting to Database. Exiting.");
            return;
        }

        try {

            facade.loadDatabase(con);

            boolean stop = false;
            while (!stop) {

                clrscr();

                System.out.println("--------------------------------------------------------");
                System.out.println("\tWelcome to Library Management System");
                System.out.println("--------------------------------------------------------");

                System.out.println("Following Functionalities are available: \n");
                System.out.println("1- Login");
                System.out.println("2- Exit");
                System.out.println("3- Admininstrative Functions");

                System.out.println("-----------------------------------------\n");

                int choice = takeInput(0, 4);

                if (choice == 3) {

                    System.out.println("\nEnter Password: ");
                    String aPass = admin.next();

                    if (aPass.equals("lib")) {
                        while (true) {

                            clrscr();
                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Admin's Portal");
                            System.out.println("--------------------------------------------------------");

                            System.out.println("1- Add Clerk");
                            System.out.println("2- Add Librarian");
                            System.out.println("3- View Issued Books History");
                            System.out.println("4- View All Books in Library");
                            System.out.println("5- Logout");

                            System.out.println("---------------------------------------------");

                            choice = takeInput(0, 6);

                            if (choice == 5)
                                break;

                            if (choice == 1)
                                facade.createClerk();
                            else if (choice == 2)
                                facade.createLibrarian();
                            else if (choice == 3)
                                facade.viewHistory();
                            else if (choice == 4)
                                facade.viewAllBooks();

                            System.out.println("\nPress any key to continue..\n");
                            admin.next();
                        }
                    } else {
                        System.out.println("\nSorry! Wrong Password.");
                    }
                }

                else if (choice == 1) {

                    Person person = facade.login();

                    if (person == null) {
                    }

                    else if (person.getClass().getSimpleName().equals("Borrower")) {

                        while (true) {
                            clrscr();

                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Borrower's Portal");
                            System.out.println("--------------------------------------------------------");
                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check Personal Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Logout");
                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0, 7);

                            if (choice == 6)
                                break;

                            allFunctionalities(person, choice, facade);
                        }
                    }

                    else if (person.getClass().getSimpleName().equals("Clerk")) {

                        while (true) {

                            clrscr();
                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Clerk's Portal");
                            System.out.println("--------------------------------------------------------");

                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check Personal Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Check out a Book");
                            System.out.println("7- Check in a Book");
                            System.out.println("8- Renew a Book");
                            System.out.println("9- Add a new Borrower");
                            System.out.println("10- Update a Borrower's Info");
                            System.out.println("11- Logout");

                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0, 12);

                            if (choice == 11)
                                break;

                            allFunctionalities(person, choice, facade);
                        }
                    }

                    else if (person.getClass().getSimpleName().equals("Librarian")) {

                        while (true) {

                            clrscr();
                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Librarian's Portal");
                            System.out.println("--------------------------------------------------------");

                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check Personal Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Check out a Book");
                            System.out.println("7- Check in a Book");
                            System.out.println("8- Renew a Book");
                            System.out.println("9- Add a new Borrower");
                            System.out.println("10- Update a Borrower's Info");
                            System.out.println("11- Add new Book");
                            System.out.println("12- Remove a Book");
                            System.out.println("13- Change a Book's Info");
                            System.out.println("14- Check Personal Info of Clerk");
                            System.out.println("15- Logout");

                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0, 16);

                            if (choice == 15)
                                break;

                            allFunctionalities(person, choice, facade);
                        }
                    }
                }

                else {
                    stop = true;
                }

                System.out.println("\nPress any key to continue..\n");
                Scanner scanner = new Scanner(System.in);
                scanner.next();
            }

            // ✅ Saving to DB through Facade
            facade.saveDatabase(con);

        } catch (Exception e) {
            System.out.println("\nExiting...\n");
        }
    }
}
