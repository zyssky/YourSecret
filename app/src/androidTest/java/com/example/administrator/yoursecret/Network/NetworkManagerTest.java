package com.example.administrator.yoursecret.Network;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.AppManager.UserManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/6/19.
 */
public class NetworkManagerTest {
    @Before
    public void init(){
        ApplicationDataManager.getInstance().setUserManager(new UserManager());
        ApplicationDataManager.getInstance().setNetworkManager(new NetworkManager());
//        Context context =new MockContext();
//        FoundationManager.setup(context);

    }

    @Test
    public void register() throws Exception {
        ApplicationDataManager.getInstance().getNetworkManager().register();
    }

}