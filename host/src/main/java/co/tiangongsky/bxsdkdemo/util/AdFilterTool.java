package co.tiangongsky.bxsdkdemo.util;

import android.content.Context;
import android.content.res.Resources;

import co.tiangongsky.bxsdkdemo.R;

/**
 * Created by clin on 2019/4/15.
 */

public class AdFilterTool {
    public static boolean isAd(Context context, String url) {
        Resources res = context.getResources();
        String[] filterUrls = res.getStringArray(R.array.adUrls);
        for (String adUrl : filterUrls) {
            if (url.contains(adUrl)) {
                return true;
            }

        }
        return false;
    }
}
