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
 * @author Ola Stålberg
 */
public class Oblig_panel extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        tab.setText("MandelBrot");
        tab.setContent(new MandelBrot());
        tabPane.getTabs().add(tab);
        
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
