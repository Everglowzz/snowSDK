package co.tiangongsky.bxsdkdemo

import android.content.Context
import cn.bmob.v3.Bmob
import cn.jpush.android.api.CustomPushNotificationBuilder
import cn.jpush.android.api.JPushInterface
import co.bxvip.sdk.BxRePluginAppLicationMakeImpl
import co.tiangongsky.bxsdkdemo.network.NetworkManger
import org.jetbrains.anko.toast
import java.lang.Exception

/**
 *
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * <pre>
 *     author: vic
 *     time  : 18-5-7
 *     desc  : application
 * </pre>
 */

class App : BxRePluginAppLicationMakeImpl() {
    override fun initRePluginYourNeed() {

    }

    override fun initJPushYouNeed() {
        if (getJgKey(this, "JPUSH_APPKEY").isNotEmpty()) {
            JPushInterface.setDebugMode(BuildConfig.DEBUG)    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this)                         // 初始化 JPush
            val builder = CustomPushNotificationBuilder(this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text)
            builder.layoutIconDrawable = R.mipmap.logo
            builder.developerArg0 = "developerArg2"
            JPushInterface.setDefaultPushNotificationBuilder(builder)
        } else {
            if (BuildConfig.DEBUG) toast("本APP没有选择配置极光的APPKEY，针对debug环境的toast提示，正式不会提示")
        }

        //通常用application的上下文初始化app全局变量
        NetworkManger.init(applicationContext)
        Bmob.initialize(this, "8e9574a349a2395af3d2af5c58b27800");
    }

    fun getJgKey(ctx: Context, name: String): String {
        var res = ""
        try {
            ctx.packageManager.getApplicationInfo(ctx.packageName, 128)?.let {
                it.metaData?.let {
                    res = "${it[name]}"
                }
            }
        } catch (e: Exception) {

        }
        return res
    }
}