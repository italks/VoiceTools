package com.buyou.voicetools.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;

public class Permissions implements PermissionUtil.OnRequestPermissionsResultCallbacks{
    private static Permissions instance = null;

    public static final Permissions getInstance() {
        if (instance == null) {
            synchronized (Permissions.class) {
                if (instance == null) {
                    instance = new Permissions();
                }
            }
        }
        return instance;
    }

    public abstract static class Return_Nextstep{
        protected abstract void nextstep();
    }

    private Return_Nextstep return_nextstep;
    public void quanxian(Context context, String[] permissionsREAD,int stype,Return_Nextstep return_nextstep){
        this.return_nextstep = return_nextstep;
        boolean bool = PermissionUtil.requestPermissions((Activity) context, stype, permissionsREAD);
        if(bool)return_nextstep.nextstep();
        else
            Log.e("权限1","请打开手机权限");
        if (lacksPermissions(context, permissionsREAD)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, stype);
            }else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, stype);
                Log.e("权限2","请打开手机权限");
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, stype);
            }else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, stype);
                Log.e("权限3","请打开手机权限");
            }
        }else return_nextstep.nextstep();
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    private boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {
        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts,permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {
        if(isAllGranted){
            return_nextstep.nextstep();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {
        if(perms != null && perms.size() > 0) {
//            for (int i = 0; i < perms.size(); i++) {
//                if(perms.get(i).equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
//                    CustomToast.showToast(MyApplication.getContext(),"请打开手机权限");
//                }else if(perms.get(i).equals(Manifest.permission.CAMERA)){
//                    CustomToast.showToast(MyApplication.getContext(),"请打开手机权限");
//                }
//            }
//            CustomToast.showToast(MyApplication.getContext(),"请打开手机权限");
        }
    }
}
