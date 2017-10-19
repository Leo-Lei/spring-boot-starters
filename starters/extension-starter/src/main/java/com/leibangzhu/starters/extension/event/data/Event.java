package com.leibangzhu.starters.extension.event.data;

import com.leibangzhu.starters.common.eventaggregator.BaseEvent;

/**
 * Author: lili
 * Date: 2016/12/30
 * Time: 10:47
 */
public class Event extends BaseEvent {

    private final String name;

    public Event(String name){

        this.name = name;
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Event) {

            Event event = (Event) obj;
            return event.name.equals(name);
        }

        return false;
    }

    public String getName() {
        return name;
    }
}
