package com.example.administrator.yoursecret.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PermissionGrantHouse {

    public PermissionGrantHouse(){
        map = new HashMap<>();
    }

    private Map<Integer,PermissionUtils.PermissionGrant> map;

    public void putPermissionGrant(int requestCode, PermissionUtils.PermissionGrant permissionGrant){
        map.put(requestCode,permissionGrant);
    }

    public PermissionUtils.PermissionGrant removePermissionGrant(int requestCode){
        return map.remove(requestCode);
    }
}
