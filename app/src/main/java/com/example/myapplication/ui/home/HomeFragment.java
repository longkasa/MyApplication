package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> tasks;

    private MyDBHelper databaseHelper;
    private EditText editText;
    private Button addBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);

        databaseHelper = new MyDBHelper(requireContext());
        listView = view.findViewById(R.id.lvItem);
        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), R.layout.home_fragment_list_sample,R.id.txtName, tasks);
        listView.setAdapter(adapter);


        tasks.addAll(databaseHelper.getAllTasks());
        adapter.notifyDataSetChanged();

        // Set up EditText and Button
        editText = view.findViewById(R.id.id_edit_text);
        addBtn = view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        addBtn = (Button) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);


        editText = view.findViewById(R.id.id_edit_text);



        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addBtn) {
            String task = editText.getText().toString().trim();
            if (!task.isEmpty()) {
                // Insert the task into the database
                databaseHelper.insertTask(task);

                // Add the task to the list view
                tasks.add(task);
                adapter.notifyDataSetChanged();

                editText.setText("");
            }
        }
    }
//    public void add_item_list(View view){
//        tasks.add(editText.getText().toString());
//        adapter.notifyDataSetChanged();
//
//        editText.setText("");
//
////        closeKeyboard();
//    }




}