/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.dto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class Provider implements Serializable {
    private int id;
    private String name;
    private String base64BitmapBytes;

    public Provider() {
    }

    public Provider(int id, String name, String base64BitmapBytes) {
        this.id = id;
        this.name = name;
        this.base64BitmapBytes = base64BitmapBytes;
    }
    @Attribute
    public int getId() {
        return id;
    }
    @Attribute
    public void setId(int id) {
        this.id = id;
    }
    @Attribute
    public String getName() {
        return name;
    }
    @Attribute
    public void setName(String name) {
        this.name = name;
    }
    @Attribute
    public String getBase64BitmapBytes() {
        return base64BitmapBytes;
    }
    @Attribute
    public void setBase64BitmapBytes(String base64BitmapBytes) {
        this.base64BitmapBytes = base64BitmapBytes;
    }
    public Bitmap getBitmap() {
        byte[] bitmapBytes = Base64.decode(base64BitmapBytes, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

}
