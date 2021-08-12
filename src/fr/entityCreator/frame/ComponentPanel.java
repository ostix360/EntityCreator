package fr.entityCreator.frame;

import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ComponentPanel extends JPanel {
    protected JPanel description;
    protected JPanel settings;
    protected ComponentListPanel componentList;
    protected Component component;
    //private JLabel requiredBy;


    public ComponentPanel(ComponentListPanel componentList, Component component) {

        //component.registerPanel(this);
        this.component = component;
        this.componentList = componentList;
        setPreferredSize(new Dimension(410, 640));
        setBorder(BorderFactory.createTitledBorder(component.getType().toString()));
        addDescriptionPanel(component);
        this.settings = new JPanel();
        this.settings.setPreferredSize(new Dimension(390, 500));
        this.settings.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        add(this.settings);
    }

    private void addDescriptionPanel(Component component) {
        this.description = new JPanel();
        this.description.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        this.description.setPreferredSize(new Dimension(390, 100));
        this.description.setLayout(new GridBagLayout());
        Border border = this.description.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        this.description.setBorder(new CompoundBorder(border, margin));
        add(this.description);
        String text = component.getType().toString();
        String desc = String.format("<html><div WIDTH=%d>%s</div><html>", 370, text);
        JLabel label = new JLabel(desc);
        label.setFont(MainFrame.VSMALL_FONT);
        this.description.add(label, getGC(0, 0));

//        ComponentType[] requiresThese = component.getType().getRequiredComponents();
//        String requiresString = "Requires: ";
//        for (ComponentType requiresThis : requiresThese) {
//            requiresString = requiresString + requiresThis.toString() + ", ";
//        }
//        JLabel requires = new JLabel(requiresString);
//        requires.setFont(MainFrame.VSMALL_FONT);
//        requires.setForeground(new Color(0, 0, 255));
//        this.description.add(requires, getGC(0, 1));
//        String requiredByString = "Required By: ";
//        for (Component requiredBy : component.getDependentComponents()) {
//            requiredByString = requiredByString + requiredBy.getType().toString() + ", ";
//        }
//        this.requiredBy = new JLabel(requiredByString);
//        this.requiredBy.setFont(MainFrame.VSMALL_FONT);
//        this.requiredBy.setForeground(new Color(244, 0, 255));
//        this.description.add(this.requiredBy, getGC(0, 2));
        JButton remove = new JButton("Remove");
        remove.setFont(MainFrame.SMALL_FONT);
        this.description.add(remove, getGC(0, 3));
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               componentList.removeComponent(component, true);
            }
        });
    }

    private GridBagConstraints getGC(int x, int y) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        return gc;
    }

    public void destroy(){
        this.component.unregisterPanel(this);
        this.cleanUp();
    };

    public abstract void cleanUp();
}
