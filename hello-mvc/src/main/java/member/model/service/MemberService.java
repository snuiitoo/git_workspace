package member.model.service;

import static common.JdbcTemplate.*;
import java.sql.Connection;

import member.model.dto.Member;

public class MemberService {

/**
 * 
 * @param memberId
 * @return
 */
	private MemberDao memberDao = new MemberDao();
	public Member findByMemberId(String memberId) {
		Connection conn = getConnection();
		Member member = memberDao.findByMemberId(conn, memberId);
		close(conn);
		return member;
	}
	
	public int insertMember(Member member) {
		int result = 0;
		Connection conn = getConnection();		
		try {
			result = memberDao.insertMember(conn, member);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);			
		}
		return result;
	}

	public int updateMember(Member member) {
		int result = 0;
		// 1. 커넥션 객체
		Connection conn = getConnection();
		// 2. dao 요청
		try {
			result = memberDao.updateMember(conn, member);
			// 3. 성공시 커밋 실패시 롤백 트랜잭션처리!
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
			// 4. 자원반납
		} finally {
			close(conn);
		}
		
		return result;
	}

	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.deleteMember(conn, memberId);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);			
		}
		return result;
	}


}
