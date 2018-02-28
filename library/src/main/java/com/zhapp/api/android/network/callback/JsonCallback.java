package com.zhapp.api.android.network.callback;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.zhapp.ard.store.network.NetAction;
import com.zhapp.ard.store.network.model.SimpleResponse;
import com.zhapp.ard.store.network.model.VipResponse;
import com.zhapp.ard.store.network.model.user_login.UserLoginModel;
import com.zhapp.ard.store.utils.Convert;
import com.zhapp.ard.store.utils.JavaUtil;
import com.zhapp.ard.store.utils.LogUtils;
import com.zhapp.ard.store.utils.SPUtils;
import com.zhapp.ard.store.utils.UIUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * <pre>
 * Describe: {@link AbsCallback}。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-01-06
 * Time: 15:14
 * <pre/>
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
    private static final String TAG = "JsonCallback";

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    public abstract void onLoginSuccess(com.lzy.okgo.model.Response response, Throwable e);

    @Override
    public T convertResponse(Response response) throws Throwable {
        //com.lzy.demo.callback.DialogCallback<com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>> 得到类的泛型，包括了泛型参数
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>
        Type type = params[0];

        // 这是String类型参数的打印解析。
        if (type == String.class) {
            String message = response != null && response.body() != null ? response.body().string() : "response == 返回值有问题！";
            LogUtils.e(message);
            return (T) message;
        }

        // 这里这么写的原因是，我们需要保证上面我解析到的type泛型，仍然还具有一层参数化的泛型，也就是两层泛型
        // 如果你不喜欢这么写，不喜欢传递两层泛型，那么以下两行代码不用写，并且javabean按照
        // https://github.com/jeasonlzy/okhttp-OkGo/blob/master/README_JSONCALLBACK.md 这里的第一种方式定义就可以实现
        if (!(type instanceof ParameterizedType)) {
            try {
                JsonReader jsonReader = new JsonReader(response.body().charStream());
                //有数据类型，表示有data
                T wechatResponse = Convert.fromJson(jsonReader, type);
                response.close();
                //noinspection unchecked
                return wechatResponse;
            } catch (Exception e) {
                throw new IllegalStateException("没有填写泛型参数");
            }
        }

        //如果确实还有泛型，那么我们需要取出真实的泛型，得到如下结果
        //class com.lzy.demo.model.LzyResponse
        //此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里获取最终内部泛型的类型 com.lzy.demo.model.ServerModel
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (typeArgument == String.class)

        {
            LogUtils.e(response != null && response.body() != null ? "response == " + response.body().string() : "response == 返回值有问题！");
            return null;
        }
        else if (typeArgument == Void.class)

        {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            //noinspection unchecked
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (simpleResponse.code == 1) {
                //noinspection unchecked
                return (T) simpleResponse.toVipResponse();
            }
            // 用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
            else if (simpleResponse.code == 40029) {
                throw new IllegalStateException("无效的code码，请重新获取");
            }
            // 用户未登陆认证
            else if (simpleResponse.code == 403) {
                String tag = (String) response.request().tag();
                String openid = SPUtils.get(UIUtils.getActivity(), SPUtils.openid);
                String login_type = SPUtils.get(UIUtils.getActivity(), SPUtils.login_type);
                String msg = user_auto_login(tag, login_type, openid);
                throw new NeedLoginException(msg);
            }
            return (T) simpleResponse.toVipResponse();
        }
        else if (rawType == VipResponse.class)

        {
            //有数据类型，表示有data
            VipResponse lzyResponse = Convert.fromJson(jsonReader, type);
            response.close();
            int code = lzyResponse.code;
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            // "3005"  => "提示:您已有1条相同的订单在处理中,暂时不允许提交此订单,谢谢！"
            // "3006"  => "请重新发送下单"
            // "2001"  => "所需积分余额不足。"
            if (code == 1 || code == 2001 || code == 3005 || code == 3006) {
                //noinspection unchecked
                return (T) lzyResponse;
            }
            // 用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
            else if (code == 40029) {
                throw new IllegalStateException("无效的code码，请重新获取");
            }
            // 用户未登陆认证
            else if (code == 403) {
//                SPUtils.clearn(UIUtils.getActivity());
//                UIUtils.getActivity().dismissLoading();
                String tag = (String) response.request().tag();
                String openid = SPUtils.get(UIUtils.getActivity(), SPUtils.openid);
                String login_type = SPUtils.get(UIUtils.getActivity(), SPUtils.login_type);
                String msg = user_auto_login(tag, login_type, openid);
                throw new NeedLoginException(msg);
            }
            //  其他问题。
            else {
//                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyResponse.message);
                throw new IllegalStateException(lzyResponse.code_str);
            }
        }
        else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable e = response.getException();
        if (e instanceof IllegalStateException) {
            if (!JavaUtil.compareStr("没有填写泛型参数", e.getMessage()) && !JavaUtil.compareStr("对不起，该作品链接有误，请粘贴自己快手作品链接", e.getMessage()) && !e.getMessage().contains("Expected ")) {
                UIUtils.getActivity().Toast(e.getMessage());
            }
        }
        else if (e instanceof NeedLoginException) {
            if (JavaUtil.compareStr(NeedLoginException.FAILURE, e.getMessage())) {
                LogUtils.e("NeedLoginException");
                SPUtils.clearn(UIUtils.getActivity());
                UIUtils.getActivity().dismissLoading();
                UIUtils.getActivity().Toast("用户需要登录！");
            }
            else {
                onLoginSuccess(response, e);
            }
        }
        else {
            UIUtils.getActivity().Toast("网络异常！");
        }
    }

    private static int logincount = 0;

    /**
     * @param tag
     * @param login_type 登录类型 1-app微信登录，2-appQQ登录
     * @param openid
     */
    private String user_auto_login(String tag, String login_type, String openid) {
        String msg = NeedLoginException.FAILURE;
        if (logincount >= 3) {
            logincount = 0;
            return msg;
        }
        try {
            LogUtils.e("自动登录-->开始");
            if (JavaUtil.isNull(openid)) {
                LogUtils.e("自动登录-->登录失败：1");
                msg = NeedLoginException.FAILURE;
                logincount++;
                return msg;
            }
            Response tmep = NetAction.user_auto_login(tag, login_type, openid).execute();
            JsonCallback<VipResponse<UserLoginModel>> temp = new JsonCallback<VipResponse<UserLoginModel>>() {


                @Override
                public void onSuccess(com.lzy.okgo.model.Response<VipResponse<UserLoginModel>> response) {

                }

                @Override
                public void onLoginSuccess(com.lzy.okgo.model.Response response, Throwable e) {

                }
            };
            VipResponse<UserLoginModel> userLoginModelVipResponse = temp.convertResponse(tmep);
            if (userLoginModelVipResponse == null || userLoginModelVipResponse.data == null) {
                new IllegalStateException("登录失败：2 " + userLoginModelVipResponse.code_str);
                LogUtils.e("自动登录-->登录失败：2 ");
                msg = NeedLoginException.FAILURE;
                logincount++;
                return msg;
            }

            SPUtils.put(UIUtils.getActivity(), new SPUtils.Params[]{
                    //
                    new SPUtils.Params(SPUtils.login_type, login_type),
                    //
                    new SPUtils.Params(SPUtils.openid, userLoginModelVipResponse.data.qq),
//                    //
//                    new SPUtils.Params(SPUtils.header_img, userLoginModelVipResponse.data.header_img),
                    //
                    new SPUtils.Params(SPUtils.jifen, userLoginModelVipResponse.data.jifen),
                    //
                    new SPUtils.Params(SPUtils.lingqu_jifen_num, userLoginModelVipResponse.data.lingqu_jifen_num),
                    //
                    new SPUtils.Params(SPUtils.is_lingqu_jifen, userLoginModelVipResponse.data.is_lingqu_jifen),
                    //
                    new SPUtils.Params(SPUtils.nickname, userLoginModelVipResponse.data.name),
                    //
                    new SPUtils.Params(SPUtils.uid, userLoginModelVipResponse.data.uid)});
            LogUtils.e("自动登录-->结束");
            LogUtils.e("自动登录-->登录成功");
            msg = NeedLoginException.SUCCESS;
            logincount = 0;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("自动登录-->出错IOException");
            LogUtils.e("自动登录-->登录失败：3");
            msg = NeedLoginException.FAILURE;
            logincount++;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("自动登录-->出错Exception");
            LogUtils.e("自动登录-->登录失败：3");
            msg = NeedLoginException.FAILURE;
            logincount++;
        } finally {
            return msg;
        }
    }
}
