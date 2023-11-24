package com.example.qroc.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

public class MMKVUtils {
    private static MMKV kv;

    public static void init(Context context) {
        String rootDir = MMKV.initialize(context);
//        System.out.println("mmkv root: " + rootDir);
        kv = MMKV.defaultMMKV();
        // 根据业务区别存储, 附带一个自己的 ID
        MMKV kv2 = MMKV.mmkvWithID("MyID");
        // 多进程同步支持
        MMKV kv3 = MMKV.mmkvWithID("MyID", MMKV.MULTI_PROCESS_MODE);
    }

    /**
     * 指定保存位置初始化
     * @param dir   dir = getFilesDir().getAbsolutePath() + "/mmkv_2";
     */
    public static void init(String dir) {
        String rootDir = MMKV.initialize(dir);
        kv = MMKV.defaultMMKV();
    }

    /**
     * Ashmem匿名内存
     * @param context 上下文
     */
    public static void initAshmem(Context context){
        kv = MMKV.mmkvWithAshmemID(context, "fdfdsfdf", 1024,
                MMKV.MULTI_PROCESS_MODE, "glader13654564EWAEWA");
    }

    /**
     * 迁移SP 到 MMKV
     */
    private void importSharedPreferences(Context context) {
        MMKV mmkv = MMKV.mmkvWithID("myData");
        SharedPreferences old_man = context.getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 迁移旧数据
        mmkv.importFromSharedPreferences(old_man);
        // 清空旧数据
        old_man.edit().clear().commit();
    }

    public static void putBoolean(String key, boolean value) {
        kv.encode(key, value);
    }

    public static Boolean getBoolean(String key) {
        return kv.decodeBool(key, false);
    }

    public static Boolean getBoolean(String key, boolean defValue) {
        return kv.decodeBool(key, defValue);
    }

    public static void putInteger(String key, int value) {
        kv.encode(key, value);
    }

    public static int getInteger(String key) {
        return kv.decodeInt(key, 1);
    }

    public static int getInteger(String key, int defValue) {
        return kv.decodeInt(key, defValue);
    }

    public static void putString(String key, String value) {
        kv.encode(key, value);
    }

    public static String getString(String key) {
        return kv.decodeString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return kv.decodeString(key, defaultValue);
    }

    /**
     * 移除数据Key
     * @param key  数据Key
     */
    public static void removeKey(String key) {
        // 删除数据
        kv.removeValueForKey(key);
//        mmkv.removeValueForKey("String")
//        mmkv.removeValuesForKeys(arrayOf("int","bool"))
//        mmkv.containsKey("String")
    }
}

