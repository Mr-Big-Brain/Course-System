package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.Course;
import DataStructures.Folder;
import DataStructures.User;
import com.example.hybernatekontrolinis.AllertBox;
import com.example.hybernatekontrolinis.HibernateControllers.CourseHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FileHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FolderHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCourseController {
    FolderHibernateController folderHibernateController;
    CourseHibernateController courseHibernateController;
    UserHibernateController userHibernateController;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private int currentUserId;


    public CreateCourseController() {
        folderHibernateController = new FolderHibernateController();
        courseHibernateController = new CourseHibernateController();
        userHibernateController = new UserHibernateController();
    }
    @FXML
    private Button createButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button backToMainButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    void createNewCourse(ActionEvent event) throws IOException {
        Folder firstCourseFolder;
        Course newCourseToCreate;
        firstCourseFolder = new Folder(0,nameTextField.getText());
        folderHibernateController.create(firstCourseFolder);
        newCourseToCreate = new Course(nameTextField.getText(),descriptionTextArea.getText(),userHibernateController.getUserById(getCurrentUserId()),folderHibernateController.getLastCreatedFolderId());
        courseHibernateController.create(newCourseToCreate);
        AllertBox.display("Success", "Course was created");

        FXMLLoader fxmlLoader = new FXMLLoader(CreateCourseController.class.getResource("/com/example/hybernatekontrolinis/main-view.fxml"));
        root = (Parent)fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setCurrentUserId(getCurrentUserId());
        mainViewController.ShowCourses();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goBackToMainPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateCourseController.class.getResource("/com/example/hybernatekontrolinis/main-view.fxml"));
        root = (Parent)fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setCurrentUserId(getCurrentUserId());
        mainViewController.ShowCourses();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setcurrentUserId(int userId)
    {
        this.currentUserId = userId;
    }

}
