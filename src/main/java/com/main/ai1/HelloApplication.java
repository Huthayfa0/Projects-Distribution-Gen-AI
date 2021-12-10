package com.main.ai1;
import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
       /* String path = "/home/huthayfa/PycharmProjects/AI1/src/main/python/main.py";
        ProcessBuilder pb = new ProcessBuilder("python3.8",path).inheritIO();
        Process p = pb.start();
        p.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while ((line = bfr.readLine()) != null) {
            System.out.println(line);
        }
*/
       launch();
    }

}