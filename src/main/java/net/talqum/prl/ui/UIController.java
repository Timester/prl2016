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
    public Button fire_btn;
    public Button arm_btn;
    public Button disarm_btn;
    public Slider delay_slider;

    private LaunchAPI apiService;
    private MoveAPI moveAPIService;

    public ListView tube_list;
    public Label status_lbl;

    public RadioButton mode_series;
    public RadioButton mode_selected;

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
            setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disarm(ActionEvent actionEvent) {
        try {
            apiService.disarm().execute();
            refreshStatus();
            setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fire(ActionEvent actionEvent) {
        handleDelay();
        if (mode_selected.isSelected()) {
            // TODO
        } else {
            // TODO
        }

        refreshStatus();
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


    private void refreshStatus() {
        Call<Status> status = apiService.status();
        try {
            Response<Status> execute = status.execute();
            status_lbl.setText(execute.body().isArmed() ? "ARMED" : "DISARMED");

            List<TubeStatus> tubes = execute.body().getTubes();

            statuses.setAll(tubes.stream().map(x -> x.getId() + " - " + x.isLoaded()).collect(Collectors.toList()));

            tube_list.setItems(statuses);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void setDisable(boolean disable) {
        tube_list.setDisable(disable);
        fire_btn.setDisable(disable);
        arm_btn.setDisable(!disable);
        disarm_btn.setDisable(disable);
        delay_slider.setDisable(disable);
        mode_selected.setDisable(disable);
        mode_series.setDisable(disable);
    }

    private void handleDelay() {
        int value = (int) delay_slider.getValue();
        // TODO
    }
}
