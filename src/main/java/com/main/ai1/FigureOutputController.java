package com.main.ai1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FigureOutputController implements Initializable {
    @FXML
    private VBox vboxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int count = 0;
        int count2 = 0;
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
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FiguresOutput.fxml"));

                vboxItems.getChildren().add(fxmlLoader.load());
                //vboxItems.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // v.getChildren().add( myImageView.setImage(myImage));

        }



    }


    private static  boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
