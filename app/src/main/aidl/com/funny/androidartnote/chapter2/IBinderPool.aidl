// IBinderPool.aidl
package com.funny.androidartnote.chapter2;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    IBinder queryBinder(String binderName);
}
