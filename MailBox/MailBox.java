import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by anastasia on 01.12.16.
 */
public class MailBox extends JFrame{
    private JComboBox comboBox1;
    private JButton createNewButton;
    private JButton reloadButton;
    JScrollPane scrollPane;
    private JTextArea message;
    private JPanel rootPanel;
    private static String myEmail = "nastiasunrise@gmail.com";
    private static String password = "kat.0987";
    private static Properties properties;
    private static Properties[] receivedProperties = null;
    private long time;

    public MailBox(){
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.store.protocol", "imaps");
        setTitle("Create Mail");
        //
        time = System.currentTimeMillis();
        //scrollPane = (JScrollPane)rootPanel.getComponent(5);
        //System.out.print(scrollPane.getComponentCount() + "\n");
        message = (JTextArea) rootPanel.getComponent(5);
        //message = new JTextArea(8, 40);
        //message.setVisible(true);
        message.setEditable(false);
        message.setMinimumSize(new Dimension(50, 100));
        //message.setText("AHHA");
        //rootPanel.add(message);
        setContentPane(rootPanel);
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        createNewButton.addActionListener(actionEvent -> new CreateMail(password));
        comboBox1.addActionListener(actionEvent -> {
            String s = (String) comboBox1.getSelectedItem();
            for(int i = 0; i < receivedProperties.length; i++){
                //System.out.print(receivedProperties[i].getProperty("topic") + "\n" + s + "\n");
                if(receivedProperties[i].getProperty("topic").equals(s)) {
                    showMessage(i);
                    break;
                }
            }
        });
        reloadButton.addActionListener(actionEvent -> checkMail());
        checkMail();
        /*for(int i = receivedProperties.length - 1; i >= 0; i-- ){
            System.out.print(receivedProperties[i].getProperty("message") + "\n");
            comboBox1.addItem(receivedProperties[i].getProperty("topic"));
        }*/
    }

    public static Properties getProperties(){
        return properties;
    }

    public static String getMyEmail() {
        return myEmail;
    }

    public void checkMail(){

        Session session = Session.getInstance(properties, null);
        try {
            comboBox1.removeAllItems();
            Store store = session.getStore();
            store.connect("imap.gmail.com", myEmail, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            receivedProperties = new Properties[10];
            for(int j = inbox.getMessageCount(); j > inbox.getMessageCount() - 10 ; j--){
                int i = inbox.getMessageCount() - j;
                receivedProperties[i] = new Properties();
                Message message = inbox.getMessage(j);
                Address[] from = message.getFrom();
                for (Address address: from) {
                    receivedProperties[i].put("sender", address.toString());
                }
                //Multipart mp = (Multipart) message.getContent();
                // message.getContent();
                Object content = message.getContent();
                if(content instanceof String){
                    receivedProperties[i].put("message", content);
                    receivedProperties[i].put("topic", new String("Without topic-" + i));
                } else
                    if(content instanceof Multipart){
                        Multipart mp = (Multipart) content;
                        if(mp != null) {
                            BodyPart body = mp.getBodyPart(0);
                            receivedProperties[i].put("topic", message.getSubject());
                            receivedProperties[i].put("message", body.getContent().toString());
                        } else {
                            receivedProperties[i].put("topic", message.getSubject());
                            receivedProperties[i].put("message", "null");
                        }
                    }
                comboBox1.addItem(receivedProperties[i].getProperty("topic"));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loop(){
        if(time + 100000 < System.currentTimeMillis()){
            checkMail();
        }
    }



    private void showMessage(int i){
        message.setText("From: " + receivedProperties[i].getProperty("sender") + "\n" +
                receivedProperties[i].getProperty("message"));

    }
    public static void main(String[] args){
        MailBox mailBox = new MailBox();
        /*while (true){
            mailBox.loop();
        }*/
    }
}
