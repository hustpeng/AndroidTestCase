package com.example.utils;


import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
public class ApkUtil {

    /**
     * 判断目标promotion package name是否已经安装
     *
     * @param context
     * @param packageName
     * @return true if has install, otherwise return false.
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean installed = false;

        if (null == context || TextUtils.isEmpty(packageName)) {
            return false;
        }

        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            if (null != packageInfo) {
                installed = true;
            }// end if
        } catch (NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 取得程序名称
     *
     * @param context
     * @return string of application name
     */
    public static String getAppName(Context context) {
        // the parameter ask for simple construct and simple adsContent
        String appName = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            int id = applicationInfo.labelRes;
            if (id != 0) {
                appName = context.getResources().getString(id);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            appName = null;
        }

        return appName;
    }

    /**
     * 取得主程序包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 取得主程序version code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    public static int getVersionCode(Context context,String packageName){
        int versionCode = 0;
        try {
        	PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            if (null != packageInfo) {
            	versionCode = packageInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 取得主程序version name
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getVersionName(context,context.getPackageName());
    }

    public static String getVersionName(Context context, String packageName){
        String versionName = "";
        try {
        	PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            if (null != packageInfo) {
            	versionName = packageInfo.versionName;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private static final int DEFAULT_LAYOUT_ID = -1;

    /**
     * 通过布局资源文件名称来查找布局文件资源ID
     *
     * @param context
     * @param layoutName
     * @return resourceId which match layoutName
     */
    public static int findLayoutIdByName(Context context,
            final String layoutName) {
        int resourceId = DEFAULT_LAYOUT_ID;
        try {
            resourceId = context.getResources().getIdentifier(layoutName,
                    "style", context.getPackageName());
        } catch (final Exception e) {
            e.printStackTrace();
            resourceId = DEFAULT_LAYOUT_ID;
        }
        return resourceId;
    }

    /**
     * 检测配置文件是否开启debug mode, 即 Apk 是否为debug状态(非root手机可以看到程序进程的Log)
     *
     * @param context
     * @return
     */
    public static boolean isDebugModeEnable(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 杀死自身进程
     */
    public static void killProcess() {
        killProcess(android.os.Process.myPid());
    }

    /**
     * 杀死指定 process id 的进程
     *
     * @param pid
     */
    public static void killProcess(int pid) {
        android.os.Process.killProcess(pid);
    }

    /**
     * 获取Application info对象
     *
     * @param context
     * @return
     */
    public static ApplicationInfo getApplicationInfo(Context context) {
        return context.getApplicationInfo();
    }


    /**
     * 安装指定路径下的APK文件。
     * @param context
     * @param apkPath
     * @return
     */
    public static boolean installApp(Context context, String apkPath){
    	boolean success = false;
    	if(!TextUtils.isEmpty(apkPath)){
    		File appFile = new File(apkPath);
    		if(appFile.exists()){
    			final Uri installUri = Uri.fromFile(appFile);
    			final Intent installIntent = new Intent(Intent.ACTION_VIEW);
    			installIntent.setDataAndType(installUri,"application/vnd.android.package-archive");
    			try {
    				context.startActivity(installIntent);
    				success = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
        return success;
    }

    /**
     * 卸载某个程序
     * @param context
     * @param packageName
     * @return
     */
    public static boolean uninstallApp(Context context, String packageName){
    	boolean success = false;
        final Uri uri = Uri.fromParts("package", packageName, null);
        final Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, uri);
        try {
        	context.startActivity(uninstallIntent);
        	success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return success;
    }


    /**
     * 在桌面上创建一个快捷方式，可自定义标题，图标，和执行动作。
     *
     * 需要Permission : com.android.launcher.permission.INSTALL_SHORTCUT
     *
     * @param context
     * @param intent 该快捷方式的意图
     * @param title  标题
     * @param iconId 图片资源
     */
    public static void createShortcut(Context context, Intent intent, String title, int iconId){
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		Parcelable icon = Intent.ShortcutIconResource.fromContext(context,iconId); // 获取快捷键的图标

		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标

		//shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, R.drawable.beach); //可以代替上面两行代码
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);// 快捷方式的标题
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);// 快捷方式的动作

		context.sendBroadcast(shortcut);// 完了你还可以告诉系统你创建了个快捷方式
    }

    /**
     * 判断包名所对应的应用是否安装在SD卡上
     * @return, true if install on SD card
     */
    public static boolean isInstallOnSDCard(Context context, String packageName) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
			if ((appInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
    }

}