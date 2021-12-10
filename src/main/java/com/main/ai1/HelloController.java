package com.main.ai1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public TableView<StudentTableView> tableView;
    public TableColumn<StudentTableView,String> Groups;
    public TableColumn<StudentTableView,Integer> FirstChoice;
    public TableColumn<StudentTableView,Integer> SecondChoice;
    public TableColumn<StudentTableView,Integer> ThirdChoice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Groups.setCellValueFactory(new PropertyValueFactory<>("Groups"));
        FirstChoice.setCellValueFactory(new PropertyValueFactory<>("FirstChoice"));
        SecondChoice.setCellValueFactory(new PropertyValueFactory<>("SecondChoice"));
        ThirdChoice.setCellValueFactory(new PropertyValueFactory<>("ThirdChoice"));
        try {
            observableList.addAll(read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setItems(observableList);
    }
    ObservableList<StudentTableView> observableList= FXCollections.observableArrayList();

    public ArrayList<StudentTableView> read() throws FileNotFoundException {
        ArrayList<StudentTableView>studentarr=new ArrayList<>();
        StudentTableView []student;
        int count = 0;
        int count2 = 0;
        StudentTableView studentObjects[];
        Scanner sc = new Scanner(new File("C:\\Users\\Ameer\\Desktop\\AIProjects\\StudentsSelections.csv"));
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
                studentarr.add( new StudentTableView(groups1,first,second,Third));
        }
        return studentarr;
    }
    public boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}


