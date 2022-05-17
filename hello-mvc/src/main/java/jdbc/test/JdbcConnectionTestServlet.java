package jdbc.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JdbcConnectionTestServlet
 */
@WebServlet("/test/dbConnection")
public class JdbcConnectionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String driverClass = "oracle.jdbc.OracleDriver"; // 드라이버 클래스명 (ojbc의 시작경로)
	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 db서버주소 (db접속프로토콜@ip:port:sid)
	String user = "web";
	String password = "web";
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// db연결테스트
		test();
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public void test() {
		//반납해야할 거 밖에 선언
		Connection conn = null;
		PreparedStatement pstmt = null; //실제론 사용X -> PreparedStatement을 사용한다.
		ResultSet rset = null;
		String sql = "select * from member"; //문자열로 만들어서 전송
		try {
			// 1. jdbc driver class 등록 시작점을 jvm한테 알려주는 것
			Class.forName(driverClass);
			System.out.println("driver class 등록 완료!"); //문제없이 연결되었다는 걸 알기위해
			
			// 2. Connection 객체 생성 (접속정보 : url, user, password) 이 접속이 있어야 statement실행가능
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("> Connection 객체 생성 성공!");
			
			// 3. Statement 객체(쿼리실행객체) 생성 
			pstmt = conn.prepareStatement(sql);
			System.out.println("> Statement객체 생성 성공!");
			
			// 4. Statement 실행 - ResultSet 객체를 반환 받는다.
			rset = pstmt.executeQuery(); //이게 실행 그냥 cute말고 cuteQuery로 찾아야함
			System.out.println("> Statement실행 및 ResultSet 반환 성공!");
			
			// 5. ResultSet을 1행씩 열람
			// 다음행이 존재하면 true 리턴
			while(rset.next()) { // 아래 한 행씩 처리(열람)
				String id = rset.getString("member_id"); //괄호안이 컬럼명
				String name = rset.getString("member_name");
				Date birthday = rset.getDate("birthday"); //getdate가 리턴하는 건 sql!date임
				System.out.printf("%s\t%s\t%s%n", id, name, birthday);
			}
		} catch (ClassNotFoundException | SQLException e) { // 이 클래스가 없으면 오류가 나는 것
			// sqlexception ctrl+T해서 확인해보자. sql관련 최상위 예외
			e.printStackTrace();
		} finally {
			// 6. 자원을 반납 후 종료
			// 객체생성 역순
			try {
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("> 자원반납 성공");
		}
	}
	

}
