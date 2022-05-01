package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.Course;
import DataStructures.User;
import com.example.hybernatekontrolinis.HibernateControllers.CourseHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FileHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FolderHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagePeopleController {
    public FolderHibernateController folderHibernateController;
    public CourseHibernateController courseHibernateController;
    public UserHibernateController userHibernateController;
    public FileHibernateController fileHibernateController;

    private Stage stage;
    private Parent root;
    private Scene scene;

    private int currentUserId;
    private int courseId;
    private String windowType;

    public ManagePeopleController() {
        folderHibernateController = new FolderHibernateController();
        courseHibernateController = new CourseHibernateController();
        userHibernateController = new UserHibernateController();
        fileHibernateController = new FileHibernateController();
    }


    @FXML
    private TableView<User> defaultUsersTable;

    @FXML
    private TableColumn<User, String> defaultLoginColumn;

    @FXML
    private TableColumn<User, String> defaultNameColumn;

    @FXML
    private TableColumn<User, String> defaultSurnameColumn;

    @FXML
    private TableView<User> specificUsersTable;

    @FXML
    private TableColumn<User, String> specificLoginColumn;

    @FXML
    private TableColumn<User, String> specificNameColumn;

    @FXML
    private TableColumn<User, String> specificSurnameColumn;

    @FXML
    private Label defaultUsersLabel;

    @FXML
    private Label specificUsersLabel;

    @FXML
    private Button fromSpecificToDefaultButton;

    @FXML
    private Button fromDefaultToSpecificButton;

    @FXML
    private Button backToCoursesButton;

    @FXML
    void goBackToCourses(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/hybernatekontrolinis/main-view.fxml"));
        root = (Parent)fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setCurrentUserId(currentUserId);
        mainViewController.ShowCourses();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void removeFromDefaultToSpecific(ActionEvent event) {
        if(getWindowType()=="manageModerators")
        {
            makeUserAdministrator(defaultUsersTable.getSelectionModel().getSelectedItem().getId(),getCourseId());
            showUsersAndModerators();
        }
        if(getWindowType()=="manageStudents")
        {
            makeUserStudent(defaultUsersTable.getSelectionModel().getSelectedItem().getId(),getCourseId());
            showUsersAndStudents();
        }
    }

    @FXML
    void removeFromSpecificToDefault(ActionEvent event) {
        if(getWindowType()=="manageModerators")
        {
            makeAdministratorUser(specificUsersTable.getSelectionModel().getSelectedItem().getId(),getCourseId());
            showUsersAndModerators();
        }
        if(getWindowType()=="manageStudents")
        {
            makeStudentUser(specificUsersTable.getSelectionModel().getSelectedItem().getId(),getCourseId());
            showUsersAndStudents();
        }

    }

    private void makeUserAdministrator(int userId, int courseId)
    {
        Course course = courseHibernateController.getCourseById(courseId);
        User user = userHibernateController.getUserById(userId);
        user.addModeratedCourse(courseHibernateController.getCourseById(courseId));
        userHibernateController.update(user);
    }
    private void makeAdministratorUser(int userId, int courseId)
    {
        Course course = courseHibernateController.getCourseById(courseId);
        User user = userHibernateController.getUserById(userId);
        user.removeModeratedCourse(courseHibernateController.getCourseById(courseId));
        userHibernateController.update(user);
    }
    private void makeUserStudent(int userId, int courseId)
    {
        Course course = courseHibernateController.getCourseById(courseId);
        course.addStudentToCourse(userHibernateController.getUserById(userId));
        courseHibernateController.update(course);
    }
    private void makeStudentUser(int userId, int courseId)
    {
        Course course = courseHibernateController.getCourseById(courseId);
        course.removeStudentFromCourse(userHibernateController.getUserById(userId));
        courseHibernateController.update(course);
    }
    public void showUsersAndModerators()
    {
        ObservableList<User> defaultUsers = FXCollections.observableList(userHibernateController.getUsersNotModerators(courseHibernateController.getCourseById(getCourseId())));
        defaultNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        defaultSurnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        defaultLoginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        defaultUsersTable.setItems(defaultUsers);

        ObservableList<User> specificUsers = FXCollections.observableList(userHibernateController.getModeratorsNotUsers(courseHibernateController.getCourseById(getCourseId())));
        specificNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        specificSurnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        specificLoginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        specificUsersTable.setItems(specificUsers);
    }
    public void showUsersAndStudents()
    {
        ObservableList<User> defaultUsers = FXCollections.observableList(userHibernateController.getUsersNotStudents(courseHibernateController.getCourseById(getCourseId())));
        defaultNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        defaultSurnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        defaultLoginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        defaultUsersTable.setItems(defaultUsers);

        ObservableList<User> specificUsers = FXCollections.observableList(userHibernateController.getStudentsNotUsers(courseHibernateController.getCourseById(getCourseId())));
        specificNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        specificSurnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        specificLoginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        specificUsersTable.setItems(specificUsers);
    }
    public void setDefaultUsersLabelText(String newLabelText) {
        defaultUsersLabel.setText(newLabelText);
    }

    public void setSpecificUsersLabelText(String newLabelText) {
        specificUsersLabel.setText(newLabelText);
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }
}
