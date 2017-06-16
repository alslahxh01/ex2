package com.choa.notice;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.choa.util.PageMaker;
@Service
//NoticeService noticeService = new NoticeService();
public class NoticeService {
	//service@@@@@@@@@@@@@@@@
	
	// private noticeDAO noticeDAO 를 
	//멤버변수로 하나 셋팅해놓고 생성자를 만들어 논다. 
	//후에 xml에서 constructor 사용하여, 
 //Reposetory에서 주어진 id 를 여기 퀄리티파이어 에 입력 하여 주입,,
	@Inject
	private NoticeDAO noticeDAO;
	
	public void test(){
		System.out.println("NoticeService 에서 의 DAO 주소값은 : "+noticeDAO);
	}
	//constructor
	
//	public NoticeService(NoticeDAO noticeDAO){
//	 this.noticeDAO = noticeDAO;
//	}
	//setter
//	public void setNoticeDAO(NoticeDAO noticeDAO) {
//		this.noticeDAO = noticeDAO;
//	}

		//hit 업
	public int hitUp(int num) throws Exception {
		return noticeDAO.hitup(num);
	}
	
	//view   모델을 매개변수로 가져와도됨,
	public NoticeDTO noticeView(int num) throws Exception{
		
		return noticeDAO.noticeView(num);
	}
	
	//list 
	public List<NoticeDTO> noticeList(int curPage) throws Exception{ //추후 나중에 멤버변수로 curPage 받아와야함.
//		int totalCount = noticeDAO.getTotalCount();

		PageMaker pageMaker = new PageMaker(curPage);
		
		return noticeDAO.noticeList(pageMaker.getRowMaker());
	}
	//Write
	public int noticeWrite(NoticeDTO noticeDTO) throws Exception{
		return noticeDAO.noticeWrite(noticeDTO);
	}
	//update
	
	public int noticeUpdate(NoticeDTO noticeDTO) throws Exception{
		
		return noticeDAO.noticeUpdate(noticeDTO);
		}
	//Delete
	public int noticeDelete(int num) throws Exception{
		
		
		return noticeDAO.noticeDelete(num);
	}
	
	
	
	
	
}
