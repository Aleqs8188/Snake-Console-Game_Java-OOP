package pl.oleksii;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private final int[][] gameBoard;
    private int direction;
    Food[] food;
    Snake snake;

    public GameBoard() {
        gameBoard = new int[WIDTH][HEIGHT];
        snake = new Snake();
        snake.snake = new ArrayList<>();
        food = new Food[1];
        direction = 1;
        initBoard();
        initFood();
        initSnake();
    }

    public void printBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (gameBoard[i][j] == 1) {
                    System.out.print("#");
                } else if (gameBoard[i][j] == 2) {
                    System.out.print("o");
                } else if (gameBoard[i][j] == 3) {
                    System.out.print("x");
                } else if (gameBoard[i][j] == 4) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void initBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (i == 0 || j == 0 || i == WIDTH - 1 || j == HEIGHT - 1) {
                    gameBoard[i][j] = 1;
                } else {
                    gameBoard[i][j] = 0;
                }
            }
        }
    }

    public void initFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH - 2) + 1;
        int y = random.nextInt(HEIGHT - 2) + 1;
        while (gameBoard[x][y] != 0) {
            x = random.nextInt(WIDTH - 2) + 1;
            y = random.nextInt(HEIGHT - 2) + 1;
        }
        food = new Food[]{new Food(x, y)};
        gameBoard[x][y] = 4;
    }

    public void initSnake() {
        snake.snake.add(new Point(5, 10));
        snake.snake.add(new Point(5, 11));
        snake.snake.add(new Point(5, 12));
        gameBoard[5][10] = 2;
        gameBoard[5][11] = 3;
        gameBoard[5][12] = 3;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("1-left || 2-up || 3-down || 4-right");
            int input = scanner.nextInt();
            if (input >= 1 && input <= 4) {
                direction = input;
            }
            Point pointHead = snake.snake.get(0);
            Point pointNewHead = new Point();
            if (direction == 1) {
                pointNewHead.setX(pointHead.getX());
                pointNewHead.setY(pointHead.getY() - 1);
            } else if (direction == 2) {
                pointNewHead.setX(pointHead.getX() - 1);
                pointNewHead.setY(pointHead.getY());
            } else if (direction == 3) {
                pointNewHead.setX(pointHead.getX() + 1);
                pointNewHead.setY(pointHead.getY());
            } else if (direction == 4) {
                pointNewHead.setX(pointHead.getX());
                pointNewHead.setY(pointHead.getY() + 1);
            }
            if (gameBoard[pointNewHead.getX()][pointNewHead.getY()] == 1) {
                Point pointOfTail = snake.snake.remove(snake.snake.size() - 1);
                gameBoard[pointOfTail.getX()][pointOfTail.getY()] = 0;
                System.out.println(pointNewHead.getX() + " " + pointHead.getY());
                if (pointNewHead.getX() == WIDTH - 1) {
                    pointNewHead.setX(1);
                } else if (pointNewHead.getX() == 0) {
                    pointNewHead.setX(WIDTH - 2);
                } else if (pointNewHead.getY() == HEIGHT - 1) {
                    pointNewHead.setY(1);
                } else if (pointNewHead.getY() == 0) {
                    pointNewHead.setY(HEIGHT - 2);
                }
                snake.snake.add(0, pointNewHead);
                gameBoard[pointNewHead.getX()][pointNewHead.getY()] = 2;
                for (int i = 1; i < snake.snake.size() - 1; i++) {
                    Point pointOfBody = snake.snake.get(i);
                    gameBoard[pointOfBody.getX()][pointOfBody.getY()] = 3;
                }
            } else if (gameBoard[pointNewHead.getX()][pointNewHead.getY()] == 3) {
                System.out.println("Game Over!!!");
                break;
            } else if (gameBoard[pointNewHead.getX()][pointNewHead.getY()] == 4) {
                snake.snake.add(0, pointNewHead);
                gameBoard[pointNewHead.getX()][pointNewHead.getY()] = 2;
                initFood();
            } else {
                Point pointOfTail = snake.snake.remove(snake.snake.size() - 1);
                gameBoard[pointOfTail.getX()][pointOfTail.getY()] = 0;
                snake.snake.add(0, pointNewHead);
                gameBoard[pointNewHead.getX()][pointNewHead.getY()] = 2;
                for (int i = 1; i < snake.snake.size(); i++) {
                    Point pointOfBody = snake.snake.get(i);
                    gameBoard[pointOfBody.getX()][pointOfBody.getY()] = 3;
                }
            }
        }
        scanner.close();
    }
}
