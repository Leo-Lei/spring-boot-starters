package com.leibangzhu.starters.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class Event {
    private KeyValue<String,String> preKeyValue = new KeyValue<>();
    private KeyValue<String,String> keyValue = new KeyValue<>();
    private EventType eventType = EventType.UNRECOGNIZED;

    public Map<String, String> getHeaders() {
        return headers;
    }

    private Map<String,String> headers = new LinkedHashMap<>();

    public static EventBuilder newBuilder(){
        return new EventBuilder();
    }

    public EventType getEventType() {
        return eventType;
    }
    public KeyValue<String, String> getPreKeyValue() {
        return preKeyValue;
    }

    public KeyValue<String, String> getKeyValue() {
        return keyValue;
    }

    public static class EventBuilder {

        private Event registryEvent = new Event();

        public EventBuilder preKey(String key){
            registryEvent.preKeyValue.setKey(key);
            return this;
        }

        public EventBuilder preValue(String value){
            registryEvent.preKeyValue.setValue(value);
            return this;
        }

        public EventBuilder key(String key){
            registryEvent.keyValue.setKey(key);
            return this;
        }

        public EventBuilder value(String value){
            registryEvent.keyValue.setValue(value);
            return this;
        }

        public Event build(){
            return registryEvent;
        }

        public EventBuilder eventType(EventType eventType){
            registryEvent.eventType = eventType;
            return this;
        }

        public EventBuilder header(String key,String value){
            registryEvent.headers.put(key,value);
            return this;
        }
    }

    public static enum EventType {
        PUT,
        DELETE,
        UNRECOGNIZED;

        private EventType() {
        }
    }
}
