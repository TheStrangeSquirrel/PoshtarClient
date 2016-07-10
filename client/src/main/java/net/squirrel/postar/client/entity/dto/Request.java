package net.squirrel.postar.client.entity.dto;

import org.simpleframework.xml.Attribute;


public class Request {
    private String codePost;
    private String providerId;

    public Request(String codePost, Provider provider) {
        this.codePost = codePost;
        this.providerId = "" + provider.getId();
    }

    @Attribute
    public String getProviderId() {
        return providerId;
    }

    @Attribute
    public void setProviderId(String providerId) {
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
}
