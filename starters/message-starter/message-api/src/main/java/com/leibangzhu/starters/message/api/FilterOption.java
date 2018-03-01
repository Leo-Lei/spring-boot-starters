package com.leibangzhu.starters.message.api;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class FilterOption {
    private String topic;
    private Set<String> tags = new LinkedHashSet<>();

    public FilterOption(FilterOptionBuilder builder){
        this.topic = builder.topic;
        this.tags = builder.tags;
    }

    public String getTopic() {
        return topic;
    }

    public Set<String> getTags() {
        return tags;
    }

    public static FilterOptionBuilder builder(){
        return new FilterOptionBuilder();
    }

    public static class FilterOptionBuilder{
        private String topic;
        private Set<String> tags = new LinkedHashSet<>();

        public FilterOptionBuilder topic(String topic){
            this.topic = topic;
            return this;
        }

        public FilterOptionBuilder tag(String tag){
            tags.add(tag);
            return this;
        }

        public FilterOptionBuilder tags(String... tags){
            this.tags.addAll(Arrays.asList(tags));
            return this;
        }

        public FilterOption build(){
            return new FilterOption(this);
        }
    }
}
