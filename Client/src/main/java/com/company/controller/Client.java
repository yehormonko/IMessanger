package com.company.controller;

import com.company.view.PrivateChat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable{
    private boolean check;
    private boolean logedIn;
    private boolean created;

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isLogedIn() {
        return logedIn;
    }

    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Client(String host, int port){
        try {
            Main.setSocket(new Socket(host, port));
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Not connected");
            JOptionPane.showMessageDialog(null, "Can't connect to server");
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(Main.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setIn(new BufferedReader(reader));

        try {
            Main.setOut(new PrintWriter(Main.getSocket().getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread listenThread = new Thread(this);
        listenThread.start();
    }

    @Override
    public void run() {
        String input;
        try {
            while((input = Main.getIn().readLine()) != null) {
                if(input.indexOf('<') != -1) {
                    input = input.substring(input.indexOf('<'));
                    System.out.println(input);
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                    Document document = null;
                    try {
                        builder = factory.newDocumentBuilder();
                        document = builder.parse(new InputSource(new StringReader(input)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    document.normalize();
                    Element main = (Element) document.getElementsByTagName("class").item(0);
                    String action = main.getAttributeNode("event").getValue();
                    if(action.equals("answer for login")) {
                        Element name = (Element) main.getElementsByTagName("name").item(0);
                        String nameStr = name.getTextContent();
                        Element bool = (Element) main.getElementsByTagName("result").item(0);
                        String checkStr = bool.getTextContent();
                        if(checkStr.equals("true")) {
                            logedIn = true;
                        } else {
                            logedIn = false;
                        }
                    }
                    if(action.equals("answer for creating user")) {
                        Element name = (Element) main.getElementsByTagName("name").item(0);
                        String nameStr = name.getTextContent();
                        Element bool = (Element) main.getElementsByTagName("result").item(0);
                        String checkStr = bool.getTextContent();
                        if(checkStr.equals("true")) {
                            created = true;
                        } else {
                            created = false;
                        }
                        int y = 0;
                    }
                    if (action.equals("chat message")) {
                        Element from = (Element) main.getElementsByTagName("from").item(0);
                        String username = from.getTextContent();
                        Element text = (Element) main.getElementsByTagName("text").item(0);
                        String message = text.getTextContent();
                        Main.getMainChat().getTextPane().setText(Main.getMainChat().getTextPane().getText() + "\n" + username + ": " + message);
                        Main.getMainChat().getTextPane().setCaretPosition(Main.getMainChat().getTextPane().getText().length());
                    }
                    if (action.equals("answer for changing")) {
                        Element name = (Element) main.getElementsByTagName("name").item(0);
                        String nameStr = name.getTextContent();
                        Element bool = (Element) main.getElementsByTagName("result").item(0);
                        String checkStr = bool.getTextContent();
                        if(checkStr.equals("true")) {
                            check = true;
                            Main.setNick(nameStr);
                        } else {
                            check = false;
                        }
                    }
                    if (action.equals("return online list")) {
                        Main.getUsers().clear();
                        for(int i = 0; i < main.getElementsByTagName("name").getLength(); i++) {
                            Element name = (Element) main.getElementsByTagName("name").item(i);
                            String nameStr = name.getTextContent();
                            Element ban = (Element) main.getElementsByTagName("ban").item(i);
                            String banStr = ban.getTextContent();
                            if(banStr.equals("true")) {
                                Main.addUser("<html><strike>" + nameStr + "</html></strike>");
                                Main.setBan(true);
                            } else {
                                Main.addUser(nameStr);
                                Main.setBan(false);
                            }
                        }
                    }
                    if (action.equals("user banned")) {
                        check = false;
                    }
                    if (action.equals("you are admin")) {
                        Main.setAdmin(true);
                        Main.setStatus("Admin");
                    }
                    if (action.equals("answer for banning")) {
                        Element name = (Element) main.getElementsByTagName("name").item(0);
                        String nameStr = name.getTextContent();
                        Element result = (Element) main.getElementsByTagName("result").item(0);
                        String resultStr = result.getTextContent();
                        if(resultStr.equals("true")) {
                            JOptionPane.showMessageDialog(null, "User " + nameStr + " is banned!");
                        }
                    }
                    if (action.equals("answer for banning")) {
                        Main.setAdmin(false);
                        Main.setStatus("User");
                    }
                    if (action.equals("you are banned")) {
                        Main.setBan(true);
                        Main.setStatus("User(banned)");
                        Main.getMainWindow().getChatBtn().setEnabled(false);
                        Main.getMainWindow().getListOfUsersBtn().setEnabled(false);
                        Main.getMainWindow().getProfileBtn().setEnabled(false);
                        JOptionPane.showMessageDialog(null, "You are banned");
                    }
                    if (action.equals("message")) {
                        Element from = (Element) main.getElementsByTagName("from").item(0);
                        final String fromStr = from.getTextContent();
                        Element to = (Element) main.getElementsByTagName("to").item(0);
                        String toStr = to.getTextContent();
                        Element text = (Element) main.getElementsByTagName("text").item(0);
                        String textStr = text.getTextContent();
                        if (!Main.getChats().containsKey(fromStr)) {
                            PrivateChat chat = new PrivateChat(fromStr);
                            Main.getChats().put(fromStr, chat);
                            JFrame frame = new JFrame();
                            frame.addWindowListener(new WindowAdapter(){
                                public void windowClosing(WindowEvent e){
                                    Main.getChats().remove(fromStr);
                                }
                            });
                            frame.setSize(398, 300);
                            frame.setTitle(fromStr);
                            frame.add(chat);
                            frame.setVisible(true);
                        }
                        Main.getChats().get(fromStr).getTextPane().setText(Main.getChats().get(fromStr).getTextPane().getText()
                                + "\n" + fromStr + ": " + textStr);
                        Main.getChats().get(fromStr).getTextPane().setCaretPosition(Main.getChats().get(fromStr).getTextPane().getText().length());
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
