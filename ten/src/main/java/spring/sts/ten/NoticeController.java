package spring.sts.ten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.notice.*;
import spring.model.ncomment.*;
import spring.utility.ten.*;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;
	
	@Autowired
	private NcommentDAO ndao;
	
	@RequestMapping("/notice/ndelete")
	public String ndelete(int comno, int noticeno, int nowPage, int nPage, String col, String word, Model model) {
		Map map= new HashMap();
		map.put("col", col);
		map.put("word", word);
		
		int total = ndao.total(map);
		int totalPage = (int) (Math.ceil((double) total / 3));
		if (ndao.delete(comno) > 0) {
			if (nPage != 1 && nPage == totalPage && total % 3 == 1) {
				nPage = nPage - 1;
			}
			model.addAttribute("bbsno", noticeno);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);

		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping("/notice/rupdate")
	public String rupdate(NcommentDTO dto, int nowPage, int nPage, String col, String word, Model model) {
		if (ndao.update(dto) > 0) {
			model.addAttribute("bbsno", dto.getNoticeno());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping("/notice/rcreate")
	public String rcreate(NcommentDTO dto, int nowPage, String col, String word, Model model) {

		if (ndao.create(dto) > 0) {
			model.addAttribute("bbsno", dto.getNoticeno());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@Autowired
	private NoticeDAO dao;
	
	@RequestMapping(value="/notice/create", method=RequestMethod.POST)
	public String create(NoticeDTO dto, HttpServletRequest request) {
		String basePath = request.getRealPath("./storage");
		
		String imgname = null;
		
		int imgsize = (int)dto.getImgnameMF().getSize();
		
		if(imgsize>0){
			imgname=Utility.saveFile(dto.getImgnameMF(), basePath);
		}else{
			imgname="noimg.jpg";
		}
		
		dto.setImgname(imgname);
		
		if(dao.create(dto)>0){
			return "redirect:/notice/list";
		} else{
			return "error/error";
		}
	}
	
	@RequestMapping(value="/notice/create", method=RequestMethod.GET)
	public String create() {
		return "/ks/notice/create";
	}
	
	@RequestMapping("/notice/read")
	public String read(int noticeno, Model model) {
		dao.upViewcnt(noticeno);
		model.addAttribute("dto", dao.read(noticeno));
		
		return "/ks/notice/read";
	}
	
	@RequestMapping(value="/notice/delete", method=RequestMethod.GET)
	public String delete(){
		
		return "/ks/notice/delete";
	}
	
	@RequestMapping(value="/notice/delete", method=RequestMethod.POST)
	public String delete(String oldfile, int noticeno,Model model,HttpServletRequest request){
		String basePath = request.getRealPath("/storage");
		
		if(dao.delete(noticeno)>0){
			model.addAttribute("col", request.getParameter("col"));
			model.addAttribute("word", request.getParameter("word"));
			model.addAttribute("nowPage", request.getParameter("nowPage"));
			
			if(oldfile!=null&&oldfile.equals("member.jpg"));
			Utility.deleteFile(basePath, oldfile);
			
			return "redirect:/notice/list";
		}else{
		return "error/error";
		}
	}
	
	@RequestMapping(value="/notice/update", method=RequestMethod.GET)
	public String update(int noticeno,Model model){
		
		model.addAttribute("dto", dao.read(noticeno));
		
		return "/ks/notice/update";
	}
	
	@RequestMapping(value="/notice/update", method=RequestMethod.POST)
	public String update(NoticeDTO dto, Model model, HttpServletRequest request){
		String basePath = request.getRealPath("/storage");
		
		String imgname = "";
		
		int imgsize = (int)dto.getImgnameMF().getSize();
		
		if(imgsize>0){
	
			imgname=Utility.saveFile(dto.getImgnameMF(), basePath);
		}else{
			imgname="noimg.jpg";
		}
		
		dto.setImgname(imgname);
		
		if(dao.update(dto)>0){
			model.addAttribute("col", request.getParameter("col"));
			model.addAttribute("word", request.getParameter("word"));
			model.addAttribute("nowPage", request.getParameter("nowPage"));
			return "redirect:/notice/list";
		}else{
			return "error/error";
		}
		
	}
	
	@RequestMapping("/notice/list")
	public String list(HttpServletRequest request, Model model){
		
		String col =Utility.nullCheck(request.getParameter("col"));
		String word =Utility.nullCheck(request.getParameter("word"));
		
		if(col.equals("total"))word="";
		//paging 관련
		
		
		int nowPage=1;//현재 페이지
		if(request.getParameter("nowPage")!=null){
			nowPage= Integer.parseInt(request.getParameter("nowPage"));
		}
		int recordPerPage= 5;//한페이지당 보여줄 레코드(글의)갯수
		
		int sno=((nowPage-1)*recordPerPage)+1;
		int eno=nowPage*recordPerPage;

		Map map= new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("col", col);
		map.put("word", word);

		List<NoticeDTO> list =dao.list(map);
		
		int total = dao.total(map);
		
		String paging =new Paging().paging3(total, nowPage, recordPerPage, col, word);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("col", col);
		model.addAttribute("word", word);
		model.addAttribute("nowPage", nowPage);
		
		return "/ks/notice/list";
	}
}