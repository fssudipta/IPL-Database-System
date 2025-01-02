package controller;

import javafx.application.Platform;

public class TransferWindowRefresh implements Runnable {
    private ClubHomeWindowController controller;
    Thread thread;

    public TransferWindowRefresh(ClubHomeWindowController controller) {
        this.controller = controller;
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Platform.runLater(() -> controller.loadTransferWindow());
                Thread.sleep(controller.getRefreshRate() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public Thread getThread() {
        return thread;
    }
}