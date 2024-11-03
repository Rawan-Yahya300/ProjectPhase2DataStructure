package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class MartyrTable {
	private ObservableList<Martyr> martyrs;
	private BorderPane root = new BorderPane();
	private LinkedList<Martyr> list;
	
	public MartyrTable(LinkedList<Martyr> list){
		this.list = list;
		 TableView<Martyr> table = new TableView<>();

         TableColumn<Martyr, String> nameCol = new TableColumn<>("Name");
         nameCol.setMinWidth(300);
         nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

         TableColumn<Martyr, Integer> ageCol = new TableColumn<>("Age");
         ageCol.setMinWidth(100);
         ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

         TableColumn<Martyr, Character> genderCol = new TableColumn<>("Gender");
         genderCol.setMinWidth(50);
         genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

         table.setItems(getMartyrs());
         table.getColumns().addAll(nameCol, ageCol, genderCol);
         root.setCenter(table);
	}
	 private ObservableList<Martyr> getMartyrs() {
	        martyrs = FXCollections.observableArrayList();
	        Node<Martyr> current = list.getHead();
	        while (current != null) {
	            martyrs.add(current.getData());
	            current = current.getNext();
	        }

	        return martyrs;
	    }
	public BorderPane getRoot() {
		return root;
	}
	public LinkedList<Martyr> getList() {
		return list;
	}
	 
}
