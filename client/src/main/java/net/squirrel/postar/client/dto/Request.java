package net.squirrel.postar.client.dto;

import org.simpleframework.xml.Attribute;

public class Request {
    private String codePost;

    public Request(String codePost) {
        this.codePost = codePost;
    }

    @Attribute
    public String getCodePost() {
        return codePost;
    }

    @Attribute
    public void setCodePost(String codePost) {
        this.codePost = codePost;
    }
}
