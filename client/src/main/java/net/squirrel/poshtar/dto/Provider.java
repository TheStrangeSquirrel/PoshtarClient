package net.squirrel.poshtar.dto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class Provider implements Serializable {
    private int id;
    private String name;
    private byte[] bitmapBytes;

    public Provider(int id, String name, byte[] bitmap) {
        this.id = id;
        this.name = name;
        this.bitmapBytes = bitmap;
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
    public byte[] getBitmapBytes() {
        return bitmapBytes;
    }

    @Attribute
    public void setBitmapBytes(byte[] bitmapBytes) {
        this.bitmapBytes = bitmapBytes;
    }

    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}
