package pers.xjh.note.ui.note;

import pers.xjh.note.model.bean.Note;
import pers.xjh.note.ui.base.BasePresenter;
import pers.xjh.note.ui.base.BaseView;

import java.util.List;

/**
 * V和P的协议类
 * Created by XJH on 2017/3/25.
 */
public class NoteContract {

    interface NoteView extends BaseView {
        /**
         * 笔记加载完成的回调
         * @param noteList
         */
        void onNoteLoadComplete(List<Note> noteList);
    }

    interface Presenter extends BasePresenter<NoteView> {
        /**
         * 加载笔记
         */
        void loadNote(int noteType);
    }
}
