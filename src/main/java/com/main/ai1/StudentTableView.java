package com.main.ai1;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
