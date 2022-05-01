package com.example.hybernatekontrolinis.JavaFxControllers;

import DataStructures.File;
import DataStructures.Folder;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UniversalOneLineEditController {

    public FolderHibernateController folderHibernateController;
    public CourseHibernateController courseHibernateController;
    public UserHibernateController userHibernateController;
    public FileHibernateController fileHibernateController;

    private Stage stage;
    private Parent root;
    private Scene scene;
    private int currentUserId;
    private int courseId;
    private String whatAction;
    private int idForAction;
    private int idForAction2;


    public UniversalOneLineEditController() {
        folderHibernateController = new FolderHibernateController();
        courseHibernateController = new CourseHibernateController();
        userHibernateController = new UserHibernateController();
        fileHibernateController = new FileHibernateController();
    }

    @FXML
    private TextField universalTextField;

    @FXML
    private Label explainingLabel;

    @FXML
    private Button actionButton;

    @FXML
    private Button closeButton;

    @FXML
    void goToPreviousWindow(ActionEvent event) throws IOException {
        if(getWhatAction()=="addSubfolder"||getWhatAction()=="modifyFolder"||getWhatAction()=="addFile")
        {
            FXMLLoader fxmlLoader = new FXMLLoader(UniversalOneLineEditController.class.getResource("/com/example/hybernatekontrolinis/manage-course.fxml"));
            root = (Parent)fxmlLoader.load();
            ManageCourseController manageCourseController = fxmlLoader.getController();
            manageCourseController.setCurrentUserId(getCurrentUserId());
            manageCourseController.setCourseId(courseId);
            if(folderHibernateController.getFolderById(idForAction).getParentFolderId()==0)
            {
                manageCourseController.showFoldersAndFiles(courseId,true);
            }
            else
            {
                manageCourseController.showFoldersAndFiles(folderHibernateController.getFolderById(idForAction).getParentFolderId(),false);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(getWhatAction()=="modifyFile")
        {
            FXMLLoader fxmlLoader = new FXMLLoader(UniversalOneLineEditController.class.getResource("/com/example/hybernatekontrolinis/manage-course.fxml"));
            root = (Parent)fxmlLoader.load();
            ManageCourseController manageCourseController = fxmlLoader.getController();
            manageCourseController.setCurrentUserId(getCurrentUserId());
            manageCourseController.setCourseId(courseId);
            if(folderHibernateController.getFolderById(idForAction).getParentFolderId()==0)
            {
                manageCourseController.showFoldersAndFiles(courseId,true);
            }
            else
            {
                manageCourseController.showFoldersAndFiles(folderHibernateController.getFolderById(idForAction).getId(),false);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void performAction(ActionEvent event) {
        if(getWhatAction()=="addSubfolder")
        {
            addSubfolderAction(getIdForAction());
        }
        else if(getWhatAction()=="modifyFolder")
        {
            Folder folderToEdit = folderHibernateController.getFolderById(idForAction);
            folderToEdit.setTitle(universalTextField.getText());
            folderHibernateController.update(folderToEdit);
        }
        else if(getWhatAction()=="modifyFile")
        {
            File fileToEdit = fileHibernateController.getFileById(idForAction2);
            fileToEdit.setName(universalTextField.getText());
            fileHibernateController.update(fileToEdit);
        }
        else if(getWhatAction()=="addFile")
        {
            addFileAction(getIdForAction());
        }
        else
        {
            AllertBox.display("Error", "Error, unknown whatAction variable");
        }

    }
    private void addSubfolderAction(int fatherFolderId)
    {
        folderHibernateController.create(new Folder(fatherFolderId,universalTextField.getText()));
    }
    private void addFileAction(int fatherFolderId)
    {
        fileHibernateController.create(new File(universalTextField.getText(),fatherFolderId));
    }
    public void setActionButtonText(String text)
    {
        actionButton.setText(text);
    }
    public void setExplainingLabelName(String labelName)
    {
        this.explainingLabel.setText(labelName);
    }

    public void setUniversalTextFieldText(String text)
    {
        universalTextField.setText(text);
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

    public String getWhatAction() {
        return whatAction;
    }

    public void setWhatAction(String whatAction) {
        this.whatAction = whatAction;
    }
    public int getIdForAction() {
        return idForAction;
    }

    public void setIdForAction(int idForAction) {
        this.idForAction = idForAction;
    }
    public int getIdForAction2() {
        return idForAction2;
    }

    public void setIdForAction2(int idForAction2) {
        this.idForAction2 = idForAction2;
    }

}
