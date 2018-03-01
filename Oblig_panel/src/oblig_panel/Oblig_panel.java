/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oblig_panel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 *
 * @author Patrick Pedersen
 */
public class Oblig_panel extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        TabPane tabPane = new TabPane();
        
        Tab mandelbrot = new Tab();
        mandelbrot.setText("MandelBrot");
        mandelbrot.setContent(new MandelBrot());
        
        Tab bifurcation = new Tab();
        bifurcation.setText("Bifrucation");
        bifurcation.setContent(new Bifurcation());
        
        Tab cellularautomaton = new Tab();
        cellularautomaton.setText("Cellular Automaton");
        cellularautomaton.setContent(new CellularAutomaton());
        
        tabPane.getTabs().addAll(mandelbrot, bifurcation, cellularautomaton);
        
        Scene scene = new Scene(tabPane, 600, 660);
        
        primaryStage.setTitle("Kaos-Prosjekt!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
