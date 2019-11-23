package dev.foodie.cq.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.foodie.cq.R;
import dev.foodie.cq.adapters.ChatsAdapter;
import dev.foodie.cq.data.DatabaseHandler;
import dev.foodie.cq.models.Message;
import dev.foodie.cq.util.Colors;

public class ChatsFragment extends Fragment
    implements View.OnClickListener {

    public static final String TAG = "CHATS";
    private EditText messageField;
    private FloatingActionButton sendMessageButton;
    private RecyclerView chatsRecycler;
    private ChatsAdapter chatsAdapter;

    private FirebaseFirestore mFirebaseFirestore;

    private List<Message> messages;
    private DatabaseHandler db;

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseFirestore.collection("messages")
                .orderBy("timeAdded", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        try {
                            String source = queryDocumentSnapshots != null && queryDocumentSnapshots.getMetadata().hasPendingWrites()
                                    ? "Local" : "Server";
                            Log.d(TAG, "onEvent: " + source);
                            if (queryDocumentSnapshots == null) {
                                Log.d(TAG, "onEvent: data is null!");
                            } else {
                                messages  = new ArrayList<>();
                                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                                    messages.add(doc.toObject(Message.class));
                                }
                                initialize();
                                if (messages.size() > 0) {
                                    chatsRecycler.smoothScrollToPosition(chatsAdapter.getItemCount()-1);
                                }
                            }
                        }  catch (Exception ex) {
                            Log.e(TAG, "onEvent: " + ex.getMessage());
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());

        messageField = view.findViewById(R.id.chatMessage);
        sendMessageButton = view.findViewById(R.id.sendChatMessageButton);
        messages = new ArrayList<>();

        sendMessageButton.setOnClickListener(this);
        chatsRecycler = view.findViewById(R.id.chatsRecycler);

        chatsRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: recycler clicked!");
            }
        });

//        messages = db.getAllMessages();

        initialize();
    }

    private void initialize() {
        chatsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        chatsAdapter = new ChatsAdapter(getContext(), messages);
        Log.d(TAG, "initialize: " + messages.size());

        chatsRecycler.setAdapter(chatsAdapter);
        Log.d(TAG, "initialize: item count: " + chatsAdapter.getItemCount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendChatMessageButton: {
                String content = messageField.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message();
                    message.setAuthor(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    message.setContent(content);
                    message.setColor(Colors.get());


                    message.setTimeAdded(System.currentTimeMillis());
                    db.addMessage(message);
                    chatsAdapter.notifyDataSetChanged();

                    messageField.getText().clear();
                } else {
                    Toast.makeText(getContext(), "Enter a message!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
