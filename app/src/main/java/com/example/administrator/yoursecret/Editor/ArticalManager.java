package com.example.administrator.yoursecret.Editor;

import android.net.Uri;
import android.util.Log;

import com.example.administrator.yoursecret.MetaData.Artical;
import com.example.administrator.yoursecret.UserManager;
import com.example.administrator.yoursecret.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/8.
 */

public class ArticalManager {
    private String template = "<!DOCTYPE html>\n" +
            "<html itemscope itemtype=\"http://schema.org/WebPage\">\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>{browser_title_content}</title>" +                               //浏览器标题
            "           <meta name=\"viewport\" content=\"width=device-width, height=device-height, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0\">" +
            "           <link rel=\"canonical\" href=\"{artical_href}\">" +         //全文链接
            "           <meta name=\"description\" content=\"{description_content}\"" +    //简介
            "           <meta name=\"keywords\" content=\"{artical_type}\">" +      //类型
            "           <meta name=\"image\" content=\"{mainImageUri}\">" +     //图片
            "           <meta name=\"location\" content=\"{location_content}\"" +   //地址
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
            "        <div class=\"user-title\"> style=\"color:#aaa;font-size:15px;line-height:normal\"" +
            "            <img class=\"note_author_avatar\" style=\"max-width:10%;height:auto;\" src=\"{author_iconUri}\" alt=\"{author_name}\">\n" +   //作者icon和昵称
            "            <span class=\"info\">\n" +
            "              {author_name}\n" +      //作者昵称
            "              <span class=\"timestamp\">{timestamp_content}</span>\n" +  //时间戳
            "            </span>\n" +
            "        </div>\n" +
            "        <div class=\"location\">\n" +
            "            <img class=\"location_icon\" style=\"max-width:5%;height:auto;\" src=\"{location_iconUri}\">\n" +  //定位图标
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
            "                <img src=\"{app_iconUri}\" width=\"48\" style=\"\">\n" +
            "                <div class=\"info-content\" style=\"display:inline-block;text-align:left\">\n" +
            "                    <strong style=\"font-size:24px;font-style: normal;\">足迹</strong>\n" +
            "                    <div style=\"white-space:pre;line-height:18px\">踏遍世界每一个角落</div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "    </body>\n" +
            "</html>";



    private Artical artical;

    public ArticalManager(){
        artical = new Artical();
    }

    public void setArticalType(String type){
        artical.articalType = type;
    }

    public void deleteArtical(){
        List<Uri> list = DataManager.getInstance().getPhotoManager().getPhotos();
        for (int i = 0; i < list.size(); i++) {
                FileUtils.fileDelete(list.get(i).getPath());
        }
    }

    public void setArticalSaveType(int type){
        artical.saveType = type;
    }

    public void saveArtical(String html){
        if(artical.saveType == -1){
            deleteArtical();
        }

        Log.d("HTML  ", "saveArtical: "+html);

        String pattern = "^<h1>(.+?)</h1>(<hr>)?(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(html);
        String title  = "";
        String content = "";
        if (m.find( )) {
            title = m.group(1);
            content = m.group(3);
        } else {
            content = html;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            title = df.format(new Date());
        }

        artical.title = title;
        artical.content_html = content;

        artical.artical_id =Long.toHexString(new Random().nextLong());
        artical.author_id = UserManager.getInstance().getUserId();

        artical.images = DataManager.getInstance().getPhotoManager().getImagesWithLocation();

        artical.introduction = getIntroduction(content);

    }

    public String getIntroduction(String content_html){
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
            return null;
        }
        return sb.toString();
    }

    public void setLocation(Artical.ImageLocation location){
        artical.location = location;
    }

    public void setImageUri(Uri imageUri){
        artical.imageUri = imageUri;
    }

    public boolean hasLocation(){
        if(null!=artical.location)
            return true;
        return false;
    }

    public boolean hasImageUri(){
        if(null!=artical.imageUri){
            return true;
        }
        return false;
    }

    public String getArticalHtml(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        setHtmlContent(artical.content_html);
        setHtmlDescriptino(artical.introduction);
        setHtmlTimeStamp(df.format(new Date()));
        setHtmlLocation(artical.location.description);
        setHtmlAutohrName(UserManager.getInstance().getUserName());
        setHtmlTitle(artical.title);
        setHtmlType(artical.articalType);

        setHtmlAppIcon(FileUtils.getAppIconPath());
        setHtmlImage(artical.imageUri.getPath());
        setHtmlLocationIcon(FileUtils.getLocationIconPath());
        setHtmlAuthorIcon(FileUtils.getUserIconPath());

        setHtmlArticalHref("");

        Log.d("html : ", "getArticalHtml: "+template);
        return template;
    }

    public void setHtmlContent(String value){
        String old = "{artical_content}";
        template.replace(old,value);
    }

    public void setHtmlAppIcon(String value){
        String old = "{app_iconUri}";
        template.replace(old,value);
    }

    public void setHtmlLocationIcon(String value){
        String old = "{location_iconUri}";
        template.replace(old,value);
    }

    public void setHtmlTimeStamp(String value){
        String old = "{timestamp_content}";
        template.replace(old,value);
    }

    public void setHtmlAuthorIcon(String value){
        String old = "{author_iconUri}";
        template.replace(old,value);
    }

    public void setHtmlAutohrName(String value){
        String old = "{author_name}";
        template.replace(old,value);
    }

    public void setHtmlTitle(String value){
        String old = "{title_content}";
        String old2 = "{browser_title_content}";
        template.replace(old,value);
        template.replace(old2,value+"-足迹");
    }

    public void setHtmlLocation(String value){
        String old = "{location_content}";
        template.replace(old,value);
    }

    public void setHtmlImage(String value){
        String old = "{mainImageUri}";
        template.replace(old,value);
    }

    public void setHtmlType(String value){
        String old = "{artical_type}";
        template.replace(old,value);
    }

    public void setHtmlDescriptino(String value){
        String old = "{description_content}";
        template.replace(old,value);
    }

    public void setHtmlArticalHref(String value){
        String old = "{artical_href}";
        template.replace(old,value);
    }
}
