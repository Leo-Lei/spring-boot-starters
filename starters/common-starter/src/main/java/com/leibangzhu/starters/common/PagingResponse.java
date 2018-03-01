package com.leibangzhu.starters.common;

public class PagingResponse extends Response {

    public PagingResponse(PagingResponseBuilder builder){
        this.meta = new Meta(builder.status,builder.error);
        this.data = builder.data;
        this.page = new Page(builder.total,builder.current,builder.size);
    }

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Page{
        private int total;
        private int current;
        private int size;

        public Page(int total,int current,int size){
            this.total = total;
            this.current = current;
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public int getCurrent() {
            return current;
        }

        public int getSize() {
            return size;
        }
    }

    public static class PagingResponseBuilder extends ResponseBuilder{
        protected int total;
        protected int current;
        protected int size;

        public PagingResponseBuilder totalPage(int total){
            this.total = total;
            return this;
        }

        public PagingResponseBuilder currentPage(int current){
            this.current = current;
            return this;
        }

        public PagingResponseBuilder size(int size){
            this.size = size;
            return this;
        }

        public PagingResponse build(){
            return new PagingResponse(this);
        }
    }
}
