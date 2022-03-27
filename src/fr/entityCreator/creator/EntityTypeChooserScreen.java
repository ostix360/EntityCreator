package fr.entityCreator.creator;

import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.particle.*;
import fr.entityCreator.graphics.particles.particleSpawn.*;
import fr.entityCreator.graphics.particles.particleSpawn.Point;
import fr.ostix.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EntityTypeChooserScreen {

    private JDialog frame;
    private Class chosen;

    public EntityTypeChooserScreen() {
        setUpFrame();
        addLabel();
        addSpinner();
        addButton();
        this.frame.setVisible(true);
    }

    private void addSpinner() {
        final JPanel shapesPanel = new JPanel();
        shapesPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        shapesPanel.setLayout(new GridBagLayout());
        this.frame.add(shapesPanel);

        Class[] possibleShapes = Entities.getEntities().toArray(new Class[]{});
        final JComboBox<Class> type = new JComboBox();
        type.setModel(new DefaultComboBoxModel<>(possibleShapes));
        type.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosen = (Class) type.getSelectedItem();
            }
        });
        shapesPanel.add(type);
    }

    public Class getChosen() {
        return chosen;
    }

    private void setUpFrame() {
        this.frame = new JDialog();
        this.frame.setAlwaysOnTop(true);
        this.frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setSize(450, 600);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new GridBagLayout());
    }

    private void addButton() {
        JButton confirm = new JButton("Select");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                frame.dispose();

            }
        });
        this.frame.add(confirm, gc);
    }

    private void addLabel() {
        JLabel text = new JLabel("Choose an entity type!");
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        this.frame.add(text, gc);
    }
}
