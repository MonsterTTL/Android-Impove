package com.example.ipcdemo.services;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ipcdemo.aidl.Book;

import java.util.ArrayList;
import java.util.List;

public class mBookManagerImpl extends Binder implements mIBookManager {

    private List<Book> mList = new ArrayList<>();

    //将AIDL接口的描述符与当前Stub对象绑定，这样在客户端调用asInterface方法时，就可以根据接口的描述符获取到相应的Stub对象
    //同时，attachInterface 方法还会将 Stub 对象注册到 Binder 的管理器中，这样就可以通过 Binder 的查询机制找到相应的 Stub 对象了。
    public mBookManagerImpl(){
        attachInterface(this,DESCRIPTOR);
    }

    public static mIBookManager asInterface(IBinder obj){
        if((obj == null)){
            return null;
        }

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);//查询客户端是否有本地服务
        if((iin != null) && (iin instanceof mIBookManager)){
            return ((mIBookManager) iin); //如果查询到了本地服务，就直接返回
        }
        return new mBookManagerImpl.Proxy(obj); //否则就返回代理，通过远程调用的方式来进行服务端调用
    }

    @Override
    public IBinder asBinder(){
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(DESCRIPTOR);//强制检查该请求的调用方和被调用方是否都遵循了这个 AIDL 接口的规则
                List<Book> result = this.getBookList();
                reply.writeNoException();//说明没有出现异常
                reply.writeTypedList(result);//将返回值写入result中
                return true;//返回true说明调用成功
            }
            case TRANSACTION_addBook:{
                data.enforceInterface(DESCRIPTOR);
                Book arg0; //这是被添加的参数
                if( (0 != data.readInt())){//说明写入的参数不为空
                    arg0 = Book.CREATOR.createFromParcel(data);//通过远程调用取出客户端传入的参数
                }else{
                    arg0 = null;
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    public List<Book> getBookList(){
        synchronized (mList){
            return mList;
        }
    }

    public void addBook(Book book){
        synchronized (mList){
            mList.add(book);
        }
        return;
    }

    //远程代理类
    private static class Proxy implements mIBookManager{
        private IBinder mRemote;

        Proxy(IBinder mRemote){
            this.mRemote = mRemote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

        @Override //由于这是通过RPC调用的，需要一些特殊手段
        public List<Book> getBookList() throws RemoteException {
            //首先先构建出要写入的参数和返回的参数
            Parcel data = Parcel.obtain();//传入的参数
            Parcel reply = Parcel.obtain();//返回的参数
            List<Book> result;
            try{
                //用于在跨进程通信中写入接口标识符
                //Binder 是用于进程间通信的基本机制之一，可以将一个类作为一个服务发布到系统中，
                //然后其他进程就可以绑定到这个服务并调用其方法。每个 Binder 实例都对应一个唯一的描述符，
                //用于标识该 Binder 实例提供的服务接口。

                //在方法调用中，客户端会将其要调用的方法名称和相应的参数写入 Parcel 对象中，然后将其传递给服务端。
                // 服务端在接收到 Parcel 对象后，需要首先读取接口标识符，以确保客户端调用的方法确实是服务端提供的合法方法。
                // 这是为了防止非法调用或者攻击。  -- 这里的调用就是写入接口标识符
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList,data,reply,0);//结果已经被写入了reply中
                reply.readException();//因为这个方法是在客户端调用的，客户端需要读取服务端的调用是否出了异常
                result = reply.createTypedArrayList(Book.CREATOR);//通过reply返回结果
            }finally{
                reply.recycle();
                data.recycle();//回收
            }
            return result;
        }


        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try{
                data.writeInterfaceToken(DESCRIPTOR);//这个方法必须要先调用
                if(book != null){
                    data.writeInt(1);
                    book.writeToParcel(data,0);//将客户端传入的参数写入Parcel中以发送给服务端
                }else{
                    data.writeInt(0);
                }

                mRemote.transact(TRANSACTION_addBook,data,reply,0);
                reply.readException();
            }finally{
                reply.recycle();
                data.recycle();
            }
            //由于没有返回值，也不需要返回参数了
        }
    }
}
