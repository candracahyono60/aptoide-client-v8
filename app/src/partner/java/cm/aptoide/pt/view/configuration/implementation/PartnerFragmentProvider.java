package cm.aptoide.pt.view.configuration.implementation;

import android.support.v4.app.Fragment;
import cm.aptoide.pt.PartnerApplication;
import cm.aptoide.pt.addressbook.data.Contact;
import cm.aptoide.pt.database.realm.MinimalAd;
import cm.aptoide.pt.dataprovider.model.v7.Event;
import cm.aptoide.pt.dataprovider.util.CommentType;
import cm.aptoide.pt.dataprovider.ws.v7.store.StoreContext;
import cm.aptoide.pt.presenter.InviteFriendsContract;
//import cm.aptoide.pt.social.view.TimelineFragment;
import cm.aptoide.pt.spotandshare.view.SpotSharePreviewFragment;
import cm.aptoide.pt.timeline.view.SocialFragment;
import cm.aptoide.pt.view.addressbook.AddressBookFragment;
import cm.aptoide.pt.view.addressbook.InviteFriendsFragment;
import cm.aptoide.pt.view.addressbook.PhoneInputFragment;
import cm.aptoide.pt.view.addressbook.SyncResultFragment;
import cm.aptoide.pt.view.addressbook.ThankYouConnectingFragment;
import cm.aptoide.pt.view.app.AppViewFragment;
import cm.aptoide.pt.view.app.ListAppsFragment;
import cm.aptoide.pt.view.app.OtherVersionsFragment;
import cm.aptoide.pt.view.app.screenshots.ScreenshotsViewerFragment;
import cm.aptoide.pt.view.comments.CommentListFragment;
import cm.aptoide.pt.view.configuration.FragmentProvider;
import cm.aptoide.pt.view.downloads.DownloadsFragment;
import cm.aptoide.pt.view.downloads.scheduled.ScheduledDownloadsFragment;
import cm.aptoide.pt.view.feedback.SendFeedbackFragment;
import cm.aptoide.pt.view.fragment.DescriptionFragment;
import cm.aptoide.pt.view.reviews.LatestReviewsFragment;
import cm.aptoide.pt.view.reviews.ListReviewsFragment;
import cm.aptoide.pt.view.reviews.RateAndReviewsFragment;
import cm.aptoide.pt.view.search.SearchFragment;
import cm.aptoide.pt.view.search.SearchPagerTabFragment;
import cm.aptoide.pt.view.settings.SettingsFragment;
import cm.aptoide.pt.view.store.FragmentTopStores;
import cm.aptoide.pt.view.store.GetStoreFragment;
import cm.aptoide.pt.view.store.GetStoreWidgetsFragment;
import cm.aptoide.pt.view.store.ListStoresFragment;
import cm.aptoide.pt.view.store.StoreFragment;
import cm.aptoide.pt.view.store.StoreTabGridRecyclerFragment;
import cm.aptoide.pt.view.store.ads.GetAdsFragment;
import cm.aptoide.pt.view.store.home.HomeFragment;
import cm.aptoide.pt.view.store.my.MyStoresFragment;
import cm.aptoide.pt.view.store.my.MyStoresSubscribedFragment;
import cm.aptoide.pt.view.store.recommended.RecommendedStoresFragment;
import cm.aptoide.pt.view.updates.UpdatesFragment;
import cm.aptoide.pt.view.updates.excluded.ExcludedUpdatesFragment;
import cm.aptoide.pt.view.updates.rollback.RollbackFragment;
import java.util.ArrayList;
import java.util.List;

public class PartnerFragmentProvider implements FragmentProvider {

  private final PartnerApplication aptoideApplication;

  public PartnerFragmentProvider(PartnerApplication aptoideApplication) {
    this.aptoideApplication = aptoideApplication;
  }

  @Override public Fragment newScreenshotsViewerFragment(ArrayList<String> uris, int currentItem) {
    return ScreenshotsViewerFragment.newInstance(uris, currentItem);
  }

  @Override public Fragment newSendFeedbackFragment(String screenshotFilePath) {
    return SendFeedbackFragment.newInstance(screenshotFilePath);
  }

  @Override public Fragment newStoreFragment(String storeName, String storeTheme) {
    return StoreFragment.newInstance(storeName, aptoideApplication.getDefaultTheme());
  }

  @Override public Fragment newStoreFragment(String storeName, String storeTheme,
      StoreFragment.OpenType openType) {
    return StoreFragment.newInstance(storeName, aptoideApplication.getDefaultTheme(), openType);
  }

  @Override
  public Fragment newStoreFragment(String storeName, String storeTheme, Event.Name defaultTab,
      StoreFragment.OpenType openType) {
    return StoreFragment.newInstance(storeName, aptoideApplication.getDefaultTheme(), defaultTab,
        openType);
  }

  @Override public Fragment newStoreFragment(long userId, String storeTheme, Event.Name defaultTab,
      StoreFragment.OpenType openType) {
    return StoreFragment.newInstance(userId, aptoideApplication.getDefaultTheme(), defaultTab,
        openType);
  }

  @Override public Fragment newStoreFragment(long userId, String storeTheme,
      StoreFragment.OpenType openType) {
    return StoreFragment.newInstance(userId, aptoideApplication.getDefaultTheme(), openType);
  }

  @Override
  public Fragment newHomeFragment(String storeName, StoreContext storeContext, String storeTheme) {
    return HomeFragment.newInstance(storeName, storeContext, aptoideApplication.getDefaultTheme());
  }

  @Override public Fragment newSearchFragment(String query) {
    if (aptoideApplication.getBootConfig()
        .getPartner()
        .getSwitches()
        .getOptions()
        .getMultistore()
        .isSearch()) {
      return SearchFragment.newInstance(query);
    } else {
      return SearchFragment.newInstance(query, aptoideApplication.getDefaultStore());
    }
  }

  @Override public Fragment newSearchFragment(String query, boolean onlyTrustedApps) {
    if (aptoideApplication.getBootConfig()
        .getPartner()
        .getSwitches()
        .getOptions()
        .getMultistore()
        .isSearch()) {
      return SearchFragment.newInstance(query, onlyTrustedApps);
    } else {
      return SearchFragment.newInstance(query, onlyTrustedApps,
          aptoideApplication.getDefaultStore());
    }
  }

  @Override public Fragment newSearchFragment(String query, String storeName) {
    return SearchFragment.newInstance(query, storeName);
  }

  @Override public Fragment newAppViewFragment(String packageName, String storeName,
      AppViewFragment.OpenType openType) {
    return AppViewFragment.newInstance(packageName, storeName, openType);
  }

  @Override public Fragment newAppViewFragment(String md5) {
    return AppViewFragment.newInstance(md5);
  }

  @Override public Fragment newAppViewFragment(long appId, String packageName,
      AppViewFragment.OpenType openType) {
    return AppViewFragment.newInstance(appId, packageName, openType);
  }

  @Override public Fragment newAppViewFragment(long appId, String packageName) {
    return AppViewFragment.newInstance(appId, packageName, aptoideApplication.getDefaultTheme(),
        aptoideApplication.getDefaultStore());
  }

  @Override public Fragment newAppViewFragment(long appId, String packageName, String storeTheme,
      String storeName) {
    return AppViewFragment.newInstance(appId, packageName, aptoideApplication.getDefaultTheme(),
        storeName);
  }

  @Override public Fragment newAppViewFragment(MinimalAd minimalAd) {
    return AppViewFragment.newInstance(minimalAd, aptoideApplication.getDefaultTheme());
  }

  @Override
  public Fragment newAppViewFragment(String packageName, AppViewFragment.OpenType openType) {
    return AppViewFragment.newInstance(packageName, openType);
  }

  @Override public Fragment newFragmentTopStores() {
    return FragmentTopStores.newInstance();
  }

  @Override public Fragment newUpdatesFragment() {
    return UpdatesFragment.newInstance();
  }

  @Override public Fragment newLatestReviewsFragment(long storeId) {
    return LatestReviewsFragment.newInstance(storeId);
  }

  @Override
  public Fragment newStoreTabGridRecyclerFragment(Event event, String storeTheme, String tag,
      StoreContext storeContext) {
    return StoreTabGridRecyclerFragment.newInstance(event, aptoideApplication.getDefaultTheme(),
        tag, storeContext);
  }

  @Override
  public Fragment newStoreTabGridRecyclerFragment(Event event, String title, String storeTheme,
      String tag, StoreContext storeContext) {
    return StoreTabGridRecyclerFragment.newInstance(event, title,
        aptoideApplication.getDefaultTheme(), tag, storeContext);
  }

  @Override public Fragment newListAppsFragment() {
    return new ListAppsFragment();
  }

  @Override public Fragment newGetStoreFragment() {
    return new GetStoreFragment();
  }

  @Override public Fragment newMyStoresSubscribedFragment() {
    return new MyStoresSubscribedFragment();
  }

  @Override public Fragment newMyStoresFragment() {
    return new MyStoresFragment();
  }

  @Override public Fragment newGetStoreWidgetsFragment() {
    return new GetStoreWidgetsFragment();
  }

  @Override public Fragment newListReviewsFragment() {
    return new ListReviewsFragment();
  }

  @Override public Fragment newGetAdsFragment() {
    return new GetAdsFragment();
  }

  @Override public Fragment newListStoresFragment() {
    return new ListStoresFragment();
  }

  //@Override public Fragment newAppsTimelineFragment(String action, Long userId, Long storeId,
  //    StoreContext storeContext) {
  //  return TimelineFragment.newInstance(action, userId, storeId, storeContext);
  //}

  @Override
  public Fragment newSubscribedStoresFragment(Event event, String storeTheme, String tag) {
    return MyStoresFragment.newInstance(event, aptoideApplication.getDefaultTheme(), tag);
  }

  @Override public Fragment newSearchPagerTabFragment(String query, boolean subscribedStores,
      boolean hasMultipleFragments) {
    return SearchPagerTabFragment.newInstance(query, subscribedStores, hasMultipleFragments);
  }

  @Override public Fragment newSearchPagerTabFragment(String query, String storeName) {
    return SearchPagerTabFragment.newInstance(query, storeName);
  }

  @Override public Fragment newDownloadsFragment() {
    return DownloadsFragment.newInstance();
  }

  @Override
  public Fragment newOtherVersionsFragment(String appName, String appImgUrl, String appPackage) {
    if (aptoideApplication.getBootConfig()
        .getPartner()
        .getSwitches()
        .getOptions()
        .getMultistore()
        .isSearch()) {
      return OtherVersionsFragment.newInstance(appName, appImgUrl, appPackage);
    } else {
      return OtherVersionsFragment.newInstance(appName, appImgUrl, appPackage,
          aptoideApplication.getDefaultStore());
    }
  }

  @Override
  public Fragment newOtherVersionsFragment(String appName, String appImgUrl, String appPackage,
      String storeName) {
    return OtherVersionsFragment.newInstance(appName, appImgUrl, appPackage, storeName);
  }

  @Override public Fragment newRollbackFragment() {
    return RollbackFragment.newInstance();
  }

  @Override public Fragment newExcludedUpdatesFragment() {
    return ExcludedUpdatesFragment.newInstance();
  }

  @Override public Fragment newScheduledDownloadsFragment() {
    return ScheduledDownloadsFragment.newInstance();
  }

  @Override
  public Fragment newScheduledDownloadsFragment(ScheduledDownloadsFragment.OpenMode openMode) {
    return ScheduledDownloadsFragment.newInstance(openMode);
  }

  @Override public Fragment newRateAndReviewsFragment(long appId, String appName, String storeName,
      String packageName, String storeTheme) {
    return RateAndReviewsFragment.newInstance(appId, appName, storeName, packageName,
        aptoideApplication.getDefaultTheme());
  }

  @Override public Fragment newRateAndReviewsFragment(long appId, String appName, String storeName,
      String packageName, long reviewId) {
    return RateAndReviewsFragment.newInstance(appId, appName, storeName, packageName, reviewId);
  }

  @Override
  public Fragment newDescriptionFragment(String appName, String description, String storeTheme) {
    return DescriptionFragment.newInstance(appName, description,
        aptoideApplication.getDefaultTheme());
  }

  @Override public Fragment newSocialFragment(String socialUrl, String pageTitle) {
    return SocialFragment.newInstance(socialUrl, pageTitle);
  }

  @Override public Fragment newSettingsFragment() {
    return SettingsFragment.newInstance();
  }

  //@Override public Fragment newTimeLineFollowersUsingUserIdFragment(Long userId, String storeTheme,
  //    String title) {
  //  return TimeLineFollowersFragment.newInstanceUsingUser(userId,
  //      aptoideApplication.getDefaultTheme(), title);
  //}
//
  //@Override public Fragment newTimeLineFollowingFragmentUsingUserId(Long id, String storeTheme,
  //    String title) {
  //  return TimeLineFollowingFragment.newInstanceUsingUserId(id,
  //      aptoideApplication.getDefaultTheme(), title);
  //}
//
  //@Override
  //public Fragment newTimeLineFollowersUsingStoreIdFragment(Long storeId, String storeTheme,
  //    String title) {
  //  return TimeLineFollowersFragment.newInstanceUsingStore(storeId,
  //      aptoideApplication.getDefaultTheme(), title);
  //}
//
  //@Override public Fragment newTimeLineFollowingFragmentUsingStoreId(Long id, String storeTheme,
  //    String title) {
  //  return TimeLineFollowingFragment.newInstanceUsingStoreId(id,
  //      aptoideApplication.getDefaultTheme(), title);
  //}
//
  //@Override
  //public Fragment newTimeLineLikesFragment(String cardUid, long numberOfLikes, String storeTheme,
  //    String title) {
  //  return TimeLineLikesFragment.newInstance(aptoideApplication.getDefaultTheme(), cardUid,
  //      numberOfLikes, title);
  //}

  @Override
  public Fragment newCommentGridRecyclerFragment(CommentType commentType, String elementId) {
    return CommentListFragment.newInstance(commentType, elementId);
  }

  @Override public Fragment newCommentGridRecyclerFragmentUrl(CommentType commentType, String url,
      String storeAnalyticsAction) {
    return CommentListFragment.newInstanceUrl(commentType, url, storeAnalyticsAction);
  }

  @Override
  public Fragment newCommentGridRecyclerFragmentWithCommentDialogOpen(CommentType commentType,
      String elementId) {
    return CommentListFragment.newInstanceWithCommentDialogOpen(commentType, elementId);
  }

  @Override public Fragment newAddressBookFragment() {
    return AddressBookFragment.newInstance();
  }

  @Override public Fragment newSyncSuccessFragment(List<Contact> contacts, String tag) {
    return SyncResultFragment.newInstance(contacts, tag);
  }

  @Override public Fragment newPhoneInputFragment(String tag) {
    return PhoneInputFragment.newInstance(tag);
  }

  @Override public Fragment newInviteFriendsFragment(InviteFriendsContract.View.OpenMode openMode,
      String tag) {
    return InviteFriendsFragment.newInstance(openMode, tag);
  }

  @Override public Fragment newSpotShareFragment(boolean showToolbar) {
    return SpotSharePreviewFragment.newInstance(showToolbar);
  }

  @Override public Fragment newThankYouConnectingFragment(String tag) {
    return ThankYouConnectingFragment.newInstance(tag);
  }

  //@Override public Fragment newTimeLineFollowersFragment(String storeTheme, String title) {
  //  return TimeLineFollowersFragment.newInstanceUsingUser(aptoideApplication.getDefaultTheme(),
  //      title);
  //}

  @Override public Fragment newRecommendedStoresFragment() {
    return new RecommendedStoresFragment();
  }
}
