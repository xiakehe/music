package com.example.study_core.app;

import com.example.study_core.util.storage.StudySharedPreference;

public class AccountManager {

    private enum UserTag {
        USER_TAG
    }

    public static void setSingState(boolean state) {
        StudySharedPreference.setAppFlag(UserTag.USER_TAG.name(), state);
    }

    public static boolean isSign() {
        return StudySharedPreference.getAppFlag(UserTag.USER_TAG.name());
    }

    public static void checkSignIn(IUserCheck userCheckListener) {
        if (isSign()) {
            userCheckListener.onSignIn();
        } else {
            userCheckListener.onNotSignIn();
        }
    }
}
