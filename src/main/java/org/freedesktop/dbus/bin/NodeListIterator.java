/*
   D-Bus Java Implementation
   Copyright (c) 2005-2006 Matthew Johnson

   This program is free software; you can redistribute it and/or modify it
   under the terms of either the GNU Lesser General Public License Version 2 or the
   Academic Free Licence Version 2.1.

   Full licence texts are included in the COPYING file with this program.
*/
package org.freedesktop.dbus.bin;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class NodeListIterator implements Iterator<Node> {
    // CHECKSTYLE:OFF
    NodeList nl;
    int      i;
    // CHECKSTYLE:ON

    NodeListIterator(NodeList _nl) {
        this.nl = _nl;
        i = 0;
    }

    @Override
    public boolean hasNext() {
        return i < nl.getLength();
    }

    @Override
    public Node next() {
        Node n = nl.item(i);
        i++;
        return n;
    }

    @Override
    public void remove() {
    };
}
