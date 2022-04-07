package com.darkyver.javafxview.controllersView.accountingView.elements;

import com.darkyver.domain.entity.User;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserListView that = (UserListView) o;
        return Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
