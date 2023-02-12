package Utils;

import java.util.ArrayList;

public class Member {

    private String name;
    private String level;
    private int maxlengthLoan;
    private int maxNumberLoan;
    private String username;
    private String email;
    private String expireDate;
    private ArrayList<LoanBook> loanBooks;
    private ArrayList<LoanBook> reserveBooks;

    public Member() {
        loanBooks = new ArrayList<>();
        reserveBooks = new ArrayList<>();
        maxlengthLoan = 0;
        maxNumberLoan = 0;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public int getMaxlengthLoan() {
        return maxlengthLoan;
    }

    public int getMaxNumberLoan() {
        return maxNumberLoan;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setMaxlengthLoan(int maxlengthLoan) {
        this.maxlengthLoan = maxlengthLoan;
    }

    public void setMaxNumberLoan(int maxNumberLoan) {
        this.maxNumberLoan = maxNumberLoan;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<LoanBook> getLoanBooks() {
        return loanBooks;
    }

    public void setLoanBooks(ArrayList<LoanBook> loanBooks) {
        this.loanBooks = loanBooks;
    }

    public ArrayList<LoanBook> getReserveBooks() {
        return reserveBooks;
    }

    public void setReserveBooks(ArrayList<LoanBook> reserveBooks) {
        this.reserveBooks = reserveBooks;
    }

    // this object -> array
    public String[] toList() {
        return new String[]{getUsername(), getName(), getEmail(), getLevel(), getExpireDate()};
    }

    // Judgment of equality
    public boolean equals(Member member) {
        return member.getName().equals(name) && member.getUsername().equals(username) &&
                member.getLevel().equals(level) && member.getEmail().equals(email) &&
                member.getExpireDate().equals(expireDate);
    }
}
