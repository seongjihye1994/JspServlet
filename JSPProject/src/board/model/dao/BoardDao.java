package board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.Search;

import static common.JDBCTemplate.close;

public class BoardDao {
   private Properties prop = new Properties();
   
   public BoardDao() {
      String fileName = BoardDao.class.getResource("/sql/board/board-query.xml").getPath();
      try {
         prop.loadFromXML(new FileInputStream(fileName));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   //게시글 총 개수 조회
   public int getListCount(Connection conn) {
      int listCount = 0;
      Statement stmt = null;
      //게시글의 갯수를 조회하는거라서 resultSet 필요
      ResultSet rset = null;
      String sql = prop.getProperty("getListCount");
      
      try {
         stmt = conn.createStatement();
         rset = stmt.executeQuery(sql);
         
         if(rset.next()) {
            // 게시글의 첫번째 숫자는 1 ....105로 늘어나게 하기
        	// sql에서 count를 select하는 명령문을 실행했을 때 
        	// 결과적으로 조회되는 컬럼의 갯수는 1임 (count = n개)
            listCount = rset.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(rset);
         close(stmt);
      }
      
      return listCount;
   }
   
   // 페이징 처리 된 boardList 조회
   public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
	   
	   ArrayList<Board> list = new ArrayList<>();
	   PreparedStatement pstmt = null;
	   ResultSet rset = null;
	   
	   String sql = prop.getProperty("selectList");
	   
	   try {
		pstmt = conn.prepareStatement(sql);
		
		int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
		int endRow = startRow + pi.getBoardLimit() - 1;
		
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);
		
		rset = pstmt.executeQuery();
		
		while (rset.next()) {
			list.add(new Board(rset.getInt(2),
								rset.getInt(3),
								rset.getString(4),
								rset.getString(5),
								rset.getString(6),
								rset.getString(7),
								rset.getInt(8),
								rset.getDate(9),
								rset.getDate(10),
								rset.getString(11)));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
        close(rset);
        close(pstmt);
     }
	   
	   return list;
   }
   
   
   // 검색 리스트 카운트 조회
	public int getSearchListCount(Connection conn, Search s) {
		
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "";
		
		if (s.getSearchCondition().equals("writer")) {
			sql = prop.getProperty("getSearchWriterListCount");
		} else if (s.getSearchCondition().equals("title")) {
			sql = prop.getProperty("getSearchTitleListCount");			
		} else {
			sql = prop.getProperty("getSearchContentListCount");
		}
		   
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, s.getSearch());
			
			rset = pstmt.executeQuery(); // 쿼리 select
			
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
			
		return listCount;
	}
	
	public ArrayList<Board> selectSearchList(Connection conn, PageInfo pi, Search s) {
	
		ArrayList<Board> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = "";
		
		if (s.getSearchCondition().equals("writer")) {
			sql = prop.getProperty("selectSearchWriterList");
		} else if (s.getSearchCondition().equals("title")) {
			sql = prop.getProperty("selectSearchTitleList");			
		} else {
			sql = prop.getProperty("selectSearchContentList");	
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setString(1, s.getSearch());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				list.add(new Board(rset.getInt(2),
									rset.getInt(3),
									rset.getString(4),
									rset.getString(5),
									rset.getString(6),
									rset.getString(7),
									rset.getInt(8),
									rset.getDate(9),
									rset.getDate(10),
									rset.getString(11)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	// 조회수 증가
	public int increaseCount(Connection conn, int bId) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}
	
	// 게시글 조회
	public Board selectBoard(Connection conn, int bId) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board b = null;
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bId); // 세팅
			
			rset = pstmt.executeQuery(); // 조회
			
			if (rset.next()) {
				b = new Board(rset.getInt("bid"),
							  rset.getInt("btype"),
							  rset.getString("cname"),
							  rset.getString("btitle"),
							  rset.getString("bcontent"),
							  rset.getInt("bwriter"),
							  rset.getString("user_name"),
							  rset.getInt("bcount"),
							  rset.getDate("create_date"),
							  rset.getDate("modify_date"),
							  rset.getString("status")						  
							  );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return b;
	}
	
	// 게시판 글 insert
	public int insertBoard(Connection conn, Board b) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 쿼리 세팅
			pstmt.setInt(1, Integer.parseInt(b.getCategory())); // 카테고리 값은 숫자지만, 타입이 String타입이기 때문에 형변환 해줌
			pstmt.setString(2, b.getbTitle());
			pstmt.setString(3, b.getbContent());
			pstmt.setInt(4, b.getUserNo());
			
			// 쿼리수행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// 게시글 update
	public int updateBoard(Connection conn, Board b) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 쿼리 세팅
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getbTitle());
			pstmt.setString(3, b.getbContent());
			pstmt.setInt(4, b.getbId());

			
			// 쿼리수행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// 게시글 삭제
	public int deleteBoard(Connection conn, int bId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 쿼리 세팅 
			pstmt.setInt(1, bId); 
			
			// 쿼리수행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


}