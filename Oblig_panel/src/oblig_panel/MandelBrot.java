/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oblig_panel;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Ola!
 */
public class MandelBrot extends BorderPane {

    final int START = 0;
    final double ZOOMRATE = 0.55;
    double xLengde = 2.5;
    double yHøyde = 2.0;
    double xVenstre = -2.0;
    double xHøyre = 0.5;
    double yBunn = -1;
    double yTopp = +1;
    final double CANVASWIDTH = 600.0;
    final double CANVASHEIGHT = 600.0;
    GraphicsContext gc = null;
    final int MAXRUNDER = 500;
    CheckBox utZoom = new CheckBox("Zoom ut");

    public MandelBrot() {

        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        HBox hb = new HBox();
        hb.getChildren().add(utZoom);
        hb.setAlignment(Pos.CENTER);
        canvas.setWidth(CANVASWIDTH);
        canvas.setHeight(CANVASHEIGHT);
        this.setCenter(canvas);
        this.setBottom(hb);

        tegnMandelbrot();
        this.setOnMouseClicked(musKlikk);
    }

    private void tegnMandelbrot() {
        // Mattematisk:
        for (int x = START; x <= CANVASWIDTH; x++) {
            for (int y = START; y <= CANVASHEIGHT; y++) {
                int runder = START;
                double skalertX = konverterX(x);
                double skalertY = konverterY(y);

                double nesteA = START;
                double nesteBi = START;

                while (runder < MAXRUNDER) {
                    double xtemp = nesteA * nesteA - nesteBi * nesteBi + skalertX;
                    nesteBi = 2.0 * nesteBi * nesteA + skalertY;
                    nesteA = xtemp;

                    if (nesteA * nesteA + nesteBi * nesteBi > 2 * 2) {

                        break;
                    }
                    runder += 1;
                }
                if (runder == MAXRUNDER) {
                    PixelWriter pw = gc.getPixelWriter();
                    pw.setColor(x, y, Color.BLACK);
                } else {
                    tegnPixler(x, y, runder);
                }

            }
        }
    }

    private void tegnPixler(int x, int y, int runder) {
        PixelWriter pw = gc.getPixelWriter();
        Color c = Color.hsb((runder / 100.00) * 255, 1, 1);
        pw.setColor(x, y, c);
    }

    private double konverterX(double x) {
        double midlertidigKonvertertX = x / CANVASWIDTH;
        double ekteVerdiX = midlertidigKonvertertX * xLengde;
        ekteVerdiX = ekteVerdiX + xVenstre;
        return ekteVerdiX;
    }

    private double konverterY(double y) {
        double midlertidigKonvertertY = y / CANVASHEIGHT;
        double ekteVerdiY = (midlertidigKonvertertY * yHøyde) * -1;
        ekteVerdiY = ekteVerdiY + yTopp;
        return ekteVerdiY;
    }

    private EventHandler<MouseEvent> musKlikk = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            double xPixl = mouseEvent.getX();
            double yPixl = mouseEvent.getY();

            double nyX = konverterX(xPixl);
            double nyY = konverterY(yPixl);
            if (!utZoom.isSelected()) {
                xLengde = xLengde * ZOOMRATE;
                yHøyde = yHøyde * ZOOMRATE;
            } else {
                xLengde = xLengde / ZOOMRATE;
                yHøyde = yHøyde / ZOOMRATE;
            }
            xVenstre = nyX - xLengde / 2.0;
            xHøyre = nyX + xLengde / 2.0;
            yBunn = nyY - yHøyde / 2.0;
            yTopp = nyY + yHøyde / 2.0;
            tegnMandelbrot();
        }

    };

}
