package fr.entityCreator.core.resourcesProcessor;

public abstract class GLRequest {
    protected boolean isExecuted = false;
    protected void execute(){
        System.out.println("executed");
        isExecuted = true;
    }

    public boolean isExecuted() {
        return isExecuted;
    }
}
