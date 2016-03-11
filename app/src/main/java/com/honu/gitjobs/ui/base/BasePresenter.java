package com.honu.gitjobs.ui.base;

import java.lang.ref.WeakReference;

/**
 *
 */
public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private WeakReference<T> mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = new WeakReference<T>(mvpView);
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView.get();
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Must call MvpPresenter.attachView(MvpView) before requesting data");
        }
    }
}
