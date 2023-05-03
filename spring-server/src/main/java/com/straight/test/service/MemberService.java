package com.straight.test.service;
import com.straight.test.domain.Member;
import com.straight.test.domain.dto.MemberDTO;
import com.straight.test.domain.dto.PageRequestDTO;
import com.straight.test.domain.dto.PageResultDTO;
import com.straight.test.repository.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceImp{

    private final SpringDataJpaMemberRepository memberRepository;

    public Member joinMember(MemberDTO m) {
        Member member = Member.builder()
                .userId(m.getUser_id())
                .userPw(m.getUser_pw())
                .userBirth(m.getUser_birth())
                .userGender(m.getUser_gender())
                .userName(m.getUser_name())
                .userClass(m.getUser_class())
                .userEmail(m.getUser_email())
                .build();
        memberRepository.save(member);
        return member;
    }
    @Override
    public MemberDTO read(String user_id) {
        Optional<Member> result = memberRepository.findByUserId(user_id);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }
/*

    public Long deleteMember(String name) {
//        Long result = memberRepository.deleteByName(name);
        return null;
    }
*/

    public boolean loginMember(String user_id, String user_pw) {
//        Optional<Member> dbInfo = memberRepository.getLoginInfo(user_id, user_pw);
        Optional<Member> dbInfo = memberRepository.findByUserIdAndUserPw(user_id, user_pw);
//        System.out.println("pw :" + dbInfo.get().getUser_pw());
        System.out.println(dbInfo);
        if (dbInfo.isPresent()) return true;
        else return false;
    }

    @Override
    public void modify(MemberDTO member) {
        Optional<Member> result = memberRepository.findByUserId(member.getUser_id());

        if(result.isPresent()) {
            Member entity = result.get();
            entity.changeName(member.getUser_name());
            entity.changePw(member.getUser_pw());
            memberRepository.save(entity);
        }

    }

    @Override
    public void remove(Long id) {

        memberRepository.deleteById(id);
    }
}
