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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;


public class HelloController implements Initializable {

    @FXML
    private Button graph;
    @FXML
    private Button Figures;
    public void GraphClicked() throws IOException {{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Graph.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }}

    public void FiguresClicked() throws IOException {{

        int count = 0;
        int count2 = 0;
        Scanner sc = new Scanner(new File("src/main/resources/com/main/ai1/StudentsSelections.csv"));
        sc.nextLine();
        String groups1="";
        int first=0;
        int second=0;
        int Third=0;
        while (sc.hasNext())  //returns a boolean value
        {
            groups1="";
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String str;
            String[] strings;
            str = sc.nextLine().replaceAll(String.valueOf('"'), "");
            str = str.strip();

            strings = str.split(",");
            for (int i = 0; i < strings.length; i++) {
                if (isNumeric(strings[i])) {
                    if (count2 == 0) {
                        first= Integer.parseInt(strings[i]);
                        count2++;
                    } else if (count2 == 1){
                        second= Integer.parseInt(strings[i]);
                        count2++;
                    }else if (count2 == 2){
                        Third= Integer.parseInt(strings[i]);
                        count2++;
                        count2=0;
                    }
                }else if (strings[i].isEmpty()==false){

                    groups1+=strings[i]+",";
                }
            }


            Label l=new Label(groups1 + "  " + first + "  " + second +"  " +Third);

           // v.getChildren().add( myImageView.setImage(myImage));

        }




    }}
    private static  boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
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