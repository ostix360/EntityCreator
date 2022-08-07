package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.frame.JFloatSlider;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.VectorPanel;
import fr.entityCreator.toolBox.maths.Maths;
import org.joml.Math;
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

public class Line implements ParticleSpawn {

    private Vector3f axis = new Vector3f();
    private float length = 1;
    private JPanel panel;

    public Line() {
        setUpPanel();
    }

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f actualAxis = Maths.rotateVector(this.axis, rotX, rotY, rotZ);
        actualAxis.normalize();
        actualAxis.mul(this.length * scale);
        actualAxis.mul(this.random.nextFloat() - 0.5F);
        Vector3f offset = new Vector3f(x, y, z);
        offset.add(actualAxis, actualAxis);
        return actualAxis;
    }



    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("1;" + this.length + ";" + this.axis.x + ";" + this.axis.y + ";" + this.axis.z));
    }

    @Override
    public void load(String[] values) {
        this.length = Float.parseFloat(values[1]);
        Vector3f axis = new Vector3f(Float.parseFloat(values[2]),Float.parseFloat(values[3]),Float.parseFloat(values[4]));
        this.axis = axis;
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Float.compare(line.length, length) == 0 && Objects.equals(axis, line.axis);
    }

    @Override
    public String toString() {
        return "Line";
    }

    @Override
    public int hashCode() {
        return Objects.hash(axis, length);
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
        this.panel.add(createSlider("Length:", 1.0F, this.length), gc);
        gc.gridy = 1;
        gc.weighty = 2.0D;
        VectorPanel vectorPanel = new VectorPanel(200, 30, "Direction", this.axis.x, this.axis.y, this.axis.z);
        addXFieldListener(vectorPanel);
        addYFieldListener(vectorPanel);
        addZFieldListener(vectorPanel);
        this.panel.add(vectorPanel, gc);
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
                axis.x = Float.parseFloat(vectorPanel.getXField().getText().replaceAll(",", ""));
                if (axis.lengthSquared() == 0.0F) {
                    axis.y = 1.0F;
                }
                axis.normalize();
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
                axis.y = Float.parseFloat(vectorPanel.getYField().getText().replaceAll(",", ""));
                if (axis.lengthSquared() == 0.0F) {
                    axis.y = 1.0F;
                }
                axis.normalize();
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
                axis.z = Float.parseFloat(vectorPanel.getZField().getText().replaceAll(",", ""));
                if (axis.lengthSquared() == 0.0F) {
                    axis.y = 1.0F;
                }
                axis.normalize();
            }
        });
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

                length = convertScaleValue(slider.getActualValue());
            }
        });
        panelSlider.add(slider, gc);
        slider.setPreferredSize(new Dimension(250, 20));
        gc.gridx = 2;
        gc.weightx = 1.0D;
        panelSlider.add(valueReading, gc);
        return panelSlider;
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

    public Line setAxis(Vector3f axis) {
        this.axis = axis;
        return this;
    }

    public Line setLength(float length) {
        this.length = length;
        return this;
    }

    @Override
    public JPanel getSettingsPanel() {
        return panel;
    }
}
