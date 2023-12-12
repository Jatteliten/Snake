import javax.swing.*;
import java.awt.*;

public class GridSection extends JPanel {
    boolean snakeHead;
    boolean snakeBody;
    boolean wall;
    boolean apple;

    GridSection(){
        this.snakeHead = false;
        this.snakeBody = false;
        this.wall = false;
        this.apple = false;
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

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isApple() {
        return apple;
    }

    public void setApple(boolean apple) {
        this.apple = apple;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if(wall){
            g.setColor(Color.BLACK);
        }else if(isSnakeHead()){
            g.setColor(new Color(9, 100, 11));
        }else if(isSnakeBody()){
            g.setColor(new Color(42, 138, 44));
        }else if(isApple()){
            g.setColor(Color.RED);
        }
        g.fillRect(0,0, getWidth(), getHeight());
    }

}
