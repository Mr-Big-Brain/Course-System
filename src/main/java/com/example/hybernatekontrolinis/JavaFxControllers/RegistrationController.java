package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.Course;
import DataStructures.User;
import DataStructures.UserType;
import com.example.hybernatekontrolinis.AllertBox;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import com.example.hybernatekontrolinis.Start;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class RegistrationController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    UserHibernateController userHibernateController;
    EntityManagerFactory entityManagerFactory;

    public RegistrationController() {
        userHibernateController = new UserHibernateController();
    }
    private EntityManager getEntityManager()
    {

        return entityManagerFactory.createEntityManager();
    }

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField companyTextField;

    @FXML
    private Button registrateButton;

    @FXML
    private Button back;

    @FXML
    void goBackToLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/hybernatekontrolinis/login-view.fxml"));
        root = (Parent)fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    void registrate(ActionEvent event)
    {
        List<User> allUsersFromDataBase = userHibernateController.getUserListFromDatabase();
        boolean isUnique = true;
        for(int i=0;i<allUsersFromDataBase.size();i++)
        {
            if(allUsersFromDataBase.get(i).getLogin().equals(getUserFromRegistration().getLogin()))
            {
                isUnique = false;
                break;
            }
        }
        if(isUnique)
        {
            userHibernateController.create(getUserFromRegistration());
            AllertBox.display("Success", "You have been successfully registrated");
        }
        else
        {
            AllertBox.display("Allert", "There is user with your login. Create another one.");
        }
    }
    private User getUserFromRegistration()
    {
        if(companyTextField.getText()=="")
        {
            return new User(loginTextField.getText(), nameTextField.getText(),surnameTextField.getText(), passwordTextField.getText(), UserType.INDIVIDUAL);
        }
        else
        {
            return new User(loginTextField.getText(), nameTextField.getText(),surnameTextField.getText(), passwordTextField.getText(), UserType.COMPANY,companyTextField.getText());
        }

    }
}