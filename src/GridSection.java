import javax.swing.*;
import java.awt.*;

public class GridSection extends JPanel {
    boolean hasSnakeHead;
    boolean hasSnakeSection;
    boolean isWall;
    boolean isApple;

    GridSection(){
        this.hasSnakeHead = false;
        this.hasSnakeSection = false;
        this.isWall = false;
        this.isApple = false;
    }

    public boolean isHasSnakeHead() {
        return hasSnakeHead;
    }

    public void setHasSnakeHead(boolean hasSnakeHead) {
        this.hasSnakeHead = hasSnakeHead;
    }

    public boolean isHasSnakeSection() {
        return hasSnakeSection;
    }

    public void setHasSnakeSection(boolean hasSnakeSection) {
        this.hasSnakeSection = hasSnakeSection;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isApple() {
        return isApple;
    }

    public void setApple(boolean apple) {
        isApple = apple;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if(isWall){
            g.fillRect(0, 0, getWidth(), getHeight());
        }else if(isHasSnakeHead()){
            g.setColor(new Color(9, 100, 11));
            g.fillRect(0,0, getWidth(), getHeight());
        }else if(isHasSnakeSection()){
            g.setColor(new Color(42, 138, 44));
            g.fillRect(0,0, getWidth(), getHeight());
        }else if(isApple()){
            g.setColor(Color.RED);
            g.fillRect(0,0, getWidth(), getHeight());
        }
    }


}
