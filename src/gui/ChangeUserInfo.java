package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.User;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/** Pane for changing user's information
 * 
 * @author Mattias Larsson
 *
 */
public class ChangeUserInfo {
	private EntityManagerFactory emf;
	private EntityManager em;
	private User user;
	private List<User> userList;
	
	/** Constructor
	 * 
	 */
	public ChangeUserInfo() {}
	
	/** Constructor
	 * 
	 * @param user - an instance of User
	 */
	public ChangeUserInfo(User user) {
		this.user = user;
		userList = new ArrayList<>();
	}
	
	/** Shows the pane 
	 * 
	 * @return GridPane
	 */
	public GridPane showPane() {
		GridPane grid = new GridPane();
		emf = Persistence.createEntityManagerFactory("Testverktyg");
		em = emf.createEntityManager();
		// Hittar användaren
		Query findUser = em.createNamedQuery("loginByName");
		findUser.setParameter("uname", user.getUserName());
		userList = findUser.getResultList();
		user = userList.get(0);
		
		// Skapar grafiska komponenter
		Label lblUserName = new Label("Username: ");
		TextField txtUserName = new TextField();
		txtUserName.setText(user.getUserName());
		grid.add(lblUserName, 0, 0);
		grid.add(txtUserName, 1, 0);
		
		Label lblFirstName = new Label("First name: ");
		TextField txtFirstName = new TextField();
		txtFirstName.setText(user.getfName());
		grid.add(lblFirstName, 0, 1);
		grid.add(txtFirstName, 1, 1);
		
		Label lblLastName = new Label("Last name: ");
		TextField txtLastName = new TextField();
		txtLastName.setText(user.getlName());
		grid.add(lblLastName, 0, 2);
		grid.add(txtLastName, 1, 2);
		
		Label lblSSN = new Label("Social Security Number: "); // TODO
		TextField txtSSN = new TextField();
		txtLastName.setText(user.getSSN());
		grid.add(lblSSN, 0, 3);
		grid.add(txtSSN, 1, 3);
		
		Label lblEmail = new Label("E-mail: ");
		TextField txtEmail = new TextField();
		txtEmail.setText(user.getEmail());
		grid.add(lblEmail, 0, 4);
		grid.add(txtEmail, 1, 4);
		
		Label lblPhone = new Label("Phone number: ");
		TextField txtPhone = new TextField();
		txtPhone.setText(user.getPhone());
		grid.add(lblPhone, 0, 5);
		grid.add(txtPhone, 1, 5);
		
		Label lblStreet = new Label("Street: ");
		TextField txtStreet = new TextField();
		txtStreet.setText(user.getAddress());
		grid.add(lblStreet, 0, 6);
		grid.add(txtStreet, 1, 6);
		
		Label lblZip = new Label("Zipcode: ");
		TextField txtZip = new TextField();
		txtZip.setText((Integer.toString(user.getZip())));
		grid.add(lblZip, 0, 7);
		grid.add(txtZip, 1, 7);
		
		Label lblCity = new Label("City: ");
		TextField txtCity = new TextField();
		txtCity.setText(user.getCity());
		grid.add(lblCity, 0, 8);
		grid.add(txtCity, 1, 8);
		
		Button saveChanges = new Button("save changes");
		saveChanges.setOnAction(save -> {
			em.getTransaction().begin();
				user.setCity(txtCity.getText());
				user.setEmail(txtEmail.getText());
				user.setfName(txtFirstName.getText());
				user.setlName(txtLastName.getText());
				user.setPhone(txtPhone.getText());
				user.setSSN(txtSSN.getText()); // TODO
				user.setStreet(txtStreet.getText());
				user.setZip(Integer.parseInt(txtZip.getText()));
				user.setUserName(txtUserName.getText());
			em.getTransaction().commit();
			
		});
		Button changePassword = new Button("ChangePassword");
		changePassword.setOnAction(cpwd -> {
			Dialog<Pair<String, String>> pwDialog = new Dialog<>();
			pwDialog.setTitle("Change password");
			pwDialog.setHeaderText("Change password");
			
			ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
			pwDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
			
			GridPane gridPane = new GridPane();
			 TextField oldPw = new TextField();
			 oldPw.setPromptText("Old password");
			 TextField newPw = new TextField();
			 newPw.setPromptText("New password");
			 TextField confirmPw = new TextField();
			 confirmPw.setPromptText("Confirm new password");
			 
			 gridPane.add(oldPw, 0, 0);
			 gridPane.add(newPw, 0, 1);
			 gridPane.add(confirmPw, 0, 2);
			 
			 Node okButton = pwDialog.getDialogPane().lookupButton(okButtonType);
			 okButton.setDisable(true);
			 
			 confirmPw.textProperty().addListener(c -> {
				 if (confirmPw.getText().equals(newPw.getText())) {
					 okButton.setDisable(false);
				 }
			 });
			 
			 pwDialog.getDialogPane().setContent(gridPane);
			 pwDialog.setResultConverter(dialogButton -> {
				 if (dialogButton == okButtonType) {
					 return new Pair<>(oldPw.getText(), newPw.getText());
				 }
				 return null;
			 });
				 
			 
			 Optional<Pair<String, String>> result = pwDialog.showAndWait();
			 result.ifPresent(password -> {
				 if (password.getKey().equals(user.getPassword())) {
					 em.getTransaction().begin();
					 user.setPassword(password.getValue());
					 em.getTransaction().commit();
				 }
			 });
			 
		});
		
		grid.add(saveChanges, 3, 0);
		grid.add(changePassword, 4, 0);
		
		return grid;		
	}
}
