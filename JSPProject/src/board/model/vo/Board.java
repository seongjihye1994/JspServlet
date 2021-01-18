package board.model.vo;

import java.sql.Date;

public class Board {
	
	private int bId;			// 게시글 고유 번호
	private int bType;			// 게시글 타입 (1. 일반게시판, 2. 사진게시판)
	private String category;	// 게시글 분류 (공통, 운동, 등산, 게임, 낚시, 요리, 기타)
	// int cid가 아닌 category 테이블 조인 결과 값인 String category로 선언
	private String bTitle;		// 게시글 제목
	private String bContent;	// 게시글 내용
	private int userNo;			// 게시글 작성자 (user_no 참조값)
	private String bWriter;		// 게시글 작성자 이름
	// int bWriter가 아닌 member 테이블 조인 결과 값인 String bWriter로 선언
	private int bCount;		// 게시글 조회수
	private Date createDate;	// 게시글 작성일
	private Date modifyDate;	// 게시글 수정일
	private String status;		// 게시글 상태 (Y, N)
	
	public Board() {}

	public Board(int bId, int bType, String category, String bTitle, String bContent, int userNo, String bWriter,
			int bCount, Date createDate, Date modifyDate, String status) {
		super();
		this.bId = bId;
		this.bType = bType;
		this.category = category;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.userNo = userNo;
		this.bWriter = bWriter;
		this.bCount = bCount;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}


	public Board(int bId, int bType, String category, String bTitle, String bContent, String bWriter, int bCount,
			Date createDate, Date modifyDate, String status) {
		super();
		this.bId = bId;
		this.bType = bType;
		this.category = category;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bWriter = bWriter;
		this.bCount = bCount;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	public Board(String category, String bTitle, String bContent, int userNo) {
		super();
		this.category = category;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.userNo = userNo;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public int getbType() {
		return bType;
	}

	public void setbType(int bType) {
		this.bType = bType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getbWriter() {
		return bWriter;
	}

	public void setbWriter(String bWriter) {
		this.bWriter = bWriter;
	}

	public int getbCount() {
		return bCount;
	}

	public void setbCount(int bCount) {
		this.bCount = bCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Board [bId=" + bId + ", bType=" + bType + ", category=" + category + ", bTitle=" + bTitle
				+ ", bContent=" + bContent + ", userNo=" + userNo + ", bWriter=" + bWriter + ", bCount=" + bCount
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}

	

}
