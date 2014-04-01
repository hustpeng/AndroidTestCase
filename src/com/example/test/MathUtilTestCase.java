package com.example.test;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.example.utils.MathUtil;

public class MathUtilTestCase extends AndroidTestCase {
	static final String LOG_TAG = "MathTest";

    protected int num1;
    protected int num2;

    /**
     * 该方法会在TestCase启动前调用
     */
    @Override
	public void setUp() {
        num1 = 2;
        num2 = 3;
    }

    public void testAdd() {
        Assert.assertEquals(5,MathUtil.add(num1 ,num2));
    }

    public void testDec() {
        Assert.assertEquals(1,MathUtil.dec(num2, num1));
    }

    public void testAnd() {
        Assert.assertEquals(6,MathUtil.and(num1, num2));
    }

    public void testDiv() {
        Assert.assertEquals(1,MathUtil.div(num2, num1));
    }

    public void testMod() {
        Assert.assertEquals(2,MathUtil.mod(num1, num2));
    }

}
