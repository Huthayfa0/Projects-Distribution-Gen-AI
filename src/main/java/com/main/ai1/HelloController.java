package com.main.ai1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

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


        try {
            observableList.addAll(StudentTableView.read());
            observableListProject.addAll(ProjectTableView.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(observableList);
        Projectstudent.setItems(observableListProject);
    }

    ObservableList<StudentTableView> observableList = FXCollections.observableArrayList();
    ObservableList<ProjectTableView> observableListProject = FXCollections.observableArrayList();

}