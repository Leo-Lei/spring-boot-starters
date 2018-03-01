package com.leibangzhu.starters.common.templateengine;

import java.util.Map;

public interface ITemplateEngine {

    String render(String template, Map<String, String> map);
}
