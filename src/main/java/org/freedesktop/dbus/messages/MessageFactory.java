package org.freedesktop.dbus.messages;

import org.freedesktop.dbus.errors.Error;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.MessageTypeException;

import java.text.MessageFormat;

public class MessageFactory {

    public static Message createMessage(byte _type, byte[] _buf, byte[] _header, byte[] _body) throws DBusException, MessageTypeException {
        Message m;
        switch (_type) {
            case Message.MessageType.METHOD_CALL:
                m = new MethodCall();
                break;
            case Message.MessageType.METHOD_RETURN:
                m = new MethodReturn();
                break;
            case Message.MessageType.SIGNAL:
                m = new DBusSignal();
                break;
            case Message.MessageType.ERROR:
                m = new Error();
                break;
            default:
                throw new MessageTypeException(MessageFormat.format("Message type {0} unsupported", _type));
        }

        m.populate(_buf, _header, _body);
        return m;
    }

}
