package dev.foodie.cq.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dev.foodie.cq.R;
import dev.foodie.cq.models.Message;
import dev.foodie.cq.models.Student;
import dev.foodie.cq.models.User;

public class DatabaseHandler {

    private FirebaseFirestore mFirebaseFirestore;
    private Context ctx;
    private static final String TAG = "DB";

    public DatabaseHandler(Context context) {
        this.ctx = context;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    /*
    * CRUD - User
    * */

    public void addUser(User user) {
        Log.d(TAG, "addUser: getting here!");
        this.mFirebaseFirestore.document("users/" + user.getUid())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: user added!");
                        } else {
                            Log.d(TAG, "onComplete: error adding user: " + task.getException().getMessage());
                        }
                    }
                });
    }

    /*
     * CRUD - Messages
     * */

    public void addMessage(Message message) {
        Log.d(TAG, "addMessage: " + message.getTimeAdded());
        this.mFirebaseFirestore.collection("messages")
                .add(message)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: message added!");
                        }
                    }
                });
    }

    public List<Message> getAllMessages() {
        final List<Message> messages = new ArrayList<>();

        this.mFirebaseFirestore.collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshots = task.getResult();

                        if (snapshots != null) {
                            for (QueryDocumentSnapshot doc: snapshots) {
                                messages.add(doc.toObject(Message.class));
                            }
                        }
                    }
                });

        Log.d(TAG, "getAllMessages: " + messages.size());
        return messages;
    }

    public List<Student> getStudents() {
        final List<Student> students = new ArrayList<>();

        this.mFirebaseFirestore.collection("students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            for (QueryDocumentSnapshot doc: snapshots) {
                                students.add(doc.toObject(Student.class));
                            }
                        }
                    }
                });

        Log.d(TAG, "getStudents: " + students.size());
        return students;
    }


}
