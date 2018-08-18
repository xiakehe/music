package com.example.study_core.rx;

public class Event<T> {

    private final T obj;
    private final EVENT_INFO eventInfo;

    public Event(T obj, EVENT_INFO eventInfo) {
        this.obj = obj;
        this.eventInfo = eventInfo;
    }

    public T getObj() {
        return obj;
    }

    public EVENT_INFO getEventInfo() {
        return eventInfo;
    }

    public enum EVENT_INFO {
        SIGN_IN,
        SIGN_OUT
    }

}
