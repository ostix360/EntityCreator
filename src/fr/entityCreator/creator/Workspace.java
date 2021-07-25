package fr.entityCreator.creator;

import fr.entityCreator.core.exporter.EntityExporter;
import fr.entityCreator.entity.Entity;

import javax.swing.*;

public class Workspace {

    private Entity currentEntity;

    public void save() {
        try {
            EntityExporter.exportEntity(currentEntity);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something wrong during export of entity");
        }
    }

    public Entity getCurrentEntity() {
        return currentEntity;
    }

    public void open(){

    }

    public void newEntity(String name){
        this.currentEntity = new Entity(name);
    }

    public void createNewEntity() {
        this.currentEntity = new Entity("test");
    }
}
