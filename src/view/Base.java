package view;

import javax.swing.*;

public class Base extends JFrame{
    private JPanel panel1;
    private JCheckBox checkBox1;
    private JLabel OurTroups;
    private JLabel EnemieTroups;

    private JCheckBox checkBox2;
    private JButton a1Button;
    private JButton a5Button;
    private JButton fillButton;
    private JButton allButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton clearButton;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JTable table1;

    public Base(String name) {
        setName(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,600);
        //JButton button = new JButton("Click me");
        this.getContentPane().add(panel1); // Adds Button to content pane of frame
        this.setVisible(true);
    }
}
