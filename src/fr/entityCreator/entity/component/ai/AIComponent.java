package fr.entityCreator.entity.component.ai;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.core.collision.maths.Vector3;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;
import org.joml.Random;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class AIComponent extends Component {

    private final Random r = new Random();
    private final AIProperties properties;
    private float time = 0;
    private float pos = 0;
    private float rotY = 0;


    public AIComponent(Entity e, AIProperties properties) {
        super(ComponentType.AI_COMPONENT, e);
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIComponent that = (AIComponent) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getType().toString(), properties);
    }

    @Override
    public void update() {
        time++;
        if (time % (r.nextInt((int) properties.getUpdate()) + 30) == 0) {
            if (r.nextInt((int) properties.getStaticTime()) == 0) {
                pos = 0;
                e.setMovement(Entity.MovementType.STATIC);
            } else {
                pos = generatePositiveValue(properties.getSpeed(), properties.getSpeedError());

                e.setMovement(Entity.MovementType.FORWARD);
            }
            if (r.nextInt((int) properties.getRotateProbabilities()) <= 1) {
                rotY = generateRotation(properties.getSpeedTurn(), properties.getSpeedTurnError());
            } else {
                rotY = 0;
            }

        }

        float dx = (float) (pos * Math.sin(Math.toRadians(e.getRotation().y())));
        float dz = (float) (pos * Math.cos(Math.toRadians(e.getRotation().y())));
        e.getTorque().set(new Vector3(0, rotY, 0));
        e.increaseRotation(new Vector3f(0, rotY, 0));
        e.getForceToCenter().add(new Vector3(dx, 0, dz));
    }

    @Override
    public void export(FileChannel fc) throws IOException {

        fc.write(DataTransformer.casteString(this.getType().toString()));
        fc.write(DataTransformer.casteString(JsonUtils.gsonInstance(false).toJson(properties)));

    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel) {
        return null;
    }

    private float generateRotation(float average, float errorMargin) {
        float offset = (r.nextFloat() - 0.5f) * 2f * errorMargin;
        return average + offset;
    }


    private float generatePositiveValue(float average, float errorMargin) {
        float offset = (r.nextFloat()) * errorMargin;
        return average + offset;
    }
}
