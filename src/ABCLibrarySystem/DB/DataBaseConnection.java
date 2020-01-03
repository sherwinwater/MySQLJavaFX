package ABCLibrarySystem.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataBaseConnection {
	
	public static PreparedStatement preparedStatementSearch;
	public static PreparedStatement preparedStatementInsert;
	public static PreparedStatement preparedStatementUpdate;
	public static PreparedStatement preparedStatementDelete;

	public static void initializeDB() {
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
//      Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded");

			// Establish a connection
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ABClibrary", "scott", "tiger");
//    ("jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl",
//     "scott", "tiger");
			System.out.println("Database connected");

			//creat a query
//			String querystring = "select firstName, mi, "
//					+ "lastName, title, grade from Student, Enrollment, Course "
//					+ "where Student.ssn = ? or Enrollment.courseId = ? or Student.firstName = ?";
//					+ "and Enrollment.courseId = Course.courseId";
			String querystringSearch = "select * from books where books.id = ?";

			// Create a statement
//			stmt = connection.createStatement();
			preparedStatementSearch = connection.prepareStatement(querystringSearch);

			// Create a preparedstatement for populating data
			String querystringInsert = "insert into books(id,callno,name,author,publisher,quantity,issued,added_date) "
					+ "values(?,?,?,?,?,?,?,?)";
			preparedStatementInsert = connection.prepareStatement(querystringInsert);
	  
			// Update data
			String querystringUpdate = "update Student set firstName = ? where ssn = ? and lastName = ?";
			preparedStatementUpdate = connection.prepareStatement(querystringUpdate);

			// Delete data
			String querystringDelete = "delete from Student where ssn = ? or firstName = ? or lastName = ?";
			preparedStatementDelete = connection.prepareStatement(querystringDelete);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
