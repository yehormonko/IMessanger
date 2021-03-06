package Model.Client;

import java.util.ArrayList;

/**
 * class for representation XMLs from server in comfortable form
 */
public class Info {
    private String action;
    private String name;
    private String check;
    private String message;
    private String from;
    private String to;
    private ArrayList<String> userList;
    private ArrayList<String> banList;

    /**
     * initialize userList and banList
     */
    public Info() {
        userList = new ArrayList<>();
        banList = new ArrayList<>();
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public ArrayList<String> getBanList() {
        return banList;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCheck() {
        return check;
    }
}
