package com.main.ai1;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentTableView {
private SimpleStringProperty Groups;
private SimpleIntegerProperty FirstChoice;
private SimpleIntegerProperty SecondChoice;
private SimpleIntegerProperty ThirdChoice;

    public StudentTableView(String groups,int FirstChoice,int SecondChoice , int ThirdChoice) {
        this.Groups=new SimpleStringProperty(groups);
        this.FirstChoice=new SimpleIntegerProperty(FirstChoice);
        this.SecondChoice=new SimpleIntegerProperty(SecondChoice);
        this.ThirdChoice=new SimpleIntegerProperty(ThirdChoice);
    }

    public String getGroups() {
        return Groups.get();
    }

    public  static ArrayList<StudentTableView> read() throws FileNotFoundException {
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
    private static  boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }



    public void setGroups(String groups) {

        this.Groups=new SimpleStringProperty(groups);
    }

    public int getFirstChoice() {
        return FirstChoice.get();
    }


    public void setFirstChoice(int firstChoice) {
      this.FirstChoice=new SimpleIntegerProperty(firstChoice);
    }

    public int getSecondChoice() {
        return SecondChoice.get();
    }


    public void setSecondChoice(int secondChoice) {
        this.SecondChoice= new SimpleIntegerProperty(secondChoice);
    }

    public int getThirdChoice() {
        return ThirdChoice.get();
    }



    public void setThirdChoice(int thirdChoice) {
        this.ThirdChoice= new SimpleIntegerProperty(thirdChoice);
    }
}
