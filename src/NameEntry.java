import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameEntry extends JPanel {
    JButton startButton;
    JTextField nameTextField;
    JLabel enterName;

    public NameEntry(){
        setLayout(new GridLayout(3,1));
        this.enterName = new JLabel("Enter name");
        this.startButton = new JButton("Start");
        this.nameTextField = new JTextField();
        enterName.setHorizontalAlignment(SwingConstants.CENTER);
        nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterName);
        add(nameTextField);
        add(startButton);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }
}
