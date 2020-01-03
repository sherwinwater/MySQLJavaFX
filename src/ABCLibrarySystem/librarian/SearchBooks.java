package ABCLibrarySystem.librarian;

import ABCLibrarySystem.DB.DataBaseConnection;
import ABCLibrarySystem.LibrarySystem;
import static ABCLibrarySystem.LibrarySystem.mainStage;
import ABCLibrarySystem.admin.AdminLogin;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
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
import javafx.util.Callback;
import javax.swing.event.MenuEvent;
import GUIExamples.DynamicTable;

public class SearchBooks extends LibrarianMenu {

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

	//TABLE VIEW AND DATA
	private ObservableList<ObservableList> data;
	private TableView tableview = new TableView();

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
	public void showBook() throws SQLException, Exception {
		LibrarianMenu menu = new LibrarianMenu();
		menuStage.close();

		tableview.getItems().clear();
		tableview.getColumns().clear();

		data = FXCollections.observableArrayList();

		String _book_Id = tf_book_Id.getText();
//		String _book_Callno = tf_book_Callno.getText();
//		String _book_Author = tf_book_Author.getText();
//		String _book_Name = tf_book_Name.getText();
//		String _book_Publisher = tf_book_Publisher.getText();
//		String _book_Added_date = tf_book_Added_date.getText();
		System.out.println("search");

		int cols;
		int rows;
		int count;
		TableColumn col = null;
		ObservableList<String> row = null;

		if (_book_Id.equals("")) {

			try {

				// Execute a query
				String queryall = "select * from books";
				ResultSet resultSet = DataBaseConnection.preparedStatementSearch.executeQuery(queryall);
				cols = resultSet.getMetaData().getColumnCount();
				rows = resultSet.getRow();
				count = 0;

				// Table columns added
				for (int i = 0; i < cols; i++) {
					final int j = i;
					col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
					col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
						public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
							return new SimpleStringProperty(param.getValue().get(j).toString());
						}
					});

					System.out.println("Column [" + i + "]  " + col.toString() + " " + resultSet.getMetaData().getColumnName(i + 1));
					tableview.getColumns().add(col);

				}

				// data added to table
				while (resultSet.next()) {
					//Iterate Row
					row = FXCollections.observableArrayList();
					for (int i = 1; i <= cols; i++) {
						//Iterate Column
						row.add(resultSet.getString(i));
					}
					System.out.println("Row [1] added " + row);
					data.add(row);
					count++;
				}
				labelStatus.setText(count + " found");
				//FINALLY ADDED TO TableView
				tableview.setItems(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				DataBaseConnection.preparedStatementSearch.setString(1, _book_Id);

				ResultSet resultSet = DataBaseConnection.preparedStatementSearch.executeQuery();
				cols = resultSet.getMetaData().getColumnCount();
				rows = resultSet.getRow();
				count = 0;

				if (!resultSet.next()) {
					labelStatus.setText("Not found");
					System.out.println("Not found");
				} else {
					// output the title
					for (int i = 0; i < cols; i++) {
						final int j = i;
						col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
						col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
						System.out.println("Column [" + i + "]  " + col.toString() + " " + resultSet.getMetaData().getColumnName(i + 1));
						tableview.getColumns().addAll(col);

					}

					resultSet.beforeFirst();// move the pointer to the default position (before first)

					// Data added to table
					while (resultSet.next()) {

						//Iterate Row
						row = FXCollections.observableArrayList();
						for (int i = 1; i <= cols; i++) {
							//Iterate Column
							row.add(resultSet.getString(i));
						}
						System.out.println("Row [1] added " + row);
						data.add(row);
						count++;
					}
					labelStatus.setText(count + " found");
					//FINALLY ADDED TO TableView
					tableview.setItems(data);
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		menu.getPane().setCenter(searchBooks());
		menu.getPane().setRight(tableview);
		menu.getPane().setBottom(labelStatus);
		menu.start(LibrarianMenu.menuStage);

		double stageWidth = menu.primScreenBounds.getWidth() * 0.7;
		double stageHeight = menu.primScreenBounds.getHeight() * 1;

		tableview.setPrefSize(stageWidth * 0.6, stageHeight);

		LibrarianMenu.menuStage.setWidth(stageWidth);
		LibrarianMenu.menuStage.setX(menu.primScreenBounds.getWidth() / 2 - stageWidth / 2);

	}

}
