/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradetree2netbeansedition;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author AliMain
 */
public class GradeCalculatorV2 extends Application {
      @FXML
    private Slider categoryWeight;
          @FXML
    private Label lblCategoryWeight;
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        
    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
    
    
    
    
        Scene scene = new Scene(root);
    
        scene.getStylesheets().add("darkSnake.css");
        
        
        
        primaryStage.setTitle("Welcome To Grade Tree");
       primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("mainIcon.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        
        primaryStage.show();
    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
