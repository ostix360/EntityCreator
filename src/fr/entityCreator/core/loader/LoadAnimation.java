package fr.entityCreator.core.loader;


import fr.entityCreator.entity.animated.animation.animation.Animation;
import fr.entityCreator.entity.animated.animation.loaders.AnimationLoader;
import fr.entityCreator.toolBox.FileType;
import fr.entityCreator.toolBox.Config;

public class LoadAnimation {


    private static Animation animation;

    public static void loadAnimatedModel(String animationFile) {

        animation = AnimationLoader.loadAnimation(Config.RES_FOLDER + animationFile + FileType.COLLADA.getExtension());

    }

    public static Animation getAnimation() {
        return animation;
    }
}
