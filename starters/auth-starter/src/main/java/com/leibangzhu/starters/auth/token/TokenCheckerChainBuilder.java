package com.leibangzhu.starters.auth.token;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TokenCheckerChainBuilder {

    @Autowired
    private ITokenChecker tokenChecker;
    @Autowired
    private List<ITokenCheckerFilter> filters;

    public ITokenChecker buildChain() {
        ITokenChecker last = tokenChecker;
        for (int i = filters.size() - 1; i >= 0; i--) {
            ITokenCheckerFilter filter = filters.get(i);
            ITokenChecker next = last;

            last = request -> filter.handle(next, request);
        }
        return last;
    }
}