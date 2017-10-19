package com.leibangzhu.starters.common.eventaggregator;

import java.util.Map;

public interface IEvent {

    String getName();
    Map<String, Object> getParams();
}
