package com.leibangzhu.sample.common;

import com.leibangzhutech.starters.business.common.model.Heartbeat;
import com.leibangzhutech.starters.business.common.model.LockManufacturer;
import com.leibangzhutech.starters.common.http.IHttpClient;
import com.leibangzhutech.starters.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeartBeatService {

    @Autowired
    private IOfoService ofoService;
    @Autowired
    private MongoService mongoService;
    @Autowired
    private IHttpClient httpClient;

    public void process(Heartbeat heartbeat, LockManufacturer lockManufacturer) throws Exception {
        String communicateId = heartbeat.getCommunicateId();
        LockManufacturer type = lockManufacturer;

        String lockId = mongoService.getLockId(communicateId,type);
        String ofoSn = ofoService.getOfoSn(lockId);

        if (StringUtil.isNotBlank(ofoSn)){
            httpClient.post("http://foo.bar.com",ofoSn);
        }
    }
}
