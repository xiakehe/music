package com.example.study_ui.luancher;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.study_ui.R;

public class LauncherHolder extends Holder<Integer> {

    private AppCompatImageView imageView;

    public LauncherHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.banner_image);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setBackgroundResource(data);
    }
}
