package com.muralis.watchingdirectory.domain;

public enum FileExtension {

    XML (".xml"), JSON (".json");

    private String extention;

    private FileExtension ( String extention ) {
        this.extention = extention;
    }

    public String getExtention () {
        return extention;
    }

    public void setExtention ( String extention ) {
        this.extention = extention;
    }

}
