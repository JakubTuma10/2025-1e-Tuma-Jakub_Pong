package com.example.pong;

import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GamePanel {

    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;

    public int scoreLeft;
    public int scoreRight;

    private int startDelay = 0;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean gameOver = false;
    private String winner = "";

    private double paddleSpeed = 10;

    public GamePanel() {
        leftPaddle = new Paddle(50, 250, paddleSpeed);
        rightPaddle = new Paddle(730, 250, paddleSpeed);
        ball = new Ball(390, 290, 6, 6);
    }

    public void update() {

        if (wPressed && leftPaddle.y > 0) leftPaddle.y -= paddleSpeed;
        if (sPressed && leftPaddle.y < 600 - 100) leftPaddle.y += paddleSpeed;

        if (upPressed && rightPaddle.y > 0) rightPaddle.y -= paddleSpeed;
        if (downPressed && rightPaddle.y < 600 - 100) rightPaddle.y += paddleSpeed;

        if (startDelay > 0) {
            startDelay--;

            if (startDelay == 0) {
                ball.speedX = (Math.random() > 0.5 ? 7 : -7);
                ball.speedY = (Math.random() - 0.5) * 7;
            }
            return;
        }

        double newBallX = ball.x + ball.speedX;
        double newBallY = ball.y + ball.speedY;

        if (newBallY <= 0 || newBallY >= 600 - 20) {
            ball.speedY = -ball.speedY;

            if (newBallY <= 0) newBallY = 0;
            else newBallY = 600 - 20;
        }

        if (ball.speedX < 0 &&
                newBallX <= leftPaddle.x + 20 &&
                newBallX + 20 >= leftPaddle.x &&
                newBallY + 20 >= leftPaddle.y &&
                newBallY <= leftPaddle.y + 100) {

            double paddleCenter = leftPaddle.y + 50;
            double ballCenter = newBallY + 10;

            double hitPos = (ballCenter - paddleCenter) / 50.0;
            hitPos = Math.max(-1, Math.min(1, hitPos));

            double speed = Math.sqrt(ball.speedX * ball.speedX + ball.speedY * ball.speedY);
            speed *= 1.05;

            double maxAngle = Math.toRadians(60);
            double angle = hitPos * maxAngle;

            ball.speedX = speed * Math.cos(angle);
            ball.speedY = speed * Math.sin(angle);

            newBallX = leftPaddle.x + 20;
        }

        if (ball.speedX > 0 &&
                newBallX + 20 >= rightPaddle.x &&
                newBallX <= rightPaddle.x + 20 &&
                newBallY + 20 >= rightPaddle.y &&
                newBallY <= rightPaddle.y + 100) {

            double paddleCenter = rightPaddle.y + 50;
            double ballCenter = newBallY + 10;

            double hitPos = (ballCenter - paddleCenter) / 50.0;
            hitPos = Math.max(-1, Math.min(1, hitPos));

            double speed = Math.sqrt(ball.speedX * ball.speedX + ball.speedY * ball.speedY);
            speed *= 1.05;

            double maxAngle = Math.toRadians(60);
            double angle = hitPos * maxAngle;

            ball.speedX = -speed * Math.cos(angle);
            ball.speedY = speed * Math.sin(angle);

            newBallX = rightPaddle.x - 20;
        }

        if (newBallX <= 0) {
            scoreRight++;
            resetBall();
            return;
        }

        if (newBallX >= 800 - 20) {
            scoreLeft++;
            resetBall();
            return;
        }

        if (scoreLeft >= 5) {
            gameOver = true;
            winner = "Levý hráč vyhrál!";
        }

        if (scoreRight >= 5) {
            gameOver = true;
            winner = "Pravý hráč vyhrál!";
        }

        if (gameOver) {
            return;
        }

        ball.x = newBallX;
        ball.y = newBallY;
    }

    public void restart() {
        scoreLeft = 0;
        scoreRight = 0;
        resetBall();
        gameOver = false;
    }

    private void resetBall() {
        ball.x = 390;
        ball.y = 290;
        ball.speedX = 0;
        ball.speedY = 0;
        startDelay = 60;
    }

    public void render(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(40));
        gc.fillText(scoreLeft + " : " + scoreRight, 350, 50);

        leftPaddle.draw(gc);
        rightPaddle.draw(gc);
        ball.draw(gc);

        if (gameOver) {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(48));
            gc.fillText(winner, 200, 150);
            gc.fillText("Stiskni R pro restart", 200, 200);
        }
    }

    public void keyPressed(KeyCode code) {

        if (code == KeyCode.W) wPressed = true;
        if (code == KeyCode.S) sPressed = true;

        if (code == KeyCode.UP) upPressed = true;
        if (code == KeyCode.DOWN) downPressed = true;

        if (code == KeyCode.R) restart();
    }

    public void keyReleased(KeyCode code) {

        if (code == KeyCode.W) wPressed = false;
        if (code == KeyCode.S) sPressed = false;

        if (code == KeyCode.UP) upPressed = false;
        if (code == KeyCode.DOWN) downPressed = false;
    }
    public boolean isGameOver() {
        return gameOver;
    }
}