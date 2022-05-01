package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.Course;
import DataStructures.User;
import com.example.hybernatekontrolinis.AllertBox;
import com.example.hybernatekontrolinis.HibernateControllers.CourseHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FolderHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{

    public FolderHibernateController folderHibernateController;
    public CourseHibernateController courseHibernateController;
    public UserHibernateController userHibernateController;

    private Stage stage;
    private Parent root;
    private Scene scene;
    private int currentUserId;

    public MainViewController() {
        folderHibernateController = new FolderHibernateController();
        courseHibernateController = new CourseHibernateController();
        userHibernateController = new UserHibernateController();
    }

    @FXML
    private TableView<Course> administratorTableView;

    @FXML
    private TableColumn<Course, String> adminNameColumn;

    @FXML
    private TableColumn<Course, String> adminDescriptionColumn;

    @FXML
    private Button addNewCourseButton;

    @FXML
    private Button deleteCourseButton;

    @FXML
    private Button manageCourseAdministratorButton;

    @FXML
    private Button manageStudentsAdministratorButton;

    @FXML
    private Button manageModeratorsButton;

    @FXML
    private Button manageStudentsModeratorButton;

    @FXML
    private Button manageCourseModeratorButton;

    @FXML
    private TableView<Course> moderatorTableView;

    @FXML
    private TableColumn<Course, String> moderatorNameColumn;

    @FXML
    private TableColumn<Course, String> ModeratorDescriptionColumn;

    @FXML
    void deleteCourse(ActionEvent event) {
        if(userHibernateController.getModeratorsNotUsers(administratorTableView.getSelectionModel().getSelectedItem()).size()!=0||userHibernateController.getStudentsNotUsers(administratorTableView.getSelectionModel().getSelectedItem()).size()!=0)
        {
            AllertBox.display("Allert", "You have students or moderators left. \n  You have to remove them from course first");
        }
        else
        {
            ManageCourseController manageCourseController = new ManageCourseController();
            manageCourseController.deleteFolderAndFilesInside(administratorTableView.getSelectionModel().getSelectedItem().getMainFolderId());
            courseHibernateController.delete(administratorTableView.getSelectionModel().getSelectedItem().getId());
            ShowCourses();
        }

    }

    @FXML
    void addNewCourse(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/create-course-view.fxml"));
        root = (Parent)fxmlLoader.load();
        CreateCourseController createCourseController = fxmlLoader.getController();
        createCourseController.setcurrentUserId(currentUserId);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void manageCourseAsAdministrator(ActionEvent event) throws IOException {
        int courseId;
        System.out.println("Course id is: " + administratorTableView.getSelectionModel().getSelectedItem().getId());
        courseId = administratorTableView.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/manage-course.fxml"));
        root = (Parent)fxmlLoader.load();
        ManageCourseController manageCourseController = fxmlLoader.getController();
        manageCourseController.setCurrentUserId(getCurrentUserId());
        manageCourseController.setCourseId(administratorTableView.getSelectionModel().getSelectedItem().getId());
        manageCourseController.showFoldersAndFiles(courseId,true);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course management");
        stage.show();
    }

    @FXML
    void manageCourseAsModerator(ActionEvent event) throws IOException {
        int courseId;
        System.out.println("Course id is: " + moderatorTableView.getSelectionModel().getSelectedItem().getId());
        courseId = moderatorTableView.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/manage-course.fxml"));
        root = (Parent)fxmlLoader.load();
        ManageCourseController manageCourseController = fxmlLoader.getController();
        manageCourseController.setCurrentUserId(getCurrentUserId());
        manageCourseController.setCourseId(moderatorTableView.getSelectionModel().getSelectedItem().getId());
        manageCourseController.showFoldersAndFiles(courseId,true);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course management");
        stage.show();
    }

    @FXML
    void manageCourseStudentsAsAdministrator(ActionEvent event) throws IOException {
        int courseId;
        System.out.println("Course id is: " + administratorTableView.getSelectionModel().getSelectedItem().getId());
        courseId = administratorTableView.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/manage-people-view.fxml"));
        root = (Parent)fxmlLoader.load();
        ManagePeopleController managePeopleController = fxmlLoader.getController();
        managePeopleController.setCurrentUserId(getCurrentUserId());
        managePeopleController.setCourseId(courseId);
        managePeopleController.setWindowType("manageStudents");
        managePeopleController.setSpecificUsersLabelText("Students:");
        managePeopleController.setDefaultUsersLabelText("Default application users:");
        managePeopleController.showUsersAndStudents();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Students");
        stage.show();
    }

    @FXML
    void manageModerators(ActionEvent event) throws IOException {
        int courseId;
        courseId = administratorTableView.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/manage-people-view.fxml"));
        root = (Parent)fxmlLoader.load();
        ManagePeopleController managePeopleController = fxmlLoader.getController();
        managePeopleController.setCurrentUserId(getCurrentUserId());
        managePeopleController.setCourseId(courseId);
        managePeopleController.setWindowType("manageModerators");
        managePeopleController.setSpecificUsersLabelText("Moderators:");
        managePeopleController.setDefaultUsersLabelText("Default application users:");
        managePeopleController.showUsersAndModerators();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Moderators");
        stage.show();
    }

    @FXML
    void manageStudentsAsModerator(ActionEvent event) throws IOException {
        int courseId;
        courseId = moderatorTableView.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("/com/example/hybernatekontrolinis/manage-people-view.fxml"));
        root = (Parent)fxmlLoader.load();
        ManagePeopleController managePeopleController = fxmlLoader.getController();
        managePeopleController.setCurrentUserId(getCurrentUserId());
        managePeopleController.setCourseId(courseId);
        managePeopleController.setWindowType("manageStudents");
        managePeopleController.setSpecificUsersLabelText("Students:");
        managePeopleController.setDefaultUsersLabelText("Default application users:");
        managePeopleController.showUsersAndStudents();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Students");
        stage.show();
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void ShowCourses()
    {
        ObservableList<Course> administratedCourses = FXCollections.observableList(courseHibernateController.getAdministratedByUserIdCourses(currentUserId));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("title"));
        adminDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("description"));
        if(administratedCourses.size()!=0)
        {
            administratorTableView.setItems(administratedCourses);
        }


        List<Course> moderatedCourses = courseHibernateController.getAllCourseListFromDataBase();
        boolean isModerator = false;
        for(int i=moderatedCourses.size()-1;i>=0;i--)
        {
            isModerator = false;
            for(int j=0;j<moderatedCourses.get(i).getModerators().size();j++)
            {
                if(moderatedCourses.get(i).getModerators().get(j).getId()==currentUserId)
                {
                    isModerator = true;
                    break;
                }
            }
            if(isModerator == false)
            {
                moderatedCourses.remove(i);
            }
        }

        ObservableList<Course> moderatedCoursesForTable = FXCollections.observableList(moderatedCourses);
        moderatorNameColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("title"));
        ModeratorDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("description"));

        if(moderatedCoursesForTable.size()!=0)
        {
            moderatorTableView.setItems(moderatedCoursesForTable);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
//String name, int authorCount, String journalName, Float price, Boolean isPaid, LocalDate publicationDate