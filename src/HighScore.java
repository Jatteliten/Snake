import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HighScore extends JPanel {
    private static final Path path = Paths.get("src/High Score.txt");
    ArrayList<String> highScores = new ArrayList<>();
    private static final JButton restart = new JButton("Restart");

    public HighScore(){
        readHighScores();
        setLayout(new GridLayout(6, 2));
        restart.setFont(new Font("Arial", Font.BOLD, 40));
        generateNewHighScorePanels();
    }

    public JButton getRestartButton() {
        return restart;
    }

    /**
     * Generates new panels from the high score Array
     */
    private void generateNewHighScorePanels(){
        removeAll();
        for(String s: highScores){
            JLabel name = new JLabel(s.substring(0, s.lastIndexOf(":")));
            name.setHorizontalAlignment(SwingConstants.RIGHT);
            name.setFont(new Font("Arial", Font.BOLD, 25));
            add(name);
            JLabel score = new JLabel((s.substring(s.lastIndexOf(":"))));
            score.setFont(new Font("Arial", Font.BOLD, 25));
            add(score);
        }
        add(restart);
    }

    /**
     * Reads high scores from file and puts them into arrayList highScores
     */
    public void readHighScores(){
        highScores.clear();
        try(BufferedReader br = Files.newBufferedReader(path)){
            String reader;
            while((reader = br.readLine()) != null){
                highScores.add(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generateNewHighScorePanels();
    }

    /**
     * Checks if the score of current round is higher than anything in the high score list.
     * Adds the score to the list if that is the case.
     * Writes the updated high score list to file.
     * @param score score from current game
     */
    public void writeHighScores(String score){
        for(int i = 0; i < highScores.size(); i++){
            if(Integer.parseInt(score.substring(score.lastIndexOf(":") + 2)) >
                    Integer.parseInt(highScores.get(i).substring(highScores.get(i).lastIndexOf(":") + 2))){
                for(int j = highScores.size() - 1; j > i; j--){
                    highScores.set(j, highScores.get(j - 1));
                }
                highScores.set(i, score);
                break;
            }
        }
        try(BufferedWriter wr = Files.newBufferedWriter(path)){
            StringBuilder write = new StringBuilder();
            for(int i = 0; i < highScores.size(); i++){
                write.append(highScores.get(i));
                if(i != highScores.size() - 1){
                    write.append("\n");
                }
            }
            wr.write(String.valueOf(write));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readHighScores();
    }
}
