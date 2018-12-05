package com.funny.androidartnote.chapter2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author pengl
 */
public class Person implements Parcelable{
    private int id;

    private String age;

    public Person(int id, String age) {
        this.id = id;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    protected Person(Parcel in) {
        id = in.readInt();
        age = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age='" + age + '\'' +
                '}';
    }
}
