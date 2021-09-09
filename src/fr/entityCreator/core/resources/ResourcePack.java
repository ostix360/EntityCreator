package fr.entityCreator.core.resources;



import com.sun.prism.Texture;
import fr.entityCreator.audio.SoundSource;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.animated.animation.animation.Animation;
import fr.entityCreator.graphics.model.Model;

import java.util.HashMap;

public class ResourcePack {
    private static HashMap<AnimatedModel,HashMap<String, Animation>> animations;

    public ResourcePack(HashMap<AnimatedModel, HashMap<String, Animation>> animations) {
        this.animations = animations;
    }


    public static HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animations;
    }


}
