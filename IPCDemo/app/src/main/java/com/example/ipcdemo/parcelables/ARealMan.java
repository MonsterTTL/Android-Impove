package com.example.ipcdemo.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ARealMan implements Parcelable {

    public String name;
    public int age;

    public ARealMan(String name,int age){
        this.name = name;
        this.age = age;
    }

    //读和写的顺序要一致！！！
    protected ARealMan(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<ARealMan> CREATOR = new Creator<ARealMan>() {
        @Override
        public ARealMan createFromParcel(Parcel in) {
            return new ARealMan(in);
        }

        @Override
        public ARealMan[] newArray(int size) {
            return new ARealMan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
