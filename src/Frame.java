import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JFrame implements KeyListener {
    JPanel panel = new JPanel();
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private int move = RIGHT;
    private int nextMove = RIGHT;
    private int snakeSize = 0;
    GridSection[][] grid = new GridSection[20][20];
    ArrayList<GridSection> snakeParts = new ArrayList<>();
    String name;
    NameEntry nameEntry = new NameEntry();
    HighScore highScore = new HighScore();

    Frame(){
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);

        highScore.getRestart().addActionListener(e -> {
            restartGame();
        });

        initializeNameEntry();
        add(nameEntry);

        setVisible(true);
    }

    private void initializeNameEntry(){
        nameEntry.getStartButton().addActionListener(e -> {
            if(!nameEntry.getNameTextField().getText().isEmpty()){
                name = nameEntry.getNameTextField().getText();
            }else{
                name = "Guest";
            }
            initializeGrid();
            nextMove = -1;
            play();
            remove(nameEntry);
        });
    }

    /**
     * Sets up the starting grid.
     * Adds walls, a snake head, body part and an apple
     */
    private void initializeGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){
                GridSection tempGrid = new GridSection();
                if(i == 0 || i == grid.length - 1 || j == 0 || j == grid.length - 1){
                    tempGrid.setWall(true);
                }else if(i == grid.length/2 && j == grid.length/2 - 1){
                    tempGrid.setSnakeBody(true);
                    snakeParts.add(tempGrid);
                }else if(i == grid.length/2 && j == grid.length/2){
                    tempGrid.setSnakeHead(true);
                }
                grid[i][j] = tempGrid;
                panel.add(tempGrid);
            }
        }
        moveApple();
        panel.setLayout(new GridLayout(grid.length, grid.length));
        panel.setSize(400,400);
        requestFocus();
        add(panel);
    }

    /**
     * Play the game.
     * Head moves based on key presses and the body follows behind.
     * If the snake head hits a wall or one of its body parts the game is lost and the lose method is called.
     * If an apple is "eaten" the moveApple method is called.
     */
    public void play(){
        Timer t = new Timer(80, new ActionListener() {
            int snakeHeadY = -1, snakeHeadX = -1;
            @Override
            public void actionPerformed(ActionEvent e) {
                move = nextMove;
                for(int i = 0; i < grid.length; i++){
                    for (int j = 0; j < grid.length; j++){
                        if(grid[i][j].snakeHead){
                            grid[i][j].setSnakeHead(false);
                            snakeHeadY = i;
                            snakeHeadX = j;
                            break;
                        }
                    }
                }

                if(move == UP) {
                    moveSnakeHead(snakeHeadY - 1, snakeHeadX, e);
                }else if(move == DOWN) {
                    moveSnakeHead(snakeHeadY + 1, snakeHeadX, e);
                }else if(move == LEFT) {
                    moveSnakeHead(snakeHeadY, snakeHeadX - 1, e);
                }else if(move == RIGHT) {
                    moveSnakeHead(snakeHeadY, snakeHeadX + 1, e);
                }

                if(!snakeParts.isEmpty()) {
                    snakeParts.add(grid[snakeHeadY][snakeHeadX]);
                    snakeParts.remove(0);
                    for(GridSection[] g: grid){
                        for(GridSection gs: g){
                            gs.setSnakeBody(snakeParts.contains(gs));
                        }
                    }
                }
                panel.revalidate();
                panel.repaint();
            }

            private void moveSnakeHead(int snakeHeadY, int snakeHeadX, ActionEvent e) {
                if (grid[snakeHeadY][snakeHeadX].isWall() ||
                        grid[snakeHeadY][snakeHeadX].isSnakeBody()) {
                    lose();
                    ((Timer) e.getSource()).stop();
                } else if (grid[snakeHeadY][snakeHeadX].isApple()) {
                    moveApple();
                    snakeSize++;
                }
                grid[snakeHeadY][snakeHeadX].setSnakeHead(true);
            }
        });
        t.start();
    }

    /**
     * Removes the game from the frame.
     * Displays accumulated score and a restart button.
     * Pressing the restart button calls the restartGame method.
     */
    private void lose(){
        highScore.writeHighScores(name + ": " + snakeSize);
        remove(panel);
        add(highScore);
        revalidate();
        repaint();
    }

    /**
     * Restarts the game in the frame.
     * Resets points and snake size.
     */
    private void restartGame(){
        panel.removeAll();
        remove(panel);
        snakeParts.clear();
        initializeGrid();
        snakeSize = 0;
        nextMove = -1;
        play();
    }

    /**
     * Moves the apple if it has been eaten.
     * Checks if the spawn point is inside the snake head/body or a wall and retries if it is.
     */
    private void moveApple(){
        for(GridSection[] g: grid){
            for(GridSection gs: g){
                if(gs.apple){
                    gs.setApple(false);
                    gs.setSnakeBody(true);
                    snakeParts.add(gs);
                }
            }
        }
        Random r = new Random();
        int setAppleY, setAppleX;
        while(true){
            setAppleY = r.nextInt(grid.length - 2) + 1;
            setAppleX = r.nextInt(grid.length - 2) + 1;
            if(!grid[setAppleY][setAppleX].isSnakeHead() && !grid[setAppleY][setAppleX].isSnakeBody()){
                grid[setAppleY][setAppleX].setApple(true);
                break;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Sets which way the snake moves based on arrow key presses or WASD.
     * @param e - Keypress
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            if (move != DOWN) {
                nextMove = UP;
            }
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            if (move != UP) {
                nextMove = DOWN;
            }
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            if (move != RIGHT) {
                nextMove = LEFT;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            if (move != LEFT) {
                nextMove = RIGHT;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {}
}