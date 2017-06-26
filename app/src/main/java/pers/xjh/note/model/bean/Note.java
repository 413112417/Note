package pers.xjh.note.model.bean;

/**
 * 笔记
 * Created by XJH on 2017/3/25.
 */

public class Note {

    //笔记名
    private String noteName;
    //笔记类型
    private int noteType;
    //跳转页面
    private Class action;
    //所需权限
    private String[] permission;
    //描述的字符的id
    private int stringId;
    //图片资源id
    private int[] imageResourceId;

    public Note(String noteName) {
        this.noteName = noteName;
    }

    public Note(String noteName, Class action) {
        this.noteName = noteName;
        this.action = action;
    }

    public Note(String noteName, int stringId) {
        this.noteName = noteName;
        this.stringId = stringId;
    }

    public Note(String noteName, int stringId, int... imageResourceId) {
        this.noteName = noteName;
        this.stringId = stringId;
        this.imageResourceId = imageResourceId;
    }

    public Note(String noteName, int noteType, Class action) {
        this.noteName = noteName;
        this.noteType = noteType;
        this.action = action;
    }

    public Note(String noteName, Class action, String... permission) {
        this.noteName = noteName;
        this.action = action;
        this.permission = permission;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public Class getAction() {
        return action;
    }

    public void setAction(Class action) {
        this.action = action;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String... permission) {
        this.permission = permission;
    }

    public int getStringId() {
        return stringId;
    }

    public void setStringId(int stringId) {
        this.stringId = stringId;
    }

    public int[] getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int... imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
