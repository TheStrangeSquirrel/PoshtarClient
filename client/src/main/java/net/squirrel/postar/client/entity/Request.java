package net.squirrel.postar.client.entity;

import org.simpleframework.xml.Attribute;

public class Request {
    private Provider provider;
    private int codePost;
    private String description;

    public Request(Provider provider, String description) {
        this.provider = provider;
        this.codePost = provider.getId();
        this.description = description;
    }

    @Attribute
    public int getCodePost() {
        return codePost;
    }

    @Attribute
    public void setCodePost(int codePost) {
        this.codePost = codePost;
    }

    @Attribute
    public String getDescription() {
        return description;
    }

    @Attribute
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
