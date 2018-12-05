package com.funny.androidartnote.chapter2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.funny.androidartnote.chapter2.model.Person;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author pengl
 */
public class PersonManagerImpl extends Binder implements IPersonManager {
    private CopyOnWriteArrayList<Person> personList = new CopyOnWriteArrayList<>();

    public PersonManagerImpl(){
        this.attachInterface(this,DESCRIPTOR);
    }

    public static IPersonManager asInterface(IBinder obj){
        if(obj == null)
            return null;

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if((iin != null) && (iin instanceof IPersonManager)){
            return (IPersonManager) iin;
        }else{
            return new PersonManagerImpl.Proxy(obj);
        }
    }


    private static class Proxy implements IPersonManager{

        private IBinder remote;

        public Proxy(IBinder binder){
            this.remote = binder;
        }

        public String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if(person != null){
                    data.writeInt(1);
                    person.writeToParcel(data,0);
                }else{
                    data.writeInt(0);
                }

                remote.transact(TRANSACT_addPerson,data,reply,0);
                reply.readException();
            } finally {
                data.recycle();
                reply.recycle();
            }

        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            List<Person> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                remote.transact(TRANSACT_getPersonList,data,reply,0);
                reply.readException();
                result = reply.createTypedArrayList(Person.CREATOR);
            }finally {
                data.recycle();
                reply.recycle();
            }
            return result;
        }

        @Override
        public IBinder asBinder() {
            return remote;
        }
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACT_addPerson:
                data.enforceInterface(DESCRIPTOR);
                Person person;
                if(0 != data.readInt()){
                    person = Person.CREATOR.createFromParcel(data);
                }else{
                    person = null;
                }
                this.addPerson(person);
                reply.writeNoException();
                return true;
            case TRANSACT_getPersonList:
                data.enforceInterface(DESCRIPTOR);
                List<Person> list = this.getPersonList();
                reply.writeNoException();
                reply.writeTypedList(list);
                return true;
        }

        return super.onTransact(code, data, reply, flags);
    }


    @Override
    public void addPerson(Person person) {
        personList.add(person);
    }

    @Override
    public List<Person> getPersonList() {
        return personList;
    }
}
