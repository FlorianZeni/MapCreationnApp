package mapCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class Controller {

    public TextField SetW;
    public TextField SetH;
    @FXML
    private Text xpos;
    @FXML
    private Text ypos;
    @FXML
    private Pane gameWindow;
    private int selectedType = 0; //0 Wall; 1 Floor;  2 Stairs
    private GameWindow win = new GameWindow(50, 50);


    @FXML
    protected void handleWallButtonAction(ActionEvent event) {
        selectedType = 0;
    }

    @FXML
    protected void handleFloorButtonAction(ActionEvent event) {
        selectedType = 1;
    }

    public Stage AppInit(Stage primaryStage, int W_c, int H_c) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Map Generator");
        Scene theScene = new Scene(root, 58 + 20 * W_c, 26 + 20 * H_c);
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        Pane center = (Pane) theScene.lookup("#gameWindow");

        center.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        GameWindow wininit = new GameWindow(W_c, H_c);
        wininit.redrawWin(center);

        for (int i = 0; i <= H_c; i++) {
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(2 * theScene.getWidth());
            line.setStartY(20 * i);
            line.setEndY(20 * i);
            center.getChildren().add(line);
        }
        for (int i = 0; i <= W_c; i++) {
            Line line = new Line();
            line.setStartY(0);
            line.setEndY(2 * theScene.getHeight());
            line.setStartX(20 * i);
            line.setEndX(20 * i);
            center.getChildren().add(line);
        }
        return primaryStage;
    }

    public void MouseMoved(MouseEvent mouseEvent) {
        this.xpos.setText("  X = " + (int) (mouseEvent.getX() / 300 * 15));
        this.ypos.setText("  Y = " + (int) (mouseEvent.getY() / 300 * 15));
        //x_pix.setText("  Xp :"+ e.getX());
        //y_pix.setText("  Yp :"+ e.getY());

    }

    public void MouseDragged(MouseEvent mouseEvent) {
        this.xpos.setText("  X = " + (int) (mouseEvent.getX() / 300 * 15));
        this.ypos.setText("  Y = " + (int) (mouseEvent.getY() / 300 * 15));
        if (mouseEvent.getX() > 0 && mouseEvent.getY() > 0) {
            win.updateTile(mouseEvent.getX(), mouseEvent.getY(), selectedType, gameWindow);
        }
    }

    public void MouseClicked(MouseEvent mouseEvent) {
        this.xpos.setText("  X :" + (int) (mouseEvent.getX() / 300 * 15));
        this.ypos.setText("  Y :" + (int) (mouseEvent.getY() / 300 * 15));
        if (mouseEvent.getX() > 0 && mouseEvent.getY() > 0) {
            win.updateTile(mouseEvent.getX(), mouseEvent.getY(), selectedType, gameWindow);
        }
    }

    public void handleCloseButtonAction() {
        Stage stage = (Stage) gameWindow.getScene().getWindow();
        stage.close();
    }

    public void handleSaveButtonAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("untitled");
        dialog.setTitle("Save");
        dialog.setContentText("Please enter File name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> win.saveMap(s));
    }


    public void handleNewButtonAction() {
        win.clearWin(gameWindow);
    }

    public void handleSizeButtonAction(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("option.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleOptionApplyButtonAction(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        int W_c = parseInt(SetW.getText());
        int H_c = parseInt(SetH.getText());
        Stage newStage = new Stage();
        newStage = AppInit(newStage, W_c, H_c);
        handleOptionCancelButtonAction();
        newStage.show();

    }

    public void handleOptionCancelButtonAction() {
        Stage opt = (Stage) SetW.getScene().getWindow();
        opt.close();
    }

    public void handleLoadButtonAction() {
        TextInputDialog dialog = new TextInputDialog("untitled");
        dialog.setTitle("Load");
        dialog.setContentText("Please enter File name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> win.loadMap(s, gameWindow));
    }
}
