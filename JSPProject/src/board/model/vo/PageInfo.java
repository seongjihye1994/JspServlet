package board.model.vo;

public class PageInfo {
	
	private int currentPage;
	private int listCount;
	private int pageLimit;
	private int boardLimit;
	private int maxPage;
	private int startPage;
	private int endPage;
	
	public PageInfo() {}
	
	// 페이징처리 계산에 필요한 값을 받아 start, end, maxPage 계산하여 세팅
	public PageInfo(int currentPage, int listCount, int pageLimit, int boardLimit) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.pageLimit = pageLimit;
		this.boardLimit = boardLimit;
		
		// maxPage : 전체 리스트카운트에서 몇개씩 보여줄지 나누고 올림처리
		this.maxPage = (int)Math.ceil((double) listCount / boardLimit);
		// startPage
		this.startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		// endPage
		this.endPage = startPage + pageLimit - 1;
		
		if (this.maxPage < this.endPage) {
			this.endPage = this.maxPage;
		}
		
	}

	public PageInfo(int currentPage, int listCount, int pageLimit, int boardLimit, int maxPage, int startPage,
			int endPage) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.pageLimit = pageLimit;
		this.boardLimit = boardLimit;
		this.maxPage = maxPage;
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getBoardLimit() {
		return boardLimit;
	}

	public void setBoardLimit(int boardLimit) {
		this.boardLimit = boardLimit;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", listCount=" + listCount + ", pageLimit=" + pageLimit
				+ ", boardLimit=" + boardLimit + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + "]";
	}
	
	

}
