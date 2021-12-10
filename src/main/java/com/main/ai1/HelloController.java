package com.main.ai1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    private Button graph;

    public void GraphClicked() throws IOException {{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Graph.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }}


    public TableView<StudentTableView> tableView;
    public TableColumn<StudentTableView, String> Groups;
    public TableColumn<StudentTableView, Integer> FirstChoice;
    public TableColumn<StudentTableView, Integer> SecondChoice;
    public TableColumn<StudentTableView, Integer> ThirdChoice;

    public TableView<ProjectTableView>Projectstudent;
    public TableColumn<ProjectTableView, Long> iiID;
    public TableColumn<ProjectTableView, String> iiSupervisor;
    public TableColumn<ProjectTableView, String> iiTitle;
    public TableColumn<ProjectTableView, String> iiDescription;

    public TableView<Output>OutputProject;
    public TableColumn<Output, String> oGroups;
    public TableColumn<Output, Long> oID;
    public TableColumn<Output, String> oSupervisor;
    public TableColumn<Output, String> oTitle;
    public TableColumn<Output, String> oDescription;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Groups.setCellValueFactory(new PropertyValueFactory<>("Groups"));
        FirstChoice.setCellValueFactory(new PropertyValueFactory<>("FirstChoice"));
        SecondChoice.setCellValueFactory(new PropertyValueFactory<>("SecondChoice"));
        ThirdChoice.setCellValueFactory(new PropertyValueFactory<>("ThirdChoice"));

        iiID.setCellValueFactory(new PropertyValueFactory<>("id"));
        iiSupervisor.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        iiTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        iiDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

        oID.setCellValueFactory(new PropertyValueFactory<>("id"));
        oSupervisor.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        oTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        oDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        oGroups.setCellValueFactory(new PropertyValueFactory<>("Groups"));


        try {
            observableList.addAll(StudentTableView.read());
            observableListProject.addAll(ProjectTableView.read());
            observableListOutputProject.addAll(Output.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(observableList);
        Projectstudent.setItems(observableListProject);
        OutputProject.setItems(observableListOutputProject);
    }

    ObservableList<StudentTableView> observableList = FXCollections.observableArrayList();
    ObservableList<ProjectTableView> observableListProject = FXCollections.observableArrayList();
    ObservableList<Output> observableListOutputProject = FXCollections.observableArrayList();
}