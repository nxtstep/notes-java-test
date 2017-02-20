package io.supersimple.notes.rx;


import io.reactivex.disposables.CompositeDisposable;

public class RxUtils {
    private RxUtils(){}

    public static void safeDispose(CompositeDisposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
