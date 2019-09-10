package com.buyou.voicetools.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class PermissionUtil {

    public static boolean checkSelfPermissions(@NonNull Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取被拒绝的权限
     */
    public static List<String> getDeniedPermissions(@NonNull Activity activity, @NonNull String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        if (!deniedPermissions.isEmpty()) {
            return deniedPermissions;
        }
        return null;
    }


    /**
     * 是否拒绝了再次申请权限的请求（选择了不再询问 || 部分机型默认为不在询问）
     */
    public static boolean deniedRequestPermissionsAgain(@NonNull Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            /**
             * 注：调用该方法的前提是应用已申请过该权限，如应用未申请就调用此方法，返回false
             * 1.已请求过该权限且用户拒绝了请求,返回true
             * 2.用于拒绝了请求，且选择了不再询问,返回false
             * 3.设备规范禁止应用具有该权限，返回false
             */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 申请权限<br/>
     *
     * @return 是否已经获取权限
     */
    public static boolean requestPermissions(Activity activity, int requestCode, String... permissions) {
        if (!checkSelfPermissions(activity, permissions)) {// 权限不全
            List<String> deniedPermissions = getDeniedPermissions(activity, permissions);
            if (deniedPermissions != null) {// 申请权限
                ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            }
            return false;
        }
        return true;
    }

    /**
     * 申请权限返回
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
                                                  @NonNull OnRequestPermissionsResultCallbacks callBack) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        if (!granted.isEmpty()) {
            callBack.onPermissionsGranted(requestCode, granted, denied.isEmpty());
        }
        if (!denied.isEmpty()) {
            callBack.onPermissionsDenied(requestCode, denied, granted.isEmpty());
        }
    }

    /**
     * 申请权限返回
     */
    public interface OnRequestPermissionsResultCallbacks {
        /**
         * @param isAllGranted 是否全部同意
         */
        void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted);

        /**
         * @param isAllDenied 是否全部拒绝
         */
        void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied);
    }

    // 判断 是否拥有悬浮窗权限
    public static boolean checkAlertWindowsPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = 24;
            arrayOfObject[1] = Binder.getCallingUid();
            arrayOfObject[2] = context.getPackageName();
            int invoke = ((Integer) method.invoke(object, arrayOfObject));
            return invoke == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {
//            ActivityStack.getInstance().exit();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return false;
    }
}
