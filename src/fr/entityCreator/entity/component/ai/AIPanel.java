package fr.entityCreator.entity.component.ai;

import fr.entityCreator.entity.component.particle.*;
import fr.entityCreator.frame.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class AIPanel extends ComponentPanel {
    private AIProperties prop;

    public AIPanel(ComponentListPanel componentList, AIComponent component) {
        super(componentList, component);
        this.settings.setLayout(new GridBagLayout());
        this.prop = component.getProperties();
        setupUpdate();
        setupErrorPanels();
        setupRest();
    }

    private void setupRest() {
        JLabel label = new JLabel("Probalité de rotation");
        SpinnerNumberModel model = new SpinnerNumberModel(prop.getRotateProbabilities(), 1, 100, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        spinner.setPreferredSize(new Dimension(30, 20));
        JPanel panel = new JPanel();
        settings.add(panel,getGC(0,4));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        panel.add(label,gc2);
        gc2.gridx = 1;
        panel.add(spinner,gc2);
        label = new JLabel("Static time");
        model = new SpinnerNumberModel(prop.getStaticTime(), 0, 100, 1);
        spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        spinner.setPreferredSize(new Dimension(30, 20));
         panel = new JPanel();
        settings.add(panel,getGC(0,5));
        panel.setLayout(new GridBagLayout());
        gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        panel.add(label,gc2);
        gc2.gridx = 1;
        panel.add(spinner,gc2);
        label = new JLabel("Distance de deplacement");
        model = new SpinnerNumberModel(prop.getDistance(), 50, 300, 5);
        spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        spinner.setPreferredSize(new Dimension(30, 20));
        panel = new JPanel();
        settings.add(panel,getGC(0,6));
        panel.setLayout(new GridBagLayout());
        gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        panel.add(label,gc2);
        gc2.gridx = 1;
        panel.add(spinner,gc2);
    }

    private void setupErrorPanels() {
        final SliderWithError speedSlider = new SliderWithError("Speed",prop.getSpeed(),1,10,prop.getSpeedError(),true,false);
        this.settings.add(speedSlider, getGC(0, 1));
        speedSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                prop.setSpeed(speedSlider.getSliderReading());
            }
        });
        speedSlider.addErrorListener(new ErrorListener() {
            public void onActionOccurred() {
                prop.setSpeedError(
                        speedSlider.getErrorReading()
                                /100);
            }
        });
        final SliderWithError turnSlider = new SliderWithError("Turn Speed",prop.getSpeedTurn(),1,10,prop.getSpeedTurnError(),true,false);
        this.settings.add(turnSlider, getGC(0, 2));
        turnSlider.addSliderListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                prop.setSpeedTurn(turnSlider.getSliderReading());
            }
        });
        turnSlider.addErrorListener(new ErrorListener() {
            public void onActionOccurred() {
                prop.setSpeedTurnError(
                        turnSlider.getErrorReading()
                                /100);
            }
        });
    }


    private void setupUpdate() {
        final JLabel updatePerSecond = new JLabel("Update per second");
        SpinnerNumberModel model = new SpinnerNumberModel(prop.getUpdate()/60, 5, 100, 3);
        final JSpinner spinner = new JSpinner(model);
        spinner.setFont(MainFrame.SMALL_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        spinner.setPreferredSize(new Dimension(30, 20));
        final JPanel panel = new JPanel();
        settings.add(panel,getGC(0,3));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = 1;
        gc2.gridx = 0;
        gc2.weightx = 1.0D;
        gc2.weighty = 1.0D;
        panel.add(updatePerSecond,gc2);
        gc2.gridx = 1;
        panel.add(spinner,gc2);
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


    @Override
    public void cleanUp() {

    }
}
