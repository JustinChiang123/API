package com.zhapp.api.java;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
 * @ClassName: CommonUtil 关于java常用类的一些简单封装
 * @date 2015年6月18日 下午2:15:10
 */
public class JavaUtil {
    /**
     * 判断是否为数字
     *
     * @param s
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isNumber(String s) {
        if (s == null) {
            return false;
        }
        return s.matches("[\\d]+");
    }

    /**
     * 判断字符串是否为"空"，默认true<br>
     * "" -- true <br>
     * "null" -- true<br>
     * null -- true<br>
     * " " -- false<br>
     * "nul" --false <br>
     * "12str" -- false <br>
     *
     * @param str
     *
     * @return boolean true：满足"空"判断；false：不满足"空"判断。默认：true
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isStrNull(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str) || "null".equals(str);
    }

    /**
     * 一个对象是否为空
     *
     * @param obj
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断字符是否为空 <br>
     * null -- true<br>
     * "" -- true<br>
     * "null" -- false<br>
     * "aaaa"(aaaa表示4个空格) -- false
     *
     * @param str
     *
     * @return boolean str为空串 或者为null 返回true, 否则返回false
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str);
    }

    /**
     * 判断list是否为空，且长度大于0； <br>
     * (list == null) -- true ; <br>
     * (list.isEmpty()) -- true ; <br>
     * 其他返回false;
     *
     * @param list
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isNull(List<?> list) {
        if (list == null) {
            return true;
        }
        return list.isEmpty();
    }

    /**
     * 判断是否是金额格式<br>
     * 10.20 -- true <br>
     * -10.20 -- false <br>
     * 10.20111 -- true <br>
     * 1a0.20 -- false<br>
     * 0.01 -- true<br>
     * 0 -- true
     *
     * @param money
     *
     * @return boolean false(默认)：非金额格式; true：金额格式
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isMoney(String money) {
        boolean flag = false;
        if (money == null) {
            return flag;
        }
        try {
            flag = Double.parseDouble(money) >= 0;
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 验证是否是字母、数字、汉字(包含汉字符号)、且不能以'_'开始和结尾
     *
     * @param content 匹配内容
     *
     * @return boolean 是否成功匹配
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isAlphanumericCharacters(String content) {
        return getRegularResult("^(?!_)(?!.*?_$)[\\w|\\u0391-\\uFFE5]*$", content);
    }

    /**
     * 验证是否是电话号码或者手机号码。注意此方法判断逻辑不全面
     *
     * @param mobiles
     *
     * @return boolean 是否成功匹配
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isMobileNO(String mobiles) {
        return getRegularResult("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$|0\\d{2,3}\\d{7,8}", mobiles);
    }

    /**
     * 判断文件是否存在
     *
     * @param filepath
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isFileExists(String filepath) {
        boolean flag = false;
        if (filepath == null) {
            return flag;
        }
        File f = new File(filepath);
        if (f.exists()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断上一级目录是否存在
     *
     * @param filepath
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean isParentDirExists(String filepath) {
        boolean flag = false;
        if (filepath == null) {
            return flag;
        }
        File f = new File(filepath);
        if (f.getParentFile().exists()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据check方式，进行正则比较。
     *
     * @param patternStr 匹配规则
     * @param content    匹配内容
     *
     * @return boolean 是否成功匹配
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    private static boolean getRegularResult(String patternStr, String content) {
        boolean falg = false;
        try {
            falg = Pattern.compile(patternStr).matcher(content).matches();
        } catch (Exception e) {
            return false;
        }
        return falg;
    }

    /**
     * 暂只能check满足 xxxx-xx-xx 格式的日期。
     *
     * @param start
     * @param end
     *
     * @return int id 表示不同的check结果 <br>
     * id == 0 -- check通过<br>
     * id == 1 -- 开始时间 大于 当前时间<br>
     * id == 2 -- 开始时间 大于 结束时间<br>
     * id == -1 --开始时间 或者 结束时间格式错误
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static int compareTimes(String start, String end) {
        // 时间格式不对
        if (start.length() != 10 || end.length() != 10) {
            return -1;
        }
        // 开始时间 大于 当前时间
        if (start.compareTo(getSysTime()) > 0) {
            return 1;
        }
        // 开始时间 大于 结束时间
        if (start.compareTo(end) > 0) {
            return 2;
        }
        return 0;
    }

    /**
     * 获取系统当前时间。格式为：2010-01-01
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getSysTime() {
        // 获得系统当前日期
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return getSysTime(cal);
    }

    /**
     * 获取系统当前时间。格式为：2010-01-01
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressWarnings("resource")
    private static String getSysTime(Calendar c) {
        return new Formatter(Locale.CHINA).format("%1$tY-%1$tm-%1$td", c).toString();
    }

    /**
     * 将JSONArray 转为 String[]
     *
     * @param jsonAry
     *
     * @return String[] 失败返回null；否则返回String[]；
     *
     * @throws JSONException 异常
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String[] jsonToArray(JSONArray jsonAry) throws JSONException {
        String[] ary = null;
        if (jsonAry == null) {
            return ary;
        }
        int size = jsonAry.length();
        ary = new String[size];
        for (int j = 0; j < size; j++) {
            ary[j] = jsonAry.getString(j);
        }
        return ary;
    }

    /**
     * 判断是不是字母
     *
     * @param str
     *
     * @return true---是特殊字符或者数字,不是string
     */
    public static boolean isString(String str) {

        return getRegularResult("^[A-Za-z]+$", str);
    }

    /**
     * 对号码进行截取操作。<br>
     * 规则：去掉空格之后，排除特殊字符，截取最后11位作为号码。如果不是数字或者截取失败返回""
     *
     * @param phoneNumber
     * @param subLength
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getPhoneNumber(String phoneNumber, int subLength) {
        String phone = "";
        // 去掉特殊字符
        String[] temp = Pattern.compile("([^\\d])").split(phoneNumber);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.length; i++) {
            if (!isNull(temp[i])) {
                sb.append(temp[i]);
            }
        }
        phoneNumber = sb.toString();
        if (phoneNumber.length() < subLength) {
            return phone;
        }
        int start = phoneNumber.length() - subLength;
        int end = phoneNumber.length();
        // 截取最后xx位
        phone = phoneNumber.substring(start, end);
        if (!isNumber(phone)) {
            phone = "";
        }
        return phone;
    }

    /**
     * 去掉包含中文符号和英文符号"，。？！、,.?!"的字符串的符号<br>
     * "妈妈说，不听话的孩子不是好孩子！对么？" -- "妈妈说不听话的孩子不是好孩子对么"
     *
     * @param str
     *
     * @return String 源字符串
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String removeMark(String str) {
        String rule = "[\\,\\.\\?\\!\\，\\。\\？\\！\\、]";
        return removeRule(str, rule);
    }

    /**
     * 去掉最后一个符号
     *
     * @param source
     * @param flag
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String removeLastStr(String source, String flag) {
        if (source == null || flag == null) {
            return null;
        }
        return source.replaceAll(flag + "+$", "");
    }

    /**
     * 去掉字符串包含的空格<br>
     * "123 124 4 " -- "1231244"<br>
     * "sda sa"--"sdasa"
     *
     * @param str
     *
     * @return String 源字符串
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String removeBlank(String str) {
        String rule = "([^\\d])";
        return removeRule(str, rule);
    }

    /**
     * 根据正则rule规则，来去掉str字符串中的特殊字符
     *
     * @param str  源字符串
     * @param rule 正则规则
     *
     * @return String 去掉rule正则规则后的字符串
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String removeRule(String str, String rule) {
        if (str == null || rule == null) {
            return null;
        }
        if ("".equals(str)) {
            return "";
        }
        String[] temp = Pattern.compile(rule).split(str);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.length; i++) {
            if (!isNull(temp[i])) {
                sb.append(temp[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 清空list
     *
     * @param list 参数
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void clearList(List<?> list) {
        if (list == null) {
            return;
        }
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            if (value == null) {
                continue;
            }
            // list
            if (value instanceof List) {
                clearList((List<?>) value);
                continue;
            }
            // map
            if (value instanceof Map) {
                clearMap((Map<?, ?>) value);
                continue;
            }
        }
        list.clear();
    }

    /**
     * 清空map
     *
     * @param map 参数
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void clearMap(Map<?, ?> map) {
        if (map == null) {
            return;
        }
        Iterator<?> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            Object value = map.get(key);
            if (value == null) {
                continue;
            }
            // list
            if (value instanceof List) {
                clearList((List<?>) value);
                continue;
            }
            // map
            if (value instanceof Map) {
                clearMap((Map<?, ?>) value);
                continue;
            }
        }
        map.clear();
    }

    /**
     * 删除这个路径下面的所有文件、文件夹，不保留当前文件夹
     *
     * @param folderPath
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void delFolder(String folderPath) {
        // 删除完里面所有内容
        delAllFileFolder(folderPath);
        String filePath = folderPath;
        File myFilePath = new File(filePath.toString());
        // 删除空文件夹
        myFilePath.delete();
    }

    /**
     * 当前文件是否是".nomedia"
     *
     * @param file
     *
     * @return boolean
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressLint("DefaultLocale")
    public static boolean isNomedia(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return ".nomedia".toLowerCase().equals(file.getName().toLowerCase());
    }

    /**
     * 在当前文件夹下面 创建".nomedia"
     *
     * @param path
     *
     * @return boolean 创建成功与否
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean createNomedia(String path) {
        if (isNull(path)) {
            return false;
        }
        File file = new File(path);
        if (file == null || !file.isDirectory()) {
            return false;
        }
        file = new File(path + File.separator + ".nomedia");
        if (file == null || !file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 删除这个路径下面的所有文件,保留这个路径下的文件夹、当前文件夹、".nomedia"文件
     *
     * @param path
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void delAllFileKeepNomedia(String path) {
        delAllFile(path, true);
    }

    /**
     * 删除这个路径下面的所有文件,保留这个路径下的文件夹和当前文件夹
     *
     * @param path
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void delAllFile(String path) {
        delAllFile(path, false);
    }

    /**
     * 删除这个路径下面的所有文件,保留这个路径下的文件夹、当前文件夹
     *
     * @param path
     * @param keepNomedia 是否保留".nomedia"文件
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    private static void delAllFile(String path, boolean keepNomedia) {
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
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            }
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            // 文件
            if (temp.isFile()) {
                if (keepNomedia && ".nomedia".toLowerCase().equals(temp.getName())) {
                    continue;
                }
                temp.delete();
            }
        }
    }

    /**
     * 删除这个路径下面的所有文件、文件夹，保留当前文件夹
     *
     * @param path
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean delAllFileFolder(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            }
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            // 文件
            if (temp.isFile()) {
                temp.delete();
            }
            // 文件夹
            else if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFileFolder(path + File.separator + tempList[i]);
                // 再删除空文件夹
                delFolder(path + File.separator + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 对象转换为String，注意未进行strTrim
     *
     * @param obj
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String toString(Object obj) {
        String result = null;
        if (obj == null) {
            return result;
        }

        try {
            result = String.valueOf(obj);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 对象转换为String,如果对象不存在，就返回默认值
     *
     * @param obj 对象
     * @param def 默认值
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String toString(Object obj, String def) {
        String result = def;

        if (obj == null) {
            return result;
        }

        return toString(obj);
    }

    /**
     * duble转换为String，无科学记数法.
     *
     * @param nub
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String dubleToString(double nub) {
        String result = null;
        try {
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            result = nf.format(nub);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 对象转换为数字
     *
     * @param obj 对象
     *
     * @return Integer 返回类型，默认是0
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static Integer toInteger(Object obj) {
        Integer result = 0;

        if (obj == null) {
            return result;
        }

        try {
            double temp = toDouble(obj.toString());
            result = (int) temp;
        } catch (Exception ex) {
        }

        return result;
    }

    /**
     * 对象转换为数字
     *
     * @param obj 默认是0
     *
     * @return Long 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static Long toLong(Object obj) {

        return toLong(obj, 0);
    }

    /**
     * 对象转换为数字
     *
     * @param obj
     * @param def
     *
     * @return Long 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static Long toLong(Object obj, long def) {
        Long result = def;

        if (obj == null) {
            return result;
        }

        try {
            result = Long.valueOf(obj.toString());
        } catch (Exception ex) {
        }

        return result;
    }

    /**
     * 对象转换为数字
     *
     * @param obj
     *
     * @return Double 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static Double toDouble(Object obj) {
        double result = 0;

        if (obj == null) {
            return result;
        }

        try {
            result = Double.valueOf(obj.toString());
        } catch (Exception ex) {
        }

        return result;
    }

    /**
     * 对象转换为数字
     *
     * @param obj 对象
     * @param def 默认值
     *
     * @return Integer 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static Integer toInteger(Object obj, int def) {
        int result = def;

        if (obj == null) {
            return result;
        }

        return toInteger(obj);
    }

    /**
     * 过滤空格
     *
     * @param str
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String strTrim(String str) {

        if (str == null || str.trim() == "") {
            return null;
        }

        if ((str.trim() + "x").equals("x")) {
            return null;
        }
        return str.trim();
    }

    /**
     * html编码
     *
     * @param s
     *
     * @return String 字符串
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String htmEncode(String s) {
        String result = null;
        if ((s = strTrim(s)) == null) {
            return result;
        }
        StringBuffer stringbuffer = new StringBuffer();
        int j = s.length();
        for (int i = 0; i < j; i++) {
            char c = s.charAt(i);
            switch (c) {
                case 60:
                    stringbuffer.append("&lt;");
                    break;
                case 62:
                    stringbuffer.append("&gt;");
                    break;
                case 38:
                    stringbuffer.append("&amp;");
                    break;
                case 34:
                    stringbuffer.append("&quot;");
                    break;
                case 169:
                    stringbuffer.append("&copy;");
                    break;
                case 174:
                    stringbuffer.append("&reg;");
                    break;
                case 165:
                    stringbuffer.append("&yen;");
                    break;
                case 8364:
                    stringbuffer.append("&euro;");
                    break;
                case 8482:
                    stringbuffer.append("&#153;");
                    break;
                case 13:
                    if (i < j - 1 && s.charAt(i + 1) == 10) {
                        stringbuffer.append("<br>");
                        i++;
                    }
                    break;
                case 32:
                    if (i < j - 1 && s.charAt(i + 1) == ' ') {
                        stringbuffer.append(" &nbsp;");
                        i++;
                        break;
                    }
                default:
                    stringbuffer.append(c);
                    break;
            }
        }

        result = new String(stringbuffer.toString());
        stringbuffer = null;
        return result;
    }

    /**
     * 获取当前时间
     *
     * @param format 格式 默认yyyy-MM-dd HH:mm:ss
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String format) {

        // 格式
        format = toString(format, "yyyy-MM-dd HH:mm:ss");

        String today = null;
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
            today = sDateFormat.format(new Date());
        } catch (Exception e) {
        }
        return today;
    }

    /**
     * 满足"yyyy-MM-dd"格式的string时间，返回对应的date值。
     *
     * @param time
     *
     * @return Date 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDate(String time) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * <p>
     * <p/>
     * <pre>
     * 时间long --> 2015年07月29日
     * </pre>
     * <p/>
     * </p>
     *
     * @param millis
     *
     * @return String 返回类型
     *
     * @author <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getTime_Data(long millis) {
        String format = "%1$tY年%1$tm月%1$td日";
        return getTime(millis, format);
    }

    /**
     * <p>
     * <p/>
     * <pre>
     * 时间long-- &gt; 星期三
     * </pre>
     * <p/>
     * </p>
     *
     * @param millis
     *
     * @return String 返回类型
     *
     * @author <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getTime_Week(long millis) {
        String format = "%1$tA";
        return getTime(millis, format);
    }

    /**
     * <p>
     * <p/>
     * <pre>
     * 时间long --> 14:51:11
     * </pre>
     * <p/>
     * </p>
     *
     * @param millis
     *
     * @return String 返回类型
     *
     * @author <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getTime_Time(long millis) {
        String format = "%1$tT";
        return getTime(millis, format);
    }

    /**
     * <p>
     * <p/>
     * <pre>
     * 时间long --> 2015年07月29日星期三，14:51:31 下午
     * </pre>
     * <p/>
     * </p>
     *
     * @param millis
     *
     * @return String 返回类型
     *
     * @author <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getTime(long millis) {
        String format = "%1$tY年%1$tm月%1$td日%1$tA，%1$tT %1$tp";
        return getTime(millis, format);
    }

    /**
     * <p>
     * <p/>
     * <pre>
     * 根据传入format格式，获取时间戳对应的时间。
     * </pre>
     * <p/>
     * </p>
     *
     * @param millis 时间戳
     * @param format 格式
     *
     * @return String 返回类型
     *
     * @author <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressWarnings("resource")
    private static String getTime(long millis, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return new Formatter(Locale.CHINA).format(format, cal).toString();
    }

    /**
     * 移除非GBK的字符
     *
     * @param str
     *
     * @return String 字符串
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String removeNoGBK(String str) {
        if (str == null) {
            return str;
        }

        String regGBK = "[\\u4e00-\\u9fa5]";
        String regOTR = "[ \\w+]";
        String temp = null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            temp = String.valueOf(chars[i]);
            if (temp == null) {
                continue;
            }

            if (temp.equals("<") || temp.equals(">") || temp.equals("/")) {
                continue;
            }

            // 如果是乱麻则执行替换
            if (!temp.matches(regGBK) && !temp.matches(regOTR)) {
                str = str.replace(temp, "");
            }
        }
        return strTrim(str);
    }

    /**
     * 判断两个String 是否 相等
     *
     * @param str1
     * @param str2
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean compareStr(String str1, String str2) {
        if (str1 != null) {
            return str1.equals(str2);
        }
        return str2 == null;
    }

    /**
     * 根据file.length返回　KB、MB、GB
     *
     * @param size
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    @SuppressLint("DefaultLocale")
    public static String getFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else
            return String.format("%d B", size);
    }

    /**
     * 输出javabean的所有字段值
     *
     * @param object
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getBeanString(Object object) {
        try {
            if (null == object) {
                return null;
            }
            else {
                // 获取此类所有声明的字段
                Field[] field = object.getClass().getDeclaredFields();
                // 用来拼接所需保存的字符串
                StringBuffer sb = new StringBuffer();
                // 循环此字段数组，获取属性的值
                for (int i = 0; i < field.length && field.length > 0; i++) {
                    // 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查.
                    field[i].setAccessible(true);
                    // field[i].getName() 获取此字段的名称
                    // field[i].get(object) 获取指定对象上此字段的值
                    if (i == field.length - 1) {
                        sb.append(field[i].getName() + ": " + field[i].get(object) + ".");
                    }
                    else {
                        sb.append(field[i].getName() + ": " + field[i].get(object) + ", ");
                    }
                }
                return sb.toString();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生肖数据
     */
    public static final String[] zodiacArr = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 星座数据
     */
    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    /**
     * 星座边缘日
     */
    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    /**
     * 根据日期获取生肖
     *
     * @param date
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getZodica(Date date) {
        if (date == null) {
            return zodiacArr[0];
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     *
     * @param date
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static String getConstellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }

    /**
     * <pre>
     * 将String数据存为文件
     * 如果json是null || ""，则返回null。
     * 如果path是null || ""，则返回null。
     * </pre>
     *
     * @param json 需要写入文件的json。
     * @param path json写入文件的path。
     *
     * @return String 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static File createFileFormJson(String json, String path) {
        // 空json
        if (isNull(json)) {
            return null;
        }
        if (isNull(path)) {
            return null;
        }
        byte[] b = json.getBytes();
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
}
