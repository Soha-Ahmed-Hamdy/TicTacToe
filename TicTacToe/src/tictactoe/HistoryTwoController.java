/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import LocalData.TwoGame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author SOHA
 */
public class HistoryTwoController implements Initializable {
    private String line;
    private ObservableList<TwoGame> observableList=FXCollections.observableArrayList();

    @FXML
    private TableView<TwoGame> table;
    @FXML
    private TableColumn<TwoGame, Integer> idCol;
    @FXML
    private TableColumn<TwoGame,String> dateCol;
    @FXML
    private TableColumn<TwoGame,String> timeCol;
    @FXML
    private TableColumn<TwoGame, String> playerOneCol;
    @FXML
    private TableColumn<TwoGame, String> playerTwoCol;
    @FXML
    private TableColumn<TwoGame, String> winnerCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gettingDataFromFile();
    }    
    public void gettingDataFromFile(){
        try {
            BufferedReader reader=new BufferedReader(new FileReader(new File("C:\\Users\\SOHA\\Desktop\\HistoryData.txt")));
            
            Integer counter=new Integer(1);
            while((line=reader.readLine())!=null){
                String []game=line.split(",");
               /* for(String i:game){
                
                    System.out.println(i);
                
                }
                */
                
                idCol.setCellValueFactory(new PropertyValueFactory<TwoGame,Integer>("ID"));
                dateCol.setCellValueFactory(new PropertyValueFactory<TwoGame,String>("Date"));
                timeCol.setCellValueFactory(new PropertyValueFactory<TwoGame,String>("Time"));
                playerOneCol.setCellValueFactory(new PropertyValueFactory<TwoGame,String>("PlayerOne"));
                playerTwoCol.setCellValueFactory(new PropertyValueFactory<TwoGame,String>("PlayerTwo"));
                winnerCol.setCellValueFactory(new PropertyValueFactory<TwoGame,String>("Winner"));
                
                //TwoGame twoGame=new TwoGame(counter,game[2],game[4],game[0],game[1],game[3]);
                //System.out.println(twoGame.getPlayerOneName());
                
                observableList.add(new TwoGame(counter,game[2],game[4],game[0],game[1],game[3]));
               
                
                counter++;
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HistoryTwoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HistoryTwoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(observableList);
    
    
    }
    
}
