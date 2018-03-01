/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bifurcation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Ola Stålberg
 */
public class Bifurcation extends Application {

    final int START = 0;
    final double ZOOMRATE = 0.9;
    final double XOSTART = 0.5;
    double rLengde = 1.6;
    double rVenstre = 2.4;
    double rHøyre = 4.0;
    double xHøyde = 1.0;
    double xBunn = 0;
    double xTopp = 1;
    final double CANVASWIDTH = 600.0;
    final double CANVASHEIGHT = 600.0;
    final double CANVASHEIGHTSTART = 0.0;
    GraphicsContext gc = null;
    final int MAXRUNDER = 300;
    CheckBox utZoom = new CheckBox("Zoom ut");
    int runder = 0;
    Canvas canvas = new Canvas();

    @Override
    public void start(Stage primaryStage) {

        BorderPane bp = new BorderPane();

        gc = canvas.getGraphicsContext2D();
        HBox hb = new HBox();
        hb.getChildren().add(utZoom);
        hb.setAlignment(Pos.CENTER);
        canvas.setWidth(CANVASWIDTH);
        canvas.setHeight(CANVASHEIGHT);
        canvas.setOnMouseClicked(musKlikk);
        bp.setCenter(canvas);
        bp.setBottom(hb);

        //canvas.setOnMouseClicked(musKlikk);
        Scene scene = new Scene(bp, 600, 640);

        primaryStage.setTitle("Bifurcation");
        primaryStage.setScene(scene);
        primaryStage.show();

        tegnBifurcation();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void tegnBifurcation() {
        for (int r = START; r < CANVASWIDTH; r++) {

            double tempR = konverterR(r);
            double tempSvar = XOSTART;
            int teller = 0;

            while (teller <= 100) {
                tempSvar = tempR * tempSvar * (1 - tempSvar);
                teller++;
            }
            while (teller <= MAXRUNDER) {
                tempSvar = tempR * tempSvar * (1 - tempSvar);
                int grafPunkt = konverterTilPixel(tempSvar);
                tegnprikk(r, grafPunkt);
                teller++;
            }
        }
    }
      private int konverterTilPixel(double tempSvar) { 
          double konverteringsA = (tempSvar-xBunn)/xHøyde;
          double pixelSomSkalTegnes = CANVASHEIGHT - konverteringsA*(CANVASHEIGHT);
          
          return (int)pixelSomSkalTegnes;
    }


    private void tegnprikk(int r, int svarY) {

        PixelWriter pw = gc.getPixelWriter();

        pw.setColor(r, svarY, Color.BLACK);
    }

    private double konverterR(double x) {
        double midlertidigKonvertertX = x / CANVASWIDTH;
        double ekteVerdiX = midlertidigKonvertertX * rLengde;
        ekteVerdiX = ekteVerdiX + rVenstre;
        return ekteVerdiX;
    }

    private double konverterX(double y) {
        double midlertidigKonvertertY = y / CANVASHEIGHT;
        midlertidigKonvertertY = 1-midlertidigKonvertertY;
        double ekteVerdiY = midlertidigKonvertertY * xHøyde;
        ekteVerdiY = ekteVerdiY + xBunn;
        return ekteVerdiY;
    }

    private EventHandler<MouseEvent> musKlikk = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            double xPixl = mouseEvent.getX();
            double yPixl = mouseEvent.getY();

            double nyR = konverterR(xPixl);
            double nyX = konverterX(yPixl);
            if (!utZoom.isSelected()) {
                rLengde = rLengde * ZOOMRATE;
                xHøyde = xHøyde * ZOOMRATE;
            } else {
                rLengde = rLengde / ZOOMRATE;
                xHøyde = xHøyde / ZOOMRATE;
            }
            rVenstre = nyR - rLengde / 2.0;
            rHøyre = nyR + rLengde / 2.0;
            xBunn = nyX - xHøyde / 2.0;
            xTopp = nyX + xHøyde / 2.0;
            System.out.println("X-Høyde: " + xHøyde + "\nXBunn: " + xBunn + "\nXTopp: " + xTopp);
            tegnBifurcation();
        }

    };

  
}
