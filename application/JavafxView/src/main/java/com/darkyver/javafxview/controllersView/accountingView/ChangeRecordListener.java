package com.darkyver.javafxview.controllersView.accountingView;

import com.darkyver.domain.entity.Record;

public interface ChangeRecordListener {
    void accept(int userId, Record record);
}
