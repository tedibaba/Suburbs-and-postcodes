import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {


    public static ArrayList<String> allPostcodes = new ArrayList<>();
    public static TextArea postcodesReference;
    public static Label suburbNumberReference;
    static int counter = 0;
    @FXML
     Label suburbNumber;
    @FXML
     TextArea postcodes;
    @FXML
     TextField suburbSearch;
    @FXML
     TextField postcodeSearch;


    @FXML
    private void searchForCities(){
        String searchedCity = suburbSearch.getText();
        if (searchedCity.isEmpty()){
            addPostcodesIntoList();
        } else {
            postcodes.setText("");
            counter = 0;
            for (String postcode : allPostcodes){
                String[] splitCode = postcode.split(",", 2);
                if (splitCode[0].toLowerCase().contains(searchedCity.toLowerCase())) {
                    postcodes.appendText(postcode + '\n');
                    counter++;
                    }
                }
            };
            suburbNumber.setText(String.valueOf(counter));
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.postcodesReference = postcodes;
        Controller.suburbNumberReference = suburbNumber;
        try {
            loadCities();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

     static void loadCities() throws FileNotFoundException {
        File postcodesFile = new File("./src/main/resources/VNNet_Folio4_Postcodes.txt");
        Scanner postcodeReader = new Scanner(postcodesFile);
        //While another line exists
        while (postcodeReader.hasNext()){
            String data = postcodeReader.nextLine();
            System.out.println(data);
            allPostcodes.add(data);
        }
        postcodeReader.close();
        addPostcodesIntoList();
    }

     private static void addPostcodesIntoList(){
        //Add all the postcodes into post code textfield
        counter = 0;
        Platform.runLater( () -> {
            for (String code : allPostcodes){
                counter ++;
                postcodesReference.appendText(code + '\n');
            }
            suburbNumberReference.setText(String.valueOf(counter));
        });
    }

    @FXML
    private void deleteSuburb() throws IOException {
        File tempFile = new File("./src/main/resources/tempFile.txt");
        File postcodesFile = new File("./src/main/resources/VNNet_Folio4_Postcodes.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        //Getting the suburb and postcode that needs to be deleted
        String postcode = postcodeSearch.getText();
        String suburb = suburbSearch.getText();
        boolean suburbExists = allPostcodes.contains(suburb.toUpperCase() + ',' + postcode.toUpperCase());
        if (suburbExists){
            for (String code : allPostcodes){
                System.out.println(code);
                if (code.toLowerCase().equals(suburb.toLowerCase()  + ',' + postcode.toLowerCase())){
                    continue;
                } else {
                    writer.write(code + System.getProperty("line.separator"));
                }
                postcodeSearch.clear();
                suburbSearch.clear();
            }
            postcodesFile.delete();
            writer.close();
            tempFile.renameTo(new File("./src/main/resources/VNNet_Folio4_Postcodes.txt"));
            allPostcodes.clear();
            loadCities();

        } else {
            System.out.println("Your lying to me :(");
        }

    }

    @FXML
    private void addSuburb() throws IOException {
        boolean suburbExists = allPostcodes.contains(suburbSearch.getText().toUpperCase() + ',' + postcodeSearch.getText().toUpperCase());
        if (suburbExists){
            System.out.println("This already exists mr monkey");
        } else {
            File postcodesFile = new File("./src/main/resources/VNNet_Folio4_Postcodes.txt");
            FileWriter writer = new FileWriter(postcodesFile, true);
            String suburb = suburbSearch.getText();
            String postcode = postcodeSearch.getText();
            writer.write('\n' + suburb.toUpperCase() + ", " + postcode);
            writer.close();
            loadCities();
        }
    }

    @FXML
    private void editSuburb() throws IOException {
        String suburb = suburbSearch.getText();
        String postcode = postcodeSearch.getText();
        boolean suburbExists = allPostcodes.contains(suburbSearch.getText().toUpperCase() + ',' + postcodeSearch.getText().toUpperCase());
        if (suburbExists) {
            Suburb chosenSuburb = new Suburb(suburb, postcode);
            FXMLLoader root = new FXMLLoader(App.class.getResource("/editSuburb.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root.load()));
            stage.show();

        } else {
            System.out.println("Ooooh ahh ahahahah");
        }
    }
}
