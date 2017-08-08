package pers.xjh.note.ui.detail.function;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-8-8.
 */

public class RxJavaActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int initContentView() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                just();
                break;
            case R.id.btn_2:
                defer();
                break;
            case R.id.btn_3:
                interval();
                break;
            case R.id.btn_4:

                break;
            case R.id.btn_5:

                break;
        }
    }

    private void just() {
        Observable.just("just1","just2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void defer() {
        Observable<String> deferObservable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                Toast.makeText(RxJavaActivity.this, "call()", Toast.LENGTH_SHORT).show();
                return Observable.just("1", "2", "3");
            }
        });

        deferObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        deferObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Toast.makeText(RxJavaActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
