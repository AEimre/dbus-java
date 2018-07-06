package org.freedesktop.dbus.connections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.freedesktop.dbus.messages.ExportedObject;

public class FallbackContainer {

    /**
     * @param _abstractConnection
     */
    FallbackContainer() {
    }

    private Map<String[], ExportedObject> fallbacks = new HashMap<>();

    public synchronized void add(String path, ExportedObject eo) {
        fallbacks.put(path.split("/"), eo);
    }

    public synchronized void remove(String path) {
        fallbacks.remove(path.split("/"));
    }

    public synchronized ExportedObject get(String path) {
        int best = 0;
        int i = 0;
        ExportedObject bestobject = null;
        String[] pathel = path.split("/");
        for (String[] fbpath : fallbacks.keySet()) {
            for (i = 0; i < pathel.length && i < fbpath.length; i++) {
                if (!pathel[i].equals(fbpath[i])) {
                    break;
                }
            }
            if (i > 0 && i == fbpath.length && i > best) {
                bestobject = fallbacks.get(fbpath);
            }
        }
        return bestobject;
    }
}