package fr.entityCreator.creator;

import fr.entityCreator.core.exporter.EntityExporter;
import fr.entityCreator.core.importer.*;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.frame.*;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Workspace {

    private Entity currentEntity;

    private Logger log = Logger.getLogger("Workspace");
    public void save() {
        try {
            if (currentEntity != null)EntityExporter.exportEntity(currentEntity);
            else {
                log.log(Level.INFO,"cannot export entity because it null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.WARNING, "cannot export entity", e);
        }
    }

    public Entity getCurrentEntity() {
        return currentEntity;
    }

    public void open(){
        currentEntity = EntityImporter.importEntity();
    }

    public void newEntity(String name){
        this.currentEntity = new Entity(name);
    }

    public void createNewEntity() {
        EntityTypeChooserScreen type = new EntityTypeChooserScreen();
        try {
            this.currentEntity = ((Entity) type.getChosen().getDeclaredConstructor(String.class).newInstance("test"));
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
