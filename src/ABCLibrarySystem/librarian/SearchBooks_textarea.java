package ABCLibrarySystem.librarian;

import ABCLibrarySystem.DB.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.event.MenuEvent;

public class SearchBooks_textarea extends LibrarianMenu {

	public static Stage searchbook = new Stage();

	private Label lbl_book_Id = new Label("ID");
	private Label lbl_book_Callno = new Label("Call No");
	private Label lbl_book_Name = new Label("Name");
	private Label lbl_book_Author = new Label("Author");
	private Label lbl_book_Publisher = new Label("Publisher");
	private Label lbl_book_Added_date = new Label("Added Date");
	private TextField tf_book_Id = new TextField();
	private TextField tf_book_Callno = new TextField();
	private TextField tf_book_Name = new TextField();
	private TextField tf_book_Author = new TextField();
	private TextField tf_book_Publisher = new TextField();
	private TextField tf_book_Added_date = new TextField();
	private Button btsearchbook = new Button("Search Book");

	private TextArea outputData = new TextArea();
	private Label labelStatus = new Label();

	public GridPane searchBooks() {
		GridPane paneForbook = new GridPane();
		paneForbook.setHgap(5);
		paneForbook.setVgap(5);
		paneForbook.add(lbl_book_Id, 0, 0);
		paneForbook.add(lbl_book_Callno, 0, 1);
		paneForbook.add(lbl_book_Name, 0, 2);
		paneForbook.add(lbl_book_Author, 0, 3);
		paneForbook.add(lbl_book_Publisher, 0, 4);
		paneForbook.add(lbl_book_Added_date, 0, 5);

		paneForbook.add(tf_book_Id, 1, 0);
		paneForbook.add(tf_book_Callno, 1, 1);
		paneForbook.add(tf_book_Author, 1, 2);
		paneForbook.add(tf_book_Name, 1, 3);
		paneForbook.add(tf_book_Publisher, 1, 4);
		paneForbook.add(tf_book_Added_date, 1, 5);
		paneForbook.add(btsearchbook, 1, 6);

		// foce the input to be numeric
		tf_book_Id.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					tf_book_Id.setText(oldValue);
				}
			}
		});

		btsearchbook.setOnAction(e -> {
			try {
				showBook();
			} catch (Exception ex) {
				Logger.getLogger(SearchBooks.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		paneForbook.setMinWidth(260);
		paneForbook.setPrefWidth(300);

		paneForbook.setPadding(new Insets(20, 10, 20, 10));

		return paneForbook;
	}

	//view
	private void showBook() throws SQLException, Exception {

		String _book_Id = tf_book_Id.getText();
//		String _book_Callno = tf_book_Callno.getText();
//		String _book_Author = tf_book_Author.getText();
//		String _book_Name = tf_book_Name.getText();
//		String _book_Publisher = tf_book_Publisher.getText();
//		String _book_Added_date = tf_book_Added_date.getText();
		System.out.println("search");

		outputData.setText(null);

		if (_book_Id.equals("")) {
			// Execute a query
			String queryall = "select * from books";
			ResultSet resultSet = DataBaseConnection.preparedStatementSearch.executeQuery(queryall);
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
				DataBaseConnection.preparedStatementSearch.setString(1, _book_Id);

				ResultSet rset = DataBaseConnection.preparedStatementSearch.executeQuery();
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

		outputData.setPadding(new Insets(20, 10, 20, 10));

		LibrarianMenu menu = new LibrarianMenu();

		menu.getPane().setCenter(searchBooks());
		menu.getPane().setRight(outputData);
		menu.getPane().setBottom(labelStatus);
		menu.start(LibrarianMenu.menuStage);

		double stageWidth = menu.primScreenBounds.getWidth() * 0.8;
		double stageHeight = menu.primScreenBounds.getHeight() * 0.7;

		outputData.setPrefSize(stageWidth * 0.6, stageHeight);

		LibrarianMenu.menuStage.setWidth(stageWidth);
		LibrarianMenu.menuStage.setX(menu.primScreenBounds.getWidth() / 2 - stageWidth / 2);

	}

}
