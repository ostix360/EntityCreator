package fr.entityCreator.entity.component.light;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.VectorPanel;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.toolBox.Color;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;

public class Light extends Component {
    private Vector3f position;
    private final Color colour;
    private Vector3f attenuation;
    private float power;
    private JPanel panel;


    public Light(Vector3f position, Color colour, float power,Vector3f attenuation) {
        super(ComponentType.LIGHT_COMPONENT,null);
        this.position = position;
        this.colour = colour;
        this.power = power;
        this.attenuation = attenuation;
        MasterRenderer.addLight(this);
        setupPanel();
    }

    private void setupPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 4;
        gc.gridx = 0;
        gc.gridy = 0;
        VectorPanel posPanel = new VectorPanel(280, 25, "Position", 0, 0, 0);
        panel.add(posPanel, gc);
        posPanel.addTotalListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if ((posPanel.getXField().getText().equals("")) || (posPanel.getYField().getText().equals("")) || (posPanel.getZField().getText().equals(""))) {
                    return;
                }
                float x = Float.parseFloat(posPanel.getXField().getText().replaceAll(",", "."));
                float y = Float.parseFloat(posPanel.getYField().getText().replaceAll(",", "."));
                float z = Float.parseFloat(posPanel.getZField().getText().replaceAll(",", "."));
                setPosition(new Vector3f(x, y, z));
            }
        });
        gc.gridy = 1;
        VectorPanel rotPanel = new VectorPanel(280, 25, "Color", 0, 0, 0);
        panel.add(rotPanel, gc);
        rotPanel.addTotalListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if ((rotPanel.getXField().getText().equals("")) || (rotPanel.getYField().getText().equals("")) || (rotPanel.getZField().getText().equals(""))) {
                    return;
                }
                float r = Float.parseFloat(rotPanel.getXField().getText().replaceAll(",", "."));
                float g = Float.parseFloat(rotPanel.getYField().getText().replaceAll(",", "."));
                float b = Float.parseFloat(rotPanel.getZField().getText().replaceAll(",", "."));
               colour.set(r,g,b,1.0f);
            }
        });
        VectorPanel attenuation = new VectorPanel(280, 25, "Attenuation", 0, 0, 0);
        panel.add(rotPanel, gc);
        rotPanel.addTotalListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if ((rotPanel.getXField().getText().equals("")) || (rotPanel.getYField().getText().equals("")) || (rotPanel.getZField().getText().equals(""))) {
                    return;
                }
                float r = Float.parseFloat(rotPanel.getXField().getText().replaceAll(",", "."));
                float g = Float.parseFloat(rotPanel.getYField().getText().replaceAll(",", "."));
                float b = Float.parseFloat(rotPanel.getZField().getText().replaceAll(",", "."));
                Light.this.attenuation.set(r,g,b);
            }
        });
        gc.gridy = 2;
        panel.add(attenuation,gc);
        gc.gridy = 3;
        panel.add(createErrorPanel(), gc);
    }

    private JPanel createErrorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Power / Intensité: ");
        label.setFont(MainFrame.SMALL_FONT);
        panel.add(label, "West");
        JFormattedTextField field = createTextField(5);
        field.setText("1,0");
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            private void warn() {
                if (field.getText().equals("")) {
                    return;
                }
                power = ((Float) Float.parseFloat(field.getText().replaceAll(",",".")));
            }
        });
        panel.add(field, "East");
        return panel;
    }

    private JFormattedTextField createTextField(int columns) {
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMinimumFractionDigits(1);
        floatFormat.setMaximumFractionDigits(5);
        NumberFormatter numberFormatter = new NumberFormatter(floatFormat);
        numberFormatter.setValueClass(Float.class);
        numberFormatter.setAllowsInvalid(false);
        //numberFormatter.setMinimum(0);
        JFormattedTextField text = new JFormattedTextField(numberFormatter);
        text.setColumns(columns);
        text.setFont(MainFrame.SMALL_FONT);
        text.setHorizontalAlignment(0);
        return text;
    }

    public Light(Vector3f position, Color colour) {
        this(position,colour,1, new Vector3f(1.0f,0,0));
    }

    public Light(Vector3f position, Color colour, Vector3f attenuation) {
        this(position,colour,1.0f,attenuation);
    }

    public float getPower() {
        return power;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return new Vector3f(position).add(200,0,200);  //l'entité se trouve au meme coordoné
                                                                // la lumiére va avoir des coodoné relative
    }

    public Vector3f getColourVec3f() {
        return colour.getVec3f();
    }


    @Override
    public void update() {

    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(position.x() + ";" + position.y() + ";" + position.z()));
        fc.write(DataTransformer.casteString(colour.getRed() + ";" + colour.getGreen() + ";" + colour.getBlue()));
        fc.write(DataTransformer.casteString(attenuation.x() + ";" + attenuation.y() + ";" + attenuation.z()));
        fc.write(DataTransformer.casteString(String.valueOf(power)));
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel) {
        return new LightPanel(panel,paramComponentListPanel,this);
    }
}
