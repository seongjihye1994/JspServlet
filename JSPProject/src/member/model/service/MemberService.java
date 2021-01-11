package member.model.service;

import java.sql.Connection;

import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*;

public class MemberService {

	// 1. 로그인용 서비스 메소드
	public Member loginMember(Member m) {
		// JDBCTemplate 작성하여 DB Connection 객체 가져오기
		Connection conn = getConnection();	
		
		Member loginUser = new MemberDao().loginMember(conn, m);
		
		close(conn);
		
		return loginUser;
	}

	// 2. 회원 가입 서비스 메소드
	public int insertMember(Member mem) {
		Connection conn = getConnection();	
		
		int result = new MemberDao().insertMember(conn, mem);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	// 3. 회원 정보 수정용 서비스
	public Member updateMember(Member m) {
		Connection conn = getConnection();	
		
		Member updateMember = null;
		
		// 1. 수정하려는 정보가 담긴 member 객체를 가지고 updateMember 수행
		int result = new MemberDao().updateMember(conn, m);
		
		// 2. 수정이 잘 되었다면 수정 된 정보의 member 객체 select 후 리턴
		if (result > 0) {
			updateMember = new MemberDao().selectMember(conn, m.getUserId());
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMember;
	}

	// 4. 비밀번호 변경용 서비스
	public Member updatePwd(String userId, String userPwd, String newPwd) {
		Connection conn = getConnection();
		
		Member updateMember = null;
		
		// 수정용 dao
		int result = new MemberDao().updatePwd(conn, userId, userPwd, newPwd);
		
		if (result > 0) {
			// 수정 후 다시 조회해오는 dao
			updateMember = new MemberDao().selectMember(conn, userId);
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return updateMember;
	}

	// 5. 회원 탈퇴용 서비스
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

}
