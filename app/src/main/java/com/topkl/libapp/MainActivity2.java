package com.topkl.libapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yujing.adapter.YBaseYRecyclerViewAdapter;
import com.yujing.adapter.YSetRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.rv)
    RecyclerView rv;
    AddHardwareAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        ButterKnife.bind(this);
        init();
    }

    List<String> list;

    private void init() {
        list = new ArrayList<>();
        list.add("");
        adapter = new AddHardwareAdapter<>(this, list);
        adapter.setRecyclable(false);
        adapter.setItemEmptyText("发发发");
//      adapter.setShowEmpty(false);
        YSetRecyclerView.init(this, rv);
        YSetRecyclerView.openItemTouch(rv, adapter);

        rv.setAdapter(adapter);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                break;
            case R.id.button2:
                adapter.addItem(adapter.getItemCount(), "");
                break;
            case R.id.button3:
                adapter.removeItem(0);
                break;
            case R.id.button4:
                List<String> a = new ArrayList<>();
                adapter.setList(a);
                break;
            case R.id.button5:
                List<String> n = new ArrayList<>();
                n.add("");
                adapter.setList(n);
                break;
            case R.id.button6:
                adapter.setItemEmptyText("哈哈");
                break;
        }
    }


    public class AddHardwareAdapter<T> extends YBaseYRecyclerViewAdapter {
        public AddHardwareAdapter(Context context, List<T> list) {
            super(context, list);
        }

        @Override
        public int setLayout() {
            return R.layout.activity_handle_add_hardware_item;
        }

        @Override
        public BaseViewHolder setViewHolder(View view) {
            return new BaseViewHolder(view) {
                TextView id;
                Spinner name;
                EditText num;

                @Override
                public void findView(View view) {
                    id = view.findViewById(R.id.tv_id);
                    name = view.findViewById(R.id.sp_name);
                    num = view.findViewById(R.id.et_num);
                }

                @Override
                public void setData(int position, Object obj, List adapterList, YBaseYRecyclerViewAdapter adapter) {
                    id.setText("●");
                }
            };
        }
    }
}
