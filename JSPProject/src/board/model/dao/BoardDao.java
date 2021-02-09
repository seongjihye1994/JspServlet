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

import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.Reply;
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
   // 게시글 총 개수 조회
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
	
	// 사진 게시판 게시글 조회
	public ArrayList<Board> selectGalleryBoardList(Connection conn) {
		
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Board> list = new ArrayList<>();
		String sql = prop.getProperty("selectGalleryBoardList");
		
		try {
			stmt = conn.createStatement(); // 쿼리 세팅
			
			rset = stmt.executeQuery(sql); // 쿼리 수행
			
			while (rset.next()) { // 조회된 결과값 넣어줌
				list.add(new Board(rset.getInt("bid"),
									rset.getInt("btype"),
									rset.getString("cname"),
									rset.getString("btitle"),
									rset.getString("bcontent"),
									rset.getString("user_name"),
									rset.getInt("bcount"),
									rset.getDate("create_date"),
									rset.getDate("modify_date"),
									rset.getString("status")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}
	
	// 사진 게시판 사진 조회
	public ArrayList<Attachment> selectGalleryPhotoList(Connection conn) {
		
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Attachment> list = new ArrayList<>();
		String sql = prop.getProperty("selectGalleryPhotoList");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while (rset.next()) {
				list.add(new Attachment(rset.getInt("fid"),
										rset.getInt("bid"),
										rset.getString("origin_name"),
										rset.getString("change_name"),
										rset.getString("file_path"),
										rset.getDate("upload_date"),
										rset.getInt("file_level"),
										rset.getInt("download_count"),
										rset.getString("status")));
			}
		} catch (SQLException e) {	
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}
	
	// 사진 게시판 게시글 insert용
	public int insertGalleryBoard(Connection conn, Board b) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertGalleryBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getbTitle());
			pstmt.setString(3, b.getbContent());
			pstmt.setInt(4, b.getUserNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// 사진 게시판 첨부파일(이미지) insert용
	public int insertGalleryPhoth(Connection conn, ArrayList<Attachment> fileList) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertGalleryPhoto");
		
		// 몇개를 첨부했는지 모르니 반복문 처리
			try {
				for (int i = 0; i < fileList.size(); i++) {
					pstmt = conn.prepareStatement(sql);
					
					Attachment at = fileList.get(i);
					pstmt.setString(1, at.getOriginName());
					pstmt.setString(2, at.getChangeName());
					pstmt.setString(3, at.getFilePath());
					pstmt.setInt(4, at.getFileLevel());
					
					// 한 행마다 이 쿼리문을 세팅하므로 누적해서 처리
					result += pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
		
		return result;
	}
	
	// 사진게시글 상세 조회(첨부파일 조회)
	public ArrayList<Attachment> selectGalleryPhoto(Connection conn, int bId) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Attachment> list = new ArrayList<>();
		String sql = prop.getProperty("selectGalleryPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bId);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Attachment(rset.getInt("fid"),
										rset.getInt("bid"),
										rset.getString("origin_name"),
										rset.getString("change_name"),
										rset.getString("file_path"),
										rset.getDate("upload_date"),
										rset.getInt("file_level"),
										rset.getInt("download_count"),
										rset.getString("status")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}
	
	// 첨부파일 다운로드 수 증가
	public int updateDownloadCount(Connection conn, int fId) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateDownloadCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// 다운로드할 첨부파일 선택
	public Attachment selectAttachment(Connection conn, int fId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Attachment at = null;
		String sql = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fId);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				at = new Attachment();
				at.setOriginName(rset.getString("origin_name")); // 사용자가 해당 첨부파일을 다운받을 땐 업로드 시 원래 이름으로 다운받도록
				at.setChangeName(rset.getString("change_name")); // 해당 파일을 찾아오기 위해서는 변경한 이름도 저장
				at.setFilePath(rset.getString("file_path"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return at;
	}
	
	// 게시글 당 댓글 리스트 조회
	public ArrayList<Reply> selectReplyList(Connection conn, int bId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Reply> rList = new ArrayList<Reply>();
		String sql = prop.getProperty("selectReplyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				rList.add(new Reply(rset.getInt("rid"),
									rset.getString("rcontent"),
									rset.getInt("ref_bid"),
									rset.getInt("rwriter"),
									rset.getString("user_name"),
									rset.getDate("create_date"),
									rset.getDate("modify_date"),
									rset.getString("status")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return rList;
	}
	
	// 댓글 insert
	public int insertReply(Connection conn, Reply r) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 쿼리 세팅
			pstmt.setString(1, r.getrContent());
			pstmt.setInt(2, r.getRefBid()); 
			pstmt.setInt(3, r.getrWriter());
			
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