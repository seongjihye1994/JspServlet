package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import board.model.service.BoardService;
import board.model.vo.Reply;

/**
 * Servlet implementation class ReplyInsertServlet
 */
@WebServlet("/board/insertReply")
public class ReplyInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 넘어온 파라미터 값 추출 -> Reply 객체화
		// data : {writer : writer, bId : bId, content : content}
		int writer = Integer.parseInt(request.getParameter("writer"));
		int bId = Integer.parseInt(request.getParameter("bId"));
		String content = request.getParameter("content");
		
		Reply r = new Reply();
		r.setrWriter(writer);
		r.setRefBid(bId);
		r.setrContent(content);
		
		//ArrayList<Reply> rList = new BoardService().insertReply(/*객체명*/);
		ArrayList<Reply> rList = new BoardService().insertReply(r);
	
		// JSON 또는 GSON 라이브러리 추가 후 rList 응답
		// GSON 사용 시 날짜 값은 Date 포멧에 대한 컨트롤이 필요함
		// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// gson.toJson(rList, response.getWriter());
		
		// 응답 처리
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// toJson()의 첫번째 인자는 Object 타입 --> 어떤 객체든 처리 가능
		// toJson()의 두번쨰 인자는 응답할 스트림
		gson.toJson(rList, response.getWriter());
				
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
