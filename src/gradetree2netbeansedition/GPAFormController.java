/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradetree2netbeansedition;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javafx.fxml.FXML;
import javafx.scene.control.Label;
 



/**
 *
 * @author AliMain
 */
public class GPAFormController {
    @FXML
    private Label weightedAverageGPA;
    
    @FXML
    private Label GPA4;
    
    @FXML
    private Label GradeLetter;
    
    public void setWeightedAverageGPA(String text ){
    weightedAverageGPA.setText(text);
    }
    public void setGPA4(String text ){
    GPA4.setText(text);
    }
     public void setGradeLetter(String text ){
    GradeLetter.setText(text);
    }
}
