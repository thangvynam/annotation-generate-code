package zp.cps.annotation.entity;

import zp.cps.annotation.annotation.CPSRedisKey;
import zp.cps.annotation.annotation.Mandatory;

/**
 * @author namtv3
 */
@CPSRedisKey(prefixRedis = "CPS")
public class CPSEntity {

    @Mandatory
    private int appId;

    private int serviceId;

    @Mandatory
    private String phoneNumber;
}
