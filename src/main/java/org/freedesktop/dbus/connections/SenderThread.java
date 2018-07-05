package org.freedesktop.dbus.connections;

import org.freedesktop.dbus.messages.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class SenderThread extends Thread {

    private boolean      terminate;

    private final LinkedBlockingQueue<Message> outgoingQueue = new LinkedBlockingQueue<>();

    private final AbstractConnection abstractConnection;


    SenderThread(AbstractConnection _abstractConnection) {
        abstractConnection = _abstractConnection;
        setName("DBUS Sender Thread");
    }

    public void terminate() {
        terminate = true;
        interrupt();
    }

    public LinkedBlockingQueue<Message> getOutgoingQueue() {
        return outgoingQueue;
    }

    @Override
    public void run() {
        Message m = null;
        // block on the outbound queue and send from it
        while (!terminate) {
            try {
                m = outgoingQueue.take();
                if (m != null) {
                    abstractConnection.sendMessage(m);
                    m = null;
                }
            } catch (InterruptedException _ex) {
            }
        }
        // flush the outbound queue before disconnect.
        while (!outgoingQueue.isEmpty()) {
            Message poll = outgoingQueue.poll();
            if (poll != null) {
                abstractConnection.sendMessage(outgoingQueue.poll());
            } else {
                break;
            }
        }
    }
}
