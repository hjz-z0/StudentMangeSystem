package com.zte.sms.service;

import com.zte.sms.entity.Sysuser;
import com.zte.sms.entity.vo.SysuserVO;
import com.zte.sms.exception.OldPassWrongException;
import com.zte.sms.exception.UserOrPassWrongException;


public interface SysuserService {

	public Sysuser findUserByUsernamePass(SysuserVO sysuserVO)throws UserOrPassWrongException;

	public Sysuser findUserByIdAndPass(SysuserVO sysuserVO)throws OldPassWrongException;

	public void modifyPassById(SysuserVO sysuserVO);


	Sysuser findUserById(int id);

	void modifyUsername(Sysuser sysuser);
}
