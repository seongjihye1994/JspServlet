package common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		
		// 전달 받은 원본 파일명을 중복되는 값 없게끔 변경해서 변경 된 파일명을 반환하는 기능
		// 변경명은 파일 업로드 한 시간 (년월일시분초) + 0~10만 사이의 랜덤값 부여
		
		// 파일명을 변경하더라도 확장자를 유지하기 위해 확장자명 먼저 가져오기
		String name = originFile.getName(); // -> 원본 파일명 ex. tour1.png
		
		String ext = "";	// 확장자만 추출해서 담을 변수
		
		int dot = name.lastIndexOf(".");	// .의 인덱스 위치 리턴
		
		if (dot != -1) { // .이라는 문자가 name안에 존재한다면 (lastIndexOf는 값이 없으면 -1을 리턴)
			ext = name.substring(dot); // .의 위치값부터 마지막까지 전체 추출 ex. ".png"
		}
		
		// 년월일시분초 포멧
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		// 최종적으로 수정할 파일명 (현재날짜년월일시분초 + 최대 5자리 랜덤값 + 파일 확장자)
		String fileName = sdf.format(new Date()) + (int)(Math.random() * 100000) + ext; 
													// 최대 5자리까지 랜덤 숫자 발생시키기
		
		// 원본 파일을 변경 된 파일명으로 생성
		File newFile = new File(originFile.getParent(), fileName);
								// 원본파일				// 새로 변경된 파일명
		
		return newFile;
	}
	
	

}
