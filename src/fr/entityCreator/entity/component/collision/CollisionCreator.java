package fr.entityCreator.entity.component.collision;


import com.flowpowered.react.collision.shape.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;

import java.io.BufferedReader;
import java.util.*;

public class CollisionCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new CollisionComponent(entity,new CollisionProperties());
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        CollisionProperties prop = new CollisionProperties();
        List<BoundingModel> bounds = new ArrayList<>();
        String[] lines = component.split("\n");
        if (!lines[0].equalsIgnoreCase("Collision Component")) {
            return null;
        }
        for (int i = 1; i < lines.length - 1; i++) {
            String line = lines[i];
            CollisionShape.CollisionShapeType type = isType(line);
            if (type != null) {
                BoundingModel model;
                switch (type) {
                    case BOX:
                        model = BoxShape.load(lines[++i]);
                        break;
                    case CAPSULE:
                        model = CapsuleShape.load(lines[++i]);
                        break;
                    case CONE:
                        model = ConeShape.load(lines[++i]);
                        break;
                    case CYLINDER:
                        model = CylinderShape.load(lines[++i]);
                        break;
                    default:
                        model = SphereShape.load(lines[++i]);
                }
                Transform t = Transform.load(lines[++i]);
                model.setRelativeTransform(t);
                ((CollisionShape)model).setScale();
                bounds.add(model);
            } else {
                String sb = line + "\n" +
                        lines[i++];
                bounds.add(BoundingModel.load(sb));
            }
        }
        prop.setCanMove(Boolean.parseBoolean(lines[lines.length - 1]));
        prop.setBoundingModels(bounds);

        return new CollisionComponent(entity, prop);
    }

    private CollisionShape.CollisionShapeType isType(String line) {
        for (CollisionShape.CollisionShapeType type : CollisionShape.CollisionShapeType.values()) {
            if (line.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }
}
