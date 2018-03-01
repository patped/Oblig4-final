/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oblig4algoritmer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author kiman
 */
public class Oblig4 extends Application {

    int nåHøyde = 0;
    int bredde = 100;
    int høyde = bredde / 2;
    boolean[][] tabell = new boolean[36][2];
    boolean[][] svart = new boolean[bredde + 1][høyde + 1];
    Label[][] lab = new Label[bredde + 1][høyde + 1];
    BorderPane root = new BorderPane();
    GridPane grid = new GridPane();
    GridPane menu = new GridPane();
    HBox venstre = new HBox();
    Button btn = new Button("Lag");
    Label beskrivelse = new Label("Klikk på hva du ønsker å ha svart på topp, deretter valgte \"regler\" for hvordan mønsteret beveger seg: ");


    @Override

    public void start(Stage primaryStage) {

        lagMeny();
        menu.setPadding(new Insets(20,20,20,20));
        btn.setOnAction(c -> action());
        lagFørsteLinje();
        root.setLeft(venstre);
        root.setTop(beskrivelse);
        venstre.getChildren().add(btn);

        root.setCenter(grid);
        root.setBottom(menu);
        Scene scene = new Scene(root, 1400, 500);

        primaryStage.setTitle("Oblig 4!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void lagFørsteLinje() {
        for (int kol = 0; kol < bredde; kol++) {
            lab[kol][nåHøyde] = new Label();
            lab[kol][nåHøyde].setMinSize(13, 6);
            lab[kol][nåHøyde].setMaxSize(13, 6);
            /*
            if (kol == bredde / 2) {
                svart[kol][nåHøyde] = true;
                int k = kol;
                lab[kol][nåHøyde].setOnMouseClicked(e-> fargSvart(lab[k][nåHøyde],k,nåHøyde));
                lab[kol][nåHøyde].setStyle("-fx-border-color:black; -fx-background-color: black;");
            } else {*/
            svart[kol][nåHøyde] = false;
            int k = kol;
            lab[kol][nåHøyde].setStyle(("-fx-border-color:black; -fx-background-color: white;"));
            lab[kol][nåHøyde].setOnMouseClicked(e -> fSvart(lab[k][nåHøyde], k, nåHøyde));
            grid.add(lab[kol][nåHøyde], kol, nåHøyde);

        }
    }

    private void fSvart(Label l, int kol, int rad) {
        svart[kol][rad] = true;
        l.setStyle(("-fx-border-color:black; -fx-background-color: black;"));
        l.setOnMouseClicked(a -> fHvit(l, kol, rad));
    }

    private void fHvit(Label l, int kol, int rad) {
        svart[kol][rad] = false;
        l.setStyle(("-fx-border-color:black; -fx-background-color: white;"));
        l.setOnMouseClicked(e -> fSvart(l, kol, rad));
    }

    private void lagMeny() {
        int MENYLENGDE = 35;
        menu.add(btn, 0, 4);

        for (int kol = 0; kol < MENYLENGDE; kol++) {
            if (kol == 2 || kol == 3 || kol == 4 || kol == 6 || kol == 7 || kol == 8 || kol == 10 || kol == 11 || kol == 12 || kol == 14 || kol == 15 || kol == 16 || kol == 18 || kol == 19 || kol == 20 || kol == 22 || kol == 24 || kol == 23
                    || kol == 26 || kol == 28 || kol == 27 || kol == 32 || kol == 30 || kol == 31) {
                Label l = new Label();
                l.setMinSize(23, 10);
                l.setMaxSize(23, 10);
                fargHvit(l, kol, 0);

                menu.add(l, kol, 0);
            } else {
                Label l = new Label();
                l.setMinSize(25, 10);
                l.setMaxSize(25, 10);
                menu.add(l, kol, 0);

            }
        }
        int midt = 3;
        for (int kol = 0; kol < MENYLENGDE; kol++) {
            Label j = new Label();
            j.setMinSize(23, 10);
            j.setMaxSize(23, 10);
            if (kol == midt) {
                if (midt < 16) {
                    midt += 4;
                    fargHvit(j, kol, 1);
                } else if (midt > 16) {
                    midt += 4;
                    fargSvart(j, kol, 1);
                }

            }
            menu.add(j, kol, 2);
        }

    }

    private void fargSvart(Label l, int kol, int rad) {
        tabell[kol][rad] = true;
        l.setStyle(("-fx-border-color:black; -fx-background-color: black;"));
        l.setOnMouseClicked(a -> fargHvit(l, kol, rad));
    }

    private void fargHvit(Label l, int kol, int rad) {
        tabell[kol][rad] = false;
        l.setStyle(("-fx-border-color:black; -fx-background-color: white;"));
        l.setOnMouseClicked(e -> fargSvart(l, kol, rad));
    }

    private void action() {
        nåHøyde++;
        lagRegler(3);

    }

    private void lagRegler(int svarthvit) {
        if (svarthvit < 33) {
            if (tabell[svarthvit][1] == true) {
                tegn(svarthvit);
            } else {
                lagRegler(svarthvit + 4);
            }

        } else {
            nåHøyde++;
            if (nåHøyde <= høyde) {
                lagRegler(3);
            } else {
                nåHøyde = 0;
            }

        }

    }

    private void tegn(int svarthvit) {

        for (int kol = 1; kol < bredde; kol++) {
            lab[kol][nåHøyde] = new Label();
            lab[kol][nåHøyde].setMinSize(13, 6);
            lab[kol][nåHøyde].setMaxSize(13, 6);
            if ((tabell[svarthvit - 1][0]) == (svart[kol - 1][nåHøyde - 1]) && (tabell[svarthvit][0]) == (svart[kol][nåHøyde - 1])
                    && (tabell[svarthvit + 1][0]) == (svart[kol + 1][nåHøyde - 1])) {
                svart[kol][nåHøyde] = true;
                lab[kol][nåHøyde].setStyle("-fx-border-color:black; -fx-background-color: black;");
            } else if (svart[kol][nåHøyde] != true) {
                svart[kol][nåHøyde] = false;
                lab[kol][nåHøyde].setStyle("-fx-border-color:black; -fx-background-color: white;");
            }
            grid.add(lab[kol][nåHøyde], kol, nåHøyde);

        }
        lagRegler(svarthvit + 4);

    }



}
