package com.example.ipcdemo3contentprovider.RoomDataBas;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Room数据库的数据实体类
@Entity(tableName = "Books")
public class Book implements Parcelable {
    @PrimaryKey(autoGenerate = true) //定义主键
    public int id;

    @ColumnInfo(name = "uid")
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    public int getId(){
        return id;
    }

    public Book(int uid,String name){
        this.uid = uid;
        this.name = name;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(uid);
        dest.writeString(name);
    }
}
