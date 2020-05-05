package com.zte.sms.dao;

import com.zte.sms.entity.Sysuser;
import com.zte.sms.entity.vo.SysuserVO;


public interface SysuserDAO {

	public Sysuser selectUserByUsernamePass(SysuserVO sysuserVO);

	public Sysuser selectUserByIdAndPass(SysuserVO sysuserVO);

	public void updatePassById(SysuserVO sysuserVO);

    Sysuser selectUserById(int id);

	void updateUserByUsername(Sysuser sysuser);
}
