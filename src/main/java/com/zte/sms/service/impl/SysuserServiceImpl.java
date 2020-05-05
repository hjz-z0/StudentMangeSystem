package com.zte.sms.service.impl;

import com.zte.sms.dao.SysuserDAO;
import com.zte.sms.entity.Sysuser;
import com.zte.sms.entity.vo.SysuserVO;
import com.zte.sms.exception.OldPassWrongException;
import com.zte.sms.exception.UserOrPassWrongException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.SysuserService;


public class SysuserServiceImpl implements SysuserService{

	@Override
	public Sysuser findUserByUsernamePass(SysuserVO sysuserVO) throws UserOrPassWrongException {
		SysuserDAO  sysuserDAO=  (SysuserDAO)ObjectFactory.getObject("sysuserDAO");
		Sysuser sysuser=sysuserDAO.selectUserByUsernamePass(sysuserVO);
		if(sysuser==null){
			throw new UserOrPassWrongException("用户名或密码错误");
			
		}
		return sysuser;
		
	}

	@Override
	public Sysuser findUserByIdAndPass(SysuserVO sysuserVO) throws OldPassWrongException {
		// TODO Auto-generated method stub
		SysuserDAO  sysuserDAO=  (SysuserDAO)ObjectFactory.getObject("sysuserDAO");
		Sysuser sysuser=sysuserDAO.selectUserByIdAndPass(sysuserVO);
		if(sysuser==null){
			throw new OldPassWrongException("旧密码错误");
		}
		return sysuser;
	}

	@Override
	public void modifyPassById(SysuserVO sysuserVO) {
		// TODO Auto-generated method stub
		SysuserDAO  sysuserDAO=  (SysuserDAO)ObjectFactory.getObject("sysuserDAO");
		sysuserDAO.updatePassById(sysuserVO);
		
	}

	@Override
	public Sysuser findUserById(int id) {
		SysuserDAO  sysuserDAO=  (SysuserDAO)ObjectFactory.getObject("sysuserDAO");
		Sysuser sysuser = sysuserDAO.selectUserById(id);
		return sysuser;
	}

	@Override
	public void modifyUsername(Sysuser sysuser) {
		SysuserDAO  sysuserDAO=  (SysuserDAO)ObjectFactory.getObject("sysuserDAO");
		sysuserDAO.updateUserByUsername(sysuser);
	}


}
