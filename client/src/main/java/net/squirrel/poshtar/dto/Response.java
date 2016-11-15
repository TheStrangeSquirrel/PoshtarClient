/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Response {
    private String status;

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
