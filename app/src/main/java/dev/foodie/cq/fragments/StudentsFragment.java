package dev.foodie.cq.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.foodie.cq.R;
import dev.foodie.cq.adapters.StudentsAdapter;
import dev.foodie.cq.data.DatabaseHandler;
import dev.foodie.cq.models.Student;

public class StudentsFragment extends Fragment {

    private RecyclerView studentsRecycler;
    private StudentsAdapter adapter;
    private DatabaseHandler db;

    private List<Student> students;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseFirestore.getInstance()
                .collection("students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshots = task.getResult();
                        students = new ArrayList<>();
                        if (snapshots != null) {
                            for (QueryDocumentSnapshot doc: snapshots) {
                                students.add(doc.toObject(Student.class));
                                Log.d("STD", "onComplete: " + students.size());
                            }
                            initialize();
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        db = new DatabaseHandler(getContext());
        studentsRecycler = view.findViewById(R.id.studentsRecycler);
    }

    private void initialize() {
        studentsRecycler.setHasFixedSize(true);
        studentsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new StudentsAdapter(getContext(), students);
        studentsRecycler.setAdapter(adapter);
    }
}
