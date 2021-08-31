package fr.entityCreator.entity;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.VectorPanel;
import fr.entityCreator.toolBox.Maths;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    private Matrix3f rotationMatrix;
    private JPanel panel;

    public Transform(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        setupPanel();
    }

    private void setupPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 3;
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
                float x = Float.parseFloat(posPanel.getXField().getText().replaceAll(",", ""));
                float y = Float.parseFloat(posPanel.getYField().getText().replaceAll(",", ""));
                float z = Float.parseFloat(posPanel.getZField().getText().replaceAll(",", ""));
                setPosition(new Vector3f(x, y, z));
            }
        });
        gc.gridy = 1;
        VectorPanel rotPanel = new VectorPanel(280, 25, "Rotation", 0, 0, 0);
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
                float x = Float.parseFloat(rotPanel.getXField().getText().replaceAll(",", ""));
                float y = Float.parseFloat(rotPanel.getYField().getText().replaceAll(",", ""));
                float z = Float.parseFloat(rotPanel.getZField().getText().replaceAll(",", ""));
                setRotation(new Vector3f(x, y, z));
            }
        });
        gc.gridy = 2;
        panel.add(createErrorPanel(), gc);
        this.panel = panel;
    }

    public Transform(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        this.position = new Vector3f(x, y, z);
        this.rotation = new Vector3f(rotX, rotY, rotZ);
        this.scale = scale;
    }

    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(
                position.x() + ";" + position.y() + ";" + position.z() + ";" +
                        rotation.x() + ";" + rotation.y() + ";" + rotation.z() + ";" + scale));
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setQ(Quaternionf q) {
        rotationMatrix = new Matrix3f().identity();
        rotationMatrix.rotate(q);
    }

    public Matrix4f getTransformation() {
        Matrix4f m = Maths.createTransformationMatrix(this.position, this.rotation, this.scale);
        if (rotationMatrix != null) m.mul(rotationMatrix.get(new Matrix4f()));
        return m;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getX() {
        return position.x();
    }

    public float getY() {
        return position.y();
    }

    public float getZ() {
        return position.z();
    }

    public float getRotX() {
        return rotation.x();
    }

    public float getRotY() {
        return rotation.y();
    }

    public float getRotZ() {
        return rotation.z();
    }

    public float getScale() {
        return scale;
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createErrorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Scale: ");
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
                setScale((Float) Float.parseFloat(field.getText().replaceAll(",","")));
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
}
