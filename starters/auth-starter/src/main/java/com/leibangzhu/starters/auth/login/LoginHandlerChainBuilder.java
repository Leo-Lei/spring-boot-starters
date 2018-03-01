package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.login.filter.ILoginRequestHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginHandlerChainBuilder {

    @Autowired
    private ILoginRequestHandler handler;
    @Autowired
    private List<ILoginRequestHandlerFilter> filters;

    // 构建登录请求的处理链
    // 参考Dubbo的RPC调用链的Filter设计
    // 独立出Handler和Filter，再结合责任链和修饰模式，创建一个处理链
    // Handler实际处理请求
    // Filter添加额外的处理逻辑
    // 对使用者暴露的是Handler，对扩展者暴露的是Filter
    public ILoginRequestHandler buildHandlerChain(){
        ILoginRequestHandler last = handler;
        for (int i = filters.size() - 1; i >= 0; i --){
            ILoginRequestHandlerFilter filter = filters.get(i);
            ILoginRequestHandler next = last;

            last = request -> filter.handle(next,request);
        }
        return last;
    }
}
