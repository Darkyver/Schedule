package com.darkyver.javafxview.controllersView.accountingView;

import java.io.IOException;

public interface OnButtonListener<T>  {
    void handle(T t) throws IOException;
}
