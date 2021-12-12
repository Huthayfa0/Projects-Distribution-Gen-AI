package com.main.ai1;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Output {

    private SimpleLongProperty id;
    private SimpleStringProperty supervisor;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty Groups;

    public Output(Long id,String supervisor,String title , String description,String Groups) {
        this.id=new SimpleLongProperty(id);
        this.supervisor=new SimpleStringProperty(supervisor);
        this.title=new SimpleStringProperty(title);
        this.description=new SimpleStringProperty(description);
        this.Groups=new SimpleStringProperty(Groups);
    }

    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id=new SimpleLongProperty(id);
    }

    public String getSupervisor() {
        return supervisor.get();
    }

    public void setSupervisor(String supervisor) {
        this.supervisor=new SimpleStringProperty(supervisor);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title=new SimpleStringProperty(title);
    }

    public String getDescription() {
        return description.get();
    }


    public void setDescription(String description) {
        this.description=new SimpleStringProperty(description);
    }

    public String getGroups() {
        return Groups.get();
    }

    public void setGroups(String groups) {

        this.Groups=new SimpleStringProperty(groups);
    }

    public static ArrayList<Output> read() throws FileNotFoundException {

        ArrayList<Output>OUTPut_Project=new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {

            JSONObject a = (JSONObject) parser.parse(new FileReader("src/main/resources/com/main/ai1/json/Output.json"));
            JSONArray array1 = (JSONArray) a.get("distribution");
            JSONArray b = (JSONArray) parser.parse(new FileReader("src/main/resources/com/main/ai1/json/Students.json"));
            for (int i = 0; i <b.size(); i++) {
                JSONObject project = (JSONObject) array1.get(i);
                JSONObject studentgroup= (JSONObject) b.get(i);
                JSONArray names=(JSONArray) studentgroup.get("names");
                StringBuilder stringbuilder= new StringBuilder((String) names.get(0));
                for (int j = 1; j < names.size(); j++) {

                    stringbuilder.append(", ").append((String) names.get(j));


                }

                String line=stringbuilder.toString();

                Long id =Long.valueOf (String.valueOf((Number) project.get("id")));

                String supervisor = (String) project.get("supervisor");


                String title = (String) project.get("title");


                String description = (String) project.get("description");

                OUTPut_Project.add(new Output(id,supervisor,title , description,line));


            }

        }catch(ParseException | FileNotFoundException pe) {
            System.out.println(pe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OUTPut_Project;
    }
}
