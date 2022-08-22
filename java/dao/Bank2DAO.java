package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.JDBCUtil;
import vo.Bank2VO;

// bank2에서 계좌이체를 받는 일은 있어도 주는 일은 없기 때문에 트랜잭션 사용 x  
public class Bank2DAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectOne = "SELECT * FROM BANK2 WHERE BID = 222";
	
	public Bank2VO selectOne(Bank2VO vo) {
		Bank2VO data = null;
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_selectOne);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				data = new Bank2VO();
				data.setBid(rs.getInt("BID"));
				data.setBname(rs.getString("BNAME"));
				data.setBalance(rs.getInt("BALANCE"));
				System.out.println("Bank2DAO : selectOne() :" + data);
				return data;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;
		
	}
}
