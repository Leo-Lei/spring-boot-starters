package com.leibangzhu.starters.common.templateengine;

import java.util.Map;

public class YunpianTemplateEngine implements ITemplateEngine{

    // Yunpian使用#code#表示变量
    @Override
    public String render(String template, Map<String, String> map) {
        for (Map.Entry<String,String> entry : map.entrySet()){
            template = template.replace("#" + entry.getKey() + "#", entry.getValue());
        }

        return template;
    }
}
