package com.leibangzhu.starters.common;

import com.leibangzhu.starters.common.util.TestUtil;
import org.junit.Test;

public class TestUtilTest {
    @Test
    public void test() throws Exception {
        Bar bar = new Bar();
        TestUtil.setField(bar,"name","leo");
        System.out.println(bar.getEmail());
        System.out.println(bar.getName());
    }
}
