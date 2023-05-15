package com.example.ipcdemo2.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
    public String BookName;
    public String Author;

    public Book(String BookName,String Author){
        this.BookName = BookName;
        this.Author = Author;
    }

    protected Book(Parcel in) {
        BookName = in.readString();
        Author = in.readString();
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
        dest.writeString(BookName);
        dest.writeString(Author);
    }
}
