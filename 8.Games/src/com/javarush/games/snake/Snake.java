package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

import static com.javarush.games.snake.SnakeGame.HEIGHT;
import static com.javarush.games.snake.SnakeGame.WIDTH;


public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        GameObject o1 = new GameObject(x, y);
        GameObject o2 = new GameObject(x + 1, y);
        GameObject o3 = new GameObject(x + 2, y);
        snakeParts.add(o1);
        snakeParts.add(o2);
        snakeParts.add(o3);
    }

    public void draw(Game game) {
        snakeParts.forEach(item -> {
            if (snakeParts.indexOf(item) == 0) {
                game.setCellValueEx(item.x, item.y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            } else {
                game.setCellValueEx(item.x, item.y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            }
        });
    }

    public void setDirection(Direction direction) {
        GameObject o1 = snakeParts.get(0);
        GameObject o2 = snakeParts.get(1);
        if (Math.abs(this.direction.ordinal() - direction.ordinal()) == 2
                || (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) && o1.y == o2.y
                || (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) && o1.x == o2.x) {
            return;
        }
        this.direction = direction;
    }

    public void move(Apple apple) {
        if (isAlive) {
            GameObject head = createNewHead();
            if (checkCollision(head) || head.x > WIDTH - 1 || head.x < 0 || head.y > HEIGHT - 1 || head.y < 0) {
                isAlive = false;
            } else {
                snakeParts.add(0, head);
                if (apple.x == head.x && apple.y == head.y) {
                    apple.isAlive = false;
                } else {
                    removeTail();
                }
            }
        }
    }

    public GameObject createNewHead() {
        switch (direction) {
            case LEFT:
                return new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
            case RIGHT:
                return new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
            case DOWN:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
            case UP:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
            default:
                return null;
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject o) {
        for (GameObject obj : snakeParts) {
            if (obj.x == o.x && obj.y == o.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
