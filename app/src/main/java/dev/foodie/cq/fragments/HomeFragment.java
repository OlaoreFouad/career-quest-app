package dev.foodie.cq.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.foodie.cq.R;
import dev.foodie.cq.adapters.EventsAdapter;
import dev.foodie.cq.models.Event;

public class HomeFragment extends Fragment {

    public static final String TAG = "EVT";
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView eventsRecycler;
    private EventsAdapter eventsAdapter;
    private List<Event> events;
    private FirebaseFirestore mFirebaseFirestore;

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseFirestore.collection("events")
                .orderBy("index", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshots = task.getResult();
                            Log.d(TAG, "onComplete: getting here!");
                            events = new ArrayList<>();
                            if (snapshots != null) {
                                for (QueryDocumentSnapshot doc: snapshots) {
                                    events.add(doc.toObject(Event.class));
                                }
                                initialize();
                            }
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        TextView username = view.findViewById(R.id.username);
        eventsRecycler = view.findViewById(R.id.eventsRecycler);

        username.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        events = new ArrayList<>();
        return view;
    }

    private void initialize() {
        eventsRecycler.setHasFixedSize(true);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        eventsAdapter = new EventsAdapter(getContext(), events);
        eventsRecycler.setAdapter(eventsAdapter);
    }
}
