package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class NavigationEvent extends Event {

    /**
     * Common supertype for all view event types.
     */
    public static final EventType<NavigationEvent> ANY = new EventType<>(Event.ANY, "NAVIGATION");

    /**
     * This event occurs on view just after it is hidden.
     */
    public static final EventType<NavigationEvent> CHANGED = new EventType<>(NavigationEvent.ANY, "CHANGED");

    private static final long serialVersionUID = -5281769613671923503L;

    private final Object presenter;

    public NavigationEvent(Object source, EventType<? extends Event> eventType) {
        super(source, null, eventType);
        presenter = source;
    }

    @Override
    public NavigationEvent copyFor(Object newSource, EventTarget newTarget) {
        return (NavigationEvent) super.copyFor(newSource, newTarget);
    }

    /**
     * Creates a copy of the given event with the given fields substituted.
     * 
     * @param source the new source of the copied event
     * @param target the new target of the copied event
     * @param eventType the new eventType
     * @return the event copy with the fields substituted
     */
    public NavigationEvent copyFor(Object newSource, EventTarget newTarget, EventType<NavigationEvent> type) {
        NavigationEvent e = copyFor(newSource, newTarget);
        e.eventType = type;
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<NavigationEvent> getEventType() {
        return (EventType<NavigationEvent>) super.getEventType();
    }

    public Object getPresenter() {
        return presenter;
    }

    /**
     * Returns a string representation of this {@code ViewEvent} object.
     * 
     * @return a string representation of this {@code ViewEvent} object.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewEvent [");
        sb.append("source = ").append(getSource());
        sb.append(", eventType = ").append(getEventType());
        sb.append(", consumed = ").append(isConsumed());
        return sb.append("]").toString();
    }
}