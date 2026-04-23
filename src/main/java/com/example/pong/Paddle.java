package com.example.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    public double y;
    public final double x;     //nemeni se, takze final
    public final double width = 20;
    public final double height = 100;
    public final double speed;

    public Paddle(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, y, width, height);
    }
}