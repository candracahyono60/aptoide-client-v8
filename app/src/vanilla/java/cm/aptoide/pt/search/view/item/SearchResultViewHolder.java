package cm.aptoide.pt.search.view.item;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cm.aptoide.pt.R;
import cm.aptoide.pt.dataprovider.model.v7.Malware;
import cm.aptoide.pt.networking.image.ImageLoader;
import cm.aptoide.pt.search.model.SearchAppResult;
import cm.aptoide.pt.store.StoreTheme;
import cm.aptoide.pt.utils.AptoideUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.Date;
import rx.subscriptions.CompositeSubscription;

public class SearchResultViewHolder extends SearchResultItemView<SearchAppResult> {

  public static final int LAYOUT = R.layout.new_search_app_row;
  private final PublishRelay<SearchAppResult> onItemViewClick;
  private final PublishRelay<Pair<SearchAppResult, android.view.View>> onOpenPopupMenuClick;

  private TextView nameTextView;
  private ImageView iconImageView;
  private TextView downloadsTextView;
  private TextView ratingBar;
  private TextView timeTextView;
  private TextView storeTextView;
  private ImageView icTrustedImageView;
  private ImageView overflowImageView;
  private View bottomView;
  private SearchAppResult searchApp;
  private CompositeSubscription subscriptions;

  public SearchResultViewHolder(View itemView, PublishRelay<SearchAppResult> onItemViewClick,
      PublishRelay<Pair<SearchAppResult, android.view.View>> onOpenPopupMenuClick) {
    super(itemView);
    subscriptions = new CompositeSubscription();
    this.onItemViewClick = onItemViewClick;
    this.onOpenPopupMenuClick = onOpenPopupMenuClick;
    bindViews(itemView);
  }

  @Override public void setup(SearchAppResult searchApp) {
    this.searchApp = searchApp;
    setAppName();
    setDownloadCount();
    setAverageValue();
    //setDateModified();
    //setBackground();
    setStoreName();
    setIconView();
    //setTrustedBadge();
    //setOverflowMenu();
  }

  public void prepareToRecycle() {
    if (subscriptions.hasSubscriptions() && !subscriptions.isUnsubscribed()) {
      subscriptions.unsubscribe();
    }
  }

  private void setOverflowMenu() {
    overflowImageView.setVisibility(View.VISIBLE);
  }

  private void setTrustedBadge() {
    if (Malware.Rank.TRUSTED.ordinal() == searchApp.getRank()) {
      icTrustedImageView.setVisibility(View.VISIBLE);
    } else {
      icTrustedImageView.setVisibility(View.GONE);
    }
  }

  private void setIconView() {
    ImageLoader.with(iconImageView.getContext())
        .load(searchApp.getIcon(), iconImageView);
  }

  private void setStoreName() {
    storeTextView.setText(searchApp.getStoreName());
  }

  private void setBackground() {
    final Resources resources = itemView.getResources();
    final StoreTheme theme = StoreTheme.get(searchApp.getStoreTheme());
    Drawable background = bottomView.getBackground();

    if (background instanceof ShapeDrawable) {
      ((ShapeDrawable) background).getPaint()
          .setColor(resources.getColor(theme.getPrimaryColor()));
    } else if (background instanceof GradientDrawable) {
      ((GradientDrawable) background).setColor(resources.getColor(theme.getPrimaryColor()));
    }

    background = storeTextView.getBackground();
    if (background instanceof ShapeDrawable) {
      ((ShapeDrawable) background).getPaint()
          .setColor(resources.getColor(theme.getPrimaryColor()));
    } else if (background instanceof GradientDrawable) {
      ((GradientDrawable) background).setColor(resources.getColor(theme.getPrimaryColor()));
    }
  }

  private void setDateModified() {
    final Date modified = new Date(searchApp.getModifiedDate());
    final Resources resources = itemView.getResources();
    final Context context = itemView.getContext();
    String timeSinceUpdate = AptoideUtils.DateTimeU.getInstance(context)
        .getTimeDiffAll(context, modified.getTime(), resources);
    if (timeSinceUpdate != null && !timeSinceUpdate.equals("")) {
      timeTextView.setText(timeSinceUpdate);
    }
  }

  private void setAverageValue() {
    float avg = searchApp.getAverageRating();
    if (avg <= 0) {
      ratingBar.setText("- -");
    } else {
      ratingBar.setVisibility(View.VISIBLE);
      ratingBar.setText(Float.toString(avg));
    }
  }

  private void setDownloadCount() {
    String downloadNumber =
        String.format("%s %s", AptoideUtils.StringU.withSuffix(searchApp.getTotalDownloads()),
            bottomView.getContext()
                .getString(R.string.downloads));
    downloadsTextView.setText(downloadNumber);
  }

  private void setAppName() {
    nameTextView.setText(searchApp.getAppName());
  }

  private void bindViews(View itemView) {
    nameTextView = (TextView) itemView.findViewById(R.id.app_name);
    iconImageView = (ImageView) itemView.findViewById(R.id.app_icon);
    downloadsTextView = (TextView) itemView.findViewById(R.id.downloads);
    ratingBar = (TextView) itemView.findViewById(R.id.rating);
    //timeTextView = (TextView) itemView.findViewById(R.id.search_time);
    storeTextView = (TextView) itemView.findViewById(R.id.store_name);
    //icTrustedImageView = (ImageView) itemView.findViewById(R.id.ic_trusted_search);
    bottomView = itemView;
    //overflowImageView = (ImageView) itemView.findViewById(R.id.overflow);

    subscriptions.add(RxView.clicks(itemView)
        .map(__ -> searchApp)
        .subscribe(data -> onItemViewClick.call(data)));

    //subscriptions.add(RxView.clicks(overflowImageView)
    //    .map(__ -> new Pair<>(searchApp, (View) overflowImageView))
    //    .subscribe(data -> onOpenPopupMenuClick.call(data)));
  }
}
