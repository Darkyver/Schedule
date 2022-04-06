package com.darkyver.firebase.service;

import com.darkyver.domain.port.OnChangeUserList;
import com.darkyver.firebase.FirebaseInitialization;
import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.EventListener;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class FirebaseUserRepository implements UserRepository {

    private static final String COLLECTION_NAME = "users";

    private List<OnChangeUserList> listeners = new ArrayList<>();
//    private TreeSet<User> users = new TreeSet<>();


    @Override
    public void connect() {
        FirebaseInitialization firebaseInitialization = new FirebaseInitialization();
        firebaseInitialization.initialization();

//        for (DocumentReference listDocument : FirestoreClient.getFirestore().collection(COLLECTION_NAME).listDocuments()) {
//            listDocument.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(DocumentSnapshot documentSnapshot, FirestoreException e) {
//                    System.out.println(documentSnapshot.toObject(User.class));
//                }
//            });
//        }

//        DocumentReference document = FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(String.valueOf(0));
//        document.get().addListener(() -> System.out.println("run"), command -> System.out.println(command));

        FirestoreClient.getFirestore().collection(COLLECTION_NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots == null) {
                return;
            }

            List<User> users = queryDocumentSnapshots.toObjects(User.class);
//            this.users.addAll(users);
            for (OnChangeUserList listener : listeners) {
                listener.change(users);
            }
        });
    }

    @Override
    public Optional<User> getUser(int id) {
//        Optional<User> first = users.stream().filter(u -> u.getId() == id).findFirst();
//        if(first.isPresent()) return first;

        try {

            Firestore firestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(String.valueOf(id));
            ApiFuture<DocumentSnapshot> future = documentReference.get();

            DocumentSnapshot doc = future.get();
            if (doc.exists()) {

                return Optional.ofNullable(doc.toObject(User.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            Iterable<DocumentReference> listDocuments = firestore.collection(COLLECTION_NAME).listDocuments();
            for (DocumentReference documentReference : listDocuments) {
                DocumentSnapshot doc = documentReference.get().get();
                if(doc.exists()){
                    users.add(doc.toObject(User.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean createUser(String name, int price, String note) {
        try {
            OptionalInt max = getAllUsers().stream().mapToInt(User::getId).max();
            if(max.isEmpty()) max = OptionalInt.of(-1);
            int id = max.getAsInt()+1;
            User user = new User();
            user.setId(id);
            user.setPrice(price);
            user.setNote(note);
            user.setName(name);

            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(String.valueOf(id)).set(user);

            return !apiFuture.get().getUpdateTime().toString().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public void addListUsersChangeListener(OnChangeUserList listener) {
        listeners.add(listener);
    }


    @Override
    public boolean updateUser(int id, User user) {
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(String.valueOf(user.getId())).set(user);

            return !apiFuture.get().getUpdateTime().toString().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(String.valueOf(id)).delete();
            return !apiFuture.get().getUpdateTime().toString().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addRecordToUser(int idUser, Record record) {
        Optional<User> user = getUser(idUser);
        if(user.isEmpty()) return false;
        boolean addRecord = user.get().addRecord(record);
        return addRecord && updateUser(idUser, user.get());
    }

    @Override
    public boolean updateRecordToUser(int id, Record record) {
        Optional<User> user = getUser(id);
        if(user.isEmpty()) return false;
        boolean b = user.get().updateRecord(record);
        if(!b) return false;
        return updateUser(id, user.get());
    }
}
