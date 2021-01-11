package notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import static common.JDBCTemplate.*;

import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {

	// 1. 공지사항 리스트 조회용 서비스
	public ArrayList<Notice> selectList() {
		
		Connection conn = getConnection();
		ArrayList<Notice> list = new NoticeDao().selectList(conn);
		
		close(conn);
		
		return list;
	}

}
