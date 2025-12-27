
package LMS;

import static LMS.Library.librarian;
import static LMS.Library.persons;

public class Librarian extends Staff {

    protected int officeNo;
    public static int currentOfficeNumber = 0;

    // ✅ OCP policy (Strategy)
    public static LibrarianAssignmentPolicy assignmentPolicy = new SingleLibrarianPolicy();

    public Librarian(int id, String n, String a, int p, double s, int of) {
        super(id, n, a, p, s);

        if (of == -1)
            officeNo = currentOfficeNumber;
        else
            officeNo = of;

        currentOfficeNumber++;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Office Number: " + officeNo);
    }

    // ✅ OCP: Strategy Interface
    public static interface LibrarianAssignmentPolicy {
        boolean canAdd(Librarian lib);
        void onAdd(Librarian lib);
    }

    // ✅ Default Policy: Only one librarian allowed
    public static class SingleLibrarianPolicy implements LibrarianAssignmentPolicy {
        public boolean canAdd(Librarian lib) {
            return Library.librarian == null;
        }

        public void onAdd(Librarian lib) {
            Library.librarian = lib;
            Library.persons.add(lib);
            System.out.println("Librarian added successfully.");
        }
    }

    // ✅ OCP: add librarian through policy
    public static boolean addLibrarian(Librarian lib) {
        if (assignmentPolicy.canAdd(lib)) {
            assignmentPolicy.onAdd(lib);
            return true;
        } else {
            System.out.println("Sorry, cannot add librarian under current policy.");
            return false;
        }
    }

    // ✅ OCP: Change behavior without modifying code
    public static void setAssignmentPolicy(LibrarianAssignmentPolicy policy) {
        assignmentPolicy = policy;
    }
}