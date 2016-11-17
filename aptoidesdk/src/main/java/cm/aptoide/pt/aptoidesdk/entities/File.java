package cm.aptoide.pt.aptoidesdk.entities;

import cm.aptoide.pt.aptoidesdk.misc.SdkUtils;
import cm.aptoide.pt.model.v7.GetAppMeta;
import lombok.Data;

/**
 * Created by neuro on 03-11-2016.
 */
@Data public class File {
  private final String path;
  private final String alternativePath;
  private final long size;
  private final String md5sum;

  public static File from(GetAppMeta.GetAppMetaFile file) {

    String path = file.getPath() + getQueryString();
    String alternativePath = file.getPathAlt() + getQueryString();
    long size = file.getFilesize();
    String md5sum = file.getMd5sum();

    return new File(path, alternativePath, size, md5sum);
  }

  private static String getQueryString() {
    return SdkUtils.FileParameters.getDownloadQueryString();
  }
}
