package com.main.ai1;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
public class ProjectTableView {

    private SimpleLongProperty id;
    private SimpleStringProperty supervisor;
    private SimpleStringProperty title;
    private SimpleStringProperty description;


    public ProjectTableView(Long id,String supervisor,String title , String description) {
        this.id=new SimpleLongProperty(id);
        this.supervisor=new SimpleStringProperty(supervisor);
        this.title=new SimpleStringProperty(title);
        this.description=new SimpleStringProperty(description);
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

    public static ArrayList<ProjectTableView>  read() throws FileNotFoundException{

        ArrayList<ProjectTableView>projects=new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {

            JSONArray a = (JSONArray) parser.parse(new FileReader("C:\\Users\\Ameer\\Desktop\\AIProjects\\src\\main\\resources\\com\\main\\ai1\\json\\Projects.json"));

            for (Object o : a)
            {
                JSONObject project = (JSONObject) o;

                Long id = (Long) project.get("id");

                String supervisor = (String) project.get("supervisor");


                String title = (String) project.get("title");


                String description = (String) project.get("description");

                projects.add(new ProjectTableView(id,supervisor,title , description));
            }
        }catch(ParseException | FileNotFoundException pe) {
            System.out.println(pe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
