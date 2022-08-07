package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.frame.JFloatSlider;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.toolBox.maths.Maths;
import org.joml.Math;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.Random;

public class Sphere implements ParticleSpawn {

    private float radius = 1;
    private JPanel panel;

    public Sphere() {
        setupPanel();
    }

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f spherePoint = Maths.generateRandomUnitVector();

        spherePoint.mul(this.radius * scale);

        float fromCenter = new Random().nextFloat();

        spherePoint.mul(fromCenter);

        Vector3f offset = new Vector3f(x, y, z);

        spherePoint.add(offset, spherePoint);



        return spherePoint;
    }

    private void setupPanel(){
        this.panel = new JPanel();
        this.panel.add(createSlider("Radius:", 1.0F, this.radius));
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
    public JPanel getSettingsPanel() {
        return panel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Float.compare(sphere.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("3;" + this.radius));
    }

    @Override
    public void load(String[] values) {
        this.radius = Float.parseFloat(values[1]);
    }

    @Override
    public int getID() {
        return 3;
    }

    public Sphere setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public String toString() {
        return "Sphere";
    }

}
