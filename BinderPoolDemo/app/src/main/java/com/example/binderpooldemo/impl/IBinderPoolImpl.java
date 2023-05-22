package com.example.binderpooldemo.impl;

import static com.example.binderpooldemo.impl.BinderPool.BINDER_COMPUTE;
import static com.example.binderpooldemo.impl.BinderPool.BINDER_NONE;
import static com.example.binderpooldemo.impl.BinderPool.BINDER_SECURITY_CENTER;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.binderpooldemo.aidl.IBinderPool;


public class IBinderPoolImpl extends IBinderPool.Stub {

    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder iBinder = null;
        switch (binderCode){
            case BINDER_SECURITY_CENTER:
                iBinder = new SecurityCenterImpl();
                break;
            case BINDER_COMPUTE:
                iBinder = new ComputeImpl();
                break;
            default:
                break;

        }
        return iBinder;
    }
}
