package it.is.socket.client.processor;

import it.is.socket.client.processor.lock.MonitorContainer;

public class MapRewriterProcessor {

    private final char[][] playground;

    public MapRewriterProcessor(char[][] playground) {
        this.playground = playground;
    }

    public void startRewriter() {
        Thread serverCallbackListener = new Thread(() -> {
            while (true) {
                rewrite();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "playgroundRewriter");
        serverCallbackListener.setDaemon(true);
        serverCallbackListener.start();
    }

    private void rewrite() {
        synchronized (MonitorContainer.playgroundMonitor) {
            for (char[] chars : playground) {
                System.out.println();
                for (char aChar : chars) {
                    System.out.print(aChar + " ");
                }
            }
        }
    }
}
