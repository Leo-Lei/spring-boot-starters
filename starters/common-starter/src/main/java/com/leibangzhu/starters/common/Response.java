package com.leibangzhu.starters.common;

public class Response {
    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }

    protected Meta meta;
    protected Object data;

    public Response(ResponseBuilder builder){
        this.meta = new Meta(builder.status,builder.error);
        this.data = builder.data;
    }

    public static ResponseBuilder newBuilder(){
        return new ResponseBuilder();
    }

    public Response(){}

    public static class Meta{
        private String status;
        private String error;

        public Meta(int status,String error){
            this.status = String.valueOf(status);
            this.error = error;
        }

        public String getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }
    }

    public static class ResponseBuilder{
        protected int status = 200;
        protected String error = "";
        protected Object data = "";

        public ResponseBuilder error(String error){
            this.status = 500;
            this.error = error;
            return this;
        }

        public ResponseBuilder error(int status,String error){
            this.status = status;
            this.error = error;
            return this;
        }

        public ResponseBuilder data(Object data){
            this.data = data;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }
}
