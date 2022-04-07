package com.darkyver.domain.port;

import com.darkyver.domain.entity.User;

import java.util.List;

public interface OnChangeListener<T> {
    void change(T changes);
}
