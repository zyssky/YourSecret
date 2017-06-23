package com.example.administrator.yoursecret.Network;

import com.example.administrator.yoursecret.Entity.Comment;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/22.
 */

public interface CommentService {
    @POST("Rest/comment/get")
    Observable<List<Comment>> getComments(@Body RequestBody body);

    @POST("Rest/comment")
    Observable<ResponseBody> putComment(@Body RequestBody body);
}
