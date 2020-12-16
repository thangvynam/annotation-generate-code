import org.junit.Assert;
import org.junit.Test;
import zp.cps.annotation.entity.RedisKey;

/**
 * @author namtv3
 */
public class RedisKeyTest {

    public void test_FeatureA() {

    }

    public void featureC() {

    }

    @Test
    public void test() {
        String key = RedisKey.genCPSEntityRedisKey(441, "0906368182");
        String expect = "CPS|441|0906368182";
        Assert.assertEquals(key, expect);
    }
}
