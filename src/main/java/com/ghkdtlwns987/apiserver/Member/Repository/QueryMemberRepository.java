package com.ghkdtlwns987.apiserver.Member.Repository;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import java.util.Optional;

public interface QueryMemberRepository {
    /**
     * Id를 기반으로 회원을 찾는 기능입니다.
     * @param Id
     * @return Optional<Member>
     */
    Optional<Member> findById(Long Id);
    /**
     * email를 기반으로 회원을 찾는 기능입니다.
     * @param email
     * @return Optional<Member>
     */
    Optional<Member> findMemberByEmail(String email);
    /**
     * logindId 를 기반으로 회원을 찾는 기능입니다.
     * @param loginId
     * @return Optional<Member>
     */
    Optional<Member> findMemberByLoginId(String loginId);
    /**
     * phone 를 기반으로 회원을 찾는 기능입니다.
     * @param phone
     * @return Optional<Member>
     */
    Optional<Member> findMemberByPhone(String phone);


    /**
     * email을 기반으로 회원이 이미 존재하는지 찾는 메서드 입니다.
     * @param email
     * @return boolean
     */
    boolean existsMemberByEmail(String email);

    /**
     * loginId를 기반으로 회원이 이미 존재하는지 확인하는 메서드 입니다.
     * @param loginId
     * @return boolean
     */
    boolean existsMemberByLoginId(String loginId);

    /**
     * nickname을 기반으로 회원이 이미 존재하는지 확인하는 메서드 입니다.
     * @param nickname
     * @return boolean
     */
    boolean existsMemberByNickname(String nickname);

    /**
     * phone을 기반으로 회원이 이미 존재하는지 확인하는 메서드 입니다.
     * @param phone
     * @return
     */
    boolean existsMemberByPhone(String phone);

}
