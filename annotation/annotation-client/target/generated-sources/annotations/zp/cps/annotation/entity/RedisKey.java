package zp.cps.annotation.entity;

public final class RedisKey {

    public static String genCPSEntityRedisKey(
                                            Object appId
                                            ,
                                            Object phoneNumber
                                            ,
                                        String ... values) {
        StringBuilder builder = new StringBuilder("CPS");

            builder.append("|").append(appId);
            builder.append("|").append(phoneNumber);

        String []data = values;
        int length = data.length;

        for (int i = 0; i < length; ++i) {
            String key = data[i];
            builder.append("|").append(key);
        }

        return builder.toString();
    }
}
