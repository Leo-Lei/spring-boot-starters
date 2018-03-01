package com.leibangzhu.starters.auth;

// 保存当前Web回话的用户
// 使用ThreadLocal来保存和访问当前用户。是线程安全的。
// 可以在多个层来使用该类访问当前用户。比如Controller，Service，Dao等。避免了代码中频繁的通过方法传参在不同层中传递用户信息。
// 只要不切换线程，都可以使用WebContext.currentUser()来获取当前用户。如果切换了线程，可以使用传统的方式，使用方法参数来传递用户信息。
public class WebContext {
    private static final ThreadLocal<Principal> threadLocal = new ThreadLocal<>();

    public static void set(Principal principal){
        threadLocal.set(principal);
    }

    public static Principal currentUser(){
        return threadLocal.get();
    }
}
