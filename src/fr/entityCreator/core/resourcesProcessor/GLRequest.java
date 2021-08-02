package fr.entityCreator.core.resourcesProcessor;

public abstract class GLRequest {
    protected boolean isExecuted = false;
    protected void execute(){
        isExecuted = true;
    }

    public boolean isExecuted() {
        return isExecuted;
    }
}
