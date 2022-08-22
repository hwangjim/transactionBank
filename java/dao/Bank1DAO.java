package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.JDBCUtil;
import vo.Bank1VO;

public class Bank1DAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectOne = "SELECT * FROM BANK1 WHERE BID = 101 ";
	// bid 값이 고정적이라면, 즉 한명밖에 없다면 selectOne 파라미터 값을 없애줘도 무방하다. 이는 곧 유지보수를 위해 사용되기 떄문이다. 
	// 파라미터를 VO로 해놓지 않으면 매번 칼럼이 늘어날때마다 추가를 해야함 
	// 즉 메소드 시그니처인 selectOne은 바뀌는 일이 없도록 
	// 단 트랜잭션 중 파라미터 값을 int balance 로 고정한다면 계좌이체시 금액만을 강제하는 것이기 때문에 강제성이 존재 
	// 적재적소에 사용하면 된다.
	// DAO의 메소드는 일반적으로 vo
	// 강제적으로 자료형, 인자의 개수를 고정할 수도 있음 -> 자유도가 없어진 만큼 즉 강제성이 생긴만큼 개발자의 실수를 방지할 수 있다. 
	public Bank1VO selectOne(Bank1VO vo) {
		// null 값으로 먼저 주고 
		Bank1VO data = null;
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_selectOne);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				// 값이 들어간다면, new하게끔 작성 
				data = new Bank1VO();
				data.setBid(rs.getInt("BID"));
				data.setBname(rs.getString("BNAME"));
				data.setBalance(rs.getInt("BALANCE"));
				// 로그를 작성하는 습관을 들이자 제발
				System.out.println("Bank1DAO : selectOne() :" + data);
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
	// 계좌 이체만 작성
	// balance = balance +- 이용 
	final String sql_transfer1="UPDATE BANK1 SET BALANCE=BALANCE-? WHERE BID=101";
	final String sql_transfer2="UPDATE BANK2 SET BALANCE=BALANCE+? WHERE BID=222";
	public boolean transfer(int balance) {
		// DAO의 메서드는 일반적으로 vo
		// 강제적으로 자료형,인자의 개수를 고정할수도있음!
		conn=JDBCUtil.connect();
		try {
			conn.setAutoCommit(false);
			// 트랜잭션의 시작을 설정하는 메서드
			// 자동commit을 해제할수있음(MySQL)
			
			// +++하나의 작업단위+++
			pstmt=conn.prepareStatement(sql_transfer1);
			pstmt.setInt(1, balance);
			pstmt.executeUpdate();
			
			pstmt=conn.prepareStatement(sql_transfer2);
			pstmt.setInt(1, balance);
			pstmt.executeUpdate();
			// +++하나의 작업단위+++
			
			System.out.println("얍!");
			// pk가 101인 bank1 정보 꺼내기 
			pstmt=conn.prepareStatement(sql_selectOne);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			// 매번 BNAME과 계산된 BALANCE로 출력 
			System.out.println(rs.getString("BNAME")+" | "+rs.getInt("BALANCE"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rs.close();
			// pk가 101인 bank1 정보 꺼내기 
			pstmt=conn.prepareStatement(sql_selectOne);
			rs=pstmt.executeQuery();
			rs.next();
			if(rs.getInt("BALANCE")<0) { // 가진금액보다 더많이 계좌이체를 하려고할때
				conn.rollback();
				return false;
			}
			else {
				conn.commit();
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
}
