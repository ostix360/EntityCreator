package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.textures.Texture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class TexturePanel extends JPanel {

    private Texture texture;
    private JLabel diffuseIcon;
    private JLabel normalIcon;
    private JLabel extraIcon;
    private JCheckBox glows;
    private Entity e;

    public TexturePanel(Entity entity, int width, int height) {
        this.e = entity;
        setPreferredSize(new Dimension(width, height));
        this.texture = entity.getModel().getTexture();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        boolean showGlow = setUpIconPanel(gc);
        gc.gridx = 1;
        gc.weightx = 4.0D;
        setUpSettingsPanel(gc, showGlow);
        gc.gridx = 5;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        setUpNormalIconPanel(gc);
    }

    private void setUpNormalIconPanel(GridBagConstraints gc) {
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 1;
        gc2.gridy = 1;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;

        String normal = this.texture.getNormalMapFile();
        if (normal != null) {
            try {
                this.normalIcon = createIcon(new FileInputStream(normal), 3);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.normalIcon = createIcon(TexturePanel.class.getResourceAsStream("/res/NormalMap.png"), 3);
        }
        iconPanel.add(this.normalIcon, "East");
        add(iconPanel, gc);

    }

    private boolean setUpIconPanel(GridBagConstraints gc) {
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.gridy = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;

        String diffuseFile = this.texture.getTextureLoader().getFile().getAbsolutePath();

        if (diffuseFile != null) {
            try {
                this.diffuseIcon = createIcon(new FileInputStream(diffuseFile), 1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.diffuseIcon = createIcon(TexturePanel.class.getResourceAsStream("/res/DiffuseTexture.png"), 1);
        }
        iconPanel.add(this.diffuseIcon, gc2);
        gc2.gridy = 1;
        String specularFile = this.texture.getSpecularMapFile();
        if (specularFile != null) {
            try {
                this.extraIcon = createIcon(new FileInputStream(specularFile), 2);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.extraIcon = createIcon(TexturePanel.class.getResourceAsStream("/res/ExtraMap.png"), 2);
        }
        iconPanel.add(this.extraIcon, gc2);
        add(iconPanel, gc);
        if (specularFile != null) {
            return true;
        }
        return false;
    }

    private void setUpSettingsPanel(GridBagConstraints gc, boolean showGlow) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.gridy = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        settingsPanel.add(createShineSlider(), gc2);
        gc2.gridy = 1;
        settingsPanel.add(createReflectivitySlider(), gc2);
        gc2.gridy = 2;
        JCheckBox transparency = new JCheckBox("Has Transparency?");
        transparency.setFont(MainFrame.SMALL_FONT);
        transparency.setSelected(this.texture.isTransparency());
        transparency.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                TexturePanel.this.texture.setTransparency(transparency.isSelected());
            }
        });
        settingsPanel.add(transparency, gc2);
        gc2.gridy = 3;
        final JCheckBox realLighting = new JCheckBox("Use Real Light?");
        realLighting.setFont(MainFrame.SMALL_FONT);
        realLighting.setSelected(!this.texture.useFakeLighting());
        realLighting.addActionListener((e) -> {
            texture.setUseFakeLighting(!realLighting.isSelected());
        });
        settingsPanel.add(realLighting, gc2);
        gc2.gridy = 4;
        addSpinner(gc2, settingsPanel);
        add(settingsPanel, gc);
    }

    private JPanel createReflectivitySlider() {
        return createSlider("Reflectivity:", 1.0F, reverseConvertReflectValue(this.texture.getReflectivity()), false);
    }

    private JPanel createShineSlider() {
        return createSlider("Shinyness:", 1.0F, reverseConvertShineValue(this.texture.getShineDamper()), true);
    }

    private JPanel createSlider(String name, float maximum, float start, boolean shine) {
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
        nameLabel.setPreferredSize(new Dimension(60, 20));
        panelSlider.add(nameLabel, gc);
        gc.weightx = 4.0D;
        gc.gridx = 1;
        JLabel valueReading = new JLabel();
        valueReading.setPreferredSize(new Dimension(20, 20));
        valueReading.setFont(MainFrame.SMALL_FONT);
        JFloatSlider slider = new JFloatSlider(0, 0.0F, maximum, start);
        if (shine) {
            valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 5));
        } else {
            valueReading.setText(limitChars(Float.toString(convertReflectValue(slider.getActualValue())), 5));
        }
        if (shine) {
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent arg0) {
                    valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 5));
                    texture.setShineDamper(convertShineValue(slider.getActualValue()));
                }
            });
        } else {
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent arg0) {
                    valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 5));
                    texture.setReflectivity(convertReflectValue(slider.getActualValue()));
                }
            });
        }
        panelSlider.add(slider, gc);
        slider.setPreferredSize(new Dimension(20, 20));
        gc.gridx = 2;
        gc.weightx = 1.0D;
        panelSlider.add(valueReading, gc);
        return panelSlider;
    }

    private void addSpinner(GridBagConstraints gc, JPanel settingsPanel) {
        SpinnerNumberModel model = new SpinnerNumberModel(this.texture.getNumbersOfRows(), 1, 10, 1);
        final JSpinner spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        spinner.setPreferredSize(new Dimension(30, 20));
        JPanel panel = new JPanel();
        settingsPanel.add(panel, gc);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        JLabel label = new JLabel("Order of Atlas: ");
        label.setFont(MainFrame.SMALL_FONT);
        panel.add(label, gc2);
        gc2.gridx = 1;
        panel.add(spinner, gc2);
        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                texture.setNumbersOfRows(((Integer) spinner.getValue()).intValue());
            }

        });

    }

    public boolean setNewIcon(int id) {
        try {
            BufferedImage myPicture;

            ImageIcon original;
            ImageIcon resized;
            switch (id) {
                case 2:
                    myPicture = ImageIO.read(new File(e.getModel().getTexture().getSpecularMapFile()));
                    original = new ImageIcon(myPicture);
                    resized = resizeImage(original);
                    this.extraIcon.setIcon(resized);
                    break;
                case 3:
                    myPicture = ImageIO.read(new File(e.getModel().getTexture().getNormalMapFile()));
                    original = new ImageIcon(myPicture);
                    resized = resizeImage(original);
                    this.normalIcon.setIcon(resized);
                    break;
                default:
                    myPicture = ImageIO.read(e.getModel().getTexture().getNewDiffuse());
                    original = new ImageIcon(myPicture);
                    resized = resizeImage(original);
                    this.diffuseIcon.setIcon(resized);
                    break;
            }
            validate();
            repaint();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load texture!");
        }
        return false;
    }

    private String limitChars(String original, int limit) {
        if (original.length() <= limit) {
            return original;
        }
        return original.substring(0, 5);
    }

    private float convertShineValue(float sliderValue) {
        float value = (float) (sliderValue * 1.5707963267948966D);
        value = (float) Math.sin(value);
        value = (float) Math.sin(value * 1.5707963267948966D);
        value *= 100.0F;
        return 100.0F - value;
    }

    private float convertReflectValue(float sliderValue) {
        float value = sliderValue * sliderValue;
        value *= 5.0F;
        return value;
    }

    private float reverseConvertReflectValue(float reflectValue) {
        float value = reflectValue / 5.0F;
        return (float) Math.sqrt(value);
    }

    private float reverseConvertShineValue(float shine) {
        float value = 100.0F - shine;
        value /= 100.0F;
        value = (float) Math.asin(value);
        value = (float) (value / 1.5707963267948966D);
        value = (float) Math.asin(value);
        value = (float) (value / 1.5707963267948966D);
        return value;
    }

    private JLabel createIcon(InputStream stream, int id) {

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(stream);

            stream.close();
        } catch (IOException e) {

            e.printStackTrace();

        }
        ImageIcon original = new ImageIcon(myPicture);

        ImageIcon resized = resizeImage(original);

        JLabel icon = new JLabel(resized);
        icon.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
            }

            public void mouseEntered(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
                new TextureChooseScreen(TexturePanel.this, e, id);
            }
        });
        icon.setPreferredSize(new Dimension(40, 40));
        return icon;
    }

    private ImageIcon resizeImage(ImageIcon original) {
        Image img = original.getImage();
        Image resized = getScaledImage(img, 40, 40);
        return new ImageIcon(resized);
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, 2);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
