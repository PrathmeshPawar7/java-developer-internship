package dao;

import model.Member;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of MemberDAO using a HashMap keyed by memberId.
 */
public class MemberDAOImpl implements MemberDAO {

    private final Map<Integer, Member> memberStore = new LinkedHashMap<>();

    @Override
    public void addMember(Member member) {
        memberStore.put(member.getMemberId(), member);
    }

    @Override
    public boolean removeMember(int memberId) {
        return memberStore.remove(memberId) != null;
    }

    @Override
    public Member getMemberById(int memberId) {
        return memberStore.get(memberId);
    }

    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(memberStore.values());
    }
}
