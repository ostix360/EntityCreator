package fr.entityCreator.entity.ai;

public abstract class AIAction {

    public abstract boolean shouldExecute();

    public abstract void start();

    public abstract void update();

    public abstract void reset();
}
