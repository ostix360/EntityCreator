package fr.entityCreator.core.importer;

import fr.entityCreator.entity.*;
import fr.entityCreator.frame.*;

public class EntityImporter {
    public static Entity importEntity() throws Exception {
        return Entity.load(new EntityChooserScreen().getChosen());
    }
}
