package com.clarigo.propertyfinderagent.retrofitHelper;

import com.clarigo.propertyfinderagent.dtos.AGENT_STATUS_DTO;
import com.clarigo.propertyfinderagent.dtos.CANCLE_REQUEST_DTO;
import com.clarigo.propertyfinderagent.dtos.LOGOUT_DTO;
import com.clarigo.propertyfinderagent.dtos.REFRESH_AGENT_STATUS_DTO;
import com.clarigo.propertyfinderagent.dtos.SIGNUP_DTO;
import com.clarigo.propertyfinderagent.dtos.SaveagentvehicleModel;
import com.clarigo.propertyfinderagent.dtos.USER_DETAILS_DTO;
import com.clarigo.propertyfinderagent.dtos.USER_LOGIN_DTO;
import com.clarigo.propertyfinderagent.dtos.UpdateLocationDTO;
import com.clarigo.propertyfinderagent.dtos.UpdateProfileModel;
import com.clarigo.propertyfinderagent.dtos.history_dto.HISTORY_DTO;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.PROPERTY_MAIN_DTO;
import com.clarigo.propertyfinderagent.dtos.request_data.ACCEPT_REQUEST_DTO;
import com.clarigo.propertyfinderagent.dtos.request_data.PICKUPUSERDTO;
import com.clarigo.propertyfinderagent.dtos.request_data.REQUEST_DTO;
import com.clarigo.propertyfinderagent.dtos.user_location_dto.USER_LOCATION_DTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @Multipart
    @POST("agentsignup")
    Call<SIGNUP_DTO> signup_responce(@Part MultipartBody.Part vName,
                                     @Part MultipartBody.Part vEmail,
                                     @Part MultipartBody.Part vMobile,
                                     @Part MultipartBody.Part vPassword,
                                     @Part MultipartBody.Part vConfirmPassword,
                                     @Part MultipartBody.Part vRegis_MLS,
                                     @Part MultipartBody.Part vAssociation_of_Realtors,
                                     @Part MultipartBody.Part veKey_Access,
                                     @Part MultipartBody.Part vDRE,
                                     @Part MultipartBody.Part vReal_Licence,
                                     @Part MultipartBody.Part vDriver_Licence);

    @FormUrlEncoded
    @POST("get_single_agentdetails")
    Call<USER_DETAILS_DTO> userDetails_api(@Field("agent_id") String vUserId);

    @GET("logout")
    Call<LOGOUT_DTO> logout_api();

    @FormUrlEncoded
    @POST("login_agent")
    Call<USER_LOGIN_DTO> login_api(@Field("user_email") String vMobile_Number,
                                   @Field("password") String vPassword,
                                   @Field("device_token") String vDevice_Token);

    @FormUrlEncoded
    @POST("save_on_off_status")
    Call<AGENT_STATUS_DTO> agent_current_status(@Field("agent_id") String vUserId,
                                                @Field("status") int vStatus);

    @FormUrlEncoded
    @POST("get_on_off_status")
    Call<REFRESH_AGENT_STATUS_DTO> refresh_agent_status(@Field("agent_id") String vUserId);

    @FormUrlEncoded
    @POST("save_agent_lat_long")
    Call<UpdateLocationDTO> update_lat_lng_server(@Field("agent_id") String vAgent_id, @Field("lat") double vLat, @Field("lng") double vLng);

//    @Multipart
//    @POST("update_user_details")
//    Call<PROFILE_UPDATE_DTO> profile_update_api(@Part MultipartBody.Part vName,
//                                                @Part MultipartBody.Part vAddress,
//                                                @Part MultipartBody.Part vEmergency_contact,
//                                                @Part MultipartBody.Part mFileTemp,
//                                                @Part MultipartBody.Part vUserId);


    @GET("properties?")
    Call<List<PROPERTY_MAIN_DTO>> getproperty(@Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("get_request_notification")
    Call<REQUEST_DTO> get_request_notification(@Field("agent_id") String vAgent_Id);

    @FormUrlEncoded
    @POST("get_user_lat_lng")
    Call<USER_LOCATION_DTO> get_user_location_api(@Field("user_id") String vUser_ID);


    @FormUrlEncoded
    @POST("request_accept")
    Call<ACCEPT_REQUEST_DTO> accept_request_api(@Field("req_id") String vReq_Id,
                                                @Field("status") String vStatus
            , @Field("acceptdate") String vAccept_date
            , @Field("timezone") String vTime_zone
            , @Field("agent_address") String vAgent_address);


    @FormUrlEncoded
    @POST("request_reject")
    Call<CANCLE_REQUEST_DTO> cancle_request_api(@Field("req_id") String vReq_idt,
                                                @Field("reason") String vReason,
                                                @Field("status") int vStatus,
                                                @Field("type") String vType,
                                                @Field("cancel_date") String vCancleDate,
                                                @Field("address") String vAddress,
                                                @Field("timezone") String vTimezone);

    //////4=arrived,5=user_pickup,6=arrived at property ,7=ready for dropoff, 8= drop off, 9= completed
    @FormUrlEncoded
    @POST("request_arrived")
    Call<PICKUPUSERDTO> request_arrived(@Field("req_id") String vReq_idt,
                                        @Field("status") int vStatus,
                                        @Field("vdate") String vPickupdate,
                                        @Field("timezone") String vTimezone);

    @FormUrlEncoded
    @POST("request_pickuped")
    Call<PICKUPUSERDTO> request_pickedup(@Field("req_id") String vReq_idt,
                                         @Field("status") int vStatus,
                                         @Field("vdate") String vPickupdate,
                                         @Field("timezone") String vTimezone);

    @FormUrlEncoded
    @POST("request_arrived_property")
    Call<PICKUPUSERDTO> request_arrived_property(@Field("req_id") String vReq_idt,
                                                 @Field("status") int vStatus,
                                                 @Field("vdate") String vPickupdate,
                                                 @Field("timezone") String vTimezone);

    @FormUrlEncoded
    @POST("request_ready_for_dropoff")
    Call<PICKUPUSERDTO> request_ready_for_dropoff(@Field("req_id") String vReq_idt,
                                                  @Field("status") int vStatus,
                                                  @Field("vdate") String vPickupdate,
                                                  @Field("timezone") String vTimezone);


    @FormUrlEncoded
    @POST("request_drop")
    Call<PICKUPUSERDTO> request_dropoff(@Field("req_id") String vReq_idt,
                                        @Field("status") int vStatus,
                                        @Field("vdate") String vPickupdate,
                                        @Field("timezone") String vTimezone);

    @FormUrlEncoded
    @POST("request_final_confirm")
    Call<PICKUPUSERDTO> request_complete_trip(@Field("req_id") String vReq_idt,
                                              @Field("status") int vStatus,
                                              @Field("vdate") String vPickupdate,
                                              @Field("timezone") String vTimezone,
                                              @Field("review") String vReview,
                                              @Field("rating") float vRating,
                                              @Field("to_id") String vTo_id,
                                              @Field("from_id") String vFrom_id,
                                              @Field("user_type") String vUser_type);

    @FormUrlEncoded
    @POST("get_request_history")
    Call<HISTORY_DTO> request_history_api(@Field("agent_id") String vAgent_Id, @Field("pagecount") String vPageCount);


    @Multipart
    @POST("update_agent_profile_image")
    Call<UpdateProfileModel> updateProfile(@Part MultipartBody.Part agent_id,
                                           @Part MultipartBody.Part image);

    @Multipart
    @POST("save_agent_vehicle")
    Call<SaveagentvehicleModel> saveAgentVehicle(@Part MultipartBody.Part agent_id,
                                                 @Part MultipartBody.Part vehicle_make,
                                                 @Part MultipartBody.Part car_model,
                                                 @Part MultipartBody.Part seaters,
                                                 @Part MultipartBody.Part car_reg,
                                                 @Part MultipartBody.Part other_doc,
                                                 @Part MultipartBody.Part vehicle_ins);

}