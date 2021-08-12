package fr.entityCreator.entity.component.particle;

import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.model.Texture;
import fr.entityCreator.graphics.particles.ParticleSystem;
import fr.entityCreator.graphics.particles.ParticleTexture;
import fr.entityCreator.graphics.particles.particleSpawn.Circle;
import fr.entityCreator.graphics.particles.particleSpawn.Line;
import fr.entityCreator.graphics.particles.particleSpawn.ParticleSpawn;
import fr.entityCreator.graphics.particles.particleSpawn.Point;
import fr.entityCreator.graphics.particles.particleSpawn.Sphere;
import fr.entityCreator.graphics.textures.TextureLoader;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.time.Clock.system;

public class ParticleComponentPanel extends ComponentPanel {

    private final ParticleSystem particleSystem;
//    private VectorPanel directionFields;
//    private VectorPanel offsetFields;
//    private ParticleTargetPanel targetPanel;
    private Texture particleTexture;
    private JComboBox<Integer> index;
    private JButton textureChoose;
    private JPanel currentShapesPanel = null;

    public ParticleComponentPanel(ParticleComponent component, ComponentListPanel componentList) {
        super(componentList, component);
        this.settings.setLayout(new GridBagLayout());
        this.particleSystem = component.getParticleSystem();
        setUpTexturePanel();
        setupSpawnPanel();
        setupMainSettings();
    }

    private void setupMainSettings() {

        final SliderWithError lifeSlider = new SliderWithError("Life:", this.particleSystem.getAverageLifeLength(), 30.0F, 300.0F, this.particleSystem.getAverageLifeLength(), true, false);

        this.settings.add(lifeSlider, getGC(0, 3));
        lifeSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setAverageLifeLength(lifeSlider.getSliderReading());
            }
        });
        lifeSlider.addErrorListener(new ErrorListener() {
            public void onActionOccurred() {
                particleSystem.setLifeError(lifeSlider.getErrorReading());
            }
        });
        final SliderWithError speedSlider = new SliderWithError("Speed", this.particleSystem.getAverageSpeed(), 0.0F, 20.0F, this.particleSystem.getSpeedError(), true, false);
        speedSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setAverageSpeed(speedSlider.getSliderReading());
            }
        });
        speedSlider.addErrorListener(new ErrorListener(){
            public void onActionOccurred() {
                particleSystem.setSpeedError(speedSlider.getErrorReading());
            }
        });
        this.settings.add(speedSlider, getGC(0, 4));
        final SliderWithError scaleSlider = new SliderWithError("Scale", this.particleSystem.getAverageScale(), 0.0F, 50.0F, this.particleSystem.getScaleError(), true, false);
        scaleSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setAverageScale(scaleSlider.getSliderReading());
            }
        });
        scaleSlider.addErrorListener(new ErrorListener() {
            public void onActionOccurred() {
                particleSystem.setScaleError(scaleSlider.getErrorReading()/100);
            }
        });
        this.settings.add(scaleSlider, getGC(0, 5));
        final SliderWithError ppsSlider = new SliderWithError("PPS", this.particleSystem.getPps(), 1.0F, 5000.0F, 0.0F, false, false);
        ppsSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setPps(ppsSlider.getSliderReading());
            }
        });
        this.settings.add(ppsSlider, getGC(0, 6));
        final SliderWithError gravitySlider = new SliderWithError("Gravity", this.particleSystem.getGravity(), -5.0F, 5.0F, 0.0F, false, true);
        gravitySlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setGravity(gravitySlider.getSliderReading());
            }
        });
        this.settings.add(gravitySlider, getGC(0, 7));
        final SliderWithError divergence = new SliderWithError("Diverge", this.particleSystem.getDirectionDeviation(), 0.0F, 10.0F, 0.0F, false, true);
        divergence.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                particleSystem.setDirectionDeviation(divergence.getSliderReading());
            }
        });
        this.settings.add(divergence, getGC(0, 8));
    }

    private void setupSpawnPanel() {
        final JPanel shapesPanel = new JPanel();
        shapesPanel.setPreferredSize(new Dimension(this.settings.getWidth(), 100));
        shapesPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        shapesPanel.setLayout(new GridBagLayout());
        this.settings.add(shapesPanel, getGC(0, 1));

        ParticleSpawn[] possibleShapes = {new Circle(), new Line(), new Point(), new Sphere()};
        final JComboBox<ParticleSpawn> shapes = new JComboBox(possibleShapes);
        GridBagConstraints gc = getGC(0, 0);
        shapes.setSelectedIndex(this.particleSystem.getSpawn() == null ? 0 : this.particleSystem.getSpawn().getID());
        this.currentShapesPanel = this.particleSystem.getSpawn().getSettingsPanel();
        shapesPanel.add(this.currentShapesPanel, getGC(0, 1));
        shapes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ParticleComponentPanel.this.currentShapesPanel != null) {
                    shapesPanel.remove(ParticleComponentPanel.this.currentShapesPanel);
                }
                currentShapesPanel = ((ParticleSpawn) shapes.getSelectedItem()).getSettingsPanel();
                particleSystem.setSpawn((ParticleSpawn) shapes.getSelectedItem());
                shapesPanel.add(currentShapesPanel, getGC(0, 1));
                shapesPanel.validate();
                shapesPanel.repaint();
            }
        });
        gc.anchor = 11;
        shapesPanel.add(shapes, gc);
    }

    private void setUpTexturePanel() {
        JPanel textureSettings = new JPanel();
        textureSettings.setLayout(new GridBagLayout());
        this.index = null;
        this.textureChoose = null;
        if (this.particleSystem.getTexture() == null){
            this.textureChoose = new JButton("Choisissez la texture de vos particules");
            this.textureChoose.setForeground(new Color(255, 0, 0));
        }
        else{
            this.textureChoose = new JButton("Changer la texture de vos particle");
            this.textureChoose.setForeground(new Color(0, 255, 0));
        }

        this.textureChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ParticleTextureChooser(ParticleComponentPanel.this, textureChoose);
            }
        });
        this.textureChoose.setFont(MainFrame.SMALL_FONT);
        textureSettings.add(this.textureChoose, getGC(0, 0));
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 15, 1);
        final JSpinner spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        spinner.setPreferredSize(new Dimension(30, 20));
        JPanel panel = new JPanel();
        textureSettings.add(panel, getGC(1, 0));
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
                particleSystem.getTexture().setNumberOfRows(((Integer) spinner.getValue()).intValue());
            }
        });
        this.settings.add(textureSettings,getGC(0,0));
    }



    @Override
    public void cleanUp() {

    }

    public void setTexture(TextureLoader texID) {
        particleTexture = new Texture(texID,TextureProperties.PARTICLE_DEFAULT_PROPERTIES);
        particleSystem.getTexture().setTexture(particleTexture.getTextureID());
    }


    private GridBagConstraints getGC(int x, int y) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 2;
        gc.gridx = x;
        gc.gridy = y;
        gc.weightx = 1;
        gc.weighty = 1;
        return gc;
    }
}