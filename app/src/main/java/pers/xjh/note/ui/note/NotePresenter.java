package pers.xjh.note.ui.note;

import pers.xjh.note.listener.OnResponseListener;
import pers.xjh.note.model.NoteModel;
import pers.xjh.note.model.bean.Note;

import java.util.List;

/**
 * Created by XJH on 2017/3/25.
 */

public class NotePresenter implements NoteContract.Presenter {

    private NoteModel mNoteModel;

    private NoteContract.NoteView mNoteView;

    public NotePresenter(NoteContract.NoteView view) {
        mNoteView = view;
        mNoteModel = NoteModel.getInstance();
    }

    @Override
    public void loadNote(int noteType) {
        mNoteModel.load(new OnResponseListener<List<Note>>() {
            @Override
            public void onSuccess(List<Note> response) {
                if(mNoteView != null) {
                    mNoteView.onNoteLoadComplete(response);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        }, noteType);
    }

    @Override
    public void detachView() {
        mNoteView = null;
    }
}
