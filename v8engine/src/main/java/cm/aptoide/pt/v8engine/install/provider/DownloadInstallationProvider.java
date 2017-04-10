/*
 * Copyright (c) 2016.
 * Modified by Marcelo Benites on 25/07/2016.
 */

package cm.aptoide.pt.v8engine.install.provider;

import android.support.annotation.NonNull;
import cm.aptoide.pt.database.accessors.AccessorFactory;
import cm.aptoide.pt.database.accessors.DownloadAccessor;
import cm.aptoide.pt.database.accessors.StoreMinimalAdAccessor;
import cm.aptoide.pt.database.realm.Download;
import cm.aptoide.pt.database.realm.Installed;
import cm.aptoide.pt.database.realm.StoredMinimalAd;
import cm.aptoide.pt.dataprovider.util.DataproviderUtils;
import cm.aptoide.pt.downloadmanager.AptoideDownloadManager;
import cm.aptoide.pt.v8engine.install.exception.InstallationException;
import cm.aptoide.pt.v8engine.install.installer.InstallationProvider;
import cm.aptoide.pt.v8engine.install.installer.RollbackInstallation;
import cm.aptoide.pt.v8engine.repository.InstalledRepository;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by marcelobenites on 7/25/16.
 */
public class DownloadInstallationProvider implements InstallationProvider {

  private final AptoideDownloadManager downloadManager;
  private final DownloadAccessor downloadAccessor;
  private InstalledRepository installedRepository;

  private StoreMinimalAdAccessor storeMinimalAdAccessor;

  public DownloadInstallationProvider(AptoideDownloadManager downloadManager,
      DownloadAccessor downloadAccessor, InstalledRepository installedRepository) {
    this.downloadManager = downloadManager;
    this.downloadAccessor = downloadAccessor;
    this.installedRepository = installedRepository;
    this.storeMinimalAdAccessor = AccessorFactory.getAccessorFor(StoredMinimalAd.class);
  }

  @Override public Observable<RollbackInstallation> getInstallation(String md5) {
    return downloadManager.getDownload(md5).first().flatMap(download -> {
      if (download.getOverallDownloadStatus() == Download.COMPLETED) {
        return installedRepository.get(download.getPackageName()).map(installed -> {
          if (installed == null) {
            installed = convertDownloadToInstalled(download);
          }
          return new DownloadInstallationAdapter(download, downloadAccessor, installedRepository,
              installed);
        }).doOnNext(downloadInstallationAdapter -> {
          storeMinimalAdAccessor.get(downloadInstallationAdapter.getPackageName())
              .doOnNext(handleCpd())
              .subscribeOn(Schedulers.io())
              .subscribe(storedMinimalAd -> {
              }, Throwable::printStackTrace);
        });
      }
      return Observable.error(new InstallationException("Installation file not available."));
    });
  }

  @NonNull private Installed convertDownloadToInstalled(Download download) {
    Installed installed = new Installed();
    installed.setVersionCode(download.getVersionCode());
    installed.setVersionName(download.getVersionName());
    installed.setStatus(Installed.STATUS_INVALID);
    installed.setType(Installed.TYPE_UNKNOWN);
    installed.setPackageName(download.getPackageName());
    return installed;
  }

  @NonNull private Action1<StoredMinimalAd> handleCpd() {
    return storedMinimalAd -> {
      if (storedMinimalAd != null && storedMinimalAd.getCpdUrl() != null) {
        DataproviderUtils.AdNetworksUtils.knockCpd(storedMinimalAd);
        storedMinimalAd.setCpdUrl(null);
        storeMinimalAdAccessor.insert(storedMinimalAd);
      }
    };
  }
}
