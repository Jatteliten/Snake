import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JFrame implements KeyListener {
    JPanel panel = new JPanel();
    GridSection[][] grid = new GridSection[20][20];
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    int move = RIGHT;
    int nextMove = RIGHT;
    int snakeSize = 1;
    ArrayList<GridSection> snakeParts = new ArrayList<>();
    JButton restartButton = new JButton("Restart");
    JLabel loseLabel;

    Frame(){
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);

        restartButton.setFont(new Font("Arial", Font.BOLD, 70));
        restartButton.addActionListener(e -> restartGame());

        initializeGrid();

        setVisible(true);
    }

    private void initializeGrid(){
        Random r = new Random();
        int startingAppleY = grid.length/2;
        int startingAppleX = grid.length/2;
        while(startingAppleY == grid.length/2 && startingAppleX == grid.length/2
            || startingAppleX == 0 || startingAppleX == grid.length - 1
            || startingAppleY == 0 || startingAppleY == grid.length - 1){
            startingAppleY = r.nextInt(grid.length - 2) + 1;
            startingAppleX = r.nextInt(grid.length - 2) + 1;
        }
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){
                GridSection tempGrid = new GridSection();
                if(i == 0 || i == grid.length - 1 || j == 0 || j == grid.length - 1){
                    tempGrid.setWall(true);
                }else if(i == grid.length/2 && j == grid.length/2){
                    tempGrid.setHasSnakeHead(true);
                }else if(i == startingAppleY && j == startingAppleX){
                    tempGrid.setApple(true);
                }
                grid[i][j] = tempGrid;
                panel.add(tempGrid);
            }
        }


        panel.setLayout(new GridLayout(20, 20));
        panel.setSize(400,400);
        requestFocus();
        add(panel);
    }

    void play(){
        Timer t = new Timer(100, new ActionListener() {
            int snakeHeadY = -1;
            int snakeHeadX = -1;
            @Override
            public void actionPerformed(ActionEvent e) {
                move = nextMove;
                for(int i = 0; i < grid.length; i++){
                    for (int j = 0; j < grid.length; j++){
                        if(grid[i][j].hasSnakeHead){
                            grid[i][j].setHasSnakeHead(false);
                            snakeHeadY = i;
                            snakeHeadX = j;
                            break;
                        }
                    }
                }

                if(move == UP) {
                    if(grid[snakeHeadY - 1][snakeHeadX].isWall || grid[snakeHeadY - 1][snakeHeadX].isHasSnakeSection()){
                        lose();
                        ((Timer) e.getSource()).stop();
                    }else if(grid[snakeHeadY - 1][snakeHeadX].isApple){
                        moveApple();
                    }
                    grid[snakeHeadY - 1][snakeHeadX].setHasSnakeHead(true);
                }else if(move == DOWN) {
                    if(grid[snakeHeadY + 1][snakeHeadX].isWall || grid[snakeHeadY + 1][snakeHeadX].isHasSnakeSection()){
                        lose();
                        ((Timer) e.getSource()).stop();
                    }else if(grid[snakeHeadY + 1][snakeHeadX].isApple){
                        moveApple();
                    }
                    grid[snakeHeadY + 1][snakeHeadX].setHasSnakeHead(true);
                }else if(move == LEFT) {
                    if(grid[snakeHeadY][snakeHeadX - 1].isWall || grid[snakeHeadY][snakeHeadX - 1].isHasSnakeSection()){
                        lose();
                        ((Timer) e.getSource()).stop();
                    }else if(grid[snakeHeadY][snakeHeadX - 1].isApple){
                        moveApple();
                    }
                    grid[snakeHeadY][snakeHeadX - 1].setHasSnakeHead(true);
                }else if(move == RIGHT) {
                    if(grid[snakeHeadY][snakeHeadX + 1].isWall || grid[snakeHeadY][snakeHeadX + 1].isHasSnakeSection()){
                        lose();
                        ((Timer) e.getSource()).stop();
                    }else if(grid[snakeHeadY][snakeHeadX + 1].isApple){
                        moveApple();
                    }
                    grid[snakeHeadY][snakeHeadX + 1].setHasSnakeHead(true);
                }

                if(!snakeParts.isEmpty()) {
                    snakeParts.add(grid[snakeHeadY][snakeHeadX]);
                    snakeParts.remove(0);
                    for(GridSection[] g: grid){
                        for(GridSection gs: g){
                            gs.setHasSnakeSection(snakeParts.contains(gs));
                        }
                    }
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        t.start();
    }


    void lose(){
        remove(panel);
        loseLabel = new JLabel("Points: " + snakeSize);
        loseLabel.setFont(new Font("Arial", Font.BOLD, 60));
        loseLabel.setSize(70, 70);
        add(loseLabel, BorderLayout.NORTH);
        add(restartButton, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    void restartGame(){
        remove(restartButton);
        remove(loseLabel);
        for(GridSection[] g: grid){
            for(GridSection gs: g){
                panel.remove(gs);
            }
        }
        remove(panel);
        snakeParts.clear();
        initializeGrid();
        snakeSize = 1;
        nextMove = RIGHT;
        play();
    }

    void moveApple(){
        snakeSize++;
        for(GridSection[] g: grid){
            for(GridSection gs: g){
                if(gs.isApple){
                    gs.setApple(false);
                    snakeParts.add(gs);
                    gs.setHasSnakeSection(true);
                }
            }
        }
        Random r = new Random();
        int setAppleY, setAppleX;
        while(true){
            setAppleY = r.nextInt(grid.length - 2) + 1;
            setAppleX = r.nextInt(grid.length - 2) + 1;
            if(!grid[setAppleY][setAppleX].isHasSnakeHead() && !snakeParts.contains(grid[setAppleY][setAppleX])){
                grid[setAppleY][setAppleX].setApple(true);
                break;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> {
                if(move != DOWN) nextMove = UP;
            }
            case KeyEvent.VK_DOWN -> {
                if(move != UP) nextMove = DOWN;
            }
            case KeyEvent.VK_LEFT -> {
                if(move != RIGHT) nextMove = LEFT;
            }
            case KeyEvent.VK_RIGHT -> {
                if(move != LEFT) nextMove = RIGHT;}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}