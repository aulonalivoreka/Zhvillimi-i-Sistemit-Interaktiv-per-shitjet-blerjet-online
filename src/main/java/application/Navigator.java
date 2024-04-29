package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Navigator{

    public static void navigate(Stage stage, String page) {
        FXMLLoader loader=new FXMLLoader(
                Navigator.class.getResource(page)
        );
        try{
            Scene scene=new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void navigate(Event event, String page){
        Node node=(Node)event.getSource();
        Stage stage=(Stage)node.getScene().getWindow();
        navigate(stage,page);
    }
}