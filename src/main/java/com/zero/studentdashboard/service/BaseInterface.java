package com.zero.studentdashboard.service;

import com.zero.studentdashboard.model.Response;
public interface BaseInterface<T> {
    Response save(T t);

    Response getById(Long id);
}
