package com.funny.androidartnote.chapter2;

import android.os.Binder;
import android.os.IInterface;
import android.os.RemoteException;

import com.funny.androidartnote.chapter2.model.Person;

import java.util.List;

/**
 * @author pengl
 */
public interface IPersonManager extends IInterface {

    static final String DESCRIPTOR = "com.funny.ex.aidl.PersonManagerImpl";

    static final int TRANSACT_addPerson = Binder.FIRST_CALL_TRANSACTION;
    static final int TRANSACT_getPersonList = Binder.FIRST_CALL_TRANSACTION + 1;

    void addPerson(Person person) throws RemoteException;

    List<Person> getPersonList() throws RemoteException;

}
