package com.example.test;

import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite {

	/**
	 * 添加要执行测试的TestCase，但是经过我的试验，发现这两行代码加不加都无所谓，
	 *
	 * 都能正常执行TestCase，甚至不要这个类都行，求高手正解！
	 */
    public MyTestSuite() {
        addTestSuite(MathUtilTestCase.class);
        addTestSuite(ApkUtilTestCase.class);
    }
}
