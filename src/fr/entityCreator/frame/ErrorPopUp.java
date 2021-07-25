package fr.entityCreator.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ErrorPopUp {

    private JDialog frame;

    public ErrorPopUp(String msg) {
        Toolkit.getDefaultToolkit().beep();
        setup();
        addLabel(msg);
        this.addButton();
        this.frame.setVisible(true);
    }

    private void addLabel(String msg) {
        JLabel text = new JLabel("Error!");
        text.setForeground(new Color(255, 0, 0));
        text.setFont(new Font("Segoe UI", 1, 40));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        this.frame.add(text, gc);
        gc.gridy = 1;
        JLabel messageText = new JLabel(msg);
        messageText.setForeground(new Color(255, 0, 0));
        messageText.setFont(new Font("Segoe UI", 1, 15));
        this.frame.add(messageText, gc);
    }

    private void setup() {
        this.frame = new JDialog();
        this.frame.setAlwaysOnTop(true);
        this.frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frame.setSize(400, 200);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new GridBagLayout());
    }

    private void addButton() {
        JButton confirm = new JButton("Close");
        confirm.setFont(new Font("Segoe UI", 1, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                System.out.println("MODIFY ErrorPopUp 58");
                frame.dispose();
            }
        });
        this.frame.add(confirm, gc);
    }
}
