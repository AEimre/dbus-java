package org.freedesktop.dbus.connections;

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.FatalException;
import org.freedesktop.dbus.messages.Message;

import java.util.Objects;

public class IncomingMessageThread extends Thread {

    private boolean                  terminate;
    private final AbstractConnection connection;

    public IncomingMessageThread(AbstractConnection _connection) {
        Objects.requireNonNull(_connection);
        connection = _connection;
        setName("DBusConnection");
        setDaemon(true);
    }

    public void setTerminate(boolean _terminate) {
        terminate = _terminate;
        interrupt();
    }

    @Override
    public void run() {

        Message msg = null;
        while (!terminate) {
            msg = null;

            // read from the wire
            try {
                // this blocks on outgoing being non-empty or a message being available.
                msg = connection.readIncoming();
                if (msg != null) {

                    connection.handleMessage(msg);

                    msg = null;
                }
            } catch (DBusException _ex) {
                if (_ex instanceof FatalException) {
                    if (connection.isConnected()) {
                        connection.disconnect();
                        setTerminate(true);
                    }
                }
            }
        }
    }
}
