import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSuburb implements Initializable {

    @FXML
    Label suburb;
    @FXML
    Label postcode;
    @FXML
    TextField suburbField;
    @FXML
    TextField postcodeField;

    @FXML
    private void editSuburb(ActionEvent event) throws IOException {
        String newSuburb = suburbField.getText();
        String newPostcode = postcodeField.getText();
        File postcodeFile = new File("./src/main/resources/VNNet_Folio4_Postcodes.txt");
        //Using a temporary file to rewrite all the postcodes into
        File tempFile = new File("./src/main/resources/tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        for (String code : Controller.allPostcodes){
            if(code.toUpperCase().equals(this.suburb.getText().toUpperCase() + "," + this.postcode.getText().toUpperCase())) {
                writer.write(newSuburb.toUpperCase() + ',' + newPostcode.toUpperCase() + System.getProperty("line.separator"));
            } else {
                writer.write(code + System.getProperty("line.separator"));
            }
        }
        postcodeFile.delete();
        writer.close();
        tempFile.renameTo(new File("./src/main/resources/VNNet_Folio4_Postcodes.txt"));
        Controller.allPostcodes.clear();
        Controller.loadCities();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        System.out.println("Edit has been made");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String suburb = Suburb.getSuburb();
        String postcode = Suburb.getPostcode();
        this.suburb.setText(suburb);
        this.postcode.setText(postcode);
    }
}
