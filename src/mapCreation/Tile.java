package mapCreation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Tile implements Serializable {
    private int x_pix;
    private int y_pix;
    private int x_coo;
    private int y_coo;
    private int type = 0; // 0 : Wall, 1 : Floor


    public Tile(int xc, int yc) {
        this.x_coo = xc;
        this.y_coo = yc;
        this.x_pix = xc * 20;
        this.y_pix = yc * 20;
    }

    void setType(int t) {
        this.type = t;
    }

    void redrawTile(Pane pane) {
        Rectangle rec = new Rectangle(this.x_pix + 2, this.y_pix + 2, 16, 16);
        switch (this.type) {
            case 0:
                rec.setFill(Color.BLACK);
                break;
            case 1:
                rec.setFill(Color.WHITE);
                break;
            default:
                System.out.println("Error : Unknown Tile Type");
        }
        pane.getChildren().add(rec);
    }

}
