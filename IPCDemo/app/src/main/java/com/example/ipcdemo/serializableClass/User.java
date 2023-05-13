package com.example.ipcdemo.serializableClass;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;




import java.io.Serializable;

public class User implements Serializable, Parcelable {
    private static final long serialVersionUID = 519067123721295773L;


    public User(int userId,String userName,boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }
    public int userId;
    public String userName;
    public boolean isMale;



    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        //book = in.readParcelable(Thread.currentThread().getContextClassLoader());

    }

    //这个方法配合上面那个User（Parcel in） 实现反序列化
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override //这个方法实现序列化
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        //dest.writeParcelable(book,0);
    }




}
