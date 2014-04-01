package com.example.test;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.example.utils.ApkUtil;

public class ApkUtilTestCase extends AndroidTestCase {

	private String mBrowserPackageName;

	/**
	 * 该方法会在TestCase启动前调用
	 */
	@Override
	public void setUp() {
		mBrowserPackageName = "com.google.android.browser";
	}

	public void testGetVersionName() {
		Assert.assertEquals("1.0", ApkUtil.getVersionName(getContext()));
	}

	public void testGetVersionNameWithValue() {
		Assert.assertEquals("1.0",
				ApkUtil.getVersionName(getContext(), mBrowserPackageName));
	}

	public void testGetVersionCode() {
		Assert.assertEquals(1, ApkUtil.getVersionCode(getContext()));
	}

	public void testGetVersionCodeWithValue() {
		Assert.assertEquals(1,
				ApkUtil.getVersionCode(getContext(), mBrowserPackageName));
	}

}
