package com.zte.sms.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zte.sms.constant.Constant;
import com.zte.sms.entity.Sysuser;
import com.zte.sms.entity.vo.SysuserVO;
import com.zte.sms.exception.UserOrPassWrongException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.SysuserService;


public class SysuserAction {

	public String login(HttpServletRequest req, HttpServletResponse resp) {

		SysuserService sysuserProxy = (SysuserService) ObjectFactory.getObject("sysuserProxy");
		SysuserVO sysuserVO = new SysuserVO();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		sysuserVO.setUsername(username);
		sysuserVO.setPassword(password);
		String state = req.getParameter("state");
		if ("admin".equals(state)) {
			Sysuser sysuser = null;

			try {
				sysuser = sysuserProxy.findUserByUsernamePass(sysuserVO);
				req.getSession().setAttribute("user", sysuser);
			} catch (UserOrPassWrongException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				req.setAttribute("error",e.getMessage());
				return "fail";
			}
			
			return "admin";
		}
		else if("student".equals(state)){
			Sysuser sysuser = null;
			try {
				sysuser = sysuserProxy.findUserByUsernamePass(sysuserVO);
				req.getSession().setAttribute("user",sysuser);
			} catch (UserOrPassWrongException e) {
				e.printStackTrace();
				req.setAttribute("error",e.getMessage());
				return "fail";
			}
			return "student";
		}
		
		return "fail";

		
	}
	//修改个人信息
	public void modifyUser(HttpServletRequest req,HttpServletResponse resp) throws IOException{

		//获取请求提交过来的值
		String id = req.getParameter("id");
		String username = req.getParameter("username");
		SysuserService sysuserProxy = (SysuserService) ObjectFactory.getObject("sysuserProxy");
		Sysuser sysuser = sysuserProxy.findUserById(Integer.parseInt(id));
		resp.setContentType(Constant.CONTENT_TYPE);
		try {
			if (sysuser!=null){
				sysuser.setUsername(username);
				sysuserProxy.modifyUsername(sysuser);
				resp.getWriter().print("success");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.getWriter().print("fail");
		}
	}
	//修改密码
	public void modifyPwd(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		
		//获取请求提交过来的值
		String id = req.getParameter("id");
		String oldPass = req.getParameter("oldPass");
		String newPass = req.getParameter("newPass");
		SysuserService sysuserProxy = (SysuserService) ObjectFactory.getObject("sysuserProxy");
		SysuserVO sysuserVO = new SysuserVO();
		sysuserVO.setId(Integer.parseInt(id));
		sysuserVO.setPassword(oldPass);
		sysuserVO.setNewPass(newPass);
		resp.setContentType(Constant.CONTENT_TYPE);
		try {
			sysuserProxy.findUserByIdAndPass(sysuserVO);
			//根据id修改密码
			sysuserProxy.modifyPassById(sysuserVO);
			resp.getWriter().print("success");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.getWriter().print("fail");
		}
	}
	
	//退出系统
	public String logOut(HttpServletRequest req, HttpServletResponse resp)
	{ req.getSession().removeAttribute("user");
	return "login";
	}
	
	//管理员首页
	public String toAdminMain(HttpServletRequest req,HttpServletResponse resp){
		//返回首页
		return "adminMain";
	}

	//学生首页
	public String toStudentMain(HttpServletRequest req,HttpServletResponse resp){
		//返回首页
		return "studentMain";
	}

}
