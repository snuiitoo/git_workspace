package member.model.service;

import static common.JdbcTemplate.close;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import member.model.dto.Member;
import member.model.dto.MemberRole;
import member.model.exception.MemberException;

public class MemberDao {
	private Properties prop = new Properties();
	
	public MemberDao() {
		// buildpath의 sql/member-query.properties파일의 내용 불러오기
		// .getPath가 없으면 url을 반환함, 실제 문자열로 반환하려면 getpath 필수
		String fileName = MemberDao.class.getResource("/sql/member-query.properties").getPath();
//		System.out.println("fileName@MemberDao = " + fileName);
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			// 1. PreparedStatement 생성 (미완성 sql & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberRole().toString()); // "U" "A"
			pstmt.setString(5, member.getGender());
			pstmt.setDate(6, member.getBirthday());
			pstmt.setString(7, member.getEmail());
			pstmt.setString(8, member.getPhone());
			pstmt.setString(9, member.getAddress());
			pstmt.setString(10, member.getHobby());
			
			// 2. 실행 
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원 가입 오류", e);
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		
		return result;
	}

	public Member findByMemberId(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("findByMemberId");
		Member member = null;
		
		// 1. pstmt 객체 & 미완성 쿼리 값 대입
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId); // 첫번째 물음표에 전달받은 id 대입
			
			// 2. 실행 및 rset 처리
			rset = pstmt.executeQuery();
			while(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				// "U" -> MemberRole.U, "A" -> MemberRole.A 바꿔주는 역할 valueof
				member.setMemberRole(MemberRole.valueOf(rset.getString("member_role")));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원 반납(rset, pstmt만! connection반환하지 않도록 유의)
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	/*
	 * updateMember = update member set member_name = ?, gender = ?, birthday = ?, 
	 * email = ?, phone = ?, address = ?, hobby = ? where member_id = ?
	 * */
	
	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateMember");
		try {
			
		// 1. pstmt 객체 생성, 미완성 쿼리 값 대입
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, member.getMemberName());
		pstmt.setString(2, member.getGender());
		pstmt.setDate(3, member.getBirthday());
		pstmt.setString(4, member.getEmail());
		pstmt.setString(5, member.getPhone());
		pstmt.setString(6, member.getAddress());
		pstmt.setString(7, member.getHobby());
		pstmt.setString(8, member.getMemberId());
		// 2. 실행
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			throw new MemberException("회원정보 수정!");
		} finally {
			// 3. 자원반납 - pstmt
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteMember");
	
		// 객체 생성, 미완 쿼리 값 대입
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원탈퇴 에러", e);
		} finally {
			close(pstmt);
		}
		return result;
	}



}
