package fr.entityCreator.frame;

import fr.entityCreator.entity.component.Component;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentListPanel extends JPanel {
    private final int width;
    private final int height;
    private final MainFrame frame;
    private Map<Component, JButton> componentTabs = new HashMap<>();
    private AddComponentPanel addComponentPanel;

    public ComponentListPanel(int width, int height, MainFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;
        setPreferredSize(new Dimension(width, height));
    }

    public void addComponent(Component component) {
        this.addComponentPanel.removeComponent(component.getType());
        JButton button = new JButton(component.getType().toString());
        button.setPreferredSize(new Dimension(this.width - 10, 25));
        button.setFont(MainFrame.SMALL_FONT);
        this.componentTabs.put(component, button);
        add(button);
        button.setForeground(new Color(255, 100, 0));
        button.addActionListener((e) -> {
            frame.setComponentPanel(component.getComponentPanel(ComponentListPanel.this));
            button.setForeground(new Color(0, 0, 0));
        });
        super.validate();
        super.repaint();
    }


    public void removeComponent(Component component, boolean rootDeletedComponent) {
        if (rootDeletedComponent) {
//            for (Component parent : component.getRequiredComponents()) {
//                parent.removeDependentComponent(component);
//            }
            this.frame.clearComponentPanel();
        }
//        List<Component> dependentComponents = component.getDependentComponents();
//        for (Component dependentComponent : dependentComponents) {
//            removeComponent(dependentComponent, false);
//        }
        JButton button = (JButton) this.componentTabs.remove(component);
        remove(button);
        this.addComponentPanel.returnComponentToList(component.getType());
        this.addComponentPanel.getCurrentEntity().getComponents().remove(component);
        super.validate();
        super.repaint();
    }

    public void setList(List<Component> components) {
        for (JButton button : this.componentTabs.values()) {
            remove(button);
        }
        this.componentTabs.clear();
        if (components != null) {
            for (Component component : components) {
                addComponent(component);
            }
        }
        super.validate();
        super.repaint();
    }
}
