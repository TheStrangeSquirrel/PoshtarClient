package net.squirrel.poshtar.client.entity;

import net.squirrel.poshtar.dto.Provider;
import org.simpleframework.xml.Attribute;

public class SavedTrack {
    private Provider provider;
    private String codePost;
    private String description;

    public SavedTrack(Provider provider, String codePost, String description) {
        this.provider = provider;
        this.codePost = codePost;
        this.description = description;
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
