package fr.entityCreator.core;

import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.frame.ErrorPopUp;

public class Timer {

    public static final int timpeOut = 2500;

    public static void waitForRequest(GLRequest request){
        long millis = System.currentTimeMillis();
        while(!request.isExecuted()){
           // System.out.println(request.isExecuted());
            if ((System.currentTimeMillis() - millis) > timpeOut){
                new ErrorPopUp("there is some thig wrong during the GL request");
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
