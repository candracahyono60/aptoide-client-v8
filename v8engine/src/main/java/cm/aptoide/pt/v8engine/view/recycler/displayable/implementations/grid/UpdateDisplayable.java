/*
 * Copyright (c) 2016.
 * Modified by SithEngineer on 27/07/2016.
 */

package cm.aptoide.pt.v8engine.view.recycler.displayable.implementations.grid;

import android.content.Context;
import cm.aptoide.pt.actions.PermissionManager;
import cm.aptoide.pt.actions.PermissionRequest;
import cm.aptoide.pt.database.realm.Download;
import cm.aptoide.pt.database.realm.Update;
import cm.aptoide.pt.utils.AptoideUtils;
import cm.aptoide.pt.utils.GenericDialogs;
import cm.aptoide.pt.v8engine.InstallManager;
import cm.aptoide.pt.v8engine.Progress;
import cm.aptoide.pt.v8engine.R;
import cm.aptoide.pt.v8engine.analytics.Analytics;
import cm.aptoide.pt.v8engine.util.DownloadFactory;
import cm.aptoide.pt.v8engine.view.recycler.displayable.Displayable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import rx.Observable;
import rx.Scheduler;

import static cm.aptoide.pt.utils.GenericDialogs.EResponse.YES;

/**
 * Created by neuro on 17-05-2016.
 */
@AllArgsConstructor public class UpdateDisplayable extends Displayable {

  @Getter private String packageName;
  @Getter private long appId;
  @Getter private String label;
  @Getter private String icon;
  @Getter private int versionCode;
  @Getter private String md5;
  @Getter private String apkPath;
  @Getter private String alternativeApkPath;
  @Getter private String updateVersionName;
  // Obb
  @Getter private String mainObbName;
  @Getter private String mainObbPath;
  @Getter private String mainObbMd5;
  @Getter private String patchObbName;
  @Getter private String patchObbPath;
  @Getter private String patchObbMd5;

  @Getter private Download download;
  @Getter private InstallManager installManager;

  public UpdateDisplayable() {
  }

  public static UpdateDisplayable create(Update update, InstallManager installManager,
      DownloadFactory downloadFactory) {

    return new UpdateDisplayable(update.getPackageName(), update.getAppId(), update.getLabel(),
        update.getIcon(), update.getVersionCode(), update.getMd5(), update.getApkPath(),
        update.getAlternativeApkPath(), update.getUpdateVersionName(), update.getMainObbName(),
        update.getMainObbPath(), update.getMainObbMd5(), update.getPatchObbName(),
        update.getPatchObbPath(), update.getPatchObbMd5(), downloadFactory.create(update),
        installManager);
  }

  public Observable<Progress<Download>> downloadAndInstall(Context context,
      PermissionRequest permissionRequest) {
    Analytics.Updates.update();
    PermissionManager permissionManager = new PermissionManager();
    return permissionManager.requestExternalStoragePermission(permissionRequest)
        .flatMap(sucess -> {
          if (installManager.showWarning()) {
            return GenericDialogs.createGenericYesNoCancelMessage(context, "",
                AptoideUtils.StringU.getFormattedString(R.string.root_access_dialog))
                .map(answer -> (answer.equals(YES)))
                .doOnNext(answer -> installManager.rootInstallAllowed(answer));
          }
          return Observable.just(true);
        })
        .flatMap(success -> permissionManager.requestDownloadAccess(permissionRequest))
        .flatMap(success -> installManager.install(context, download));
  }

  @Override public int getViewLayout() {
    return R.layout.update_row;
  }

  @Override protected Configs getConfig() {
    return new Configs(1, false);
  }

  /**
   * *  <dt><b>Scheduler:</b></dt>
   * <dd>{@code getUpdates} operates by default on the {@code io} {@link Scheduler}..</dd>
   * </dl>
   */
  public Observable<Progress<Download>> getUpdates() {
    return installManager.getInstallations();
  }

  public boolean isDownloadingOrInstalling(Progress<Download> progress) {
    return progress.getRequest().getOverallDownloadStatus() == Download.PROGRESS
        || progress.getRequest().getOverallDownloadStatus() == Download.PENDING
        || progress.getRequest().getOverallDownloadStatus() == Download.IN_QUEUE;
  }
}
