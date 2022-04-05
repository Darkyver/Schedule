package com.darkyver.javafxview.controllersView.accountingView;

import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.controllersView.accountingView.elements.UserListView;
import com.darkyver.javafxview.controllersView.accountingView.elements.UsersListViewHB;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UserListViewCellFactory implements Callback<ListView<UserListView>, ListCell<UserListView>> {


    private UsersListViewHB node;
    private OnButtonListener<User> listener;

    @Override
    public ListCell<UserListView> call(ListView<UserListView> userListViewListView) {
        return new ListCell<>(){
            @Override
            protected void updateItem(UserListView userListView, boolean empty) {
                super.updateItem(userListView, empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                    return;
                }
                node = new UsersListViewHB(userListView.getUser());
                node.addOnInfoBtnListener(listener);
                setGraphic(node);
            }
        };
    }

    public void addCellActionListener(OnButtonListener<User> listener) {
        this.listener = listener;
    }
}
