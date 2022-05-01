package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.User;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private EntityManagerFactory entityManagerFactory;
    private UserHibernateController userHibernateController;

    public LoginController() {
        userHibernateController = new UserHibernateController();
    }

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    void login(ActionEvent event) throws IOException {
        List<User> allUsersFromDatabase;
        allUsersFromDatabase = userHibernateController.getUserListFromDatabase();
        System.out.println("TEXT FIELDS: " + loginTextField.getText() + passwordTextField.getText());
        if(userHibernateController.checkIfUserExist(loginTextField.getText(),passwordTextField.getText())==true)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/hybernatekontrolinis/main-view.fxml"));
            root = (Parent)fxmlLoader.load();
            MainViewController mainViewController = fxmlLoader.getController();
            mainViewController.setCurrentUserId(userHibernateController.getUserIdByLoginAndPassword(loginTextField.getText(), passwordTextField.getText()));
            mainViewController.ShowCourses();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Courses");
            stage.show();
        }
        else
        {
            AllertBox.display("Error", "User not found, or password is incorrect");
        }
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/hybernatekontrolinis/registration-view.fxml"));
        root = (Parent)fxmlLoader.load();
        RegistrationController registrationController = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.show();
    }
}
