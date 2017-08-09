package pers.xjh.note.ui.detail.function;

import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
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
        findViewById(R.id.btn_6).setOnClickListener(this);
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
                timer();
                break;
            case R.id.btn_4:
                interval();
                break;
            case R.id.btn_5:
                repeat();
                break;
            case R.id.btn_6:
                test();
                break;
        }
    }

    //使用just( )，将为你创建一个Observable并自动为你调用onNext( )发射数据
    private void just() {
        Observable.just("just1","just2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //使用defer( )，有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
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

    //使用timer( ),创建一个Observable，它在一个给定的延迟后发射一个特殊的值，等同于Android中Handler的postDelay( )方法
    private void timer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long aLong) throws Exception {
                        //timer操作符默认情况下是运行在一个新线程上
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RxJavaActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    //使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器
    private void interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long aLong) throws Exception {
                        //interval操作符默认情况下是运行在一个新线程上
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RxJavaActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    //使用repeat( ),创建一个重复发射特定数据的Observable
    private void repeat() {
        Observable.just("repeatObservable")
                .repeat(3)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
        }).subscribe();
    }

    private void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("小明");
                e.onNext("大明");
                e.onNext("小许");
                e.onNext("大许");
                e.onNext("张三");
            }
            //过滤
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return (s.indexOf("许") != -1);
            }
            //转化
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return s.hashCode();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) throws Exception {
                Toast.makeText(RxJavaActivity.this, i + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
