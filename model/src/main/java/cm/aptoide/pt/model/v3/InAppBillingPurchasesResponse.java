/*
 * Copyright (c) 2016.
 * Modified by Marcelo Benites on 12/08/2016.
 */

package cm.aptoide.pt.model.v3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by marcelobenites on 8/12/16.
 */
@Data @EqualsAndHashCode(callSuper = true) public class InAppBillingPurchasesResponse
    extends ProductPaymentResponse {

  @JsonProperty("publisher_response") private PurchaseInformation purchaseInformation;

  @Data public static class PurchaseInformation {

    @JsonProperty("INAPP_PURCHASE_ITEM_LIST") private List<String> skuList;

    @JsonProperty("INAPP_PURCHASE_DATA_LIST") private List<InAppBillingPurchase> purchaseList;

    @JsonProperty("INAAP_DATA_SIGNATURE_LIST") private List<String> signatureList;
  }

  // Order must be kept here because the resulting JSON String is going to be encrypted using a
  // public key and the resulting signature must match a signature generated on server side.
  @JsonPropertyOrder({
      "orderId", "packageName", "productId", "purchaseTime", "purchaseToken", "purchaseState",
      "developerPayload"
  }) @JsonInclude(JsonInclude.Include.NON_NULL) @Data public static class InAppBillingPurchase {
    @JsonProperty("orderId") private String orderId;
    @JsonProperty("packageName") private String packageName;
    @JsonProperty("productId") private String productId;
    @JsonProperty("purchaseTime") private long purchaseTime;
    @JsonProperty("purchaseToken") private String purchaseToken;
    @JsonProperty("purchaseState") private int purchaseState;
    @JsonProperty("developerPayload") private String developerPayload;
  }
}
