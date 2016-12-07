import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anastasia on 01.12.16.
 */
public class CreateMail extends JFrame{
    private JTextField address;
    private JTextField topic;
    private JTextArea messageText;
    private JButton sendButton;
    private JPanel rootPanel;
    private JButton cencelButton;
    private String password;

    public CreateMail(String password){
        //super("Write mail");
        this.password = password;
        setTitle("Create Mail");
        setContentPane(rootPanel);
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        sendButton.addActionListener(actionEvent -> send());
        cencelButton.addActionListener(actionEvent -> cancel());
    }

    public void cancel(){
        this.dispose();
    }


    public void send(){
        Session session = Session.getInstance(MailBox.getProperties(), new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(MailBox.getMyEmail(), password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailBox.getMyEmail()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address.getText()));
            message.setSubject(topic.getText());
            message.setText(messageText.getText());
            Transport.send(message);
            //create new OK message box
            Sent sent = new Sent();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
