package mapCreation;

import javafx.scene.layout.Pane;

import java.io.*;

public class GameWindow implements Serializable {
    private int W_coo;
    private int H_coo;
    private Tile[] TileList;

    public GameWindow(int Wc, int Hc) {
        this.W_coo = Wc;
        this.H_coo = Hc;
        TileList = new Tile[Wc * Hc];
        for (int x = 0; x < Wc; x++) {
            for (int y = 0; y < Hc; y++) {
                this.TileList[x + Wc * y] = new Tile(x, y);
            }
        }
    }

    private Tile getTileXY(double xc, double yc) {
        return TileList[(int) (xc + this.W_coo * yc)];
    }

    void updateTile(double x, double y, int type, Pane pane) {
        Tile selected = getTileXY((int) (x / 300 * 15), (int) (y / 300 * 15));
        selected.setType(type);
        selected.redrawTile(pane);
    }

    void clearWin(Pane pane) {
        for (int x = 0; x < this.W_coo; x++) {
            for (int y = 0; y < this.H_coo; y++) {
                getTileXY(x, y).setType(0);
                getTileXY(x, y).redrawTile(pane);
            }
        }
    }

    void saveMap(String address) {
        FileOutputStream fout;
        ObjectOutputStream oos;

        try {
            fout = new FileOutputStream(address + ".ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(TileList);
            System.out.println("Done Saving");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void loadMap(String address, Pane pane) {
        FileInputStream fint;
        ObjectInputStream ois;
        Tile[] L;
        try {
            fint = new FileInputStream(address + ".ser");
            ois = new ObjectInputStream(fint);
            L = (Tile[]) ois.readObject();
            System.out.println("Done Loading");
            TileList = L;
            redrawWin(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("File probably does not exist");
        }
    }

    void redrawWin(Pane pane) {
        for (int x = 0; x < this.W_coo; x++) {
            for (int y = 0; y < this.H_coo; y++) {
                getTileXY(x, y).redrawTile(pane);
            }
        }
    }
}
