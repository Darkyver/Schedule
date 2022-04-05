package com.darkyver.javafxview.controllersView.utils;

import com.darkyver.domain.entity.User;

import java.util.Comparator;

public class UserComparatorById implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o2.getId() - o1.getId();
    }

}
