package fr.entityCreator.frame;

import fr.entityCreator.entity.component.particle.ErrorListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class SliderWithError extends JPanel {
    private final float max;
    private final float start;
    private final boolean linear;
    private JFloatSlider slider;
    private JFormattedTextField errorField;

    public SliderWithError(String name, float current, float start, float max, float error, boolean hasError, boolean linear) {
        this.max = max;
        this.start = start;
        this.linear = linear;
        this.setLayout(new BorderLayout());
        if (hasError) {
            add(createErrorPanel(error),"East");
            add(createSlider(name, max, current),"West");
        }else{
            add(createSlider(name, max, current));
        }
    }

    public void addSliderListener(ChangeListener listener) {
        this.slider.addChangeListener(listener);
    }

    public void addErrorListener(ErrorListener listener) {
        this.errorField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                listener.onActionOccurred();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listener.onActionOccurred();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listener.onActionOccurred();

            }
        });
    }


    private JPanel createErrorPanel(float error) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Error: ");
        label.setFont(MainFrame.SMALL_FONT);
        panel.add(label, "West");
        this.errorField = createTextField(4);
        this.errorField.setValue(error);
        panel.add(this.errorField, "East");
        return panel;
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
        this.slider = new JFloatSlider(0, 0.0F, 1.0F, reverseConvertValue(start));
        valueReading.setText(limitChars(Float.toString(convertScaleValue(this.slider.getActualValue())), 5));


        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 5));
            }
        });
        panelSlider.add(slider, gc);
        slider.setPreferredSize(new Dimension(250, 20));
        gc.gridy = 1;
        gc.weightx = 1.0D;
        panelSlider.add(valueReading, gc);
        return panelSlider;
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

    private String limitChars(String original, int limit) {
        if (original.length() <= limit) {
            return original;
        }
        return original.substring(0, 5);
    }


    private float convertScaleValue(float sliderValue) {
        float value = sliderValue;
        if (!this.linear) {
            value *= sliderValue;
            value *= sliderValue;
        }
        value *= (this.max - this.start);
        return  value + this.start;
    }

    private float reverseConvertValue(float reflectValue) {
        reflectValue -= this.start;
        float value = reflectValue / (this.max - this.start);
        if (this.linear) {
            return value;
        }
        return (float) Math.cbrt(value);
    }

    public float getSliderReading() {
        return convertScaleValue(this.slider.getActualValue());
    }

    public float getErrorReading() {
        if (this.errorField.getText().equals("")) {
            return 0.0f;
        }
        return Float.parseFloat(this.errorField.getText().replaceAll(",", ""));
    }
}
