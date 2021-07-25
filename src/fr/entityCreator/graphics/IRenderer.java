package fr.entityCreator.graphics;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.model.Model;

public interface IRenderer {

    void prepareInstance(Entity entity);

    void prepareTexturedModel(Model model);


    void finish();
}
