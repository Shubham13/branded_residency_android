package com.paragon.sensonic.localization.dto;

import java.util.ArrayList;

public class Word {
    private String tag;
    private String module;
    private ArrayList<Translator> translator;

    public Word(String tag, String module, ArrayList<Translator> translator) {
        this.tag = tag;
        this.module = module;
        this.translator = translator;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ArrayList<Translator> getTranslator() {
        return translator;
    }

    public void setTranslator(ArrayList<Translator> translator) {
        this.translator = translator;
    }
}
