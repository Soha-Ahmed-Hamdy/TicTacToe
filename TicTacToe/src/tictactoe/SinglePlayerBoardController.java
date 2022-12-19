package tictactoe;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SinglePlayerBoardController implements Initializable {

    @FXML
    private Text playerOneName;
    @FXML
    private Text playerTwoName;
    @FXML
    private Button playerOneScoreBtn;
    @FXML
    private Button playerTwoScoreBtn;
    @FXML
    private ImageView historyIcon;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn13;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;
    @FXML
    private Button btn23;
    @FXML
    private Button btn31;
    @FXML
    private Button btn32;
    @FXML
    private Button btn33;
    @FXML
    private Button homeButton;
    @FXML
    private Button exitButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String winner;
     private int counterPlayer1 = CounterTwoPlayers.getCounterPlayer1();
    private int counterPlayer2 = CounterTwoPlayers.getCounterPlayer2();
    private int counterclicked = 0;

    Vector<Button> buttons;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        buttons = new Vector<Button>();
        buttons.add(btn11);
        buttons.add(btn12);
        buttons.add(btn13);
        buttons.add(btn21);
        buttons.add(btn22);
        buttons.add(btn23);
        buttons.add(btn31);
        buttons.add(btn32);
        buttons.add(btn33);

        System.out.println(buttons.size());

    }

    @FXML
    private void homeButton(ActionEvent event) {
          Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("The game will not be saved");
                a.setTitle("Exit Game!");
                Optional<ButtonType> result = a.showAndWait();

                if (result.get() == ButtonType.OK) {

                    try {
            root = FXMLLoader.load(getClass().getResource("choosingModeScene.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ScenesNavigator.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

                }
        
       
    }

    @FXML
    private void exitButton(ActionEvent event) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("The game will not be saved");
                a.setTitle("Exit Game!");
                Optional<ButtonType> result = a.showAndWait();

                if (result.get() == ButtonType.OK) {

                    Platform.exit();
                }
        
    }

    @FXML
    private void setUpButton(ActionEvent e) {
        String stat;
        counterclicked++;

        Button button = (Button) e.getSource();
        button.setText("X");
        button.setDisable(true);
        ComputerMove();
        stat = checkGameOver();
        if (stat.equals("XXX") || stat.equals("OOO")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WinningScreenSingle.fxml"));
                root = loader.load();
                WinningScreenSingleController winningSceneController = loader.getController();
                if (stat.equals("XXX")) {
                   
                    winningSceneController.setWinnerNameText("Congratulations \t"+playerOneName.getText());
                } else {
                   
                    winningSceneController.setWinnerNameText("You Lose!");
                }
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SinglePlayerBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void ComputerMove() {
        int randomMove = (int) (Math.random() * (9 - counterclicked)) + 1;
        for (Button button : buttons) {
            if (button.getText().equals("")) {
                randomMove--;
                if (randomMove == 0) {
                    button.setText("O");
                    button.setDisable(true);
                    break;
                }
            }
        }
        counterclicked++;
    }

    private String checkGameOver() {
        String stat = "";
        for (int i = 0; i < 8; i++) {
            String gameStatus;
            switch (i) {
                case 0:
                    gameStatus = btn11.getText() + btn12.getText() + btn13.getText();
                    break;
                case 1:
                    gameStatus = btn21.getText() + btn22.getText() + btn23.getText();
                    break;
                case 2:
                    gameStatus = btn31.getText() + btn32.getText() + btn33.getText();
                    break;
                case 3:
                    gameStatus = btn11.getText() + btn21.getText() + btn31.getText();
                    break;
                case 4:
                    gameStatus = btn12.getText() + btn22.getText() + btn32.getText();
                    break;
                case 5:
                    gameStatus = btn13.getText() + btn23.getText() + btn33.getText();
                    break;
                case 6:
                    gameStatus = btn11.getText() + btn22.getText() + btn33.getText();
                    break;
                case 7:
                    gameStatus = btn13.getText() + btn22.getText() + btn31.getText();
                    break;

                default:
                    gameStatus = null;

            }

            if (gameStatus.equals("XXX")) {
                counterPlayer1++;
                CounterTwoPlayers.setCounterPlayer1(counterPlayer1);
                WinnerPlayer.setWinnerName(playerOneName.getText());
                playerOneScoreBtn.setText(Integer.toString(counterPlayer1));
                restartGame();
                return gameStatus;

            }
            if (gameStatus.equals("OOO")) {
                counterPlayer2++;
                CounterTwoPlayers.setCounterPlayer2(counterPlayer2);
                WinnerPlayer.setWinnerName(playerTwoName.getText());
                playerTwoScoreBtn.setText(Integer.toString(counterPlayer2));
                restartGame();
                return gameStatus;

            }
            if (isWon(gameStatus) == false && isfull() == true) {
                restartGame();
            }
        }
        return stat;
    }

    private boolean isWon(String status) {
        if (status == "XXX" || status == "OOO") {
            return true;
        }
        return false;

    }

    private boolean isfull() {
        for (int i = 0; i < 8; i++) {
            if (buttons.get(i).getText() == "") {
                return false;
            }
        }
        return true;

    }

    private void restartGame() {

        buttons.forEach(this::resetButton);

    }

    public void resetButton(Button button) {
        counterclicked = 0;
        button.setDisable(false);
        button.setText("");

    }

    public void setPlayerOneNameText(String playerOneName) {
        this.playerOneName.setText(playerOneName);
    }

    public void setPlayerTwoNameText(String playerTwoName) {
        this.playerTwoName.setText(playerTwoName);
    }

    public void setPlayerCounter1(int counter1) {
        this.playerOneScoreBtn.setText(Integer.toString(counter1));
    }

    public void setPlayerCounter2(int counter2) {
        this.playerTwoScoreBtn.setText(Integer.toString(counter2));
    }
}
