package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Response {
    String status;

    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }

    @Attribute
    public String getStatus() {
        return status;
    }

    @Attribute
    public void setStatus(String status) {
        this.status = status;
    }
}
