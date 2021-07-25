package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class StandardInfoPanel extends JPanel {

    private JPanel idPanel;
    private JPanel modelPanel;
    private JButton modelButton;
//    private JPanel distanceSettings;
//    private JFormattedTextField visibility;
//    private JFormattedTextField radiusField;
//    private JCheckBox hasRefractionCheck;
    private JTextField id;
    private Entity entity;
    private MainFrame frame;


    public StandardInfoPanel(int width, int height, Entity entity, MainFrame frame) {
        this.entity = entity;
        this.frame = frame;
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        initFields();
        updateFields();
        this.setVisible(true);
    }

    private void updateFields() {
        //this.id.setText(Integer.toString(this.entity.hashCode()));
        if (this.entity.hasModel()) {
            this.modelButton.setText("Changer un Model...");
            this.modelButton.setForeground(new Color(0, 255, 0));
        } else {
            this.modelButton.setText("Choisir un Model...");
            this.modelButton.setForeground(new Color(255, 0, 0));
        }
//        this.visibility.setText(Float.toString(this.entity.getVisibleRange()));
//        this.radiusField.setText(Float.toString(this.entity.getFurthestPoint()));
//        MainApp.sphere.getTransformation().setScale(this.entity.getFurthestPoint());
//        this.hasRefractionCheck.setSelected(this.entity.isVisibleUnderWater());
        }


//    public void updateRadius() {
//        /* 105 */
//        this.radiusField.setText(Float.toString(this.entity.getFurthestPoint()));
//        /* 106 */
//        MainApp.sphere.getTransformation().setScale(this.entity.getFurthestPoint());
//        /*     */
//    }

    private void initFields() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
       // initIDPanel(gc);
        gc.gridy = 1;
        initModelButton(gc);
        gc.gridy = 2;
        //initDistanceSettings(gc);
    }

    private void initModelButton(GridBagConstraints gc) {
        this.modelPanel = new JPanel();
        if (this.entity.hasModel()) {
            this.modelButton = new JButton("Change Model...");
            this.modelButton.setForeground(new Color(0, 255, 0));
        } else {
            this.modelButton = new JButton("Choose Model...");
            this.modelButton.setForeground(new Color(255, 0, 0));
        }
        this.modelButton.setFont(MainFrame.SMALL_FONT);
        this.modelButton.addActionListener((e) ->
        {
            new ModelChooseScreen(frame, entity, modelButton, this);

        });
        modelButton.setVisible(true);
        this.modelPanel.add(this.modelButton);
        add(this.modelPanel, gc);
    }

//    private void initDistanceSettings(GridBagConstraints gc) {
//        this.distanceSettings = new JPanel();
//        JLabel visibilityLabel = new JLabel("Visibility: ");
//        visibilityLabel.setFont(MainFrame.SMALL_FONT);
//        this.distanceSettings.add(visibilityLabel);
//        this.visibility = createTextField(5);
//        this.visibility.setText(Float.toString(this.entity.getVisibleRange()));
//        this.visibility.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void insertUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void warn() {
//                if (StandardInfoPanel.this.visibility.getText().equals("")) {
//                    return;
//                }
//                StandardInfoPanel.this.entity.setVisibleRange(Float.parseFloat(StandardInfoPanel.this.visibility.getText().replaceAll(",", "")));
//            }
//        });
//        this.distanceSettings.add(this.visibility);
//        JLabel furthest = new JLabel("Radius: ");
//        furthest.setFont(MainFrame.SMALL_FONT);
//        this.distanceSettings.add(furthest);
//        this.radiusField = createTextField(5);
//        this.radiusField.setText(Float.toString(this.entity.getFurthestPoint()));
//        this.radiusField.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void insertUpdate(DocumentEvent e) {
//                warn();
//            }
//
//            public void warn() {
//                if (StandardInfoPanel.this.radiusField.getText().equals("")) {
//                    return;
//                }
//                StandardInfoPanel.this.entity.setFurthestPoint(Float.parseFloat(StandardInfoPanel.this.radiusField.getText().replaceAll(",", "")));
//                MainApp.sphere.getTransformation().setScale(StandardInfoPanel.this.entity.getFurthestPoint());
//            }
//        });
//        this.distanceSettings.add(this.radiusField);
//        add(this.distanceSettings, gc);
//    }




    private void initIDPanel(GridBagConstraints gc) {
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        this.idPanel = new JPanel();
        add(this.idPanel, gc);
        this.idPanel.setLayout(new GridBagLayout());
        JLabel label = new JLabel("ID: ");
        label.setFont(MainFrame.MEDIUM_FONT);
        this.idPanel.add(label, gc2);
        this.id = new JTextField(Integer.toString(10), 10);
        this.id.setFont(MainFrame.MEDIUM_FONT);
//        ((AbstractDocument) this.id.getDocument()).setDocumentFilter(new MyDocumentFilter(9));
//        this.id.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) {
//                warn();
//            }
//            public void removeUpdate(DocumentEvent e) {
//                warn();
//            }
//            public void insertUpdate(DocumentEvent e) {
//                warn();
//            }
//            public void warn() {
//                String text = removeLetters();
//                if (text.equals("")) {
//                    return;
//                }
//                entity.setID(Integer.parseInt(text));
//            }
//        });
        gc2.gridx = 1;
        this.idPanel.add(this.id, gc2);
    }


    private JFormattedTextField createTextField(int columns) {
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMinimumFractionDigits(1);
        floatFormat.setMaximumFractionDigits(5);
        NumberFormatter numberFormatter = new NumberFormatter(floatFormat);
        numberFormatter.setValueClass(Float.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(Float.valueOf(0.0F));
        JFormattedTextField text = new JFormattedTextField(numberFormatter);
        text.setColumns(columns);
        text.setFont(MainFrame.SMALL_FONT);
        text.setHorizontalAlignment(0);
        return text;
    }


    public void setEntity(Entity newEntity) {
        this.entity = newEntity;
    }
}
