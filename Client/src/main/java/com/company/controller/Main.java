package com.company.controller;

import com.company.view.*;
import org.apache.log4j.Logger;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * main class
 */
public class Main {
    private static Start start;
    private static MainWindow mainWindow;
    private static Chat mainChat;
    private static ListOfUsers listOfUsers;
    private static Profile profile;
    private static Client client;
    private static String nick;
    private static String status = "Your status: User";
    private static String host = "127.0.0.1";
    private static int port = 7777;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static ArrayList<String> users;
    private static HashMap<String, PrivateChat> chats;
    private static boolean admin = false;
    private static boolean ban = false;
    private static Logger logger = Logger.getRootLogger();

    public static void setStart(Start start) {
        Main.start = start;
    }

    public static Start getStart() {
        return start;
    }

    public static void setBan(boolean ban) {
        Main.ban = ban;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        Main.admin = admin;
    }

    public static HashMap<String, PrivateChat> getChats() {
        return chats;
    }

    public static ArrayList<String> getUsers() {
        return users;
    }

    public static void addUser(String user) {
        users.add(user);
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static void setIn(BufferedReader in) {
        Main.in = in;
    }

    public static void setOut(PrintWriter out) {
        Main.out = out;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        Main.socket = socket;
    }

    public static Profile getProfile() {
        return profile;
    }

    public static void setStatus(String status) {
        Main.status = status;
    }

    public static String getStatus() {
        return status;
    }

    public static void setProfile(Profile profile) {
        Main.profile = profile;
    }

    public static Chat getMainChat() {
        return mainChat;
    }

    public static ListOfUsers getListOfUsers() {
        return listOfUsers;
    }

    public static void setMainChat(Chat mainChat) {
        Main.mainChat = mainChat;
    }

    public static void setListOfUsers(ListOfUsers listOfUsers) {
        Main.listOfUsers = listOfUsers;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static Client getClient() {
        return client;
    }

    public static String getNick() {
        return nick;
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }

    public static void setNick(String nick) {
        Main.nick = nick;
    }

    public static void setMainWindow(MainWindow mainWindow) {
        Main.mainWindow = mainWindow;
    }

    public static void setClient(Client client) {
        Main.client = client;
    }

    public static void main(String[] args) {
        File file = new File("ServerSettings.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
                PrintStream fileStream = new PrintStream(file);
                fileStream.println("127.0.0.1");
                fileStream.println("7777");
            } catch (IOException e) {
                logger.error("Error during creating \"ServerSettings.txt\"");
            }
            
        }
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader("ServerSettings.txt");
            
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("Error during open \"ServerSettings.txt\"");
            logger.error("Error during open \"ServerSettings.txt\"", e);
        }
        try {
            host = br.readLine();
            port = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("Error in reading text from \"ServerSettings.txt\"");
            logger.error("Error in reading text from \"ServerSettings.txt\"", e);
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                System.out.println("Error in \"Main\" during close of readers");
                logger.error("Error in \"Main\" during close of readers", e);
            }
        }
        start = new Start();
        users = new ArrayList<>();
        chats = new HashMap<>();
        users.add("A");
        users.add("B");
        users.add("C");
        start.setVisible(true);
    }
}
