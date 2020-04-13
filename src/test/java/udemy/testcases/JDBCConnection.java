package udemy.testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

public class JDBCConnection {
	
	@Test
	public void connectDB()
	{
		String host="localhost";
		String port="3306";
		String dbName="udemySeleniumProject";
		String dbUsername="root";
		String dbPassword="root";
		//url format "jdbc:mysql://<host>:<port>/<dbName>"
		try {
			System.out.println("jdbc:mysql://"+host+":"+port+"/"+dbName+"");
			Connection con=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName+"",dbUsername ,dbPassword);
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from ApplicationDetails where Password='Pwd12345'");// 
			while(rs.next())
			{
				System.out.println(rs.getString("Username"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
