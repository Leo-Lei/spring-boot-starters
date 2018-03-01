package com.leibangzhu.starters.common.templateengine;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerTemplateEngine implements ITemplateEngine {

    private static Configuration cfg;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setDefaultEncoding("UTF-8");
    }

    @Override
    public String render(String template, Map<String, String> map) {
        try {
            Template t = new Template("",new StringReader(template),cfg);
            Writer out = new StringWriter();
            t.process(map,out);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
