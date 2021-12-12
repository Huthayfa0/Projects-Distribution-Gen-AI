package com.main.ai1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FigureOutputController implements Initializable {
    static int i=0;
    @FXML
    private Label names;
    public void setLabel1(String x){
        names.setText(x);
    }
    public void setLabel2(String x){
        Project1.setText(x);
    }
    public void setLabel3(String x){
        Project2.setText(x);
    }
    public void setLabel4(String x){
        Project3.setText(x);
    }

    @FXML
    private Label Project1;
    @FXML
    private Label Project2;
    @FXML
    private Label Project3;
    @FXML
    private ImageView image;
    public void setimage(String x){

        Image xx=new Image(Objects.requireNonNull(getClass().getResource(x)).toString());
        image.setImage(xx);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Project1.setText(String.valueOf(i++));
        int count = 0;
        int count2 = 0;
        ArrayList<String> g=new ArrayList<>();
        ArrayList<String> g1=new ArrayList<>();
        ArrayList<String> g2=new ArrayList<>();
        ArrayList<String> g3=new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File("src/main/resources/com/main/ai1/StudentsSelections.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            g.add(groups1);g1.add(String.valueOf(first));g2.add(String.valueOf(second));g3.add(String.valueOf(Third));

            // v.getChildren().add( myImageView.setImage(myImage));

        }

           setLabel1(g.get(i));
            setLabel2(g1.get(i));
           setLabel3(g2.get(i));
          setLabel4(g3.get(i));
          setimage(String.format("images/GroubsFigures/Output%d.png",i));
        i++;

    }
    private static  boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
