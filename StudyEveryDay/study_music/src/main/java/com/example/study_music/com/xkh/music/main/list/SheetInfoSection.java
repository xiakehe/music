package com.example.study_music.com.xkh.music.main.list;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;

class SheetInfoSection extends SectionEntity<SheetInfo> {
    SheetInfoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    SheetInfoSection(SheetInfo sheetInfo) {
        super(sheetInfo);
    }
}
