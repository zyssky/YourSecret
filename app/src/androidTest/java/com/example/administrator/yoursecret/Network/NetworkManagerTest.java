package com.example.administrator.yoursecret.Network;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.UserManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2017/6/19.
 */
public class NetworkManagerTest {
    @Before
    public void init(){
        App.getInstance().setUserManager(new UserManager());
        App.getInstance().setNetworkManager(new NetworkManager());
//        Context context =new MockContext();
//        FoundationManager.setup(context);

    }

    @Test
    public void register() throws Exception {
        //App.getInstance().getNetworkManager().register();
    }
}