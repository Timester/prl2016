package net.talqum.prl.server.api;

import net.talqum.prl.server.model.Command;
import net.talqum.prl.server.model.TowerStatus;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface MoveAPI {

    @GET("move")
    Call<TowerStatus> getTowerSTatus();

    @POST("move")
    Call<TowerStatus> status(@Body Command command);
}
