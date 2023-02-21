package com.sparta.interfaces;

import java.util.List;

public interface DAO <T> {

    void deletedById(int id);

    void updated(T update);

    int insert(T newRow);

    T findById(int id);
    List<T> findAll();
}
