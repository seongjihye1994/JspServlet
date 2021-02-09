package wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// HttpServletRequestWrapper를 상속 받는 클래스로 작성
public class EncryptWrapper extends HttpServletRequestWrapper {

	// 반드시 명시적으로 HttpServletRequest를 매개변수로 하는 생성자를 작성해야 함
	public EncryptWrapper(HttpServletRequest request) {
		super(request);
		
	}
	
	// ServletRequestWrapper의 getParameter 메소드 오버라이딩
	@Override
	public String getParameter(String key) {
		String value = "";
		
		if (key != null && (key.equals("userPwd") || key.equals("newPwd"))) {
			// request 객체에 담긴 파라미터  key 값이 암호화 처리가 필요한 userPwd 또는 newPwd인 경우
			value = getSha512(super.getParameter(key)); // 평문을 암호화해주는 메소드로 전달
			
		} else { 
			// request 객체에 담긴 key값이 userPwd, newPwd가 아닌
			// 암호화가 필요 없는 파라미터들은 기존 값을 그대로 사용
			value = super.getParameter(key); // 오버라이딩이 아닌 원래 사용하던 getParameter 메소드 호출
		}
		
		return value;
	}
	
	// Sha512 해쉬함수를 통한 처리
	public String getSha512(String userPwd) {
		String encPwd = null;
		
		// sha512 방식의 암호화 객체
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-512"); 
		} catch (NoSuchAlgorithmException e) { 
			// -> 매개변수로 전달한 알고리즘 명이 틀리면 (오타가 있거나 가져올 수 없는 상황에 대한 익셉션 처리)
			e.printStackTrace();
		}
		
		// 전달 받은 비밀번호를 바이트 배열로 리턴함
		byte[] bytes = userPwd.getBytes(Charset.forName("UTF-8"));
		
		// md 객체에 userPwd 바이트 배열을 전달해서 update
		md.update(bytes);
		
		// md.digest() -> 해쉬 함수 처리 된 결과 byte 배열로 리턴
		// 해당 바이트 배열을 다시 String 타입으로 인코딩
		encPwd = Base64.getEncoder().encodeToString(md.digest());
		return encPwd;
	}

}
