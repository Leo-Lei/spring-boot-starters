package com.leibangzhu.starters.extension.event.data;

import com.alibaba.fastjson.JSON;
import com.leibangzhu.starters.common.eventaggregator.IEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by lili on 2016/10/8.
 */
public final class EventMessage {

    private static final String EVENT_MESSAGE_FORMAT = "%s%s%s";
    private static final String EVENT_MESSAGE_SEPARATOR = "^";

    private IEvent event;

    private String id;

    private EventMessage(IEvent event){

        this.event = event;
        this.id = event.getParams().get(EventField.EVENT_ID).toString();
    }

    public static EventMessage FromEvent(IEvent event) {

        return new EventMessage(event);
    }

    public static EventMessage FromEventMessage(String message) {

        String[] tokens = StringUtils.split(message.toString(), EVENT_MESSAGE_SEPARATOR);
        if(2 != tokens.length){
            throw new RuntimeException("Invalid event message format.");
        }

        Map<String, Object> eventArgs = JSON.parseObject(tokens[1], Map.class);

        Event event = new Event(tokens[0]);
        event.getParams().putAll(eventArgs);

        return new EventMessage(event);
    }

    public String toMessage(){

        return String.format(EVENT_MESSAGE_FORMAT, event.getName(), EVENT_MESSAGE_SEPARATOR, JSON.toJSONString(event.getParams()));
    }

    public Map<String, Object> getEventArgs() {
        return event.getParams();
    }

    public String getId() {
        return id;
    }

    public String getEventName() {
        return event.getName();
    }

    public IEvent getEvent() {

        return event;
    }
}
