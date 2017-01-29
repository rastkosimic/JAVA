package main;

import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    
    private double dragOffsetX;
    private double dragOffsetY;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlUrl = getClass().getClassLoader().getResource("view/add_package.fxml");
        GridPane root = FXMLLoader.<GridPane>load(fxmlUrl);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/customStyle.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
                dragOffsetX = event.getScreenX() - stage.getX();
                dragOffsetY = event.getScreenY() - stage.getY();
            }
        });
        
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - dragOffsetX);
                stage.setY(event.getScreenY() - dragOffsetY);
            }
        });
    }
    
    
    
}
