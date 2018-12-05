package com.funny.androidartnote.chapter2;

import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author pengl
 */
public class BookManageImpl extends IBookManager.Stub {

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    @Override
    public void addBook(Book book) throws RemoteException {
        bookList.add(book);
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        return bookList;
    }
}
