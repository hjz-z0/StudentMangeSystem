package com.zte.sms.service.proxy;

import com.zte.sms.entity.Sysuser;
import com.zte.sms.entity.vo.SysuserVO;
import com.zte.sms.exception.DataAccessException;
import com.zte.sms.exception.OldPassWrongException;
import com.zte.sms.exception.ServiceException;
import com.zte.sms.exception.UserOrPassWrongException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.SysuserService;
import com.zte.sms.transaction.TransactionManager;


public class SysuserProxy implements SysuserService {

	@Override
	public Sysuser findUserByUsernamePass(SysuserVO sysuserVO) throws UserOrPassWrongException {
		
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		SysuserService sysuserService = (SysuserService)ObjectFactory.getObject("sysuserService");
		Sysuser sysuser=null;
		try {
			tran.beginTransaction();
			sysuser=sysuserService.findUserByUsernamePass(sysuserVO);
			tran.commit();
		} catch (UserOrPassWrongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
			throw e;
		}
		return sysuser;
		
	}

	@Override
	public Sysuser findUserByIdAndPass(SysuserVO sysuserVO) throws OldPassWrongException {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		SysuserService sysuserService = (SysuserService)ObjectFactory.getObject("sysuserService");
		Sysuser sysuser=null;
		try {
			tran.beginTransaction();
			sysuser=sysuserService.findUserByIdAndPass(sysuserVO);
			tran.commit();
		} 
        catch (DataAccessException e) {
			// TODO: handle exception
        	tran.rollback();
        	throw new ServiceException(e.getMessage());
		}		
		catch (OldPassWrongException e) {
			// TODO Auto-generated catch block
			tran.rollback();
			throw e;
		}
		return sysuser;
		
	}

	@Override
	public void modifyPassById(SysuserVO sysuserVO) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		SysuserService sysuserService = (SysuserService)ObjectFactory.getObject("sysuserService");

		try {
			tran.beginTransaction();
			sysuserService.modifyPassById(sysuserVO);
			tran.commit();
		} catch (DataAccessException  e) {
			// TODO Auto-generated catch block
			
			tran.rollback();
			throw new ServiceException(e.getMessage());
		}
		
		
	}

	@Override
	public Sysuser findUserById(int id) {

		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		SysuserService sysuserService = (SysuserService)ObjectFactory.getObject("sysuserService");
		Sysuser sysuser=null;
		try {
			tran.beginTransaction();
			sysuser=sysuserService.findUserById(id);
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
		}
		return sysuser;
	}

	@Override
	public void modifyUsername(Sysuser sysuser) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		SysuserService sysuserService = (SysuserService)ObjectFactory.getObject("sysuserService");

		try {
			tran.beginTransaction();
			sysuserService.modifyUsername(sysuser);
			tran.commit();
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
		}
	}

}
