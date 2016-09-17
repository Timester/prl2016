package net.talqum.prl.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import net.talqum.prl.server.api.LaunchAPI;
import net.talqum.prl.server.api.MoveAPI;
import net.talqum.prl.server.model.Status;
import net.talqum.prl.server.model.TubeStatus;
import net.talqum.prl.server.model.util.CommandFactory;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UIController {

    private static final String BASE_URL = "http://127.0.0.1:8000/";

    private LaunchAPI apiService;
    private MoveAPI moveAPIService;

    public ListView tube_list;
    public Label status_lbl;

    public static final ObservableList statuses = FXCollections.observableArrayList();

    public UIController() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        apiService = retrofit.create(LaunchAPI.class);
        moveAPIService = retrofit.create(MoveAPI.class);
    }

    public void arm(ActionEvent actionEvent) {
        try {
            apiService.arm().execute();
            refreshStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disarm(ActionEvent actionEvent) {
        try {
            apiService.disarm().execute();
            refreshStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void refreshStatus() {
        Call<Status> status = apiService.status();
        try {
            Response<Status> execute = status.execute();
            status_lbl.setText(String.valueOf(execute.body().isArmed()));

            List<TubeStatus> tubes = execute.body().getTubes();

            statuses.setAll(tubes.stream().map(x -> x.getId() + " - " + x.isLoaded()).collect(Collectors.toList()));

            tube_list.setItems(statuses);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void moveUp(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.startUpCommand());
    }

    public void moveDown(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.startDownCommand());
    }

    public void moveCCW(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.startCCWCommand());
    }

    public void moveCW(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.startCWCommand());
    }

    public void moveReset(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.resetCommand());
    }

    public void moveTest(ActionEvent actionEvent) {
        moveAPIService.move(CommandFactory.testCommand());
    }
}
