package com.example.administrator.yoursecret.Editor.Manager;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Image;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/8.
 */

public class ArticalManager {
    public String TAG = ArticalManager.class.getSimpleName();

    private String template = "<!DOCTYPE html>\n" +
            "<html itemscope itemtype=\"http://schema.org/WebPage\">\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>{browser_title_content}</title>\n" +                               //浏览器标题
            "           <style type=\"text/css\">img {max-width: 100%; width:auto; height:auto;}</style>\n"+
            "           <meta name=\"viewport\" content=\"width=device-width, height=device-height, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0\">\n" +
            "           <meta name=\"description\" content=\"{description_content}\"\n" +    //简介
            "           <meta name=\"keywords\" content=\"{artical_type}\">\n" +      //类型
            "           <meta name=\"image\" content=\"{mainImageUri}\">\n" +     //图片
            "           <meta name=\"location\" content=\"{location_content}\"\n" +   //地址
            "<!-- Wechat meta -->\n" +
            "    <meta property=\"weixin:timeline_title\" content=\"\" />\n" +
            "    <meta property=\"weixin:chat_title\" content=\"\" />\n" +
            "    <meta property=\"weixin:description\" content=\"\" />\n" +
            "    <meta property=\"weixin:image\" content=\"{mainImageUri}\" />" +    //分享链接看到的图片
            "\n" +
            "    </head>\n" +
            "    <body ontouchstart=\"\">\n" +
            "\n" +
            "    <div class=\"page\">\n" +
            "    \n" +
            "    <div class=\"card\">\n" +
            "        \n" +
            "    <section class=\"header\">\n" +
            "        <h1 class=\"title\">{title_content}</h1>\n" +  //标题
            "        <div style=\"display:inline\"> " +
            "            <img class=\"note_author_avatar\"  style=\"max-width:12%;height:auto;border-radius:50%;\" src=\"{author_iconUri}\" alt=\"{author_name}\">\n" +   //作者icon和昵称
            "        </div>\n"+
            "        <div style=\"display:inline-block;position:relative;top:-3px;\"" +
            "            <div class=\"author\" >\n" +
            "              <strong >{author_name}</strong>\n" +      //作者昵称
            "              <div class=\"timestamp\" style=\"color:#aaa;\">{timestamp_content}</div>\n" +  //时间戳
            "            </div>\n" +
            "        </div>\n"+
            "        <div class=\"location\" style=\"margin-top:20px;margin-bottom:20px\">\n" +
            "            <img class=\"location_icon\" style=\"position:relative;top:2px;max-width:4%;height:auto;\" src=\"{location_iconUri}\">\n" +  //定位图标
            "            <span class=\"location_address\" style=\"color:#aaa;font-size:13px;line-height:normal\" >{location_content}</span>\n" +
            "        </div>\n" +
            "    </section>" +
            "\n" +
            "    <section class=\"note-content paper\">\n" +
            "            \n" +
            "        <div class=\"note-body\" id=\"note-body\">\n" +
            "                \n" +
            "            <div class=\"rich-note\">\n" +
            "\n" +
            " <div class=\"note\" id=\"link-report\">" +
            "{artical_content}\n"+
            "<div class=\"separator\"><hr></div><p></p><p></p><p></p></div>" +
            "    <div class=\"copyright-notice\">\n" +
            "      ©本文版权归 {author_name} 所有, 任何形式转载请联系作者。\n" +
            "    </div>"+
            "\n" +
            "        </section>\n" +
            "\n" +
            "        <div class=\"download-app\" style=\"text-align:center;padding:0 0 20px 0\">\n" +
            "            <div class=\"info\" style=\"padding: 20px 0 0 0\">\n" +
            "                <img src=\"{app_iconUri}\" style=\"position:relative;top:10%;max-width:20%;height:auto;top:10px\">\n" +
            "                <div class=\"info-content\" style=\"display:inline-block;text-align:left\">\n" +
            "                    <strong style=\"font-size:24px;font-style: normal;\">实时实地</strong>\n" +
            "                    <div style=\"white-space:pre;line-height:18px\">洞悉周围每一个角落</div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "    </body>\n" +
            "</html>";



    private Artical artical;

    private boolean isNew;

    public ArticalManager(){
        artical = new Artical();
        isNew = true;
        artical.uuid = ""+new Random().nextLong();
    }

    public Artical getArtical(){
        return artical;
    }



    private void save(String title,String html){
        if(title == null || title.isEmpty()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            title = df.format(new Date());
        }
        if(html == null){
            html = "";
        }

        artical.title = title;
        artical.authorId = App.getInstance().getUserManager().getPhoneNum();
        artical.introduction = getIntroduction(html);
        artical.images = EditorDataManager.getInstance().getPhotoManager().getImages();
        artical.html = html;
        artical.date = new Date().getTime();

        if(artical.images.size()>0){
            setLocation(artical.images.get(0));
        }
    }


    private void saveToDatabase(){
        //Database operation
        if(isNew)
            AppDatabaseManager.addArtical(artical);
        else
            AppDatabaseManager.updateArtical(artical);
    }

    private void saveFinishedArtical(String title,String html){
        artical.finished = 1;
        save(title,html);

        App.getInstance().getRecordDataManager().saveFinishArtical(artical);

        saveToDatabase();

        //delete after confirm upload to server
        AppDatabaseManager.saveImages(EditorDataManager.getInstance().getPhotoManager().getImages());

        //nerwork operation
//        App.getInstance().getNetworkMonitor().pushArtical(artical);
        EditorDataManager.getInstance().getNetworkManager().uploadArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(App.getInstance().getNetworkMonitor().getUploadArticalObserver(artical));

    }

    private String getIntroduction(String content_html){
        String pattern = "<.+?>";
        Pattern r = Pattern.compile(pattern);
        String[] ss = r.split(content_html);
        StringBuilder sb = new StringBuilder();
        if (ss.length>0) {
            for (int i = 0; i < ss.length; i++) {
                sb.append(ss[i]);
                if(sb.length()>100){
                    sb.setLength(100);
                    sb.append("...");
                    return sb.toString();
                }
            }

        } else {
            return "";
        }
        return sb.toString();
    }


    //provide for editor to change artical

    public void setArticalType(String type){
        artical.articalType = type;
    }

    public void setArticalFromRecord(Artical artical) {
        this.artical = artical;

        isNew = false;
        //get data form database
        AppDatabaseManager.getImages(artical.uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(EditorDataManager.getInstance().getPhotoManager().getObserver());
    }


    //when exit editor the four choices
    public void deleteArtical(){
        List<Image> list = EditorDataManager.getInstance().getPhotoManager().getImages();
        for (int i = 0; i < list.size(); i++) {
            FileUtils.fileDelete(list.get(i).path);
        }

        if(!isNew){
            AppDatabaseManager.deleteArtical(artical.uuid);
        }
    }


    public void saveTempArtical(String title,String html){

        artical.finished = 0;
        save(title,html);
        App.getInstance().getRecordDataManager().saveTempArtical(artical);

        saveToDatabase();

        AppDatabaseManager.saveImages(EditorDataManager.getInstance().getPhotoManager().getImages());
    }



    public void saveAsPublic(String title,String html) {
        artical.saveType = AppContants.PUBLIC;
        saveFinishedArtical(title,html);

    }

    public void saveAsPrivate(String title,String html) {
        artical.saveType = AppContants.PRIVATE;
        saveFinishedArtical(title,html);
    }


    //provide to other model to deal with the aritcal

    public String getArticalHtml(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        setHtmlContent(artical.html);
        setHtmlDescriptino(artical.introduction);
        setHtmlTimeStamp(df.format(new Date()));
        if(artical.locationDesc!=null)
            setHtmlLocation(artical.locationDesc);
        setHtmlAutohrName(App.getInstance().getUserManager().getNickName());
        setHtmlTitle(artical.title);
        setHtmlType(artical.articalType);

        setHtmlAppIcon(FoundationManager.APP_ICON_URL);
        if(artical.imageUri!=null)
            setHtmlImage(artical.imageUri);
        setHtmlLocationIcon(FoundationManager.LOCATION_ICON_URL);
        setHtmlAuthorIcon(App.getInstance().getUserManager().getIconPath());

//        setHtmlArticalHref("");

        Log.d("html : ", "getArticalHtml: "+template);
        return template;
    }

    public void setLocation(Image image){
        artical.latitude = image.latitude;
        artical.longtitude = image.longtitude;
        artical.locationDesc = image.description;
        artical.imageUri = image.path;
    }

//    public void setImageUri(String path){
//        artical.imageUri = path;
//    }

//    public boolean hasLocation(){
//        if(!artical.locationDesc.isEmpty())
//            return true;
//        return false;
//    }
//
//    public boolean hasImageUri(){
//        if(!artical.imageUri.isEmpty()){
//            return true;
//        }
//        return false;
//    }



    //replace the template html

    public void setHtmlContent(String value){
        String old = "{artical_content}";
        template = template.replace(old,value);
    }

    public void setHtmlAppIcon(String value){
        String old = "{app_iconUri}";
        template = template.replace(old,value);
    }

    public void setHtmlLocationIcon(String value){
        String old = "{location_iconUri}";
        template = template.replace(old,value);
    }

    public void setHtmlTimeStamp(String value){
        String old = "{timestamp_content}";
        template = template.replace(old,value);
    }

    public void setHtmlAuthorIcon(String value){
        String old = "{author_iconUri}";
        template = template.replace(old,value);
    }

    public void setHtmlAutohrName(String value){
        String old = "{author_name}";
        template = template.replace(old,value);
    }

    public void setHtmlTitle(String value){
        String old = "{title_content}";
        String old2 = "{browser_title_content}";
        template = template.replace(old,value);
        template = template.replace(old2,value+"-足迹");
    }

    public void setHtmlLocation(String value){
        String old = "{location_content}";
        template = template.replace(old,value);
    }

    public void setHtmlImage(String value){
        String old = "{mainImageUri}";
        template = template.replace(old,value);
    }

    public void setHtmlType(String value){
        String old = "{artical_type}";
        template = template.replace(old,value);
    }

    public void setHtmlDescriptino(String value){
        String old = "{description_content}";
        template = template.replace(old,value);
    }


}
