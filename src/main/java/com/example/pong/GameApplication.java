package com.example.pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;

public class GameApplication extends Application {

    private GamePanel gamePanel;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gamePanel = new GamePanel();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gamePanel.update();
                gamePanel.render(gc);
            }
        }.start();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setTitle("Pong");
        stage.setScene(scene);
        stage.show();

        canvas.requestFocus();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                gamePanel.keyPressed(e.getCode());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                gamePanel.keyReleased(e.getCode());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}