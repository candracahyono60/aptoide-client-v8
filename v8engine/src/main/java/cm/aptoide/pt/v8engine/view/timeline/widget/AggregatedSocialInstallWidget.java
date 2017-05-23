package cm.aptoide.pt.v8engine.view.timeline.widget;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import cm.aptoide.accountmanager.AptoideAccountManager;
import cm.aptoide.pt.dataprovider.util.CommentType;
import cm.aptoide.pt.dataprovider.ws.v7.BaseBody;
import cm.aptoide.pt.dataprovider.ws.v7.BodyInterceptor;
import cm.aptoide.pt.imageloader.ImageLoader;
import cm.aptoide.pt.model.v7.timeline.MinimalCard;
import cm.aptoide.pt.v8engine.R;
import cm.aptoide.pt.v8engine.V8Engine;
import cm.aptoide.pt.v8engine.analytics.Analytics;
import cm.aptoide.pt.v8engine.crashreports.CrashReport;
import cm.aptoide.pt.v8engine.view.timeline.LikeButtonView;
import cm.aptoide.pt.v8engine.view.timeline.displayable.AggregatedSocialInstallDisplayable;
import com.jakewharton.rxbinding.view.RxView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by jdandrade on 11/05/2017.
 */

public class AggregatedSocialInstallWidget extends CardWidget<AggregatedSocialInstallDisplayable> {
  private final LayoutInflater inflater;
  private final AptoideAccountManager accountManager;
  private final BodyInterceptor<BaseBody> bodyInterceptor;
  private TextView seeMore;
  private LinearLayout subCardsContainer;
  private ImageView headerAvatar1;
  private ImageView headerAvatar2;
  private TextView headerNames;
  private TextView headerTime;
  private ImageView appIcon;
  private TextView appName;
  private RatingBar ratingBar;
  private Button getAppButton;
  private CardView cardView;

  public AggregatedSocialInstallWidget(View itemView) {
    super(itemView);
    inflater = LayoutInflater.from(itemView.getContext());
    accountManager = ((V8Engine) getContext().getApplicationContext()).getAccountManager();
    bodyInterceptor = ((V8Engine) getContext().getApplicationContext()).getBaseBodyInterceptorV7();
  }

  @Override protected void assignViews(View itemView) {
    super.assignViews(itemView);
    subCardsContainer =
        (LinearLayout) itemView.findViewById(R.id.timeline_sub_minimal_card_container);
    seeMore = (TextView) itemView.findViewById(R.id.timeline_aggregated_see_more);
    headerAvatar1 = (ImageView) itemView.findViewById(R.id.card_header_avatar_1);
    headerAvatar2 = (ImageView) itemView.findViewById(R.id.card_header_avatar_2);
    headerNames = (TextView) itemView.findViewById(R.id.card_title);
    headerTime = (TextView) itemView.findViewById(R.id.card_date);
    appIcon =
        (ImageView) itemView.findViewById(R.id.displayable_social_timeline_recommendation_icon);
    appName = (TextView) itemView.findViewById(
        R.id.displayable_social_timeline_recommendation_similar_apps);
    ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
    getAppButton = (Button) itemView.findViewById(
        R.id.displayable_social_timeline_recommendation_get_app_button);
    cardView = (CardView) itemView.findViewById(R.id.card);
  }

  @Override public void bindView(AggregatedSocialInstallDisplayable displayable) {
    super.bindView(displayable);

    ImageLoader.with(getContext())
        .loadWithShadowCircleTransform(displayable.getSharers()
            .get(0)
            .getUser()
            .getAvatar(), headerAvatar1);
    ImageLoader.with(getContext())
        .loadWithShadowCircleTransform(displayable.getSharers()
            .get(1)
            .getUser()
            .getAvatar(), headerAvatar2);
    headerNames.setText(displayable.getCardHeaderNames());
    headerTime.setText(displayable.getTimeSinceLastUpdate(getContext()));
    ImageLoader.with(getContext())
        .load(displayable.getAppIcon(), appIcon);
    appName.setText(displayable.getAppName());
    ratingBar.setRating(displayable.getAppRatingAverage());
    setCardViewMargin(displayable, cardView);
    showSeeMoreAction(displayable);
    showSubCards(displayable);

    RxView.clicks(getAppButton)
        .subscribe(view -> {
          knockWithSixpackCredentials(displayable.getAbTestingURL());

          Analytics.AppsTimeline.clickOnCard(AggregatedSocialInstallDisplayable.CARD_TYPE_NAME,
              displayable.getPackageName(), Analytics.AppsTimeline.BLANK,
              Analytics.AppsTimeline.BLANK, Analytics.AppsTimeline.OPEN_APP_VIEW);
          displayable.sendOpenAppEvent();
          getFragmentNavigator().navigateTo(V8Engine.getFragmentProvider()
              .newAppViewFragment(displayable.getAppId(), displayable.getPackageName()));
        }, throwable -> CrashReport.getInstance()
            .log(throwable));
  }

  @Override String getCardTypeName() {
    return AggregatedSocialInstallDisplayable.CARD_TYPE_NAME;
  }

  private void showSubCards(AggregatedSocialInstallDisplayable displayable) {
    subCardsContainer.removeAllViews();
    int i = 1;
    for (MinimalCard minimalCard : displayable.getMinimalCardList()) {
      if (i > 2) {
        break;
      }
      View subCardView =
          inflater.inflate(R.layout.timeline_sub_minimal_card, subCardsContainer, false);
      ImageView minimalCardHeaderMainAvatar = (ImageView) subCardView.findViewById(R.id.card_image);
      TextView minimalCardHeaderMainName = (TextView) subCardView.findViewById(R.id.card_title);
      ImageView minimalCardHeaderSecondaryAvatar =
          (ImageView) subCardView.findViewById(R.id.card_user_avatar);
      TextView minimalCardHeaderSecondaryName =
          (TextView) subCardView.findViewById(R.id.card_subtitle);
      TextView cardHeaderTimestamp = (TextView) subCardView.findViewById(R.id.card_date);
      LikeButtonView likeSubCardButton =
          (LikeButtonView) subCardView.findViewById(R.id.social_like_button);
      LinearLayout likeLayout = (LinearLayout) subCardView.findViewById(R.id.social_like);
      TextView comment = (TextView) subCardView.findViewById(R.id.social_comment);
      ImageLoader.with(getContext())
          .loadWithShadowCircleTransform(minimalCard.getSharers()
              .get(0)
              .getUser()
              .getAvatar(), minimalCardHeaderMainAvatar);

      minimalCardHeaderMainName.setText(minimalCard.getSharers()
          .get(0)
          .getUser()
          .getName());

      ImageLoader.with(getContext())
          .loadWithShadowCircleTransform(minimalCard.getSharers()
              .get(0)
              .getStore()
              .getAvatar(), minimalCardHeaderSecondaryAvatar);

      minimalCardHeaderSecondaryName.setText(minimalCard.getSharers()
          .get(0)
          .getStore()
          .getName());

      cardHeaderTimestamp.setText(
          displayable.getTimeSinceLastUpdate(getContext(), minimalCard.getDate()));

      compositeSubscription.add(RxView.clicks(likeLayout)
          .subscribe(click -> {
            if (!hasSocialPermissions(Analytics.Account.AccountOrigins.LIKE_CARD)) return;
            likeSubCardButton.performClick();
          }, throwable -> CrashReport.getInstance()
              .log(throwable)));

      compositeSubscription.add(RxView.clicks(likeSubCardButton)
          .flatMap(__ -> accountManager.accountStatus()
              .first()
              .toSingle()
              .toObservable())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(account -> likeCard(displayable, minimalCard.getCardId(), 1),
              err -> CrashReport.getInstance()
                  .log(err)));

      compositeSubscription.add(accountManager.accountStatus()
          .subscribe());
      likeLayout.setVisibility(View.VISIBLE);
      comment.setVisibility(View.VISIBLE);

      compositeSubscription.add(RxView.clicks(comment)
          .flatMap(aVoid -> Observable.fromCallable(() -> {
            final String elementId = displayable.getTimelineCard()
                .getCardId();
            Fragment fragment = V8Engine.getFragmentProvider()
                .newCommentGridRecyclerFragmentWithCommentDialogOpen(CommentType.TIMELINE,
                    elementId);
            getFragmentNavigator().navigateTo(fragment);
            return null;
          }))
          .subscribe(aVoid -> knockWithSixpackCredentials(displayable.getAbTestingURL()),
              err -> CrashReport.getInstance()
                  .log(err)));

      subCardsContainer.addView(subCardView);
      i++;
    }
  }

  private void showSeeMoreAction(AggregatedSocialInstallDisplayable displayable) {
    if (displayable.getMinimalCardList()
        .size() > 2) {
      seeMore.setVisibility(View.VISIBLE);
    } else {
      seeMore.setVisibility(View.GONE);
    }
  }
}
