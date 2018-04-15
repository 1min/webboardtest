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

		Map<String,Object>map = model.asMap();//map�� Ű�� Ű�����α��� �ʼ���
		HttpServletRequest request = (HttpServletRequest)map.get("request");//map�����ö� key�� request ĳ�����ʼ�
		String bId =request.getParameter("bId");
		
		BDao dao=new BDao();
		BDto dto = dao.contentView(bId);
		
		model.addAttribute("content_view",dto);
		
		
	}

}
