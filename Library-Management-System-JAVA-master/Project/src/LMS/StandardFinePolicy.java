package LMS;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class StandardFinePolicy implements FinePolicy {

    @Override
    public double calculateFine(Loan loan) {
        if (loan.getFineStatus()) return 0;

        Date issueDate = loan.getIssuedDate();
        Date today = new Date();

        long days = ChronoUnit.DAYS.between(issueDate.toInstant(), today.toInstant());
        days -= Library.getInstance().book_return_deadline;

        return (days > 0) ? days * Library.getInstance().per_day_fine : 0;
    }
}
