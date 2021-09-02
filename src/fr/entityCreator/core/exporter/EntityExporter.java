package fr.entityCreator.core.exporter;

import fr.entityCreator.entity.Entity;

public class EntityExporter {

    public static void exportEntity(Entity e) throws Exception{
        e.export();
    }
}
