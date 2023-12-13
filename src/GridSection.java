import javax.swing.*;
import java.awt.*;

public class GridSection extends JPanel {
    boolean snakeHead;
    boolean snakeBody;
    boolean wall;
    boolean apple;

    GridSection(boolean snakeHead, boolean snakeBody, boolean wall, boolean apple){
        this.snakeHead = snakeHead;
        this.snakeBody = snakeBody;
        this.wall = wall;
        this.apple = apple;
    }

    public boolean isSnakeHead() {
        return snakeHead;
    }

    public void setSnakeHead(boolean snakeHead) {
        this.snakeHead = snakeHead;
    }

    public boolean isSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(boolean snakeBody) {
        this.snakeBody = snakeBody;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isApple() {
        return apple;
    }

    public void setApple(boolean apple) {
        this.apple = apple;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(wall){
            g.setColor(Color.BLACK);
        }else if(snakeHead){
            g.setColor(new Color(9, 100, 11));
        }else if(snakeBody){
            g.setColor(new Color(42, 138, 44));
        }else if(apple){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillRect(0,0, getWidth(), getHeight());
    }

}
