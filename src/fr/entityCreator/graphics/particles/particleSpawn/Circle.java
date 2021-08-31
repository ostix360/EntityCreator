package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.frame.JFloatSlider;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.VectorPanel;
import fr.entityCreator.toolBox.Maths;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class Circle implements ParticleSpawn {

    private Vector3f center = new Vector3f(1,1,1);
    private float radius = 1;
    private JPanel panel;
    private final String name = "Circle";

    public Circle() {
        setUpPanel();
    }

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f actualHeading = Maths.rotateVector(this.center, rotX, rotY, rotZ);
        Vector3f randomPerpendicular = new Vector3f();
        do {
            Vector3f random = Maths.generateRandomUnitVector();
            new Vector3f(random).cross(actualHeading, randomPerpendicular);
        } while (randomPerpendicular.length() == 0.0F);
        randomPerpendicular.normalize();
        randomPerpendicular.mul(this.radius);
        float a = this.random.nextFloat();
        float b = this.random.nextFloat();
        if (a > b) {
            float temp = a;
            a = b;
            b = temp;
        }
        float randX = (float) (b * Math.cos(6.283185307179586D * (a / b)));
        float randY = (float) (b * Math.sin(6.283185307179586D * (a / b)));
        float distance = new Vector2f(randX, randY).length();
        randomPerpendicular.mul(distance * scale);
        Vector3f offset = new Vector3f(x, y, z);
        new Vector3f(offset).add(randomPerpendicular, randomPerpendicular);
        return randomPerpendicular;
    }

    @Override
    public JPanel getSettingsPanel() {
        return panel;
    }

    private void setUpPanel() {
        this.panel = new JPanel();
        this.panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        this.panel.add(createSlider("Radius:", 1.0F, this.radius), gc);
        gc.gridy = 1;
        gc.weighty = 2.0D;
        VectorPanel vectorPanel = new VectorPanel(200, 30, "Normal", this.center.x, this.center.y, this.center.z);

        addXFieldListener(vectorPanel);
        addYFieldListener(vectorPanel);
        addZFieldListener(vectorPanel);
        this.panel.add(vectorPanel, gc);
    }

    private JPanel createSlider(String name, float maximum, float start) {
        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(MainFrame.SMALL_FONT);
        nameLabel.setPreferredSize(new Dimension(50, 20));
        panelSlider.add(nameLabel, gc);
        gc.weightx = 1.0D;
        gc.gridx = 1;
        final JLabel valueReading = new JLabel();
        valueReading.setPreferredSize(new Dimension(50, 20));
        valueReading.setFont(MainFrame.SMALL_FONT);
        final JFloatSlider slider = new JFloatSlider(0, 0.0F, maximum, reverseConvertValue(start));

        valueReading.setText(limitChars(Float.toString(convertScaleValue(slider.getActualValue())), 5));


        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 5));

                radius = convertScaleValue(slider.getActualValue());
            }
        });
        panelSlider.add(slider, gc);
        slider.setPreferredSize(new Dimension(250, 20));
        gc.gridx = 2;
        gc.weightx = 1.0D;
        panelSlider.add(valueReading, gc);
        return panelSlider;
    }

    @Override
    public String toString() {
        return name;
    }

    private void addXFieldListener(final VectorPanel vectorPanel) {
        vectorPanel.addXFieldListener(new DocumentListener() {
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
                if (vectorPanel.getXField().getText().equals("")) {
                    return;
                }
                center.x = Float.parseFloat(vectorPanel.getXField().getText().replaceAll(",", ""));
                if (center.lengthSquared() == 0.0F) {
                    center.y = 1.0F;
                }
                center.normalize();
            }
        });
    }

    private void addYFieldListener(final VectorPanel vectorPanel) {
        vectorPanel.addYFieldListener(new DocumentListener() {
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
                if (vectorPanel.getYField().getText().equals("")) {
                    return;
                }
                center.y = Float.parseFloat(vectorPanel.getYField().getText().replaceAll(",", ""));
                if (center.lengthSquared() == 0.0F) {
                    center.y = 1.0F;
                }
                center.normalize();
            }
        });
    }

    private void addZFieldListener(final VectorPanel vectorPanel) {
        vectorPanel.addZFieldListener(new DocumentListener() {
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
                if (vectorPanel.getZField().getText().equals("")) {
                    return;
                }
                center.z = Float.parseFloat(vectorPanel.getZField().getText().replaceAll(",", ""));
                if (center.lengthSquared() == 0.0F) {
                    center.y = 1.0F;
                }
                center.normalize();
            }
        });
    }

    private String limitChars(String original, int limit) {
        if (original.length() <= limit) {
            return original;
        }
        return original.substring(0, 5);
    }


    private float convertScaleValue(float sliderValue) {
        float value = sliderValue * sliderValue;
        value *= 200.0F;
        return value;
    }

    private float reverseConvertValue(float reflectValue) {
        float value = reflectValue / 200.0F;
        return (float) Math.sqrt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Float.compare(circle.radius, radius) == 0 && Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("0;" + this.radius + ";" + this.center.x() + ";" + this.center.y() + ";" + this.center.z()));
    }

    @Override
    public void load(String[] values) {
        this.radius = Float.parseFloat(values[1]);
        Vector3f center = new Vector3f(Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
        this.center = center;
    }

    @Override
    public int getID() {
        return 0;
    }

    public Circle setCenter(Vector3f center) {
        this.center = center.normalize();
        return this;
    }

    public Circle setRadius(float radius) {
        this.radius = radius;
        return this;
    }
}
