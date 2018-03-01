package com.leibangzhu.starters.auth.token;

import com.leibangzhu.starters.auth.Principal;

public interface ITokenCreator {
    String create(Principal user);
}
