package pers.xjh.note.model;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pers.xjh.note.R;
import pers.xjh.note.listener.OnResponseListener;
import pers.xjh.note.model.bean.Note;
import pers.xjh.note.ui.detail.algorithm.GraphActivity;
import pers.xjh.note.ui.detail.algorithm.MinimumSpanningTreeActivity;
import pers.xjh.note.ui.detail.algorithm.ShortestPathActivity;
import pers.xjh.note.ui.detail.android.ActivityManagerActivity;
import pers.xjh.note.ui.detail.android.AudioWaveformActivity;
import pers.xjh.note.ui.detail.android.BuildInfoActivity;
import pers.xjh.note.ui.detail.android.CalendarActivity;
import pers.xjh.note.ui.detail.android.CanvasAPIActivity;
import pers.xjh.note.ui.detail.android.ClockViewActivity;
import pers.xjh.note.ui.detail.android.ColorMatrixActivity;
import pers.xjh.note.ui.detail.android.ColorMatrixApiActivity;
import pers.xjh.note.ui.detail.android.CustomAnimationActivity;
import pers.xjh.note.ui.detail.android.CustomScrollViewActivity;
import pers.xjh.note.ui.detail.android.CustomTextViewActivity;
import pers.xjh.note.ui.detail.android.DialogActivity;
import pers.xjh.note.ui.detail.android.DispatchEventActivity;
import pers.xjh.note.ui.detail.android.DrawerLayoutActivity;
import pers.xjh.note.ui.detail.android.DrawingBoardActivity;
import pers.xjh.note.ui.detail.android.FlashlightActivity;
import pers.xjh.note.ui.detail.android.FragmentActivity;
import pers.xjh.note.ui.detail.android.FragmentNestedActivity;
import pers.xjh.note.ui.detail.android.FragmentPagerActivity;
import pers.xjh.note.ui.detail.android.GPSActivity;
import pers.xjh.note.ui.detail.android.GoodClockViewActivity;
import pers.xjh.note.ui.detail.android.ImageWallActivity;
import pers.xjh.note.ui.detail.android.LayerActivity;
import pers.xjh.note.ui.detail.android.LayoutAnimationActivity;
import pers.xjh.note.ui.detail.android.LineChartViewActivity;
import pers.xjh.note.ui.detail.android.LockScreenServiceActivity;
import pers.xjh.note.ui.detail.android.MainProcessActivity;
import pers.xjh.note.ui.detail.android.MediaPlayerActivity;
import pers.xjh.note.ui.detail.android.NetworkChangeActivity;
import pers.xjh.note.ui.detail.android.NotificationActivity;
import pers.xjh.note.ui.detail.android.ObjectAnimatorActivity;
import pers.xjh.note.ui.detail.android.OverdrawActivity;
import pers.xjh.note.ui.detail.android.PackageInstallerActivity;
import pers.xjh.note.ui.detail.android.PackageManagerActivity;
import pers.xjh.note.ui.detail.android.PathEffectActivity;
import pers.xjh.note.ui.detail.android.PendingTransitionAActivity;
import pers.xjh.note.ui.detail.android.PixelsActivity;
import pers.xjh.note.ui.detail.android.ReflectActivity;
import pers.xjh.note.ui.detail.android.RefreshActivity;
import pers.xjh.note.ui.detail.android.RoundImageActivity;
import pers.xjh.note.ui.detail.android.SVGAnimationActivity;
import pers.xjh.note.ui.detail.android.ScratchCardActivity;
import pers.xjh.note.ui.detail.android.ScrollActivity;
import pers.xjh.note.ui.detail.android.SearchBarActivity;
import pers.xjh.note.ui.detail.android.SelectorActivity;
import pers.xjh.note.ui.detail.android.ShaderActivity;
import pers.xjh.note.ui.detail.android.SineWaveActivity;
import pers.xjh.note.ui.detail.android.SlidingFinishActivity;
import pers.xjh.note.ui.detail.android.SystemPropertyActivity;
import pers.xjh.note.ui.detail.android.TestProcessActivity;
import pers.xjh.note.ui.detail.android.ThreeBodyActivity;
import pers.xjh.note.ui.detail.android.TitleBarActivity;
import pers.xjh.note.ui.detail.android.UnlockActivity;
import pers.xjh.note.ui.detail.android.ValueAnimatorActivity;
import pers.xjh.note.ui.detail.android.VideoViewActivity;
import pers.xjh.note.ui.detail.android.ViewAnimationActivity;
import pers.xjh.note.ui.detail.android.ViewMeasureActivity;
import pers.xjh.note.ui.detail.android.ViewPagerActivity;
import pers.xjh.note.ui.detail.android.ViewStubActivity;
import pers.xjh.note.ui.detail.android.WavingActivity;
import pers.xjh.note.ui.detail.android.WebViewActivity;
import pers.xjh.note.ui.detail.android.activityLifeCycle.AActivity;
import pers.xjh.note.ui.detail.android.activityLifeCycle.FinishOnTaskLaunchActivity;
import pers.xjh.note.ui.detail.android.viewLifeCycle.FrameLayoutLifeCycleActivity;
import pers.xjh.note.ui.detail.android.viewLifeCycle.ViewLifeCycleActivity;
import pers.xjh.note.ui.detail.algorithm.RedBlackTreeActivity;
import pers.xjh.note.ui.detail.algorithm.TreeActivity;
import pers.xjh.note.ui.detail.algorithm.BinarySearchTreeActivity;
import pers.xjh.note.ui.detail.algorithm.SortActivity;
import pers.xjh.note.ui.detail.function.DownloadActivity;
import pers.xjh.note.ui.detail.java.HttpServerActivity;
import pers.xjh.note.ui.detail.function.BaiduMapActivity;
import pers.xjh.note.ui.detail.android.FingerprintActivity;
import pers.xjh.note.ui.detail.android.NFCActivity;
import pers.xjh.note.ui.detail.function.NetworkActivity;
import pers.xjh.note.ui.detail.function.QRCodeActivity;
import pers.xjh.note.ui.detail.android.VideoRecorderActivity;
import pers.xjh.note.ui.detail.android.VoiceRecorderActivity;
import pers.xjh.note.ui.detail.optimize.MemoryLeakActivity;
import pers.xjh.note.ui.detail.optimize.StringOptimizeActivity;
import pers.xjh.note.ui.note.NoteActivity;
import pers.xjh.note.utils.Constant;

/**
 * 笔记条目Model
 * Created by XJH on 2017/3/25.
 */

public class NoteModel {

    //饿汉单例模式
    private static NoteModel mInstance = new NoteModel();

    private NoteModel() {}

    public static NoteModel getInstance() {
        return mInstance;
    }

    /**
     * 加载笔记条目
     */
    public void load(final OnResponseListener<List<Note>> listener, final int noteType) {

        //创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<List<Note>>() {

            @Override
            public void subscribe(ObservableEmitter<List<Note>> e) throws Exception {

                List<Note> noteList = new ArrayList();

                if(Constant.NOTE_ALL == noteType) {
                    noteList.add(new Note("Java", Constant.NOTE_JAVA, NoteActivity.class));
                    noteList.add(new Note("Android", Constant.NOTE_ANDROID, NoteActivity.class));
                    noteList.add(new Note("Linux", Constant.NOTE_LINUX, NoteActivity.class));
                    noteList.add(new Note("优化", Constant.NOTE_OPTIMIZE, NoteActivity.class));
                    noteList.add(new Note("功能", Constant.NOTE_FUNCTION, NoteActivity.class));
                    noteList.add(new Note("设计模式", Constant.NOTE_DESIGN, NoteActivity.class));
                    noteList.add(new Note("算法", Constant.NOTE_ALGORITHM, NoteActivity.class));
                    noteList.add(new Note("人工智能", Constant.NOTE_AI, NoteActivity.class));
                } else if(Constant.NOTE_JAVA == noteType) {
                    noteList.add(new Note("基本概念", R.string.java_jbgn));
                    noteList.add(new Note("面向对象", R.string.java_mxdx));
                    noteList.add(new Note("变量", R.string.java_bl));
                    noteList.add(new Note("运算符", R.string.java_ysf));
                    noteList.add(new Note("方法", R.string.java_ff));
                    noteList.add(new Note("封装", R.string.java_fz));
                    noteList.add(new Note("继承", R.string.java_jc));
                    noteList.add(new Note("多态", R.string.java_dt));
                    noteList.add(new Note("重载", R.string.java_cz));
                    noteList.add(new Note("集合类", R.string.java_jh));
                    noteList.add(new Note("使用函数库", R.string.java_api));
                    noteList.add(new Note("抽象类", R.string.java_cxl));
                    noteList.add(new Note("接口", R.string.java_jk));
                    noteList.add(new Note("构造方法", R.string.java_gzff));
                    noteList.add(new Note("内存区域", R.string.java_ncqy, R.drawable.java_memory_area, R.drawable.java_oom));
                    noteList.add(new Note("垃圾回收", R.string.java_gc));
                    noteList.add(new Note("多线程", R.string.java_thread));
                    noteList.add(new Note("反射", R.string.java_reflect));
                    noteList.add(new Note("注解", R.string.java_annotation));
                    noteList.add(new Note("Http服务器", HttpServerActivity.class));
                } else if(Constant.NOTE_LINUX == noteType) {
                    noteList.add(new Note("常用快捷键", R.string.linux_kjj));
                    noteList.add(new Note("目录基本操作", R.string.linux_mljbcz));
                    noteList.add(new Note("文件查找和比较", R.string.linux_wjczhbj));
                    noteList.add(new Note("文件内容查看", R.string.linux_wjnrck));
                    noteList.add(new Note("文件处理", R.string.linux_wjcl));
                    noteList.add(new Note("文件编辑", R.string.linux_wjbj));
                    noteList.add(new Note("文件权限属性设置", R.string.linux_wjqxsxsz));
                    noteList.add(new Note("文件过滤分割与合并", R.string.linux_wjglfgyhb));
                    noteList.add(new Note("文件压缩与解压", R.string.linux_wjysyjy));
                    noteList.add(new Note("文件备份和恢复", R.string.linux_wjbfyhf));
                    noteList.add(new Note("文件传输", R.string.linux_wjcs));
                    noteList.add(new Note("文件系统管理", R.string.linux_wjxtgl));
                    noteList.add(new Note("系统关机和重启", R.string.linux_xtgjhcq));
                    noteList.add(new Note("系统安全", R.string.linux_xtaq));
                    noteList.add(new Note("进程和作业管理", R.string.linux_xtaq));
                    noteList.add(new Note("X-Windows", R.string.linux_xwindows));
                    noteList.add(new Note("SELinux", R.string.linux_selinux));
                    noteList.add(new Note("网络应用", R.string.linux_wlyy));
                    noteList.add(new Note("高级网络", R.string.linux_gjwl));
                    noteList.add(new Note("网络测试", R.string.linux_wlcs));
                    noteList.add(new Note("网络安全", R.string.linux_wlaq));
                    noteList.add(new Note("网络配置", R.string.linux_wlpz));
                    noteList.add(new Note("网络服务器", R.string.linux_wlfwq));
                    noteList.add(new Note("Shell内建命令", R.string.linux_shellnjml));
                    noteList.add(new Note("性能监测与优化", R.string.linux_xnjcyyh));
                    noteList.add(new Note("硬件管理", R.string.linux_yjgl));
                    noteList.add(new Note("内核与模块管理", R.string.linux_nhymkgl));
                    noteList.add(new Note("磁盘管理", R.string.linux_cpgl));
                    noteList.add(new Note("常用工具命令", R.string.linux_cygjml));
                    noteList.add(new Note("软件包管理", R.string.linux_rjbgl));
                    noteList.add(new Note("编程开发", R.string.linux_bckf));
                    noteList.add(new Note("打印", R.string.linux_dy));
                } else if(Constant.NOTE_ANDROID == noteType) {
                    noteList.add(new Note("View的测量", ViewMeasureActivity.class));
                    noteList.add(new Note("对现有控件进行拓展", CustomTextViewActivity.class));
                    noteList.add(new Note("组合控件", TitleBarActivity.class));
                    noteList.add(new Note("重写View(音频条形图)", AudioWaveformActivity.class));
                    noteList.add(new Note("折线图", LineChartViewActivity.class));
                    noteList.add(new Note("自定义ViewGroup", CustomScrollViewActivity.class));
                    noteList.add(new Note("下拉刷新", RefreshActivity.class));
                    noteList.add(new Note("右滑返回", SlidingFinishActivity.class));
                    noteList.add(new Note("侧滑菜单", DrawerLayoutActivity.class));
                    noteList.add(new Note("日历控件", CalendarActivity.class));
                    noteList.add(new Note("ViewGroup生命周期", FrameLayoutLifeCycleActivity.class));
                    noteList.add(new Note("View生命周期", ViewLifeCycleActivity.class));
                    noteList.add(new Note("事件分发机制", DispatchEventActivity.class));
                    noteList.add(new Note("实现滑动的方法", ScrollActivity.class));
                    noteList.add(new Note("Canvas常用API", CanvasAPIActivity.class));
                    noteList.add(new Note("绘图技巧(时钟,未优化)", ClockViewActivity.class));
                    noteList.add(new Note("绘图技巧(时钟,优化)", GoodClockViewActivity.class));
                    noteList.add(new Note("图层", LayerActivity.class));
                    noteList.add(new Note("颜色矩阵api", ColorMatrixApiActivity.class));
                    noteList.add(new Note("颜色矩阵", ColorMatrixActivity.class));
                    noteList.add(new Note("像素点分析", PixelsActivity.class));
                    noteList.add(new Note("图片墙", ImageWallActivity.class, Manifest.permission.READ_EXTERNAL_STORAGE));
                    noteList.add(new Note("飘扬效果", WavingActivity.class));
                    noteList.add(new Note("遮罩(圆角图片)", RoundImageActivity.class));
                    noteList.add(new Note("遮罩(刮刮卡)", ScratchCardActivity.class));
                    noteList.add(new Note("渐变", ShaderActivity.class));
                    noteList.add(new Note("倒影", ReflectActivity.class));
                    noteList.add(new Note("PathEffect", PathEffectActivity.class));
                    noteList.add(new Note("正弦曲线", SineWaveActivity.class));
                    noteList.add(new Note("画板", DrawingBoardActivity.class));
                    noteList.add(new Note("WebView", WebViewActivity.class));
                    noteList.add(new Note("视图动画", ViewAnimationActivity.class));
                    noteList.add(new Note("ObjectAnimator", ObjectAnimatorActivity.class));
                    noteList.add(new Note("ValueAnimator", ValueAnimatorActivity.class));
                    noteList.add(new Note("LayoutAnimation", LayoutAnimationActivity.class));
                    noteList.add(new Note("自定义动画", CustomAnimationActivity.class));
                    noteList.add(new Note("矢量动画", SVGAnimationActivity.class));
                    noteList.add(new Note("矢量动画(三体)", ThreeBodyActivity.class));
                    noteList.add(new Note("轨迹动画(搜索框)", SearchBarActivity.class));
                    noteList.add(new Note("Activity生命周期与启动模式", AActivity.class));
                    noteList.add(new Note("Activity切换动画", PendingTransitionAActivity.class));
                    noteList.add(new Note("清除任务栈", FinishOnTaskLaunchActivity.class));
                    noteList.add(new Note("Fragment", FragmentActivity.class));
                    noteList.add(new Note("Fragment嵌套", FragmentNestedActivity.class));
                    noteList.add(new Note("ViewPager", ViewPagerActivity.class));
                    noteList.add(new Note("FragmentPager", FragmentPagerActivity.class));
                    noteList.add(new Note("开启锁屏服务", LockScreenServiceActivity.class));
                    noteList.add(new Note("多进程-主进程", MainProcessActivity.class));
                    noteList.add(new Note("多进程-测试进程", TestProcessActivity.class));
                    noteList.add(new Note("监听网络状态", NetworkChangeActivity.class));
                    noteList.add(new Note("Build信息", BuildInfoActivity.class));
                    noteList.add(new Note("系统参数", SystemPropertyActivity.class));
                    noteList.add(new Note("权限", R.string.android_permission));
                    noteList.add(new Note("PackageManager", PackageManagerActivity.class));
                    noteList.add(new Note("ActivityManager", ActivityManagerActivity.class));
                    noteList.add(new Note("PackageInstaller", PackageInstallerActivity.class));
                    noteList.add(new Note("推送", NotificationActivity.class));
                    noteList.add(new Note("对话框", DialogActivity.class));
                    noteList.add(new Note("按钮状态", SelectorActivity.class));
                    noteList.add(new Note("指纹识别", FingerprintActivity.class));
                    noteList.add(new Note("手势解锁", UnlockActivity.class));
                    noteList.add(new Note("NFC", NFCActivity.class));
                    noteList.add(new Note("视频录制", VideoRecorderActivity.class, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO));
                    noteList.add(new Note("音频录制", VoiceRecorderActivity.class, Manifest.permission.RECORD_AUDIO));
                    noteList.add(new Note("VideoView", VideoViewActivity.class));
                    noteList.add(new Note("MediaPlayer", MediaPlayerActivity.class));
                    noteList.add(new Note("闪光灯", FlashlightActivity.class, Manifest.permission.CAMERA));
                    noteList.add(new Note("GPS", GPSActivity.class));
                } else if(Constant.NOTE_OPTIMIZE == noteType) {
                    noteList.add(new Note("内存泄露", MemoryLeakActivity.class));
                    noteList.add(new Note("字符串优化", StringOptimizeActivity.class));
                    noteList.add(new Note("过度绘制", OverdrawActivity.class));
                    noteList.add(new Note("ViewStub", ViewStubActivity.class));
                } else if(Constant.NOTE_ALGORITHM == noteType) {
                    noteList.add(new Note("数据结构", R.string.arithmetic_structure));
                    noteList.add(new Note("排序", SortActivity.class));
                    noteList.add(new Note("树", TreeActivity.class));
                    noteList.add(new Note("二叉查找树", BinarySearchTreeActivity.class));
                    noteList.add(new Note("红黑树", RedBlackTreeActivity.class));
                    noteList.add(new Note("图", GraphActivity.class));
                    noteList.add(new Note("无权最短路径", ShortestPathActivity.class));
                    noteList.add(new Note("最小生成树", MinimumSpanningTreeActivity.class));
                } else if(Constant.NOTE_DESIGN == noteType) {
                    noteList.add(new Note("设计原则", R.string.design_sjyz));
                    noteList.add(new Note("简单工厂模式", R.string.design_jdgc));
                    noteList.add(new Note("外观模式", R.string.design_wg));
                    noteList.add(new Note("适配器模式", R.string.design_spq));
                    noteList.add(new Note("单例模式", R.string.design_dl));
                    noteList.add(new Note("工厂方法模式", R.string.design_gcff));
                    noteList.add(new Note("抽象工厂模式", R.string.design_cxgc));
                    noteList.add(new Note("生成器模式", R.string.design_scq));
                    noteList.add(new Note("原型模式", R.string.design_yx));
                    noteList.add(new Note("中介者模式", R.string.design_zjz));
                    noteList.add(new Note("代理模式", R.string.design_dlms));
                    noteList.add(new Note("观察者模式", R.string.design_gcz));
                    noteList.add(new Note("命令模式", R.string.design_ml));
                    noteList.add(new Note("迭代器模式", R.string.design_ddq));
                    noteList.add(new Note("组合模式", R.string.design_zh));
                    noteList.add(new Note("模版方法模式", R.string.design_mbff));
                    noteList.add(new Note("策略模式", R.string.design_cl));
                    noteList.add(new Note("状态模式", R.string.design_zt));
                    noteList.add(new Note("备忘录模式", R.string.design_bwl));
                    noteList.add(new Note("享元模式", R.string.design_xy));
                    noteList.add(new Note("解释器模式", R.string.design_jsq));
                    noteList.add(new Note("装饰模式", R.string.design_zs));
                    noteList.add(new Note("责任链模式", R.string.design_zrl));
                    noteList.add(new Note("桥接模式", R.string.design_qj));
                    noteList.add(new Note("访问者模式", R.string.design_fwz));
                } else if(Constant.NOTE_FUNCTION == noteType) {
                    noteList.add(new Note("网络请求", NetworkActivity.class));
                    noteList.add(new Note("文件下载", DownloadActivity.class));
                    noteList.add(new Note("二维码扫描", QRCodeActivity.class, Manifest.permission.CAMERA));
                    noteList.add(new Note("百度地图", BaiduMapActivity.class));
                }

                e.onNext(noteList);
            }
        });

        //创建观察者
        Observer<List<Note>> observer = new Observer<List<Note>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Note> value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        //订阅
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
