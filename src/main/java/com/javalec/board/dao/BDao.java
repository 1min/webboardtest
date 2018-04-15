package com.javalec.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javalec.board.dto.BDto;

public class BDao {

	DataSource dataSource;
	
	public void modify(String bId,String bName,String bTitle,String bContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		
		try {
			connection= dataSource.getConnection();
			String query="update mvc_board set bName = ?,bTitle = ?,bContent = ? where bId = ?";
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			
			int rn = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
			try {
				if(preparedStatement!=null)preparedStatement.close();
				if(connection!=null)connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
	}
	
	
	public BDto contentView(String strID) {
		
		upHit(strID);
		
		BDto dto =null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId=?";
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {//resultSet이 존재하면 실행
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
		try {
			if(resultSet!=null)resultSet.close();
			if(preparedStatement!=null)preparedStatement.close();
			if(connection!=null)connection.close();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}	
	}
		
		
		return dto;
		
	}
	
	
	public void write(String bName,String bTitle,String bContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		
		try {
			
			connection= dataSource.getConnection();
			String query="insert into mvc_board(bId,bName,bTitle,bContent,bHit,bGroup,bStep,bIndent) values(mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int rn =preparedStatement.executeUpdate();//실제 데이터베이스에 쿼리문 실행(넣기시) //레코드 한줄 입력하면 1반환
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null)preparedStatement.close();
				if(connection!=null)connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
		
		
	}
	
	public BDao() {
		// TODO Auto-generated constructor stub
	try {
		Context context = new InitialContext();
		dataSource= (DataSource) context.lookup("java:comp/env/jdbc/Oracle10g");
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		try {
			connection=dataSource.getConnection();
			String query="select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board order by bGroup desc,bStep asc";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
			if(resultSet!=null)resultSet.close();
			if(preparedStatement!=null)preparedStatement.close();
			if(connection!=null)connection.close();
			
			}catch(Exception e2) {}
			}
		
		return dtos;
		
	} 
	
	public void delete(String strID) {
		
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		
		try {
			
			connection = dataSource.getConnection();
			String query="delete from mvc_board where bId = ? ";
			preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			
			int rn=preparedStatement.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement!=null)preparedStatement.close();
				if(connection!=null)connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		
		
	}
	
	
	private void upHit( String bId) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public BDto reply_view(String strID) {
		
		BDto dto = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		
		try {
		
			connection = dataSource.getConnection();
			String query="select * from mvc_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet=preparedStatement.executeQuery();
			
			 if(resultSet.next()) {//값이 있으면
				int bId = resultSet.getInt("bId");
				String bName= resultSet.getString("bName");
				String bTitle= resultSet.getString("bTitle");
				String bContent= resultSet.getString("bContent");
				Timestamp bDate= resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto= new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet!=null)resultSet.close();
				if(preparedStatement!=null)preparedStatement.close();
				if(connection!=null)connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		return dto;
		
	}
	
	public void reply(String bId,String bName,String bTitle,String bContent,String bGroup,String bStep,String bIndent) {
		
		replyShape(bGroup,bStep);
		
		Connection connection = null;
		PreparedStatement preparedStatement=null;
		
		try {
			
			connection = dataSource.getConnection();
			String query="insert into mvc_board(bId,bName,bTitle,bContent,bGroup,bStep,bIndent) values(mvc_board_seq.nextval,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep)+1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);
			
			int rn = preparedStatement.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement!=null)preparedStatement.close();
				if(connection!=null)connection.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
	}

	
	private void replyShape( String strGroup, String strStep) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strGroup));
			preparedStatement.setInt(2, Integer.parseInt(strStep));
			
			int rn = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
}
