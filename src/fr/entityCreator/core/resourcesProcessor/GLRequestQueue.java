package fr.entityCreator.core.resourcesProcessor;

import java.util.ArrayList;
import java.util.List;

public class GLRequestQueue {

    private List<GLRequest> requestQueue = new ArrayList<>();

    public synchronized void addRequest(GLRequest request) {
        this.requestQueue.add(request);
    }

    public synchronized GLRequest acceptNextRequest() {
        return this.requestQueue.remove(0);
    }

    public synchronized boolean hasRequests() {
        return !this.requestQueue.isEmpty();
    }
}
