package com.darkyver.firebase.service;

import com.darkyver.domain.port.OnChangeListener;
import com.darkyver.firebase.FirebaseInitialization;
import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;
import com.darkyver.firebase.pojo.UserFirebase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentChange.Type;
import com.google.firebase.database.*;

import java.util.*;

public class FirebaseUserRepository implements UserRepository {

    private static final String COLLECTION_NAME = "users";

    private final List<Runnable> runnablesConnect = new ArrayList<>();
    private final List<Runnable> runnablesDisconnect = new ArrayList<>();
    private final HashMap<Type, List<OnChangeListener<User>>> listenersMap = new HashMap<>();

    public FirebaseUserRepository() {
        for (Type value : Type.values()) {
            listenersMap.put(value, new ArrayList<>());
        }
    }

    @Override
    public void onUserAdd(OnChangeListener<User> listener) {
        listenersMap.get(Type.ADDED).add(listener);
    }

    @Override
    public void onUserRemove(OnChangeListener<User> listener) {
        listenersMap.get(Type.REMOVED).add(listener);
    }

    @Override
    public void onUserUpdate(OnChangeListener<User> listener) {
        listenersMap.get(Type.MODIFIED).add(listener);
    }

    @Override
    public void onConnect(Runnable runnable) {
        runnablesConnect.add(runnable);
    }
    @Override
    public void onDisconnect(Runnable runnable) {
        runnablesDisconnect.add(runnable);
    }

    @Override
    public void connect() {
        FirebaseInitialization firebaseInitialization = new FirebaseInitialization();
        firebaseInitialization.initialization();

        FirestoreClient.getFirestore().collection(COLLECTION_NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots == null) {
                return;
            }
            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                listenersMap.get(dc.getType()).forEach(listener -> listener.change(dc.getDocument().toObject(UserFirebase.class).toUser()));
            }
        });

        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://fb-demo-3849e-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference(".info/connected");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class))
                    runnablesConnect.forEach(Runnable::run);
                else
                    runnablesDisconnect.forEach(Runnable::run);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Override
    public Optional<User> getUser(int id) {
        try {

            Firestore firestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(String.valueOf(id));
            ApiFuture<DocumentSnapshot> future = documentReference.get();

            DocumentSnapshot doc = future.get();
            if (doc.exists()) {
                return Optional.ofNullable(doc.toObject(UserFirebase.class).toUser());
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
                    users.add(doc.toObject(UserFirebase.class).toUser());
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
    public boolean updateUser(int id, User user) {
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(String.valueOf(user.getId())).set(UserFirebase.fromUser(user));

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
}
