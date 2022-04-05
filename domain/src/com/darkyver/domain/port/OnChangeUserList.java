package com.darkyver.domain.port;

import com.darkyver.domain.entity.User;

import java.util.List;

public interface OnChangeUserList {
    void change(List<User> userChanges);
}
