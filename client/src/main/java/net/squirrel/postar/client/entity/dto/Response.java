package net.squirrel.postar.client.entity.dto;

import org.simpleframework.xml.Attribute;

public class Response {
    String status;

    @Attribute
    public String getStatus() {
        return status;
    }

    @Attribute
    public void setStatus(String status) {
        this.status = status;
    }
}