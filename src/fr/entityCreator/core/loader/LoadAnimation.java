package fr.entityCreator.core.loader;


import fr.entityCreator.entity.animated.animation.animation.Animation;
import fr.entityCreator.entity.animated.animation.loaders.AnimationLoader;
import fr.entityCreator.toolBox.FileType;
import fr.entityCreator.toolBox.Config;

public class LoadAnimation {


    private static Animation animation;

    public static Animation loadAnimatedModel(String animationFile) {

         return animation = AnimationLoader.loadAnimation(animationFile);

    }

    public static Animation getAnimation() {
        return animation;
    }
}
