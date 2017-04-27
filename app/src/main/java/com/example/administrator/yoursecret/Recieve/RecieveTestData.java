package com.example.administrator.yoursecret.Recieve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveTestData implements RecieveContract.Model {
    @Override
    public List<PushMessage> getInitDatas() {
        List<PushMessage> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PushMessage message = new PushMessage();
            message.setTitle("航空mh370不知"+i);
            list.add(message);
        }
        return list;
    }

}
