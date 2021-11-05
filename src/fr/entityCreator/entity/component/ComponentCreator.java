package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;

import java.io.BufferedReader;
import java.io.File;

public interface ComponentCreator {
    Component createComponent(Entity entity);

    Component loadComponent(String component, Entity entity);
}
