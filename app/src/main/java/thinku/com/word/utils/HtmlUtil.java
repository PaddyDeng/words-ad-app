package thinku.com.word.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
    public static Spanned fromHtml(String body) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Html.fromHtml(body);
        } else {
            return Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY);
        }
    }


    public static String replaceSpace(String content) {
         String regMatchEnter="\\s*|\t|\r|\n";
         String regMatchTag = "<[^>]*>";
        if (content.contains("&amp;")) {
            content = content.replace("&amp;", "&");
        }
        if (content.contains("&nbsp;")) {
            content = content.replace("&nbsp;", " ");
        }
        if (content.contains("<p><br/></p>")) {
            content = content.replace("<p><br/></p>", " ");
        }
        if (content.contains("\\r<br/>")) {
            content = content.replace("\\r<br/>", "");
        }
        Pattern p = Pattern.compile(regMatchTag);
        Matcher m = p.matcher(content);
        content=m.replaceAll("");
        if (content.contains("<vocab>")){
            content = content.replace("\\r<br/>", "");
        }
        if (content.contains("</vocab>")){
            content = content.replace("</vocab>","");
        }
//        if (content.contains("&amp;nbsp;")) {
//            content = content.replace("&amp;nbsp;", " ");
//        }
//        if (content.contains("&rsquo;")) {
//            content = content.replace("&rsquo;", "'");
//        }
//        if (content.contains("&amp;rsquo;")) {
//            content = content.replace("&amp;rsquo;", "'");
//        }
//        if (content.contains("&ndash;")) {
//            content = content.replace("&ndash;", "–");
//        }
//        if (content.contains("&amp;ndash;")) {
//            content = content.replace("&amp;ndash;", "–");
//        }
//        if (content.contains("&amp;euml;")) {
//            content = content.replace("&amp;euml;", "ë");
//        }
//        if (content.contains("&euml;")) {
//            content = content.replace("&euml;", "ë");
//        }
        content = replaceRN(content);
        return content.trim();
    }

    public static String replaceRN(String content) {
        content = content.trim();
        content = content.replace("\n" ,"");
        content = content.replace(" ","");
//        if (content.contains("\\r")) {
//            content = content.replace("\\r", "").trim();
//        }
        return content;
    }

    /**
     * 将img标签中的src进行二次包装
     *
     * @param content     内容
     * @param replaceHttp 需要在src中加入的域名
     * @return
     */
    public static String repairContent(String content, String replaceHttp) {
//        <img src="http://www.gmatonline.cn//files/attach/images/20151109/1447062370368626.png" title="1447062370368626.png" alt="1.png"/>
        List<String> imgList =new ArrayList<>();
        String patternStr = "<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String result = content;
        while (matcher.find()) {
            String src = matcher.group(2);
            if(!imgList.contains(src)) {
                imgList.add(src);
                String replaceSrc = "";
                if (!src.startsWith("http://") && !src.startsWith("https://") && !src.contains("data:image/png;base64")) {
                    replaceSrc = replaceHttp + src;//+"\" " +"onerror=\"this.src=\"";
                } else {
                    replaceSrc = src;
                }
                result = result.replaceAll(src, replaceSrc);

            }
        }
        return result;
    }

    public static String getHtml(String content) {

        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"en\">");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<style >");
        sb.append("body{");
        sb.append("word-wrap: break-word;");
        sb.append("font-size: 13px;");
        sb.append(" color:#000000; ");
        sb.append("font-family:Arial;");
        sb.append("padding-left:10px ;");
        sb.append("padding-left:10px;");
        sb.append("}");
        sb.append("img{");
        sb.append("max-width:95%;");
        sb.append("height : auto;");
        sb.append("}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(content);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


    public static String getHtml(String content, int fontSize) {
        int font = 16;
        switch (fontSize) {
            case 0:
                font = 14;
                break;
            case 1:
                font = 15;
                break;
            case 2:
                font = 16;
                break;
            case 3:
                font = 18;
                break;
            case 4:
                font = 20;
                break;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"en\">");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<style>");
//        sb.append("@font-face{");
//        sb.append("font-family: 'customerFonts';");
//        sb.append("src: url('Helvetica Condensed.ttf') format('truetype');");
//        sb.append("src: url('fonts/ChaparralPro-Regular.otf');");
//        sb.append("font-weight: bold;");
//        sb.append("}");
        sb.append("body{");
        sb.append("word-wrap: break-word;");
//        sb.append("font-family: customerFonts;");
        sb.append("font-size: " + font + "px;");
        sb.append("line-height:"+(font*2-font/3)+"px");
        sb.append("font-family:Arial;");
        sb.append("}");
        sb.append("table{");
        sb.append("width:100%!important;word-break: break-all;");
        sb.append("}");
        sb.append("img{");
        sb.append("max-width:95%;");
        sb.append("height : auto;");
        sb.append("vertical-align: middle;");
        sb.append("}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(content);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


}
