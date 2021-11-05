package fr.entityCreator.entity.component.ai;


import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;
import fr.entityCreator.toolBox.Logger;

import java.io.BufferedReader;

public class AICreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new AIComponent(entity,new AIProperties(10,1,1,1,1,1,1));
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        AIComponent ai = null;
        try{
            AIProperties prop = JsonUtils.gsonInstance(false).fromJson(component,AIProperties.class);
            ai = new AIComponent(entity,prop);
        }catch(Exception e){
            Logger.err("Failled to load AI Component from " + entity.getModel().getName() + " ");
            e.printStackTrace();
        }
        return ai;
    }
}
