package com.javalec.board.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.board.dao.BDao;
import com.javalec.board.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void excute(Model model) {
		// TODO Auto-generated method stub

		Map<String,Object>map = model.asMap();//map은 키와 키값으로구성 맵선언
		HttpServletRequest request = (HttpServletRequest)map.get("request");//map가져올때 key는 request 캐스팅필수
		String bId =request.getParameter("bId");
		
		BDao dao=new BDao();
		BDto dto = dao.contentView(bId);
		
		model.addAttribute("content_view",dto);
		
		
	}

}
