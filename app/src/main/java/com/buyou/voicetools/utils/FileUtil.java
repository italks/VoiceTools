package com.buyou.voicetools.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.buyou.voicetools.MyApplication;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtil {

    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_ADUIO = 2;
    private static final int TYPE_VIDEO = 3;
    //文件存储类型
    public static final int ORIGINAL=10;//原图，专指应用内原图
    public static final int THUMBNAIL=11;//缩略图
    public static final int CACHE=12;//缓存

    /**
     * {@link #TYPE_IMAGE}<br/>
     * {@link #TYPE_ADUIO}<br/>
     * {@link #TYPE_VIDEO} <br/>
     *
     * @param type
     * @return
     */
    private static String getPublicFilePath(int type) {
        String fileDir = null;
        String fileSuffix = null;
        switch (type) {
            case TYPE_ADUIO:
                fileDir = MyApplication.getInstance().mVoicesDir;
                fileSuffix = ".mp3";
                break;
            case TYPE_VIDEO:
                fileDir = MyApplication.getInstance().mVideosDir;
                fileSuffix = ".mp4";
                break;
            case TYPE_IMAGE:
                fileDir = MyApplication.getInstance().mPicturesDir;
                fileSuffix = ".jpg";
                break;
        }
        if (fileDir == null) {
            return null;
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
    }

    /**
     * {@link #TYPE_ADUIO}<br/>
     * {@link #TYPE_VIDEO} <br/>
     *
     * @param type
     * @return
     */
    private static String getPrivateFilePath(int type, String userId) {
        String fileDir = null;
        String fileSuffix = null;
        switch (type) {
            case TYPE_ADUIO:
                fileDir = MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC;
                fileSuffix = ".mp3";
                break;
            case TYPE_VIDEO:
                fileDir = MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES;
                fileSuffix = ".mp4";
                break;
        }
        if (fileDir == null) {
            return null;
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
    }
    /**
     * @param type
     * @param userId
     *
     * type:图片的原始位置，缩略图，压缩后的位置
     * */
    public static String getImageFilePath(int type, String userId){
        String fileDir=null;
        switch (type){
            case ORIGINAL:
                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_PICTURES+ File.separator+"original"+ File.separator;
                break;
            case THUMBNAIL:
                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_PICTURES+ File.separator+"thumbnail"+ File.separator;
                break;
            case CACHE:
                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_PICTURES+ File.separator+"cache"+ File.separator;
                break;
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return fileDir;
    }
//    /**
//     * @param type
//     * @param userId
//     *
//     * type:录音原始位置，缩略图返回视频的截图位置，压缩后的位置
//     * */
//    public static String getAduioFilePath(int type,String userId){
//        String fileDir=null;
//        switch (type){
//            case ORIGINAL:
//                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC+File.separator+"original";
//                break;
//            case CACHE:
//                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC+File.separator+"cache";
//                break;
//        }
//        return fileDir;
//    }
//    /**
//     * @param type
//     * @param userId
//     *
//     * type:录像的原始位置，缩略图返回视频的截图位置，压缩后的位置
//     * */
//    public static String getVideoFilePath(int type,String userId){
//        String fileDir=null;
//        switch (type){
//            case ORIGINAL:
//                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES+File.separator+"original";
//                break;
//            case THUMBNAIL:
//                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES+File.separator+"thumbnail";
//                break;
//            case CACHE:
//                fileDir=MyApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES+File.separator+"cache";
//                break;
//        }
//        return fileDir;
//    }




    public static String getRandomImageFilePath() {
        return getPublicFilePath(TYPE_IMAGE);
    }

    public static String getRandomAudioFilePath() {
//        User user = MyApplication.getInstance().getLoginUser();
//        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
//            return getPrivateFilePath(TYPE_ADUIO, user.getUserId());
//        } else {
//
//        }
        return getPublicFilePath(TYPE_ADUIO);
    }

    public static String getRandomAudioAmrFilePath() {
        /*User user = MyApplication.getInstance().getLoginUser();

        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            filePath = getPrivateFilePath(TYPE_ADUIO, user.getUserId());
        } else {
            filePath = getPublicFilePath(TYPE_ADUIO);
        }*/
        String filePath = null;
        filePath = getPublicFilePath(TYPE_ADUIO);
        if (!TextUtils.isEmpty(filePath)) {
            return filePath.replace(".mp3", ".amr");
        } else {
            return null;
        }
    }

    public static String getRandomVideoFilePath() {
//        User user = MyApplication.getInstance().getLoginUser();
//        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
//            return getPrivateFilePath(TYPE_VIDEO, user.getUserId());
//        } else {
//            return getPublicFilePath(TYPE_VIDEO);
//        }
        return getPublicFilePath(TYPE_VIDEO);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static void createFileDir(String fileDir) {
        File fd = new File(fileDir);
        if (!fd.exists()) {
            fd.mkdirs();
        }
    }

    /**
     * @param fullName
     */
    public static void delFile(String fullName) {
        File file = new File(fullName);
        if (file.exists()) {
            if (file.isFile()) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 /sdcard/data/
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            System.out.println(path + tempList[i]);
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]); // 再删除空文件夹
            }
        }
    }

    /**
     * 删除文件夹
     * <p>
     * String 文件夹路径及名称 如/sdcard/data/
     * String
     *
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();
        }
    }


    public static File saveFileByBitmap(Bitmap bitmap, String url){

        String fileName = null;

        if (null != url){
            String[] sss = url.split("/");
            if (sss != null && sss.length >= 1){
                fileName = sss[sss.length -1];
            }
        }

        if (fileName == null){
            return null;
        }


        String path = MyApplication.getInstance().mPicturesDir;

        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("xuan", "saveFileByBitmap: "+myCaptureFile.getAbsolutePath());
        return myCaptureFile;
    }

}
