package com.darkyver.javafxview.controllersView.accountingView.elements;

import com.darkyver.domain.entity.User;

public class UserListView {
    private User user;

    public UserListView(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return user.getName();
    }
}
