/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oblig_panel;

import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Kim Næss
 */
public class GameOfLife extends BorderPane {

  GridPane grid = new GridPane();
  boolean[][] nyGame;
  boolean[][] game;
  Rectangle[][] rekt;
  TextField høyde = new TextField("Høyde");
  TextField bredde = new TextField("Bredde");
  Button btn = new Button("Lag brett!");
  VBox menu = new VBox();
  Button startSpill = new Button("Start");
  int radLiv = 1;
  int kolLiv = 1;

  public GameOfLife() {
    this.setCenter(grid);
    høyde.setMinSize(65, 30);
    høyde.setMaxSize(65, 30);
    bredde.setMinSize(65, 20);
    bredde.setMaxSize(65, 30);
    btn.setOnAction(e -> lagBrett(parseInt(høyde.getText()), parseInt(bredde.getText())));

    menu.getChildren().add(høyde);
    menu.getChildren().add(bredde);
    menu.getChildren().add(btn);
    this.setLeft(menu);

    startSpill.setOnAction(a -> {
      try {
        Game(parseInt((høyde.getText())), parseInt((bredde.getText())));
      } catch (InterruptedException ex) {
        Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
  }

  private void lagBrett(int høyde, int bredde) {
    try {
      game = new boolean[høyde + 2][bredde + 2];
      rekt = new Rectangle[høyde][bredde];
      for (int rad = 1; rad < høyde; rad++) {
        for (int kol = 1; kol < bredde; kol++) {
          rekt[rad][kol] = new Rectangle();
          fargHvit(rekt[rad][kol], rad, kol);
          grid.add(rekt[rad][kol], kol, rad);

        }
      }
      menu.setVisible(false);
      this.setLeft(startSpill);
    } catch (NumberFormatException e) {
      System.out.println("skriv inn tall");
    }
  }

  private void fargHvit(Rectangle rekt, int rad, int kol) {
    rekt.setHeight(14);
    rekt.setWidth(13);
    rekt.setFill(Color.GREY);

    game[rad][kol] = false;
    System.out.println("hvit: " + game[rad][kol]);
    rekt.setOnMouseClicked(a -> fargGul(rekt, rad, kol));

  }

  private void fargGul(Rectangle rekt, int rad, int kol) {
    game[rad][kol] = true;
    System.out.println("gul: " + rad + "kol: " + kol);
    rekt.setFill(Color.YELLOW);
    rekt.setOnMouseClicked(a -> fargHvit(rekt, rad, kol));

  }

  private void Game(int høyde, int bredde) throws InterruptedException {
    nyGame = new boolean[høyde + 2][bredde + 2];
    for (int g = 0; g < 9; g++) {
      for (int rad = 1; rad < høyde; rad++) {
        for (int kol = 1; kol < bredde; kol++) {
          nyGame[rad][kol] = gameOfLife(rad, kol);

        }
      }

      new Thread(() -> {
        try {
          Thread.sleep(5000);
          Platform.runLater(() -> {
            try {
              blankUt(høyde, bredde);
            } catch (InterruptedException kek) {
            }
          });
          
        } catch (InterruptedException lol) {
        }
      }).start();
    }
  }

  private boolean gameOfLife(int rad, int kol) {

    int død = 0;
    if (game[rad - 1][kol] == true) {
      død++;
    }
    if (game[rad - 1][kol - 1] == true) {
      død++;
    }
    if (game[rad - 1][kol + 1] == true) {
      død++;
    }
    if (game[rad + 1][kol + 1] == true) {
      død++;
    }
    if (game[rad + 1][kol] == true) {
      død++;
    }
    if (game[rad + 1][kol - 1] == true) {
      død++;
    }
    if (game[rad][kol - 1] == true) {
      død++;
    }
    if (game[rad][kol + 1] == true) {
      død++;
    }
    System.out.println(død);
    if (game[rad][kol] == true) {
      if (død == 2 || død == 3) {
        return true;
      } else {
        return false;
      }

    } else if (død == 3 && game[rad][kol] == false) {
      return true;

    } else {
      return false;

    }

  }

  private void blankUt(int høyde, int bredde) throws InterruptedException {
    for (int rad = 1; rad < høyde; rad++) {
      for (int kol = 1; kol < bredde; kol++) {
        System.out.println("sånn: " + rad + "kol: " + kol + "    " + nyGame[rad][kol]);
        //sleep(1000);
        if (nyGame[rad][kol] == true) {
          game[rad][kol] = true;
          rekt[rad][kol].setFill(Color.YELLOW);

        } else {
          game[rad][kol] = false;
          rekt[rad][kol].setFill(Color.GREY);
        }
        nyGame[rad][kol] = false;

      }
    }
  }
}
