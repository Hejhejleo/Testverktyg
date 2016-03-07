package gui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.SchoolClass;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/** Administrates users
 * 
 * @author Mattias Larsson
 *
 */
public class AdminUser {
	
	private ObservableList<User> userList = FXCollections.observableArrayList();
	private ObservableList<SchoolClass> classList = FXCollections.observableArrayList();
	
	public AdminUser() {}
	
	public GridPane showPane() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Testverktyg");
		EntityManager em = emf.createEntityManager();
		
		GridPane adminUserPane = new GridPane();
		
		TableView<User> userTable = new TableView<>();
		userTable.setItems(userList);
		adminUserPane.add(userTable, 0, 0);
		
		ComboBox classCombo = new ComboBox();
		classCombo.setItems(classList);
		
		em.createNamedQuery("listschoolclasses").getResultList().forEach(schoolClass -> {
			classList.add((SchoolClass) schoolClass);
		});
		
		
		
		return adminUserPane;
	}
}
