package com.gmail.silina.katsiaryna.repository;

import java.util.List;

public interface GenericRepository<I, T> {

    List<T> findAll();

    void persist(T entity);

    T findById(I id);

    void remove(T entity);

    I getCount();
}
