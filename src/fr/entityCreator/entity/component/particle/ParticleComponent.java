package fr.entityCreator.entity.component.particle;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.particles.ParticleSystem;
import fr.entityCreator.graphics.particles.ParticleTarget;
import fr.entityCreator.graphics.particles.ParticleTexture;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;


public class ParticleComponent extends Component {

    private final ParticleSystem system;
    private Vector3f offset = new Vector3f();
    private ParticleComponentPanel panel;

    public ParticleComponent(ParticleSystem system, Entity e) {
        super(ComponentType.PARTICLE_COMPONENT, e);
        this.system = system;
        this.system.setTexture(new ParticleTexture(null, 1, false, false));
    }

    @Override
    public void update() {
        system.update(e.getPosition(), e.getRotation(), e.getScale());
        ParticleTarget target = system.getTarget();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleComponent that = (ParticleComponent) o;
        return Objects.equals(system, that.system) && Objects.equals(offset, that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(system, offset);
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(this.getType().toString()));            //write ParticleSystem
        fc.write(DataTransformer.casteString("\n"));
        fc.write(DataTransformer.casteString(this.system.getPps() + ";" + this.system.getAverageSpeed() + ";" + system.getGravity() //Write the data for the particle Constrctor
                + ";" + this.system.getAverageLifeLength() + ";" + this.system.getAverageScale()));
        fc.write(DataTransformer.casteString("\n"));

        fc.write(DataTransformer.casteString(this.system.isRandomRotation() + ";" + this.system.getLifeError() + ";" + this.system.getScaleError() + ";"  //Write the error of ParticleSystem
                + this.system.getSpeedError()));
        fc.write(DataTransformer.casteString("\n"));

        fc.write(DataTransformer.casteString(offset.x() + ";" + offset.y() + ";" + offset.z()));   //Write the offset of the particle
        fc.write(DataTransformer.casteString("\n"));

        fc.write(DataTransformer.casteString(this.system.getDirection().x() + ";" +       //Write all stuff about the direction
                this.system.getDirection().y() + ";" + this.system.getDirection().z() + ";" + this.system.getDirectionDeviation()));
        fc.write(DataTransformer.casteString("\n"));

        this.system.getSpawn().export(fc);      //Write the kind of spwan
        fc.write(DataTransformer.casteString("\n"));

        if (system.getTarget() != null) {           //If target isn't null write it in the file data
            fc.write(DataTransformer.casteString(JsonUtils.gsonInstance(false).toJson(system.getTarget().getProperties())));
        } else {
            fc.write(DataTransformer.casteString("\n"));
        }
        system.getTexture().export();
        fc.write(DataTransformer.casteString(system.getTexture().getName()));
        fc.write(DataTransformer.casteString("\n"));

    }


    public ParticleSystem getParticleSystem() {
        return this.system;
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel, MainFrame mainFrame) {
        if (panel == null)panel = new ParticleComponentPanel(this, paramComponentListPanel);
        return panel;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
        //this.system.setPositionOffset(offset);
    }
}
