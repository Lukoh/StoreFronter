package com.goforer.storefronter.model.action;

/**
 * Created by USER on 2016-11-02.
 */

public class CommentLikeAction {
    private String mCommenter;
    private long mCommentId;
    private long mCommenterId;

    public String getCommenter() {
        return mCommenter;
    }

    public long getCommentId() {
        return mCommentId;
    }

    public long getCommenterId() {
        return mCommenterId;
    }

    public void setCommenter(String commenter) {
        mCommenter = commenter;
    }

    public void setCommentId(long id) {
        mCommentId = id;
    }

    public void setCommenterId(long id) {
        mCommenterId = id;
    }
}
