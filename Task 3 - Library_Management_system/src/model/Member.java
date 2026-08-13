package model;

import java.time.LocalDate;

/**
 * Represents a library member who can borrow books.
 */
public class Member {

    private int memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;

    public Member(int memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = LocalDate.now();
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    @Override
    public String toString() {
        return String.format(
                "%-5d %-20s %-25s %-15s %-12s",
                memberId, name, email, phone, membershipDate
        );
    }
}
