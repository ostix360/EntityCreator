package fr.entityCreator.graphics.font.rendering;



import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.graphics.font.meshCreator.FontType;
import fr.entityCreator.graphics.font.meshCreator.GUIText;
import fr.entityCreator.graphics.font.meshCreator.TextMeshData;
import fr.entityCreator.graphics.model.MeshModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterFont {

    private static final Map<FontType, List<GUIText>> guisTexts = new HashMap<>();
    private static Loader loader;
    private final FontRenderer renderer;

    public MasterFont(Loader theLoader) {
        renderer = new FontRenderer();
        loader = theLoader;
    }

    public static void add(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        MeshModel vao = loader.loadFontToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = guisTexts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public void render() {
        renderer.render(guisTexts);
    }

    public static void remove(GUIText text) {
        List<GUIText> texts = guisTexts.get(text.getFont());
        texts.remove(text);
        if (texts.isEmpty()) {
            guisTexts.remove(text.getFont());
        }

    }

    public void cleanUp() {
        for (FontType font : guisTexts.keySet()) {
            List<GUIText> texts = guisTexts.get(font);
            for (GUIText text : texts) {
                text.remove();
            }
            texts.clear();
        }
        guisTexts.clear();
        renderer.cleanUp();
    }
}
