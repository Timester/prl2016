package net.talqum.prl.server.api;

import net.talqum.prl.server.model.Status;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;

public interface LaunchAPI {
    @POST("launch/arm")
    Call<Void> arm();

    @POST("launch/disarm")
    Call<Void> disarm();

    @GET("launch/status")
    Call<Status> status();

    @POST("launch/fire")
    Call<Void> fire();
}
