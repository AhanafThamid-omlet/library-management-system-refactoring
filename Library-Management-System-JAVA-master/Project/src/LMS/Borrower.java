
package LMS;

import java.io.*;
import java.util.*;

public class Borrower extends Person 
{    
    private ArrayList<Loan> borrowedBooks;          //Those books which are currently borrowed by this borrower
    private ArrayList<HoldRequest> onHoldBooks;  //Those books which are currently requested by this borrower to be on hold

    
    
    public Borrower(int id,String name, String address, int phoneNum) // para. cons
    {
        super(id,name,address,phoneNum);

        borrowedBooks = new ArrayList();
        onHoldBooks = new ArrayList();
    }

    
    // Printing Borrower's Info
    @Override
    public void printInfo()
    {
        super.printInfo();
               
        printBorrowedBooks();
        printOnHoldBooks();
    }
   
    // Printing Book's Info Borrowed by Borrower
    public void printBorrowedBooks()
    {
        if (!borrowedBooks.isEmpty())
        { 
            System.out.println("\nBorrowed Books are: ");
            
            System.out.println("------------------------------------------------------------------------------");            
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");
            
            for (int i = 0; i < borrowedBooks.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                borrowedBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nNo borrowed books.");                
    }
    
    // Printing Book's Info kept on Hold by Borrower
    public void printOnHoldBooks()
    {
        if (!onHoldBooks.isEmpty())
        { 
            System.out.println("\nOn Hold Books are: ");
            
            System.out.println("------------------------------------------------------------------------------");            
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");
            
            for (int i = 0; i < onHoldBooks.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                onHoldBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nNo On Hold books.");                
    }
   
    // Updating Borrower's Info
    interface BorrowerInputHandler {
        String askYesNo(String question);
        String readLine(String prompt);
        int readInt(String prompt);
    }
    class ConsoleInputHandler implements BorrowerInputHandler {
        private final Scanner sc = new Scanner(System.in);
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        public String askYesNo(String question) {
            System.out.println(question + " (y/n)");
            return sc.next();
        }
        public String readLine(String prompt) {
            try {
                System.out.println(prompt);
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public int readInt(String prompt) {
            System.out.println(prompt);
            return sc.nextInt();
        }
    }
    public class Borrower extends Person {
        private BorrowerInputHandler inputHandler;
        public Borrower(int id, String name, String address, int phoneNum, BorrowerInputHandler inputHandler) {
            super(id, name, address, phoneNum);
            this.inputHandler = inputHandler;
            borrowedBooks = new ArrayList<>();
            onHoldBooks = new ArrayList<>();
        }
        public void updateBorrowerInfo() {
            String choice;
            choice = inputHandler.askYesNo("Do you want to update " + getName() + "'s Name?");
            if (choice.equals("y")) setName(inputHandler.readLine("Type New Name:"));

            choice = inputHandler.askYesNo("Do you want to update " + getName() + "'s Address?");
            if (choice.equals("y")) setAddress(inputHandler.readLine("Type New Address:"));

            choice = inputHandler.askYesNo("Do you want to update " + getName() + "'s Phone Number?");
            if (choice.equals("y")) setPhone(inputHandler.readInt("Type New Phone Number:"));

            System.out.println("\nBorrower is successfully updated.");
        }
    }


    private void updateBorrowerPhoneNumber(String choice, Scanner sc) {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Phone Number: ");
            setPhone(sc.nextInt());
            System.out.println("\nThe phone number is successfully updated.");
        }
    }

    private void updateBorrowerAddress(String choice, BufferedReader reader) throws IOException {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Address: ");
            setAddress(reader.readLine());
            System.out.println("\nThe address is successfully updated.");
        }
    }

    private void updateBorrowerName(String choice, BufferedReader reader) throws IOException {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Name: ");
            setName(reader.readLine());
            System.out.println("\nThe name is successfully updated.");
        }
    }

    /*-- Adding and Removing from Borrowed Books---*/
    public void addBorrowedBook(Loan iBook)
    {
        borrowedBooks.add(iBook);
    }
    
    public void removeBorrowedBook(Loan iBook)
    {
        borrowedBooks.remove(iBook);
    }    
    
    /*-------------------------------------------*/
    
    /*-- Adding and Removing from On Hold Books---*/
    public void addHoldRequest(HoldRequest hr)
    {
        onHoldBooks.add(hr);
    }
    
    public void removeHoldRequest(HoldRequest hr)
    {
        onHoldBooks.remove(hr);
    }
    
    /*-------------------------------------------*/
    
    /*-----------Getter FUNCs. ------------------*/
    public ArrayList<Loan> getBorrowedBooks()
    {
        return borrowedBooks;
    }
    
    public ArrayList<HoldRequest> getOnHoldBooks()
    {
        return onHoldBooks;
    }
    /*-------------------------------------------*/
}
