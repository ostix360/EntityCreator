package fr.entityCreator.main;

import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.creator.Workspace;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.graphics.MasterRenderer;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MasterRenderer renderer = new MasterRenderer();
        Workspace workspace = new Workspace();
        Thread main = Thread.currentThread();
        MainFrame frame = new MainFrame(renderer,workspace);
        Thread renderThread = new Thread(frame.getRenderRunnable(),"Render Thread");
        renderThread.start();
    }

}
