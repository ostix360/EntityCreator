package fr.entityCreator.core.resourcesProcessor;

public class GLRequestProcessor {
    private static final int MAX_REQUEST_TIME = 12;
    private static GLRequestQueue requestQueue = new GLRequestQueue();

    public static void sendRequest(GLRequest... request) {
        requestQueue.addAllRequest(request);
    }

    public static void executeRequest() {
        float remainingTime = MAX_REQUEST_TIME * 1_000_000;
        long start = System.nanoTime();
        while (requestQueue.hasRequests()) {
            System.out.println("there is some requests");
            requestQueue.acceptNextRequest().execute();
            long end = System.nanoTime();
            long timeTaken = end - start;
            remainingTime -= (float) timeTaken;
            start = end;
            if (remainingTime < 0.0F) {
                break;
            }
        }
    }
}
