package com.javalec.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.board.command.BCommand;
import com.javalec.board.command.BContentCommand;
import com.javalec.board.command.BDeleteCommand;
import com.javalec.board.command.BListCommand;
import com.javalec.board.command.BModifyCommand;
import com.javalec.board.command.BReplyCommand;
import com.javalec.board.command.BReplyViewCommand;
import com.javalec.board.command.BWriteCommand;

@Controller
public class BController {

	BCommand command;
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list() started");
		
		command = new BListCommand();
		command.excute(model);
		
		
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view() started");
		
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request,Model model) {
		System.out.println("wirte() started");
		
		model.addAttribute("request",request);
		command=new BWriteCommand();
		command.excute(model);
		
		return "redirect:list";//뒤로 돌아가기위해 redirect
	}
	
	@RequestMapping("content_view")
	public String content_view(HttpServletRequest request,Model model) {
		System.out.println("content_view() started");
		
		model.addAttribute("request",request);//파라미터로 받은값이 request임
		command = new BContentCommand();
		command.excute(model);
		
		return "content_view";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/modify")
	public String modify(HttpServletRequest request,Model model) {
		
		System.out.println("modify() started");
		
		model.addAttribute("request",request);
		command = new BModifyCommand();
		command.excute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");
		
		model.addAttribute("request",request);
		command = new BReplyViewCommand();
		command.excute(model);
		
		return "reply_view";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/reply")
	public String reply(HttpServletRequest request,Model model) {
		System.out.println("reply() started");
		
		model.addAttribute("request",request);
		command = new BReplyCommand();
		command.excute(model);
		
	return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,Model model) {
		System.out.println("delete() started");
		
		model.addAttribute("request",request);
		command = new BDeleteCommand();
		command.excute(model);
		
		return "redirect:list";
	}
}
