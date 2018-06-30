package gradetree2netbeansedition;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/




import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import static javafx.scene.control.SelectionMode.SINGLE;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author AliMain
 */
public class mainFormController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TreeTableView <Mark> mainTreeView;
    
    @FXML
    private TreeTableColumn<Mark , String> cItem;
    
    @FXML
    private TreeTableColumn<Mark, Number> cGrade;
    
    @FXML
    private TreeTableColumn<Mark , Number> cWeight;
    
    @FXML
    private TextField SAT;
    
    @FXML
    private TextField courseName;
    
    @FXML
    private Slider courseWeight;
    
    @FXML
    private TextField categoryName;
    
    @FXML
    private Slider categoryWeight;
    
    @FXML
    private TextField itemName;
    
    @FXML
    private Slider itemWeight;
    
    @FXML
    private Label lblCategoryWeight;
    
    @FXML
    private Label lblItemWeight;
    
    @FXML
    private Label lblItemGrade;
    
    @FXML
    private Label SmartAverage;
    
    @FXML
    private CheckBox autoWeightEnabled;
    
    @FXML
    private Label weightedAverageGPA;
    
    @FXML
    private Label GPA4;
    
    @FXML
    private Label GradeLetter;
    
    TreeItem<Mark> root = new TreeItem<>(new Mark("Item",0.0,0.0));
    
    private String currentFilePath = "";
    
    class Mark{
        private SimpleStringProperty name;
        private SimpleDoubleProperty grade;
        private SimpleDoubleProperty weight;
        
        public Mark(String name, Double weight,Double grade) {
            this.name = new SimpleStringProperty(name);
            this.grade = new SimpleDoubleProperty(grade);
            this.weight = new SimpleDoubleProperty(weight);
        }
        
        public SimpleStringProperty getName() {
            return name;
        }
        
        
        
        public SimpleDoubleProperty getGrade() {
            return grade;
        }
        
        
        
        public SimpleDoubleProperty getWeight() {
            return weight;
        }
        
        
        
    }
    
    @FXML
            void addCourse(){
                root.getChildren().add(new TreeItem(new Mark(courseName.textProperty().getValue(),courseWeight.getValue(),0.0)));
                treeViewChanged();
            }
            
            @FXML
            void addCategory() {
                
                
                mainTreeView.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem(new Mark(categoryName.textProperty().getValue(),categoryWeight.getValue(),0.0)));
                
                
                treeViewChanged();
            }
            
            @FXML
            void addItem() {
                mainTreeView.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem(new Mark(itemName.textProperty().getValue(),itemWeight.getValue(),Double.parseDouble(SmartAverage.getText().split(" ")[1]))));
                treeViewChanged();
            }
            
            @FXML
            void updateMyMarks() {
                treeViewChanged();
            }
            
            
            @FXML
            void open() throws IOException{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Mark File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Grade File Type(*.gft)", "*.gft"));
                  final File file = new File(".");
                fileChooser.setInitialDirectory(file.getAbsoluteFile());
               
               
                File myFile = fileChooser.showOpenDialog(courseName.getScene().getWindow());
                
                if (myFile != null){
                    generalOpen(myFile.getAbsolutePath());
                    currentFilePath = myFile.getAbsolutePath();
                }
                
            }
            
            @FXML
            void saveAs() throws IOException{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Mark File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Grade File Type(*.gft)", "*.gft"));
                File myFile = fileChooser.showSaveDialog(courseName.getScene().getWindow());
                
                if (myFile != null){
                    generalSave(myFile.getAbsolutePath());
                }
                
            }
            @FXML
            void save() throws IOException{
                
                if ("".equals(currentFilePath)){
                    saveAs();
                }else{
                    generalSave(currentFilePath);
                }
            }
            
            
            @FXML
           void showAbout(){
               
           }
           @FXML
            void autoWeight(){
                if (autoWeightEnabled.isSelected()){
                    itemWeight.setDisable(true);
                }else{
                    itemWeight.setDisable(false);
                }
                
            }
            @FXML
            void close(){
                Stage stage = (Stage)itemWeight.getScene().getWindow();
                stage.close();
            }
            void generalSave(String fileName) throws IOException{
                
                
                
                
                String str = recursiveOutput(root,0);
                
                
                
                
                Path path = Paths.get(fileName);
                byte[] strToBytes = str.getBytes();
                
                Files.write(path, strToBytes);
                
                
            }
            
            void generalOpen (String fileName) throws IOException{
                Path filePath = Paths.get(fileName);
                Scanner scanner = new Scanner(filePath);
     
                Vector<String> items = new Vector<>();
                
                
                
                while (scanner.hasNextLine()) {
                    items.add(scanner.nextLine());
                }
                TreeItem<Mark>  tempRoot= new TreeItem<>(new Mark("Item",0.0,0.0));
                tempRoot = refluxInput(tempRoot, items,0,0);
                expandTreeView(tempRoot);
                mainTreeView.setRoot(tempRoot);
                
                root = tempRoot;
                treeViewChanged();
                
            }
            private void expandTreeView(TreeItem<?> item){
                if(item != null && !item.isLeaf()){
                    item.setExpanded(true);
                    for(TreeItem<?> child:item.getChildren()){
                        expandTreeView(child);
                    }
                }
            }
            
            
            String recursiveOutput(TreeItem<Mark> node, int depth){
                String nodeOut = "";
                
                if (node.isLeaf()){
                    return  node.getValue().getName().getValue() + "~" + node.getValue().getWeight().getValue() + "~" + node.getValue().getGrade().getValue() + "\n";
                } else {
                    
                    nodeOut =  node.getValue().getName().getValue() + "~" + node.getValue().getWeight().getValue() + "~" + node.getValue().getGrade().getValue() + "\n";
                    for (TreeItem<Mark> myChildren : node.getChildren()){
                        for (int i = 0; i < depth; i++){
                            nodeOut += ">";
                        }
                        nodeOut += recursiveOutput(myChildren, depth+1);
                        
                    }
                    return nodeOut;
                }
                
            }
            
            TreeItem<Mark> refluxInput(TreeItem<Mark> startingNode, Vector<String> items, int index, int beginDepth){
                int newDepth = 0;
                int oldDepth = beginDepth;
                TreeItem<Mark> node = startingNode;
                for (int i = 1; i < items.size(); i++){
                    newDepth = items.get(index+i).length() -  items.get(index+i).replaceAll(">", "").length();
                    
                    String item = items.get(index+i).replaceAll(">", "");
                    
                    TreeItem<Mark> myNode = new TreeItem<>(new Mark(item.split("~")[0], Double.parseDouble(item.split("~")[1]) ,Double.parseDouble(item.split("~")[2])));
                    
                    while (newDepth >= 0){
                        if (newDepth == oldDepth+1){
                            
                            node =  node.getChildren().get(node.getChildren().size() -1);
                            node.getChildren().add(myNode);
                            break;
                        }else if (newDepth == oldDepth){
                            
                            node.getChildren().add(myNode);
                            break;
                        }else if (newDepth < oldDepth){
                            
                            node = node.getParent();
                            
                            oldDepth--;
                            
                        }
                        
                    }
                    oldDepth = newDepth;
                }
                while (node.getParent() != null){
                    node = node.getParent();
                }
                return node;
                
            }
            
            
            
            void treeViewChanged(){
                processMarks(mainTreeView.getRoot());
                
            }
            
            void processMarks(TreeItem<Mark> node){
                
                if (node.isLeaf() != true){
                    node.getChildren().forEach((myMark) -> {
                        processMarks(myMark);
                    });
                      double updatedGrade = 0;
                      double leftOverWeight = 0;
                      for (TreeItem<Mark> myMark:node.getChildren() ){
                                 updatedGrade += myMark.getValue().getGrade().getValue() * (myMark.getValue().getWeight().getValue()/100);
                                   leftOverWeight +=(myMark.getValue().getWeight().getValue()/100);
                     };
                     updatedGrade = Math.round(updatedGrade/leftOverWeight * 100.0)/100.0;
                     Mark updatedMark = new Mark(node.getValue().getName().getValue(),node.getValue().getWeight().getValue(),updatedGrade);

                    node.setValue(updatedMark);
                }else {
                    double updatedGrade = 0;
                    double totalGrade = 0;
                    double leftOverWeight = 0;
                    for (TreeItem<Mark> myMark:node.getParent().getChildren() ){
                        updatedGrade += myMark.getValue().getGrade().getValue() * (myMark.getValue().getWeight().getValue()/100);
                        leftOverWeight +=(myMark.getValue().getWeight().getValue()/100);
                    }
                 
                    updatedGrade = Math.round(updatedGrade/leftOverWeight * 100.0)/100.0;
                    Mark updatedMark = new Mark(node.getParent().getValue().getName().getValue(),node.getParent().getValue().getWeight().getValue(),updatedGrade);
                    node.getParent().setValue(updatedMark);
                }
                
            }
            
            
            
            @FXML
            void removeItem() {
                TreeItem c = mainTreeView.getSelectionModel().getSelectedItem();
                c.getParent().getChildren().remove(c);
                treeViewChanged();
            }
            @FXML
            void GPAForm() throws IOException{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GPAForm.fxml"));
                Parent root1 =loader.load() ;
                
                Stage stage = new Stage();
                stage.setTitle("Your GPA");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("mainIcon.png")));
                Scene scene = new Scene(root1);
                scene.getStylesheets().add("darkSnake.css");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                
                
                
                
                double totalgrade = 0;
                double totalWeight = 0;
                TreeItem<Mark> grades = mainTreeView.getRoot();
                for (TreeItem<Mark> subjectGrade : grades.getChildren()){
                    totalgrade += subjectGrade.getValue().getGrade().getValue() * subjectGrade.getValue().getWeight().getValue();
                    totalWeight += subjectGrade.getValue().getWeight().getValue();
                }
                double myAverage = totalgrade/totalWeight;
                
             
                GPAFormController GPAForm =  loader.getController();
                GPAForm.setWeightedAverageGPA(String.valueOf(myWeightedAverage(myAverage)));
                GPAForm.setGPA4(String.valueOf(myGPA(myAverage)));
                GPAForm.setGradeLetter(gradeLetter(myAverage));
            }
            
            private double myWeightedAverage(double grade){
                
                return Math.round(grade*100.0)/100.0 ;
            }
            
            private double myGPA(double grade){
                grade = Math.round(grade);
                double GPA = grade >= 90 ? 4
                        : grade >= 85 ?  3.9
                        : grade >= 80 ?  3.7
                        : grade >= 77 ?  3.3
                        : grade >=73 ? 3
                        : grade >=70 ? 2.7
                        : grade >=67 ? 2.3
                        : grade >=63 ? 2
                        : grade >=60 ? 1.7
                        : grade >=57 ? 1.3
                        : grade >=53 ? 1
                        : grade >=50 ? 0.7
                        : grade >=40 ? 0:0
                        ;
                return GPA ;
                
            }
            private String gradeLetter(double grade){
                grade = Math.round(grade);
                String GPA = grade >= 90 ? "A+"
                        : grade >= 85 ?  "A"
                        : grade >= 80 ?  "A"
                        : grade >= 77 ?  "B"
                        : grade >=73 ? "B"
                        : grade >=70 ? "B"
                        : grade >=67 ? "C"
                        : grade >=63 ? "C"
                        : grade >=60 ? "C"
                        : grade >=57 ? "D"
                        : grade >=53 ? "D"
                        : grade >=50 ? "D"
                        : grade >=40 ? "E":"F"
                        ;
                return GPA;
                
                
                
            }
            @Override
            public void initialize(URL url, ResourceBundle rb) {
                
                
                
                
                mainTreeView.getSelectionModel().setSelectionMode(SINGLE);
                
                cItem.setCellValueFactory((TreeTableColumn.CellDataFeatures<Mark, String> param) -> param.getValue().getValue().getName());
                cGrade.setCellValueFactory((TreeTableColumn.CellDataFeatures<Mark, Number> param) -> param.getValue().getValue().getGrade());
                cWeight.setCellValueFactory((TreeTableColumn.CellDataFeatures<Mark, Number> param) -> param.getValue().getValue().getWeight());
                
                mainTreeView.setRoot(root);
                mainTreeView.setShowRoot(false);
                
                categoryWeight.valueProperty().addListener(new ChangeListener<Number>() {
                    
                    @Override
                    public void changed(ObservableValue<? extends Number> arg0,
                            Number arg1, Number arg2) {
                        
                        lblCategoryWeight.setText("Category Weight: " +Math.round(categoryWeight.getValue()*100.0)/100.0  ); //new value
                    }
                    
                });
                itemWeight.valueProperty().addListener(new ChangeListener<Number>() {
                    
                    @Override
                    public void changed(ObservableValue<? extends Number> arg0,
                            Number arg1, Number arg2) {
                        
                        lblItemWeight.setText("Item Weight: " +Math.round(itemWeight.getValue()*100.0)/100.0  ); //new value
                    }
                    
                });
                
                
                
                SAT.textProperty().addListener((observable, oldValue, newValue) -> {
                    String[] marks =  newValue.split("\\+");
                    double totalMarks = 0;
                    double totalWeight = 0;
                    double totalNumberOfMarks = marks.length;
                    boolean doCalculate = true;
                    
                    for (String mark : marks) {
                        if (mark.contains("/")) {
                            if (mark.split("/").length < 2 || mark.split("/").length > 2 ) {
                                doCalculate = false;
                            } else if (mark.split("/").length == 2){
                                
                                if (mark.split("/")[1].contains("*")){
                                    if ( mark.split("/")[1].split("\\*").length < 2 || mark.split("/")[1].split("\\*").length > 2 ){
                                        doCalculate = false;
                                    }else if (mark.split("/")[1].split("\\*").length == 2){
                                        
                                        
                                        boolean isNumeric = true;
                                        double numNum = 0, numDen = 0, weight = 0;
                                        try {
                                            numNum = Double.parseDouble(mark.split("/")[0]);
                                            numDen = Double.parseDouble(mark.split("/")[1].split("\\*")[0]);
                                            weight = Double.parseDouble(mark.split("/")[1].split("\\*")[1]);
                                        } catch (NumberFormatException e) {
                                            isNumeric = false;
                                            doCalculate = false;
                                        }
                                        if(isNumeric){
                                            totalMarks += (numNum/numDen)*weight*100;
                                            totalWeight += Math.round(weight*1000.0)/1000.0;
                                        }
                                        
                                        
                                        
                                    }
                                    
                                }else {
                                    boolean isNumeric = true;
                                    double numNum = 0, numDen = 0;
                                    try {
                                        numNum = Double.parseDouble(mark.split("/")[0]);
                                        numDen = Double.parseDouble(mark.split("/")[1]);
                                    } catch (NumberFormatException e) {
                                        isNumeric = false;
                                        doCalculate = false;
                                    }
                                    if(isNumeric){
                                        totalMarks += (numNum/numDen)*100;
                                    }
                                    
                                }
                                
                                
                                
                            }
                        }else if (mark.contains("*") && !mark.contains("/")){
                            
                            
                            if (mark.split("\\*").length == 2){
                                boolean isNumeric = true;
                                double num = 0;
                                double weight = 0;
                                try {
                                    num = Double.parseDouble(mark.split("\\*")[0]);
                                    weight = Double.parseDouble(mark.split("\\*")[1]);
                                } catch (NumberFormatException e) {
                                    isNumeric = false;
                                    doCalculate = false;
                                }
                                if(isNumeric){
                                    totalMarks += num*weight;
                                    totalWeight += Math.round(weight*1000.0)/1000.0;
                                }
                            }else if ( mark.split("\\*").length < 2 || mark.split("\\*").length > 2 ){
                                doCalculate = false;
                            }
                            
                            
                            
                        }
                        
                        
                        else {
                            
                            boolean isNumeric = true;
                            double num = 0;
                            try {
                                num = Double.parseDouble(mark);
                            } catch (NumberFormatException e) {
                                isNumeric = false;
                                doCalculate = false;
                            }
                            if(isNumeric){
                                totalMarks += num;
                            }
                            
                        }
                    }
                    
                    
                    
                    if (doCalculate == true){
                        
                        
                        if (newValue.contains("*")){
                            
                            SmartAverage.setText("Average: " + Math.round((totalMarks/totalWeight)*1000.0)/1000.0 );
                        }else{
                            SmartAverage.setText("Average: " + Math.round((totalMarks/totalNumberOfMarks)*1000.0)/1000.0 );
                        }
                        
                    }else{
                        SmartAverage.textProperty().set("Average: NA");
                    }
                    
                });
                
                
                
                
            }
            
            
}
