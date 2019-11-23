package cm.aptoide.pt.install;

import cm.aptoide.pt.presenter.Presenter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tiagopedrinho on 10/10/2018.
 */

public class DownloadsNotificationsPresenter implements Presenter {

  private static final String TAG = DownloadsNotificationsPresenter.class.getSimpleName();
  private DownloadsNotification service;
  private InstallManager installManager;
  private CompositeSubscription subscriptions;

  public DownloadsNotificationsPresenter(DownloadsNotification service,
      InstallManager installManager) {
    this.service = service;
    this.installManager = installManager;
    subscriptions = new CompositeSubscription();
  }

  private void handleCurrentInstallation() {
    Subscription subscription = installManager.getCurrentInstallation()
        .doOnNext(installation -> {
          if (!installation.isIndeterminate()) {
            String md5 = installation.getMd5();
            service.setupNotification(md5, installation.getAppName(), installation.getProgress(),
                installation.isIndeterminate());
          }
        })
        .distinctUntilChanged(install -> install.getState())
        .flatMap(install -> installManager.getDownloadState(install.getMd5()))
        .doOnNext(installationStatus -> {
          if (installationStatus != Install.InstallationStatus.DOWNLOADING) {
            service.removeNotificationAndStop();
          }
        })
        .subscribe(__ -> {
        }, throwable -> service.removeNotificationAndStop());

    subscriptions.add(subscription);
  }

  public void onDestroy() {
    subscriptions.unsubscribe();
  }

  @Override public void present() {
    handleCurrentInstallation();
  }
}
