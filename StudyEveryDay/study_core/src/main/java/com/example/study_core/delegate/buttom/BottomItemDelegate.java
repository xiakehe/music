package com.example.study_core.delegate.buttom;




import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.util.ToastHelp;


public abstract class BottomItemDelegate extends PermissionDelegate {

    private static final long WAIT_TIME = 2000L;
    private long touch_time = 0L;

    @Override
    public boolean onBackPressedSupport() {

        if (System.currentTimeMillis() - touch_time < WAIT_TIME) {
            _mActivity.finish();
        } else {
            touch_time = System.currentTimeMillis();
            ToastHelp.showLong(mContext, "双击退出");
        }

        return true;
    }


}
