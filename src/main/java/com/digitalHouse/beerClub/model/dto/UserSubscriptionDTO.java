package com.digitalHouse.beerClub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSubscriptionDTO {

    @JsonProperty("user_id")
    private  Long userId;
    @JsonProperty("new_subscription_id")
    private Long newSubscriptionId;
}
