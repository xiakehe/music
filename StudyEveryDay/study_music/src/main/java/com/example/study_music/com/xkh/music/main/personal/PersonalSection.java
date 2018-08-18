package com.example.study_music.com.xkh.music.main.personal;

import com.chad.library.adapter.base.entity.SectionEntity;

public class PersonalSection extends SectionEntity<PersonItem> {
    public PersonalSection(PersonItem personItem) {
        super(personItem);
    }

    public PersonalSection(boolean isHeader, String header) {
        super(isHeader, header);
    }
}
