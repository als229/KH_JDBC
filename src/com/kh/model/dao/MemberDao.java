package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/*
DAO (Data Access Object)

데이터베이스 관련된 작업(CRUD)을 전문적으로 담당하는 객체
DAO 안에 모든 메서드를 데이터베이스와 관련된 작업으로 만든다.

* CRUD => Create, Retrieve, Update, Delete, SQL DML
  Select
  
  Controller를 통해서 호출된 기능을 수행
  DB에 직접적으로 접근한 후 해당 SQL문을 실행 및 결과 받기 (JDBC)
  
*/

public class MemberDao {

	/*
	 * JDBC 용 객체 - Connection : DB의 연결정보를 담고 있는 객체 - (Prepared)Statement : 해당 DB에
	 * SQL문을 전달하고 실행한 후 결과를 받아내는 객체 - ResultSet : 만일 실행한 SQL문이 SELECT문일 경우 조회된 결과들이
	 * 담겨있는 객체
	 * 
	 * JDBC 처리 순서 1) JDBC Driver 등록 : 해당 DBMS가 제공하는 클래스 등록 2) Connection 생성 : 접속하고자
	 * 하는 DB정보를 입력해서 DB에 접속하면서 생성 3) Statement 생성 : Connection 객체를 이용해서 생성 4) SQL문
	 * 전달하면서 실행 : Statement 객체를 이용해서 SQL문 실행 => SELECT문일 경우 - executeQuery 메서드를 사용
	 * => DML문일 경우 - executeUpdate 메서드를 사용 5) 결과 받기 => SELECT문일 경우 - ResultSet 객체
	 * (조회된 데이터들이 담겨있음) 로 받기 => 6_1 => DML문일 경우 - int(처리된 행 수)로 받기 => 6_2 6_1)
	 * Result 에 담겨있는 데이터들을 하나씩 뽑아서 VO객체에 담기 6_2) 트랜잭션 처리 (성공이면 COMMIT, 실패면 ROLLBACK)
	 * 7) 다 쓴 JDBC 용 객체들은 반드시 자원반납(close) => 생성된 역순으로 8) 결과반환 (Controller) => SELECT
	 * 문일 경우 - 6_1 만들어진 결과 => DML문일 경우 - int(처리된 행 수)
	 * 
	 * Statement 특징 : 완성된 SQL 문을 실행할 수 있는 객체
	 * 
	 */

	/**
	 * 사용자가 추가 요청 시 입력했던 값들을 가지고 insert문을 실행하는 메서드
	 * 
	 * @param m => 사용자가 입력했던 아이디 ~ 취미까지 값들이 담겨있는 Member 객체
	 */
	public int inserMember(Member m) {
		// insert문 => 처리된 행 수 => 트랜잭션 처리

		// 필요한 변수들 먼저 셋팅
		int result = 0; // 처리된 결과 (행 수)를 담아줄 변수
		Connection conn = null; // 접속된 DB의 연결정보를 담는 변수
		Statement stmt = null; // SQL문 실행 후 결과를 받기 위한 변수

		// 실행할 SQL문 (완성된 형태로 만들어 둘 것)
		// INSERT INTO MEMBER
		// VALUES (SEQ_USERNO.NEXTVAL,'XXX','XXX','XXX',
		// 'XXXX', 'XX', 'X','XXXX','XXXX','XXXX',SYSDATE)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL , " + "'" + m.getUserId() + "'," + "'"
				+ m.getUserPwd() + "'," + "'" + m.getUserName() + "'," + "'" + m.getGender() + "', " + m.getAge() + ","
				+ "'" + m.getEmail() + "'," + "'" + m.getPhone() + "'," + "'" + m.getAddress() + "'," + "'"
				+ m.getHobby() + "', SYSDATE)";

		// System.out.println(sql);

		try {
			// 1) JDBC Driver 등록!
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ojdbc6.kar가 누락되었거나, 잘 추가 되었찌만 오타가 있을 경우
			// => ClassNotFoundException 발생!

			// 2) Connection 객체 생성(DB와 연결 -> url, 계정명, 비번)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			// 3) Statement 객체 생성
			stmt = conn.createStatement();

			// 4,5 ) DB에 완성된 SQL문을 전달하면서 실행 후 결과
			// (처리된 행 수) 받기
			result = stmt.executeUpdate(sql);

			// 6_2 ) 트랜잭션 처리
			if (result > 0) { // 성공했을 경우
				conn.commit();
			} else {// 실패했을 경우
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 JDBC용 객체 자원 반납 => 생성된 역순으로 close
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 8) 결과 반환
		return result; // 처리된 행의 수
	}

	public ArrayList<Member> selectAll() {

		// 0) 필요한 변수들 셋팅
		// 조회된 결과를 뽑아서 담아줄 변수 = ArrayList 생성(여러 회원의 정보, 여러 행)
		ArrayList<Member> list = new ArrayList<>(); // 텅 빈 리스트

		// Connection, Statement, ResultSet
		// => finally에서 자원반납을 하기 위해 try 전 미리 셋팅해둬야함!
		Connection conn = null; // 접속된 DB의 연결정보를 담는 변수
		Statement stmt = null; // SQL문 실행 훈 결과를 받기 위한 변수
		ResultSet rset = null;
		// SELECT 문이 실행된 조회 결과값들이 처음에 실질적으로 담길 객체

		// 실행할 SQL문(완성된 형태로)
		String sql = "SELECT * FROM MEMBER";

		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			// 3) Statement 객체 생성
			stmt = conn.createStatement();

			// 4,5) SQL문(SELECT)을 전달해서 실행 후 결과(ResultSet) 받기
			rset = stmt.executeQuery(sql);

			// 6_1) 현재 조회결과가 담기 ResultSet에서 한 행씩 봅아서 VO객체에 담기
			// rset.next()
			// 커서를 한 줄 아래로 옮겨주고 해당 행이 존재할 경우 true / 아니면 false 반환
			while (rset.next()) {

				// 현재 rset의 커서가 가리기고 있는 해당 행의 데이터를
				// 하나씩 뽑아서 담아줄 Member객체를 만든다.
				Member m = new Member();

				// rset 으로부터 어떤 컬럼에 해당하는 값을 뽑을 건지 제시
				// => 컬럼명(대소문자를 가리지 않음), 컬럼순번을 써더되지만 컬럼명으로 해라
				// => 권장사항 : 컬럼명으로 적고, 대문자로 쓰는 것 권장
				// rset.getInt(컬럼명 또는 컬럼순번) : int 형 값 뽑아낼 때
				// rset.getString(컬럼명 또는 컬럼순번) : String 형 값 뽑아낼때
				// rset.getDate(컬럼명 또는 컬럼순번) : Date 형 값 뽑아낼 때

				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 한행에 대한 모든 컬럼의 데이터값들을
				// 각각의 필드에 담아 하나의 Member객체에 옮겨담기 끝!

				// 리스트에 해당 Member 객체를 담아주면 됨
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 JDBC용 객체 반납(생성된 순서의 역순으로)
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 8) 결과 반환
		return list;
	}

	public Member selectByUserId(String userId) {

		// 0) 필요한 변수를 셋팅
		// 조회된 한 회원에 대한 정보를 담을 변수
		Member m = null;

		// JDBC 관련 객체들 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		// 실행할 SQL문 (완성된 형태)
		String sql = "SELECT * FROM MEMBER WHERE USERID = " + "'" + userId + "'";

		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");

			// 3) Statement 객체 생성
			stmt = conn.createStatement();

			// 4, 5) SQL문(SELECT)을 전달해서 실행 후 결과 받기
			rset = stmt.executeQuery(sql);

			// 6_1) 현재 조회 결과가 담긴 ResultSet에서
			// VO객채에 담기 => id 검색이기 때문에 한 행만 조회가 될 것이다.
			if (rset.next()) { // 커서를 한 행 움직여보고 조회결과가 있다면 true / 없다면 false

				// 조회된 한 행에 대한 모든 열에 데이터 값들을 뽑아서
				// 하나의 Member 객체에 담기
				m = new Member(rset.getInt("USERNO"), rset.getString("USERID"), rset.getString("USERPWD"),
						rset.getString("USERNAME"), rset.getString("GENDER"), rset.getInt("AGE"),
						rset.getString("EMAIL"), rset.getString("PHONE"), rset.getString("ADDRESS"),
						rset.getString("HOBBY"), rset.getDate("ENROLLDATE"));

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public ArrayList<Member> selectByKeyword(String keyword) {
		
		// 0) 필요한 변수를 셋팅
		// 조회된 한 회원에 대한 정보를 담을 변수
		ArrayList<Member> list = new ArrayList<>();
		
		// JDBC 관련 객체들 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rSet = null;
		
		// 실행할 SQL문 (완성된 형태)

		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%"+keyword+"%'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();
			rSet = stmt.executeQuery(sql);
		
			while(rSet.next()) {
				Member m = new Member();
				
			
			m.setUserNo(rSet.getInt("USERNO"));
			m.setUserId(rSet.getString("USERID"));	
			m.setUserPwd(rSet.getString("USERPWD"));	
			m.setUserName(rSet.getString("USERNAME"));	
			m.setGender(rSet.getString("GENDER"));	
			m.setAge(rSet.getInt("AGE"));	
			m.setEmail(rSet.getString("EMAIL"));	
			m.setPhone(rSet.getString("PHONE"));	
			m.setAddress(rSet.getString("ADDRESS"));	
			m.setHobby(rSet.getString("HOBBY"));	
			m.setEnrollDate(rSet.getDate("ENROLLDATE"));	
				
			list.add(m);
			}
		
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rSet.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "UPDATE MEMBER SET USERID = "
		+ "'" + m.getUserId() + "' , USERPWD = "
		+ "'" + m.getUserPwd() + "' , USERNAME = "
		+ "'" + m.getUserName() + "', GENDER = "
		+ "'" + m.getGender() + "', AGE = "
		+ m.getAge() + ", EMAIL = "
		+ "'" + m.getEmail() + "' , PHONE = "
		+ "'" + m.getPhone() + "' , ADDRESS = "
		+ "'" + m.getAddress() + "' , HOBBY = "
		+ "'" + m.getHobby() + "' WHERE USERNAME = " 
		+ "'" + m.
		;
		
		
		return 0;
	}
}
