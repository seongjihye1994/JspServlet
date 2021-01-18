package board.model.service;

import static common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.Search;

public class BoardService {

	// 게시글 페이징을 위해 총 게시글 갯수 구하기
	public int getListCount() {
		
		Connection conn = getConnection();
		
		int listCount = new BoardDao().getListCount(conn);
		
		close(conn);
		
		return listCount;
	}

	// 페이징 처리가 된 게시글 조회
	public ArrayList<Board> selectList(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		
		return list;
	}

	// 검색 리스트 카운트 조회
	public int getSearchListCount(Search s) {
		Connection conn = getConnection();
		
		int listCount = new BoardDao().getSearchListCount(conn, s);
		
		close(conn);
		
		return listCount;
	}

	// 검색용 리스트 조회
	public ArrayList<Board> selectSearchList(PageInfo pi, Search s) {
		
		Connection conn = getConnection();
		
		ArrayList <Board> list = new BoardDao().selectSearchList(conn, pi, s);
		
		return list;
	}

	// 게시글 상세보기 (조회수 증가)
	public Board selectBoard(int bId) {
		Connection conn = getConnection();
		
		BoardDao bd = new BoardDao();
		
		// 조회수 증가 (insert)
		int result = bd.increaseCount(conn, bId);
		
		// 게시글 조회 (select)
		Board b = null;
		
		if (result > 0) {
			commit(conn);
			b = bd.selectBoard(conn, bId);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return b;
	}

	// 게시글 상세보기 (조회수 증가 없이)
	public Board selectBoardNoCnt(int bId) {

		Connection conn = getConnection();
		
		Board b = new BoardDao().selectBoard(conn, bId);
		
		close(conn);
		
		return b;
	}

	// 게시글 insert
	public int insertBoard(Board b) {
		Connection conn = getConnection();
		
		int result = new BoardDao().insertBoard(conn, b);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	// 게시글 update
	public int updateBoard(Board b) {
		Connection conn = getConnection();
		
		int result = new BoardDao().updateBoard(conn, b);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	// 게시글 삭제
	public int deleteBoard(int bId) {
		
		Connection conn = getConnection();
		
		int result = new BoardDao().deleteBoard(conn, bId);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	// 사진 게시판 리스트 조회
	public Map<String, Object> selectGalleryList() {
		Connection conn = getConnection();
		
		BoardDao bd = new BoardDao();
		
		// 1. btype이 2인 게시글 리스트
		ArrayList<Board> bList = bd.selectGalleryBoardList(conn);
		
		// 2. attachment 테이블 조회
		ArrayList<Attachment> fList = bd.selectGalleryPhotoList(conn);
		
		// 게시글과 사진을 한번에 들고오기 위해 map으로 묶어줌
		Map<String, Object> map = new HashMap<>();
		map.put("bList", bList);
		map.put("fList", fList);
		
		return map;
	}

	// 사진 게시판의 첨부파일(이미지)와 게시글용 서비스
	public int insertGallery(Board b, ArrayList<Attachment> fileList) {
		
		Connection conn = getConnection();
		BoardDao bd = new BoardDao();
		// 1. boardInsert
		int result1 = bd.insertGalleryBoard(conn, b);
		// 2. AttachmentInsert
		int result2 = bd.insertGalleryPhoth(conn, fileList);
		
		if (result1 > 0 && result2 == fileList.size()) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result1;
	}

}