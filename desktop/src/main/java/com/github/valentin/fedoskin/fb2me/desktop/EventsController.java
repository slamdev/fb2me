package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

import com.sun.javafx.event.EventHandlerManager;

public class EventsController implements EventTarget {

    private final EventHandlerManager eventDispatcher = new EventHandlerManager(this);

    /**
     * @see EventHandlerManager#addEventHandler(EventType, EventHandler)
     */
    public <E extends Event> void addEventHandler(EventType<E> eventType, EventHandler<E> eventHandler) {
        eventDispatcher.addEventHandler(eventType, eventHandler);
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(eventDispatcher);
    }

    public void fire(Event event) {
        Event.fireEvent(this, event);
    }

    /**
     * @see EventHandlerManager#removeEventHandler(EventType, EventHandler)
     */
    public <E extends Event> void removeEventHandler(EventType<E> eventType, EventHandler<E> eventHandler) {
        eventDispatcher.removeEventHandler(eventType, eventHandler);
    }
}