package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ayprojects.helpinghands.AppConstants.ADDED_BY;
import static com.ayprojects.helpinghands.AppConstants.DEFAULT_NAME;
import static com.ayprojects.helpinghands.AppConstants.TYPE_OF_PLACE_CATEGORY;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class Validations {

    /*public static List<String> findMissingFieldsForViews(DhViews dhViews) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhViews == null) return missingFieldsList;
        if (Utility.isFieldEmpty(dhViews.getAddedBy())) missingFieldsList.add("AddedBy");
        if (Utility.isFieldEmpty(dhViews.getContentType())) missingFieldsList.add("ContentType");
        if (Utility.isFieldEmpty(dhViews.getContentId())) missingFieldsList.add("ContentId");
        return missingFieldsList;
    }*/

    public static List<String> findMissingFieldsForRequirements(DhRequirements dhRequirements) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhRequirements == null) return missingFieldsList;
        if (Utility.isFieldEmpty(dhRequirements.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhRequirements.getRequirementId()))
            missingFieldsList.add(AppConstants.REQUIREMENT_ID);
        if (dhRequirements.getAddressDetails() == null)
            missingFieldsList.add(AppConstants.ADDRESS_DETAILS);
        else {
            if (dhRequirements.getAddressDetails().getLat() == 0)
                missingFieldsList.add(AppConstants.LATTITUDE);
            if (dhRequirements.getAddressDetails().getLng() == 0)
                missingFieldsList.add(AppConstants.LONGITUDE);
            if (Utility.isFieldEmpty(dhRequirements.getAddressDetails().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }
        if (dhRequirements.getContactDetails() == null)
            missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhRequirements.getContactDetails().getMobile())) {
                missingFieldsList.add(AppConstants.KEY_MOBILE);
            }
            if (Utility.isFieldEmpty(dhRequirements.getContactDetails().getEmail())) {
                missingFieldsList.add(AppConstants.EMAIL);
            }
        }
        if (Utility.isFieldEmpty(dhRequirements.getRequirementTitle()))
            missingFieldsList.add(AppConstants.REQ_TITLE);
        if (Utility.isFieldEmpty(dhRequirements.getRequirementDesc()))
            missingFieldsList.add(AppConstants.REQ_DESC);
        return missingFieldsList;
    }

    public static List<String> findMissingFieldsForRatings(DhRatingAndComments dhRatingComments) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhRatingComments == null) return missingFieldsList;

        if (Utility.isFieldEmpty(dhRatingComments.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (dhRatingComments.getRating() <= 0) missingFieldsList.add(AppConstants.RATING);
        if (dhRatingComments.getContentType()==null)
            missingFieldsList.add(AppConstants.CONTENT_TYPE);
        if (Utility.isFieldEmpty(dhRatingComments.getContentId()))
            missingFieldsList.add(AppConstants.CONTENT_ID);
        return missingFieldsList;
    }

    public static List<String> findMissingFieldsForPlaces(DhPlace dhPlace) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhPlace == null) return missingFieldsList;
        if (Utility.isFieldEmpty(dhPlace.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);
        if (Utility.isFieldEmpty(dhPlace.getPlaceMainCategoryId()))
            missingFieldsList.add(AppConstants.PLACE_MAIN_CATEGORY_ID);
        if (Utility.isFieldEmpty(dhPlace.getPlaceCategoryName()))
            missingFieldsList.add(AppConstants.PLACE_CATEGORY_NAME);
        if (Utility.isFieldEmpty(dhPlace.getPlaceName()))
            missingFieldsList.add(AppConstants.PLACE_NAME);
        if (Utility.isFieldEmpty(dhPlace.getPlaceType()))
            missingFieldsList.add(AppConstants.PLACE_TYPE);

        if (dhPlace.getPlaceAvailablityDetails() == null)
            missingFieldsList.add(AppConstants.PLACE_AVAILABLITY_DETAILS);
        else {
            if (!dhPlace.getPlaceAvailablityDetails().isProvide24into7()) {
                if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getPlaceOpeningTime()))
                    missingFieldsList.add(AppConstants.PLACE_OPENING_TIME);
                if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getPlaceClosingTime()))
                    missingFieldsList.add(AppConstants.PLACE_CLOSING_TIME);
                if (!dhPlace.getPlaceAvailablityDetails().getHaveNoLunchHours()) {
                    if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getLunchStartTime()))
                        missingFieldsList.add(AppConstants.LUNCH_START_TIME);
                    if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getLunchEndTime()))
                        missingFieldsList.add(AppConstants.LUNCH_END_TIME);
                }
            }

            if (dhPlace.getPlaceAvailablityDetails().isHaveExchangeFacility()) {
                if (!dhPlace.getPlaceAvailablityDetails().isAnyTimeExchange()) {
                    if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getExchangeStartTime()))
                        missingFieldsList.add(AppConstants.EXCHANGE_START_TIME);
                    if (Utility.isFieldEmpty(dhPlace.getPlaceAvailablityDetails().getExchangeEndTime()))
                        missingFieldsList.add(AppConstants.EXCHANGE_END_TIME);
                }
            }
        }

        if (dhPlace.getPlaceAddress() == null) missingFieldsList.add(AppConstants.PLACE_ADDRESS);
        else {
            if (dhPlace.isAddressGenerated()) {
                if (dhPlace.getPlaceAddress().getLat() == 0)
                    missingFieldsList.add(AppConstants.LATTITUDE);
                if (dhPlace.getPlaceAddress().getLng() == 0)
                    missingFieldsList.add(AppConstants.LONGITUDE);
            }

            if (Utility.isFieldEmpty(dhPlace.getPlaceAddress().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }
        if (dhPlace.getPlaceContact() == null) missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile())) {
                missingFieldsList.add(AppConstants.KEY_MOBILE);
            }
        }
        if (dhPlace.getOwnerName() == null) missingFieldsList.add(AppConstants.OWNER_NAME);
        if (dhPlace.getProductDetails() == null || dhPlace.getProductDetails().size() <= 0)
            missingFieldsList.add(AppConstants.PRODUCT_DETAILS);

        return missingFieldsList;

    }

    public static List<String> findMissingFieldsForPosts(DhPromotions dhPromotions) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhPromotions == null) return missingFieldsList;
        if (Utility.isFieldEmpty(dhPromotions.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPromotions.getPromotionType()))
            missingFieldsList.add(AppConstants.PROMOTION_TYPE);
        if (Utility.isFieldEmpty(dhPromotions.getPromotionId())) missingFieldsList.add(AppConstants.PROMOTION_ID);


        if (dhPromotions.getPromotionType().matches(AppConstants.REGEX_BUSINESS_PROMOTION)) {
            if (Utility.isFieldEmpty(dhPromotions.getPlaceId())) {
                missingFieldsList.add(AppConstants.PLACE_ID);
            }
        }

        if (Utility.isFieldEmpty(dhPromotions.getFullAddress()))
            missingFieldsList.add(AppConstants.FULL_ADDRESS);

        if (dhPromotions.getContactDetails() == null)
            missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhPromotions.getContactDetails().getMobile())) {
                missingFieldsList.add(AppConstants.KEY_MOBILE);
            }
        }

        if (Utility.isFieldEmpty(dhPromotions.getPromotionTitle()))
            missingFieldsList.add(AppConstants.PROMOTION_TITLE);

        if (Utility.isFieldEmpty(dhPromotions.getPromotionDesc()))
            missingFieldsList.add(AppConstants.PROMOTION_DESC);

        if (Utility.isFieldEmpty(dhPromotions.getOfferStartTime()) && !Utility.isFieldEmpty(dhPromotions.getOfferEndTime())) {
            missingFieldsList.add(AppConstants.PROMOTION_OFFER_START_TIME);
        } else if (Utility.isFieldEmpty(dhPromotions.getOfferEndTime()) && !Utility.isFieldEmpty(dhPromotions.getOfferStartTime())) {
            missingFieldsList.add(AppConstants.PROMOTION_OFFER_END_TIME);
        }

        return missingFieldsList;
    }

    public static List<String> findMissingFieldsForMainPlaceCategory(DhPlaceCategories dhPlaceCategories) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhPlaceCategories == null) return missingFieldsList;

        if (Utility.isFieldEmpty(dhPlaceCategories.getTypeOfPlaceCategory())) {
            missingFieldsList.add(TYPE_OF_PLACE_CATEGORY);
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getAddedBy())) {
            missingFieldsList.add(ADDED_BY);
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getDefaultName())) {
            missingFieldsList.add(DEFAULT_NAME);
        }
        return missingFieldsList;
    }

    public static List<String> findMissingFieldsForSubPlaceCategory(PlaceSubCategories placeSubCategory, String mainPlaceCategoryId) {
        List<String> missingFieldsList = new ArrayList<>();
        if (placeSubCategory == null) return missingFieldsList;

        if (Utility.isFieldEmpty(mainPlaceCategoryId))
            missingFieldsList.add(AppConstants.PLACE_MAIN_CATEGORY_ID);

        if (Utility.isFieldEmpty(placeSubCategory.getDefaultName())) {
            missingFieldsList.add(DEFAULT_NAME);
        }
        return missingFieldsList;
    }

    public static List<String> findMissingFieldsForProducts(DhProduct dhProduct) {
        List<String> missingFieldsList = new ArrayList<>();
        if (dhProduct == null) return missingFieldsList;

        if (Utility.isFieldEmpty(dhProduct.getMainPlaceCategoryId()))
            missingFieldsList.add(AppConstants.PLACE_MAIN_CATEGORY_ID);

        if (dhProduct.getSubPlaceCategoryIds()==null || dhProduct.getSubPlaceCategoryIds().isEmpty()) {
            missingFieldsList.add(AppConstants.PLACE_SUB_CATEGORY_ID);
        }

        if (Utility.isFieldEmpty(dhProduct.getDefaultName())) {
            missingFieldsList.add(DEFAULT_NAME);
        }
        return missingFieldsList;
    }

        public static Response<DhUser> validateAddUser(String language, DhUser dhUserDetails, MongoTemplate mongoTemplate, UserDetailsServiceImpl userDetailsService) {
        if (dhUserDetails == null) {
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhUserDetails.getMobileNumber()))
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY), new ArrayList<>());

        try {
            UserDetailsDecorator userDetailsDecorator = userDetailsService.loadUserByUsername(dhUserDetails.getMobileNumber());
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED), new ArrayList<>());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("validateAddUser=" + e.getMessage());
        }

        if (!Utility.isFieldEmpty(dhUserDetails.getEmailId())) {
            Query queryFindUserByEmailId = new Query(Criteria.where(AppConstants.EMAIL).is(dhUserDetails.getEmailId()));
            DhUser userByEmailId = mongoTemplate.findOne(queryFindUserByEmailId, DhUser.class);
            if (userByEmailId != null) {
                return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMAIL_ALREADY_USED), new ArrayList<>());
            }
        }


        if (Utility.isFieldEmpty(dhUserDetails.getPassword())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhUserDetails.getProfileImgLow())) {
            dhUserDetails.setUserId(Utility.getUUID());
        } else if (Utility.isFieldEmpty(dhUserDetails.getUserId())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        }

        return new Response<>(true, 201, dhUserDetails.getFirstName() + " Sir", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUserDetails));
    }
}
