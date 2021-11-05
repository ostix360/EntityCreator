package fr.entityCreator.toolBox;

import fr.entityCreator.entity.animated.animation.animation.Animation;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.textures.TextureLoader;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.FileSystem;

public class Config {

    public static File RES_FOLDER = new File("");
    public static File OUTPUT_FOLDER = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\entityExporter");
    public static File MODELS_FOLDER = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\entityExporter");
    public static File TEXTURES_FOLDER = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\entityExporter");
    public static File MODEL_LOCATION = new File("");
    public static File optionFile = new File(System.getProperty("user.home", ".")+ "\\AppData\\Roaming\\EntityCreator.config");
    public static MeshModel BOX;
    public static MeshModel CAPSULE;
    public static MeshModel CONE;
    public static MeshModel CYLINDER;
    public static MeshModel SPHERE;
    public static TextureLoader WHITE;
    public static MeshModel CUBE;
    public static Animation CURRENT_ANIMATION;

}
