package fr.entityCreator.entity.component.animation;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.resources.ResourcePack;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.animated.animation.animation.Animation;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;
import fr.entityCreator.toolBox.Logger;
import org.joml.Random;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class AnimationComponent extends Component {


    public AnimationComponent(Entity e) {
        super(ComponentType.ANIMATED_COMPONENT, e);
        if (!(e.getModel() instanceof AnimatedModel)) {
            System.err.println("Your Model is not an AnimatedModel");
        }
    }

    @Override
    public int hashCode() {
        return this.getType().toString().hashCode();
    }

    @Override
    public void update() {
        AnimatedModel model = (AnimatedModel) e.getModel();
        Animation a = ResourcePack.getAnimationByName().get(model).get(e.getMovement().getId());
        if (a == null) {
            if (new Random().nextInt(5) == 0)
                Logger.warn("The animation, " + e.getMovement().getId() + " for the model " + " is not available");
        } else {
            if (model.getPriorityAnimation() == null) {
                model.doAnimation(a);
            } else if (!model.getPriorityAnimation().equals(a)) {
                model.doAnimation(a);
            }
        }
        if (model.getPriorityAnimation() != null) {
            model.update(1f / 60f);
        }
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(this.getType().toString()));

    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel) {
        return null;
    }
}
