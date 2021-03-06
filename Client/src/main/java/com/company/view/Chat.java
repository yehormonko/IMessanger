/*
 * Created by JFormDesigner on Sat Mar 03 14:22:12 EET 2018
 */

package com.company.view;

import com.company.controller.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * class that creates chat panel
 */
public class Chat extends JPanel {
    public JTextPane getTextPane() {
        return textPane;
    }

    /**
     * creates new chat panel
     */
    public Chat() {
        initComponents();
    }

    private void sendBtnActionPerformed(ActionEvent e) {
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"chat message\"> <from>" + Main.getNick() + "</from> <text>" + textField.getText() +
                "</text> </class>";
        System.out.println(msg);
        Main.getOut().println(msg);
        Main.getOut().flush();
        textField.setText("");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        panel = new JPanel();
        sendBtn = new JButton();
        textField = new JTextField();
        scrollPane1 = new JScrollPane();
        textPane = new JTextPane();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                        Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

        setLayout(new BorderLayout(5, 5));

        //======== panel ========
        {
            panel.setLayout(new BorderLayout(5, 5));

            //---- sendBtn ----
            sendBtn.setText("Send");
            sendBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendBtnActionPerformed(e);
                }
            });
            panel.add(sendBtn, BorderLayout.EAST);

            //---- textField ----
            panel.add(textField, BorderLayout.CENTER);
        }
        add(panel, BorderLayout.SOUTH);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textPane);
        }
        add(scrollPane1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JPanel panel;
    private JButton sendBtn;
    private JTextField textField;
    private JScrollPane scrollPane1;
    private JTextPane textPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
