package com.leibangzhu.starters.auth;

import java.util.*;

// 包含用户id和用户相关的附加信息，比如角色,权限等
public class Principal {
    public static final String USER_ID = "userId";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";

    // 用户ID
    private String userId = "";

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    // 用户角色
    private List<String> roles = new ArrayList<>();
    // 用户权限
    private List<String> permissions = new ArrayList<>();
    // 用户额外信息,可自定义
    private Map<String,String> properties = new LinkedHashMap<>();

    public Principal(PrincipalBuilder builder){
        this.userId = builder.userId;
        this.properties = builder.properties;
        this.roles = builder.roles;
        this.permissions = builder.permissions;
    }

    public static PrincipalBuilder newBuilder(){
        return new PrincipalBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getProperty(String key){
        return properties.get(key);
    }


    // Builder类
    public static class PrincipalBuilder{
        private String userId;
        private List<String> roles = new ArrayList<>();
        private List<String> permissions = new ArrayList<>();
        private Map<String,String> properties = new LinkedHashMap<>();

        public PrincipalBuilder userId(String userId){
            this.userId = userId;
            return this;
        }

        public PrincipalBuilder role(String role){
            this.roles.add(role);
            return this;
        }

        public PrincipalBuilder roles(String... roles){
            this.roles.addAll(Arrays.asList(roles));
            return this;
        }

        public PrincipalBuilder permissions(String... permissions ){
            this.permissions.addAll(Arrays.asList(permissions));
            return this;
        }

        public PrincipalBuilder permission(String permission){
            this.permissions.add(permission);
            return this;
        }

        public PrincipalBuilder property(String key,String value){
            this.properties.put(key,value);
            return this;
        }

        public PrincipalBuilder properties(Map<String,String> properties){
            for (Map.Entry<String,String> entry : properties.entrySet()){
                this.properties.put(entry.getKey(),entry.getValue());
            }
            return this;
        }

        public Principal build(){
            return new Principal(this);
        }
    }
}
