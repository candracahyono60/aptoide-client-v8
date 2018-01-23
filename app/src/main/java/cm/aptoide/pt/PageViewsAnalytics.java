package cm.aptoide.pt;

import cm.aptoide.pt.analytics.NavigationTracker;
import cm.aptoide.pt.analytics.analytics.AnalyticsManager;
import java.util.HashMap;
import java.util.Map;

public class PageViewsAnalytics {

  public final static String PAGE_VIEW_EVENT = "Page_View";
  private final AnalyticsManager analyticsManager;
  private final NavigationTracker navigationTracker;

  public PageViewsAnalytics(AnalyticsManager analyticsManager,
      NavigationTracker navigationTracker) {
    this.analyticsManager = analyticsManager;
    this.navigationTracker = navigationTracker;
  }

  public void sendPageViewedEvent() {
    analyticsManager.logEvent(createEventMap(navigationTracker.getCurrentViewName()),
        PAGE_VIEW_EVENT, AnalyticsManager.Action.CLICK,
        navigationTracker.getViewName(true, "PageView"));
  }

  private Map<String, Object> createEventMap(String currentViewName) {
    Map<String, Object> map = new HashMap<>();
    map.put("fragment", currentViewName);
    return map;
  }
}
