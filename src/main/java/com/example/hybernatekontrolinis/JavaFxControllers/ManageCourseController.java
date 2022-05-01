package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.Course;
import DataStructures.File;
import DataStructures.Folder;
import com.example.hybernatekontrolinis.AllertBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageCourseController {

    public FolderHibernateController folderHibernateController;
    public CourseHibernateController courseHibernateController;
    public UserHibernateController userHibernateController;
    public FileHibernateController fileHibernateController;

    private Stage stage;
    private Parent root;
    private Scene scene;
    private int currentUserId;
    private int courseId;



    private int folderId;



    public ManageCourseController() {
        folderHibernateController = new FolderHibernateController();
        courseHibernateController = new CourseHibernateController();
        userHibernateController = new UserHibernateController();
        fileHibernateController = new FileHibernateController();
    }

    @FXML
    private TableView<Folder> folderTableView;

    @FXML
    private TableColumn<Folder, String> folderNameColumn;

    @FXML
    private TableView<File> fileTableView;

    @FXML
    private TableColumn<File, String> fileNameColumn;

    @FXML
    private Button previousFolderButton;

    @FXML
    private Button openSubfoldersButton;

    @FXML
    private Button deleteFolderButton;

    @FXML
    private Button addSubfolderButton;

    @FXML
    private Button backToCoursesButton;

    @FXML
    private Button addFileButton;

    @FXML
    private Button deleteFileButton;

    @FXML
    private Button modifyFileButton;

    @FXML
    private Button modifyFolderButton;

    @FXML
    void addFile(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCourseController.class.getResource("/com/example/hybernatekontrolinis/universal-one-line-edit.fxml"));
        root = (Parent)fxmlLoader.load();
        UniversalOneLineEditController universalOneLineEditController = fxmlLoader.getController();
        universalOneLineEditController.setCurrentUserId(currentUserId);
        universalOneLineEditController.setCourseId(courseId);
        universalOneLineEditController.setIdForAction(folderTableView.getSelectionModel().getSelectedItem().getId());
        universalOneLineEditController.setExplainingLabelName("New file name:");
        universalOneLineEditController.setWhatAction("addFile");
        universalOneLineEditController.setActionButtonText("Create");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addSubfolder(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCourseController.class.getResource("/com/example/hybernatekontrolinis/universal-one-line-edit.fxml"));
        root = (Parent)fxmlLoader.load();
        UniversalOneLineEditController universalOneLineEditController = fxmlLoader.getController();
        universalOneLineEditController.setCurrentUserId(currentUserId);
        universalOneLineEditController.setCourseId(courseId);
        universalOneLineEditController.setIdForAction(folderTableView.getSelectionModel().getSelectedItem().getId());
        universalOneLineEditController.setExplainingLabelName("New folder name:");
        universalOneLineEditController.setWhatAction("addSubfolder");
        universalOneLineEditController.setActionButtonText("Create");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteFile(ActionEvent event) {
        int fileFolderId;
        fileFolderId = fileTableView.getSelectionModel().getSelectedItem().getFolderId();
        fileHibernateController.delete(fileTableView.getSelectionModel().getSelectedItem().getId());
        showFoldersAndFiles(fileFolderId,false);
    }

    @FXML
    void deleteFolder(ActionEvent event) {
        int folderToDeleteId = folderTableView.getSelectionModel().getSelectedItem().getId();
        int fatherFolderId = folderHibernateController.getFolderById(folderHibernateController.getFolderById(folderToDeleteId).getParentFolderId()).getId();
        if(fatherFolderId != 0)
        {
            deleteFolderAndFilesInside(folderToDeleteId);

            if(folderHibernateController.getAllSubFoldersList(fatherFolderId).size()==0&&fileHibernateController.getAllSubFilesList(fatherFolderId).size()==0)
            {
                showFoldersAndFiles(folderHibernateController.getFolderById(fatherFolderId).getParentFolderId(),false);
            }
            else
            {
                showFoldersAndFiles(fatherFolderId, false);
            }
        }
        else
        {
            AllertBox.display("Error", "Error, you cant delete main course folder");
        }




    }


    @FXML
    void goBackToCourses(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCourseController.class.getResource("/com/example/hybernatekontrolinis/main-view.fxml"));
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
    void modifyFile(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCourseController.class.getResource("/com/example/hybernatekontrolinis/universal-one-line-edit.fxml"));
        root = (Parent)fxmlLoader.load();
        UniversalOneLineEditController universalOneLineEditController = fxmlLoader.getController();
        universalOneLineEditController.setCurrentUserId(currentUserId);
        universalOneLineEditController.setCourseId(courseId);
        universalOneLineEditController.setIdForAction(fileTableView.getSelectionModel().getSelectedItem().getFolderId());
        universalOneLineEditController.setIdForAction2(fileTableView.getSelectionModel().getSelectedItem().getId());
        universalOneLineEditController.setExplainingLabelName("Edit file name:");
        universalOneLineEditController.setWhatAction("modifyFile");
        universalOneLineEditController.setActionButtonText("Modify");
        universalOneLineEditController.setUniversalTextFieldText(fileTableView.getSelectionModel().getSelectedItem().getName());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modifyFolder(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCourseController.class.getResource("/com/example/hybernatekontrolinis/universal-one-line-edit.fxml"));
        root = (Parent)fxmlLoader.load();
        UniversalOneLineEditController universalOneLineEditController = fxmlLoader.getController();
        universalOneLineEditController.setCurrentUserId(currentUserId);
        universalOneLineEditController.setCourseId(courseId);
        universalOneLineEditController.setIdForAction(folderTableView.getSelectionModel().getSelectedItem().getId());
        universalOneLineEditController.setExplainingLabelName("Edit folder name:");
        universalOneLineEditController.setWhatAction("modifyFolder");
        universalOneLineEditController.setActionButtonText("Modify");
        universalOneLineEditController.setUniversalTextFieldText(folderTableView.getSelectionModel().getSelectedItem().getTitle());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openChildFolders(ActionEvent event) {
        showFoldersAndFiles(folderTableView.getSelectionModel().getSelectedItem().getId(),false);
    }

    @FXML
    void openFatherFolder(ActionEvent event) {
        int parentFolderId;

        if(folderTableView.getItems().size()!=0)
        {
            parentFolderId = folderTableView.getItems().get(0).getParentFolderId();
        }
        else
        {
            parentFolderId = fileTableView.getItems().get(0).getFolderId();
        }
        if(folderHibernateController.getFolderById(parentFolderId).getParentFolderId()==0)
        {
            showFoldersAndFiles(getCourseId(),true);
        }
        else
        {
            if(folderTableView.getItems().size()!=0)
            {
                showFoldersAndFiles(folderHibernateController.getFolderById(folderTableView.getItems().get(0).getParentFolderId()).getParentFolderId(),false);
            }
            else
            {
                showFoldersAndFiles(folderHibernateController.getFolderById(fileTableView.getItems().get(0).getFolderId()).getParentFolderId(),false);

            }
        }


    }

    public void showFoldersAndFiles(int fatherFolderId, boolean isFirstFolder)
    {
        if(isFirstFolder==false)
        {
            ObservableList<Folder> folders = FXCollections.observableList(folderHibernateController.getAllSubFoldersList(fatherFolderId));
            folderNameColumn.setCellValueFactory(new PropertyValueFactory<Folder,String>("title"));
            ObservableList<File> files = FXCollections.observableList(fileHibernateController.getAllSubFilesList(fatherFolderId));
            fileNameColumn.setCellValueFactory(new PropertyValueFactory<File,String>("name"));
            if(folders.size()!=0 || files.size()!=0)
            {
                folderTableView.setItems(folders);
                fileTableView.setItems(files);
            }
            else
            {
                AllertBox.display("Warning", "There is no folders or files here");
            }
        }
        else
        {
            List<Folder> foldersToInsert = new ArrayList<>();
            Folder mainFolder;
            mainFolder = folderHibernateController.getFolderById(courseHibernateController.getCourseById(fatherFolderId).getMainFolderId());
            foldersToInsert.add(mainFolder);
            ObservableList<Folder> folders = FXCollections.observableList(foldersToInsert);
            folderNameColumn.setCellValueFactory(new PropertyValueFactory<Folder,String>("title"));
            if(folders.size()!=0)
            {
                folderTableView.setItems(folders);
            }
        }

    }

    void deleteFolderAndFilesInside(int folderId)
    {
        List<Folder> subFolders = folderHibernateController.getAllSubFoldersList(folderId);
        List<File> subFiles = fileHibernateController.getAllSubFilesList(folderId);
        if(subFolders.size()!=0)
        {
            for(int i=subFolders.size()-1;i>=0;i--)
            {
                deleteFolderAndFilesInside(subFolders.get(i).getId());
            }
        }
        if(subFiles.size()!=0)
        {
            for(int i=subFiles.size()-1;i>=0;i--)
            {
                fileHibernateController.delete(subFiles.get(i).getId());
            }
        }
        folderHibernateController.delete(folderId);

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

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
