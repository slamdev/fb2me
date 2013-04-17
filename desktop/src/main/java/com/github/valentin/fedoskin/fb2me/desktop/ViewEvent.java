package com.github.valentin.fedoskin.fb2me.desktop;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ViewEvent extends Event {

    /**
     * Common supertype for all view event types.
     */
    @SuppressWarnings("hiding")
    public static final EventType<ViewEvent> ANY = new EventType<>(Event.ANY, "VIEW");

    /**
     * This event occurs on view just after it is hidden.
     */
    public static final EventType<ViewEvent> VIEW_HIDDEN = new EventType<>(ViewEvent.ANY, "VIEW_HIDDEN");

    /**
     * This event occurs on view just after it is shown.
     */
    public static final EventType<ViewEvent> VIEW_SHOWN = new EventType<>(ViewEvent.ANY, "VIEW_SHOWN");

    private final View<?, ?> view;

    public ViewEvent(View<?, ?> source, EventType<? extends Event> eventType) {
        super(source, null, eventType);
        view = source;
    }

    @Override
    public ViewEvent copyFor(Object newSource, EventTarget newTarget) {
        return (ViewEvent) super.copyFor(newSource, newTarget);
    }

    /**
     * Creates a copy of the given event with the given fields substituted.
     * 
     * @param source the new source of the copied event
     * @param target the new target of the copied event
     * @param eventType the new eventType
     * @return the event copy with the fields substituted
     */
    public ViewEvent copyFor(Object newSource, EventTarget newTarget, EventType<ViewEvent> type) {
        ViewEvent e = copyFor(newSource, newTarget);
        e.eventType = type;
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<ViewEvent> getEventType() {
        return (EventType<ViewEvent>) super.getEventType();
    }

    public View<?, ?> getView() {
        return view;
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