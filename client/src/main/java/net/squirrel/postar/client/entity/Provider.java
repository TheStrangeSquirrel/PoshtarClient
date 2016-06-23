package net.squirrel.postar.client.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class Provider implements Serializable {
    @Attribute
    private int id;
    @Attribute
    private String name;
    @Attribute
    private byte[] bitmapBytes;


    public Provider(int id, String name, byte[] bitmap) {
        this.id = id;
        this.name = name;
        this.bitmapBytes = bitmap;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getBitmapBytes() {
        return bitmapBytes;
    }

    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}
