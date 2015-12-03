package com.hilo.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by hilo on 15/12/3.
 * <p>
 * Drscription:
 */
@Table(name = "T_TEST")
public class T_TEST implements Serializable, Comparator<T_TEST> {

    @Column(name = "id", isId = true)
    public long id;
    @Column(name = "name")
    public String name;
    @Column(name = "email")
    public String email;
    @Column(name = "age")
    public int age;

    public T_TEST() {}

    public T_TEST(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compare(T_TEST lhs, T_TEST rhs) {
        return String.valueOf(lhs.age).compareTo(String.valueOf(rhs.age));
    }

    @Override
    public boolean equals(Object mo) {
        if (this == mo) return true;
        if (mo == null || getClass() != mo.getClass()) return false;

        T_TEST mmT_test = (T_TEST) mo;

        if (age != mmT_test.age) return false;
        if (name != null ? !name.equals(mmT_test.name) : mmT_test.name != null) return false;
        return !(email != null ? !email.equals(mmT_test.email) : mmT_test.email != null);

    }

    @Override
    public int hashCode() {
        int mresult = name != null ? name.hashCode() : 0;
        mresult = 31 * mresult + (email != null ? email.hashCode() : 0);
        mresult = 31 * mresult + age;
        return mresult;
    }

    @Override
    public String toString() {
        return "T_TEST{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
