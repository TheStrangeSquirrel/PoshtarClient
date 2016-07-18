package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Attribute;

import java.util.Locale;


public class Request {
    private String codePost;
    private int providerId;

    private Locale language;

    public Request(String codePost, Provider provider, Locale language) {
        this.codePost = codePost;
        this.providerId = provider.getId();
        this.language = language;
    }

    @Attribute
    public int getProviderId() {
        return providerId;
    }

    @Attribute
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Attribute
    public String getCodePost() {
        return codePost;
    }

    @Attribute
    public void setCodePost(String codePost) {
        this.codePost = codePost;
    }

    @Attribute
    public Locale getLanguage() {
        return language;
    }

    @Attribute
    public void setLanguage(Locale language) {
        this.language = language;
    }

}
