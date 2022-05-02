package jdbctest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

	Connection con = null;
	
	public DBTest() {
		String url= "jdbc:mysql://localhost/jdbctest";
		String userid= "root";
		String password= "@@@@@@@@@@비밀번호 자리@@@@@@@@@@";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL Server JDBD Driver Loaded Successfully!");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("Connecting to database...");
			con = DriverManager.getConnection(url, userid, password);
			System.out.println("Connected...");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void sqlRun() {
		String query = "SELECT * FROM Book";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("BOOK ID   BOOK NAME                PUBLISHER           PRICE");
			
			while(rs.next()) {
				int bookId = rs.getInt(1);
				String bookName = rs.getString(2);
				String publisher = rs.getString(3);
				int price = rs.getInt(4);
				
				String str = String.format("%-10d%-24s%-20s%d", bookId, bookName, publisher, price);
				System.out.println(str);
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updatePhoneNumber() {
		String query1 = "UPDATE Customer SET phone = \"010-1234-5678\" WHERE custid=5";
		
		String query2 = "SELECT * FROM Customer";
		
		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(query1);
			System.out.println(result + " 개의 행에 적용됨.");
			
			ResultSet rs = stmt.executeQuery(query2);
			System.out.println("CUST ID   NAME                     ADDRESS             PHONE");
			while(rs.next()) {
				int custId = rs.getInt(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone = rs.getString(4);
				
				String str = String.format("%-10d%-24s%-20s%s", custId, name, address, phone);
				System.out.println(str);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void selectCustomerBook(String customerName) {
		String query = "SELECT Book.bookname\r\n"
				+ "FROM Orders\r\n"
				+ "LEFT JOIN Customer ON Orders.custid = Customer.custid\r\n"
				+ "LEFT JOIN Book ON Orders.bookid = Book.bookid\r\n"
				+ "WHERE Customer.name = \""+customerName+"\"";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(customerName + " 님이 구매한 책 목록입니다...\n");
			System.out.println("BOOK NAME");
			while(rs.next()) {
				String bookName = rs.getString(1);
				
				String str = String.format("%-24s", bookName);
				System.out.println(str);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void selectBookCustomer(String bookName) {
		String query = "SELECT Customer.name\r\n"
				+ "FROM Orders\r\n"
				+ "LEFT JOIN Customer ON Orders.custid = Customer.custid\r\n"
				+ "LEFT JOIN Book ON Orders.bookid = Book.bookid\r\n"
				+ "WHERE Book.bookname = \""+bookName+"\"";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(bookName + " 을 구매한 고객 목록입니다...\n");
			System.out.println("CUSTOMER NAME");
			while(rs.next()) {
				String name = rs.getString(1);
				
				String str = String.format("%-24s", name);
				System.out.println(str);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBTest so = new DBTest();
		
		//2번
		so.sqlRun();
		
		//3번
		//so.updatePhoneNumber();
		
		//4-1번
		//so.selectCustomerBook("박지성");
		
		//4-2번
		//so.selectBookCustomer("Olympic Champions");
	}

}
