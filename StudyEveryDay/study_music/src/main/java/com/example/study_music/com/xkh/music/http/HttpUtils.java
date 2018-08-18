package com.example.study_music.com.xkh.music.http;

import com.example.study_core.net.RestCreator;

public class HttpUtils {

    /**
     * Service接口
     */
    private static final class RetrofitServiceHolder {
        private static final ApiServices REST_SERVICE = RestCreator.RetrofitHolder.RETROFIT_CLIENT.create(ApiServices.class);
    }

    private static final class RetrofitBaiDuServiceHolder {
        private static final ApiServices BAI_DU_REST_SERVICE = RestCreator.RetrofitHolder.RETROFIT_CLIENT_BAI_DU.create(ApiServices.class);
    }

    public static ApiServices getApiService(SERVER_TYPE type) {

        switch (type) {
            case BAI_DU:
                return RetrofitBaiDuServiceHolder.BAI_DU_REST_SERVICE;
            case ME:
                return RetrofitServiceHolder.REST_SERVICE;
            default:
                break;
        }

        return RetrofitServiceHolder.REST_SERVICE;
    }

    public enum SERVER_TYPE {
        BAI_DU,
        ME
    }
}
