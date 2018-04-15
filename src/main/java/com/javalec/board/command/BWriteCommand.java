package com.javalec.board.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.board.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void excute(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> map = model.asMap();//¸ðµ¨À» ¸ÊÇüÅÂ·Î ¹Ù²Þ
		HttpServletRequest request=(HttpServletRequest)map.get("request");
		
		String bName =request.getParameter("bName");
		String bTitle =request.getParameter("bTitle");
		String bContent =request.getParameter("bContent");
		
		BDao dao= new BDao();
		dao.write(bName,bTitle,bContent);
		
		
	}

}
