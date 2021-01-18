package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MyFileRenamePolicy;
import member.model.vo.Member;

/**
 * Servlet implementation class GalleryInsertServlet
 */
@WebServlet("/gallery/insert")
public class GalleryInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GalleryInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// String title = request.getParameter("title");
		// System.out.println("title : " + title);
		// -> form 전송 시 enctype 을 multipart/form-data로 전송하는 경우
		// 	    기존 방식으로 request.getParameter("name속성값")와 같이 값을 추출할 수 없음
		
		// com.orelilly.servlet의 약자로 cos.jar 라이브러리를 다운받아 사용해야 값 추출 가능 (WEB-INF > lib 폴더에 추가)
		// http://www.servlets.com
		
		// 1. enctype이 multipart/form-data로 전송 되었는지 확인
		if (ServletFileUpload.isMultipartContent(request)) {
			// 전송파일 용량제한 : 10Mbyte로 제한
			int maxSize = 1024*1024*10;
			
			// 웹 서버 컨테이너 경로 추출
			String root = request.getSession().getServletContext().getRealPath("/"); // WebContent
			System.out.println("root : " + root);
			
			// 파일이 실제로 저장 될 경로
			String savePath = root + "resources\\uploadFiles\\";
			
			// HttpServletRequest --> MultipartRequest 변경
			// MultipartRequest multiRequest = new MultipartRequest(request, savePath,
			// 		maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			// 위의 MultipartRequest 객체 생성과 동시에 업로드한 파일들이 서버에 업로드 된다.
			// 즉, 이후 프로세스에 문제가 있던 없던 우선 서버에 업로드가 되므로
			// 추후 프로세스에서 문제가 생길 경우 업로드 된 파일을 삭제할 것
			
			// 사용자가 올린 파일명을 그대로 저장할 경우 같은 파일명이 있을 경우 이전 파일을 덮어쓰거나
			// 한글 파일명 특수기호 띄어쓰기 등은 서버에 따라 문제가 생길 수 있어
			// 파일명을 수정하여 저장하는 것이 일반적
			
			// DefaultFileRenamePolicy는 cos.jar가 제공하는 클래스로
			// 같은 파일명이 있을 경우 파일명 뒤에 카운팅 된 숫자를 붙여줌
			// 만약, user.png를 저장하려고 했으나 같은 이름의 파일이 있다면 -> user1.png로 규칙을 정해 지어줌
			
			// DB에 저장
			MultipartRequest multiRequest = new MultipartRequest(request, savePath,
					maxSize, "UTF-8", new MyFileRenamePolicy());
			// multipartRequest 객체가 생성 되는 순간 전달 된 매개변수에 맞게
			// (MyFileRenamePloicy에서 정의한 파일 리네임 규칙, 저장 경로, 용량 제한 등)
			// 파일이 서버에 저장 됨
			
			// DB의 Board와 Attachment에 데이터 저장
			
			// DB에 저장하기 위해 change_name과 origin_name 각각의 리스트를 만들어주는 작업
			// 다중 파일을 묶어서 업로드하기에 컬렉션을 사용
			
			// 전송 된 파일의 원래 이름을 저장할 리스트
			ArrayList<String> originFiles = new ArrayList<String>();
			// 전송 된 파일의 리네임 된 이름(실제 저장 된 이름)을 저장할 리스트
			ArrayList<String> changeFiles = new ArrayList<String>();
			
			// multipartRequest로부터 파일에 관한 정보 추출
			// Enumeration은 Iterator의 자바 초기 버전
			// input type="file" 엘리먼트들의 name 속성값 -> getFileNames()
			Enumeration<String> files = multiRequest.getFileNames();
			
			while(files.hasMoreElements()) {
				String name = files.nextElement();
				System.out.println("name : " + name);
				
				// 파일이 null이 아닌 경우 (input 태그에 파일이 첨부되어서 넘어온 경우)
				if (multiRequest.getFilesystemName(name) != null) {
					// getFilesystemName("name속성값")
					// -> MyRenamePolicy의 rename 메소드에서 작성 한대로 rename된 파일명
					changeFiles.add(multiRequest.getFilesystemName(name));
					
					// getOriginalFileName("name속성값")
					// -> 실제 사용자가 업로드 할 때의 파일명
					originFiles.add(multiRequest.getOriginalFileName(name));
				}
			}
			
			// Attachment 테이블에 값 삽입을 위한 작업
			ArrayList<Attachment> fileList = new ArrayList<>();
			// 전송 순서 역순으로 파일이 changeFiles, originFiles에 저장 되었기 때문에
			// 반복문을 역순으로 수행하기
			
			for (int i = originFiles.size() - 1; i >= 0; i--) {
				Attachment at = new Attachment();
				at.setFilePath("/resources/uploadFiles/");
				at.setOriginName(originFiles.get(i));
				at.setChangeName(changeFiles.get(i));
				
				// thumbnailImg의 경우 file_level -> 0
				// contentImg의 경우 file_level -> 1
				if (i == originFiles.size() - 1) { // 썸네일이면 0, 내용이미지면 1
					at.setFileLevel(0);
				} else {
					at.setFileLevel(1);
				}
				
				fileList.add(at);
			}
			
			// 첨부 파일 외의 게시판 Board에 삽입할 내용 추출(action 방식이 바꼈기 때문에 일반타입도 multi로 수행)
			String category = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			// 작성자는 넘겨받은적이 없으니 세션의 loginUser에서 가져옴
			int userNo = ((Member) request.getSession().getAttribute("loginUser")).getUserNo();
			
			Board b = new Board(category, title, content, userNo);
			
			// 묶은 사진과 게시글을 서비스로 보내기
			// 사진 게시판 작성 비즈니스 로직을 처리할 서비스 요청
			int result = new BoardService().insertGallery(b, fileList);
			
			if (result > 0) {
				// 목록으로 재요청
				response.sendRedirect(request.getContextPath() + "/gallery/list");
			} else {
				// 실패시 저장 된 사진 삭제
				for (int i = 0; i < changeFiles.size(); i++) {
					// 서버에 저장 된 이름 목록을 통해 반복문을 수행하면서
					File failedFile = new File(savePath + changeFiles.get(i));
					failedFile.delete();
				}
				
				request.setAttribute("msg", "사진 게시판 게시글 등록에 실패하였습니다.");
				request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
				
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
