package dao;

import model.Member;

import java.util.List;

/**
 * Data access contract for Member persistence operations.
 */
public interface MemberDAO {
    void addMember(Member member);
    boolean removeMember(int memberId);
    Member getMemberById(int memberId);
    List<Member> getAllMembers();
}
