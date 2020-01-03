package ABCLibrarySystem.librarian;

import ABCLibrarySystem.DB.DataBaseConnection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AddBooks extends LibrarianMenu {

	public static Stage addbook = new Stage();
	private Label lbl_book_Id = new Label("ID");
	private Label lbl_book_Callno = new Label("Call No");
	private Label lbl_book_Name = new Label("Name");
	private Label lbl_book_Author = new Label("Author");
	private Label lbl_book_Publisher = new Label("Publisher");
	private Label lbl_book_Quantity = new Label("Quantity");
	private Label lbl_book_Issued = new Label("Issued");
	private Label lbl_book_Added_date = new Label("Added Date");
	private TextField tf_book_Id = new TextField();
	private TextField tf_book_Callno = new TextField();
	private TextField tf_book_Name = new TextField();
	private TextField tf_book_Author = new TextField();
	private TextField tf_book_Publisher = new TextField();
	private TextField tf_book_Quantity = new TextField();
	private TextField tf_book_Issued = new TextField();
	private TextField tf_book_Added_date = new TextField();
	private Button btAddbook = new Button("Add Book");

	public GridPane addBooks(){
		GridPane paneForbook = new GridPane();
		paneForbook.setHgap(5);
		paneForbook.setVgap(5);
		paneForbook.add(lbl_book_Id, 0, 0);
		paneForbook.add(lbl_book_Callno, 0, 1);
		paneForbook.add(lbl_book_Name, 0, 2);
		paneForbook.add(lbl_book_Author, 0, 3);
		paneForbook.add(lbl_book_Publisher, 0, 4);
		paneForbook.add(lbl_book_Quantity, 0, 5);
		paneForbook.add(lbl_book_Issued, 0, 6);
		paneForbook.add(lbl_book_Added_date, 0, 7);

		paneForbook.add(tf_book_Id, 1, 0);
		paneForbook.add(tf_book_Callno, 1, 1);
		paneForbook.add(tf_book_Author, 1, 2);
		paneForbook.add(tf_book_Name, 1, 3);
		paneForbook.add(tf_book_Publisher, 1, 4);
		paneForbook.add(tf_book_Quantity, 1, 5);
		paneForbook.add(tf_book_Issued, 1, 6);
		paneForbook.add(tf_book_Added_date, 1, 7);
		paneForbook.add(btAddbook, 1, 8);
		
		btAddbook.setAlignment(Pos.CENTER_RIGHT);
		
				// foce the input to be numeric
		tf_book_Id.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					tf_book_Id.setText(oldValue);
				}
			}
		});

		btAddbook.setOnAction(e -> addbook());
		
		paneForbook.setPadding(new Insets(20, 10, 20, 10));

		
		return paneForbook;
	}
	
	//add book
	private void addbook() {
		String _book_Id = tf_book_Id.getText();
		String _book_Callno = tf_book_Callno.getText();
		String _book_Author = tf_book_Author.getText();
		String _book_Name = tf_book_Name.getText();
		String _book_Publisher = tf_book_Publisher.getText();
		String _book_Quantity = tf_book_Quantity.getText();
		String _book_Issued = tf_book_Issued.getText();
		String _book_Added_date = tf_book_Added_date.getText();

		try {
			DataBaseConnection.preparedStatementInsert.setString(1, _book_Id);
			DataBaseConnection.preparedStatementInsert.setString(2, _book_Callno);
			DataBaseConnection.preparedStatementInsert.setString(3, _book_Author);
			DataBaseConnection.preparedStatementInsert.setString(4, _book_Name);
			DataBaseConnection.preparedStatementInsert.setString(5, _book_Publisher);
			DataBaseConnection.preparedStatementInsert.setString(6, _book_Quantity);
			DataBaseConnection.preparedStatementInsert.setString(7, _book_Issued);
			DataBaseConnection.preparedStatementInsert.setString(8, _book_Added_date);
			// execute the query to populate data
			DataBaseConnection.preparedStatementInsert.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
