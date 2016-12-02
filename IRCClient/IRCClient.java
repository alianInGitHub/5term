package com.mycompany.app;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anastasia on 29.11.16.
 */


public class IRCClient {

    private static String line;
    private static Scanner scanner = new Scanner(System.in);
    static boolean sendDataFlag = false;
    private static String server = "irc.freenode.net";
    private static String channel = "#irctest";

    private static BufferedWriter writer;
    private static BufferedReader reader;

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Enter your nickname: ");
        String login = scanner.nextLine();

        System.out.println("Connecting ...");

        Socket socket = new Socket(server, 6667);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Log on
        writer.write("NICK " + login + "\r\n");
        writer.write("USER " + login + " 8 * : Java IRC Test\r\n");
        writer.flush();

        // Read lines from the server until it tells us we have connected.
        while ((line = reader.readLine()) != null) {
            if (line.contains("004")) {
                // We are now logged in.
                break;
            } else if (line.contains("433")) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }

        // Join the channel
        writer.write("JOIN " + channel + "\r\n");
        writer.flush();

        Thread getData = new Thread(new Runnable() {
            //@Override
            public void run() {
                try {
                    // Keep reading lines from the server
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (!sendDataFlag && line.contains("366")) {
                            sendDataFlag = true;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Thread sendData = new Thread(new Runnable() {
            String msg = new String();

            //@Override
            public void run() {
                // Sending messages
                System.err.println("Write message and press ENTER to send it. Type \"/exit\" to leave.");
                while (true) {
                    try {
                        msg = scanner.nextLine();
                        if (msg.equals("/exit")) {
                            System.exit(0);
                        }
                        writer.write("PRIVMSG " + channel + " : " + msg + "\r\n");
                        writer.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        getData.start();
        while (!sendDataFlag) {
            Thread.sleep(20);
        }
        sendData.start();
    }
}