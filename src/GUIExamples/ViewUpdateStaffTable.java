package GUIExamples;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class ViewUpdateStaffTable extends Application {

	private PreparedStatement preparedStatement;
	private PreparedStatement preparedStatementInsert;
	private PreparedStatement preparedStatementUpdate;
	private PreparedStatement preparedStatementDelete;
	private Statement statement;

	private TextField tfssn = new TextField();
	private TextField tfLastName = new TextField();
	private TextField tfFirstName = new TextField();
	private TextField tfCourseID = new TextField();
	private TextField tfAddress = new TextField();
	private TextField tfDeptID = new TextField();
	private TextField tfMi = new TextField();
	private TextField tfPhone = new TextField();
	private TextField tfZipcode = new TextField();
	private TextField tfBirthdate = new TextField();

	private TextArea outputData = new TextArea();

	private Label labelssn = new Label("ssn");
	private Label labelCourseID = new Label("CourseID");
	private Label labelLastName = new Label("Last Name");
	private Label labelFirstName = new Label("First Name");
	private Label labelAddress = new Label("Address");
	private Label labelDeptID = new Label("DeptID");
	private Label labelMi = new Label("Mi");
	private Label labelPhone = new Label("Phone");
	private Label labelZipcode = new Label("Zipcode");
	private Label labelBirthdate = new Label("Birthdate");
	private Label labelStatus = new Label();

	@Override
	public void start(Stage primaryStage) {
		initializeDB();

		Button btView = new Button("view");
		Button btInsert = new Button("insert");
		Button btUpdate = new Button("update");
		Button btDelete = new Button("delete");

		HBox h1Box = new HBox(5);
		h1Box.getChildren().addAll(labelssn, tfssn, labelCourseID, tfCourseID);

		HBox h2Box = new HBox(5);
		h2Box.getChildren().addAll(labelLastName, tfLastName, labelFirstName, tfFirstName);

		HBox h3Box = new HBox(5);
		h3Box.getChildren().addAll(labelAddress, tfAddress, labelDeptID, tfDeptID);

		HBox h4Box = new HBox(5);
		h4Box.getChildren().addAll(labelBirthdate, tfBirthdate, labelZipcode, tfZipcode);

		HBox h5Box = new HBox(5);
		h5Box.getChildren().addAll(labelPhone, tfPhone, labelMi, tfMi);

		HBox h6Box = new HBox(5);
		h6Box.getChildren().addAll(btView, btInsert, btUpdate, btDelete);
		h6Box.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(5);
		vBox.getChildren().addAll(h1Box, h2Box, h3Box, h4Box, h5Box, h6Box);
		vBox.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(new ScrollPane(outputData));
		pane.setTop(vBox);
		pane.setBottom(labelStatus);

		// Create a scene and place it in the stage
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(pane, primScreenBounds.getWidth() / 2, primScreenBounds.getHeight() / 2);
		primaryStage.setTitle("ViewStaff"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		//view
		btView.setOnAction(e -> {
			try {
				viewStaff();
			} catch (SQLException ex) {
				Logger.getLogger(ViewUpdateStaffTable.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		//insert
		btInsert.setOnAction(e -> insertStudent());
		//update
		btUpdate.setOnAction(e -> updateStudent());
		//clear
		btDelete.setOnAction(e -> deleteStudent());

	}

	//view
	private void viewStaff() throws SQLException {
		String ssn = tfssn.getText();
		String courseID = tfCourseID.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();
		outputData.setText(null);

		if (ssn.equals("") && _firstname.equals("") && _lastname.equals("")) {
			// Execute a query
			String queryall = "select * from Student";
			ResultSet resultSet = statement.executeQuery(queryall);
			ResultSetMetaData rsmd1 = resultSet.getMetaData();
			int cols1 = rsmd1.getColumnCount();
			int rows1 = resultSet.getRow();
			int count1 = 0;
			//title
			for (int i = 1; i <= cols1; i++) {
				String outL1 = String.format("%-12s", rsmd1.getColumnName(i));
				outputData.appendText(outL1);
				System.out.printf(outL1);
			}
			outputData.appendText("\n");
			System.out.println();

			// Iterate through the result and print the student names
			while (resultSet.next()) {
				for (int i = 1; i <= cols1; i++) {
					String outF1 = String.format("%-12s", resultSet.getObject(i));
					outputData.appendText(outF1);
					System.out.printf(outF1);
				}
				outputData.appendText("\n");
				count1++;
				System.out.println();
			}
			labelStatus.setText(count1 + " found");
		} else {
			try {
				preparedStatement.setString(1, ssn);
				preparedStatement.setString(2, "%"+_firstname+"%");
				preparedStatement.setString(3, _lastname);

				ResultSet rset = preparedStatement.executeQuery();
				ResultSetMetaData rsmd = rset.getMetaData();
				int cols = rsmd.getColumnCount();
				int rows = rset.getRow();
				int count = 0;

				if (!rset.next()) {
					labelStatus.setText("Not found");
					System.out.println("Not found");
				} else {
					// output the title
					for (int i = 1; i <= cols; i++) {
						String outL = String.format("%-12s", rsmd.getColumnName(i));
						outputData.appendText(outL);
						System.out.printf(outL);
					}
					outputData.appendText("\n");
					System.out.println();

					rset.beforeFirst();// move the pointer to the default position (before first)

					// output the data content
					while (rset.next()) {
						for (int i = 1; i <= cols; i++) {
							String outF = String.format("%-12s", rset.getObject(i));
							outputData.appendText(outF);
							System.out.printf(outF);
						}
						outputData.appendText("\n");
						count++;
						System.out.println();
					}
					labelStatus.setText(count + " found");
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	//insert student
	private void insertStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();
		String _birth = tfBirthdate.getText();
		String _address = tfAddress.getText();
		String _zipcode = tfZipcode.getText();
		String _mi = tfMi.getText();
		String _phone = tfPhone.getText();
		String _deptID = tfDeptID.getText();
		ArrayList<String> _data = new ArrayList<>();
		_data.add(ssn);
		_data.add(_firstname);
		_data.add(_lastname);
		_data.add(_birth);
		_data.add(_address);
		_data.add(_zipcode);
		_data.add(_mi);
		_data.add(_phone);
		_data.add(_deptID);
		
		for(int i = 0; i < _data.size(); ++i){
			System.out.println("_data " + _data.get(i));
		}

		try {
			
			for(int i = 0; i < _data.size(); ++i){
				preparedStatementInsert.setString(i+1, _data.get(i));
			}
//			preparedStatementInsert.setString(1, ssn);
//			preparedStatementInsert.setString(2, _firstname);
//			preparedStatementInsert.setString(3, _mi);
//			preparedStatementInsert.setString(4, _lastname);
//			preparedStatementInsert.setString(5, _birth);
//			preparedStatementInsert.setString(6, _address);
//			preparedStatementInsert.setString(7, _phone);
//			preparedStatementInsert.setString(8, _zipcode);
//			preparedStatementInsert.setString(9, _deptID);
			// execute the query to populate data
			preparedStatementInsert.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// update student
	private void updateStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();

		try {
			preparedStatementUpdate.setString(1, _firstname);
			preparedStatementUpdate.setString(2, ssn);
			preparedStatementUpdate.setString(3, _lastname);

			// execute the query to populate data
			preparedStatementUpdate.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// delete student
	private void deleteStudent() {
		String ssn = tfssn.getText();
		String _firstname = tfFirstName.getText();
		String _lastname = tfLastName.getText();

		try {
			preparedStatementDelete.setString(1, ssn);
			preparedStatementDelete.setString(2, _firstname);
			preparedStatementDelete.setString(3, _lastname);

			// execute the query to populate data
			preparedStatementDelete.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// initialize
	private void initializeDB() {
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
//      Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded");

			// Establish a connection
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
//    ("jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl",
//     "scott", "tiger");
			System.out.println("Database connected");

			// Create a statement
			statement = connection.createStatement();

			//creat a query
//			String querystring = "select firstName, mi, "
//					+ "lastName, title, grade from Student, Enrollment, Course "
//					+ "where Student.ssn = ? or Enrollment.courseId = ? or Student.firstName = ?";
//					+ "and Enrollment.courseId = Course.courseId";

			String querystring = "select * from Student where Student.ssn = ?"
					+ "or Student.firstName = ? or Student.lastName = ?";
			
			String querystringlike = "select * from Student where Student.ssn like ?"
					+ "or Student.firstName like ? or Student.lastName like ?";
			
			// Create a statement
//			stmt = connection.createStatement();
			preparedStatement = connection.prepareStatement(querystringlike);

			// Create a preparedstatement for populating data
			String querystringInsert = "insert into Student(ssn,firstName,mi,lastName,birthDate,street,phone,zipCode,deptId) "
					+ "values(?,?,?,?,?,?,?,?,?)";
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

	//main
	public static void main(String[] args) {
		launch(args);
	}

}
