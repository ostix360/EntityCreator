package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.entity.component.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddComponentPanel extends JPanel {

    private JComboBox<ComponentType> componentMenu;
    private Entity currentEntity = null;
    private ComponentListPanel listPanel;

    public AddComponentPanel(int width, int height, ComponentListPanel listPanel) {
        this.listPanel = listPanel;
        listPanel.setAddPanel(this);
        super.setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        addComponentsMenu();
        addButton();
    }

    public Entity getCurrentEntity() {
        return this.currentEntity;
    }

    public void returnComponentToList(ComponentType type) {
        this.componentMenu.addItem(type);
    }


    public void removeComponent(ComponentType type) {
        this.componentMenu.removeItem(type);
    }

    public void showStaticEntity(Entity entity) {
        if (entity == this.currentEntity) {
            return;
        }
        this.currentEntity = entity;
        List<ComponentType> goodTypes = new ArrayList<>();
        for (ComponentType type : ComponentType.values()) {
            goodTypes.add(type);
        }
        for (Component component : entity.getComponents()) {
            goodTypes.remove(component.getType());
        }
        ComponentType[] typeArray = new ComponentType[goodTypes.size()];
        for (int i = 0; i < typeArray.length; i++) {
            typeArray[i] = ((ComponentType) goodTypes.get(i));
        }
        this.componentMenu.setModel(new DefaultComboBoxModel(typeArray));
        this.listPanel.setList(null);
    }
//
//    public void showForAnimatedEntity(AnimatedEntity entity) {
//        if (entity == this.currentEntity) {
//            return;
//        }
//        this.currentEntity = entity;
//        List<ComponentType> goodTypes = new ArrayList();
//        for (ComponentType type : ComponentType.values()) {
//            goodTypes.add(type);
//        }
//        for (Component component : entity.getFabricator().getComponents()) {
//            goodTypes.remove(component.getType());
//        }
//        ComponentType[] typeArray = new ComponentType[goodTypes.size()];
//        for (int i = 0; i < typeArray.length; i++) {
//            typeArray[i] = ((ComponentType) goodTypes.get(i));
//        }
//        this.componentMenu.setModel(new DefaultComboBoxModel(typeArray));
//        this.listPanel.setList(null);
//    }


    public void clearAll() {
        this.componentMenu.setModel(new DefaultComboBoxModel(new ComponentType[0]));
        this.listPanel.setList(null);
        this.currentEntity = null;
    }


    private void addComponentsMenu() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 10.0D;
        gc.weighty = 1.0D;
        this.componentMenu = new JComboBox();
        this.componentMenu.setFont(MainFrame.SMALL_FONT);
        add(this.componentMenu, gc);
    }

    private void addButton() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 2;
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        JButton addButton = new JButton("Add");
        addButton.setFont(MainFrame.SMALL_FONT);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (currentEntity != null) {

                    ((ComponentType) componentMenu.getSelectedItem()).createComponent(currentEntity, listPanel);

                }
            }
        });
        add(addButton, gc);
    }
}
