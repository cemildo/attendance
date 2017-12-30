package ch.cemil.info.yoklama.services;

import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Organization;
import ch.cemil.info.yoklama.domain.repository.MemberRepository;
import ch.cemil.info.yoklama.domain.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);
        return members;
    }

    public List<Member> findByOrganizationId(long orgId) {
        List<Member> members = new ArrayList<>();
        memberRepository.findByOrganizationId(orgId).forEach(members::add);
        return members;
    }


    public Member findOne (long memberId){
        return memberRepository.findOne(memberId);
    }

    public void delete(long orgId){
        memberRepository.delete(orgId);
    }

    public Member save(Member member){
        return  memberRepository.save(member);
    }
}
