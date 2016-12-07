import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anastasia on 30.10.16.
 */
public class Sent extends JFrame{
    private JPanel panel1;
    private JButton OKButton;

    public Sent(){
        //super("Sent");
        setTitle("Message");
        setResizable(false);
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
}
