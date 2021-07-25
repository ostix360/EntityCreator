package fr.entityCreator.graphics.font.meshCreator;



import fr.entityCreator.graphics.font.rendering.MasterFont;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.toolBox.Color;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class GUIText {

    private final String textString;
    private final float fontSize;
    private final boolean centerText;
    private final Vector2f position;
    private final float lineMaxSize;
    private final FontType font;
    private MeshModel textMeshVao;
    private int vertexCount;
    private int numberOfLines;
    private Vector3f colour = new Vector3f(0f, 0f, 0f);

    public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
                   boolean centered) {
        this.textString = text;
        this.fontSize = fontSize;
        this.font = font;
        this.position = position;
        this.lineMaxSize = maxLineLength;
        this.centerText = centered;
        MasterFont.add(this);
    }

    public void remove() {
        MasterFont.remove(this);
    }

    public FontType getFont() {
        return font;
    }

    public void setColour(Color c) {
        colour = c.getVec3f();
    }


    public Vector3f getColour() {
        return colour;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    protected void setNumberOfLines(int number) {
        this.numberOfLines = number;
    }

    public Vector2f getPosition() {
        return position;
    }

    public MeshModel getVao() {
        return textMeshVao;
    }

    public void setMeshInfo(MeshModel vao, int verticesCount) {
        this.textMeshVao = vao;
        this.vertexCount = verticesCount;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    protected float getFontSize() {
        return fontSize;
    }

    protected boolean isCentered() {
        return centerText;
    }

    protected float getMaxLineSize() {
        return lineMaxSize;
    }

    protected String getTextString() {
        return textString;
    }

}
