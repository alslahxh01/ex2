package com.choa.ex2;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choa.notice.NoticeDAO;
import com.choa.notice.NoticeDTO;
import com.choa.notice.NoticeService;

@Controller
@RequestMapping(value="/notice/**")
public class NoticeController {
	//Inject 를 사용하여 DI 를 해달라고 Spring에게 전달하면 Spring Container 가 알아서 new ~~ 해줌 - xml로 가서 코드 쓰면됨.
	
	@Autowired //만들어진 서비스를 주입시키기
	private NoticeService noticeService;
	
	@RequestMapping(value="test")
	public void test(){
		System.out.println(noticeService);
		noticeService.test();
	}
	
	//List
@RequestMapping(value="noticeList" , method=RequestMethod.GET )
	public void noticeList(Model model, @RequestParam(defaultValue="1") Integer curPage) throws Exception{
										//널이면 초기값 1로 만들기
		List<NoticeDTO> ar =noticeService.noticeList(curPage);
		//얘를 noticeList.jsp 에 보내야한다
		model.addAttribute("list",ar);
}	

	//View
	@RequestMapping(value="noticeView" , method=RequestMethod.GET )
	public void noticeView(Integer num,Model model) throws Exception{ //오류를 줄이기 위해 int 보단 Integer 로 사용.
//		if(num == null){} //뭔가 처리
		
		int result =noticeService.hitUp(num);
		if(result > 0){
		NoticeDTO noticeDTO = noticeService.noticeView(num);
		model.addAttribute("dto", noticeDTO);
		}
	}
	
	//WriteForm
	@RequestMapping(value="noticeWrite" , method=RequestMethod.GET )
		public void noticeWrite(Model model){
			model.addAttribute("path","Write");
	}	
	//write(DB)
	@RequestMapping(value="noticeWrite" , method=RequestMethod.POST )
	public String noticeWrite(RedirectAttributes rd,NoticeDTO noticeDTO) throws Exception{
		int result = noticeService.noticeWrite(noticeDTO);
		String message="FAIL";
		//if로 성공 실패 여부 적기
		if(result>0){
			message="SUCCESS";
		}
//		model.addAttribute("message", message);
	
		rd.addFlashAttribute("message", message);
		//현재방법 Forwarding 
		
//		return "notice/result";
		return "redirect:noticeList?curPage=1";  //하지만 Redirect 로 가고싶다면 이방법
		// redirect로 홈을 가고싶으면 / 주면됨
		
	}
	
	//UpdateFOrm
	@RequestMapping(value="noticeUpdate" , method=RequestMethod.GET )
	public String noticeUpdate(Integer num, Model model) throws Exception{
		NoticeDTO noticeDTO = noticeService.noticeView(num);		
		
		model.addAttribute("dto",noticeDTO);
		model.addAttribute("path", "Update");
		
		return "notice/noticeWrite";
		
	}
	//Update
	@RequestMapping(value="noticeUpdate" , method=RequestMethod.POST )
	public String noticeUpdate(RedirectAttributes rd,NoticeDTO noticeDTO) throws Exception{			
		
			int result = noticeService.noticeUpdate(noticeDTO);
			String message="Update Failed";
			if(result >0){
				message="Update Success";
			}
			rd.addFlashAttribute("message",message);
			
			return "redirect:noticeList?curPage=1";
		}
	//Delete
	
	@RequestMapping(value="noticeDelete" , method=RequestMethod.GET )
	public String noticeDelete(RedirectAttributes rd, Integer num) throws Exception{
	int result = noticeService.noticeDelete(num);
	String message="FAIL DELETE";
	if(result>0){
		message="SUCCESS DELETE";
	}
	rd.addFlashAttribute("message", message);
	return "redirect:noticeList?curPage=1";
				//if
	}
}
