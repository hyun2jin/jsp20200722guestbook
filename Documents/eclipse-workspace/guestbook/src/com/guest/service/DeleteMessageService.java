package com.guest.service;

import java.sql.Connection;

import com.guest.dao.MessageDao;
import com.guest.jdbc.ConnectionProvider;
import com.guest.jdbc.JdbcUtil;
import com.guest.model.Message;

import javafx.scene.control.Alert;
import sun.security.jca.GetInstance.Instance;

public class DeleteMessageService {
	
	private static DeleteMessageService instance = new DeleteMessageService();
	
	public static DeleteMessageService getInstance() {
		return instance;
	}
	
	private DeleteMessageService() {		
	}
	
	public String deleteMessage(int messageId, String password)
	{
		Connection conn = null;
		
		try {
			
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			MessageDao messageDao = MessageDao.getInstance();
			Message message = messageDao.select(conn, messageId);
			if(message == null) {
				JdbcUtil.rollback(conn);
				JdbcUtil.close(conn);
				return "메시지 없음";
			}
			
			if(!message.matchPassword(password)) {
				JdbcUtil.rollback(conn);
				JdbcUtil.close(conn);
				return "패스워드가 다름";
			}
			
			messageDao.delete(conn, messageId);
			conn.commit();
			
			return "삭제 성공";
			
		} catch (Exception e) {
			// TODO: handle exception
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return "삭제 실패";
	}

}
