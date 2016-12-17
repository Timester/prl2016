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

    private LaunchAPI launchApiService;
    private MoveAPI moveApiService;

    public ListView<TubeStatus> tube_list;
    public Label status_lbl;

    public RadioButton mode_series;
    public RadioButton mode_selected;

    private static final ObservableList statuses = FXCollections.observableArrayList();

    public UIController() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        launchApiService = retrofit.create(LaunchAPI.class);
        moveApiService = retrofit.create(MoveAPI.class);
    }

    public void arm(ActionEvent actionEvent) {
        try {
            launchApiService.arm().execute();
            refreshStatus();
            setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disarm(ActionEvent actionEvent) {
        try {
            launchApiService.disarm().execute();
            refreshStatus();
            setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fire(ActionEvent actionEvent) {
        if (mode_selected.isSelected()) {
            ObservableList<TubeStatus> selectedItems = tube_list.getSelectionModel().getSelectedItems();

            List<Integer> tubesToStart = selectedItems.stream()
                    .filter(TubeStatus::isLoaded)
                    .map(TubeStatus::getId)
                    .collect(Collectors.toList());

            try {
                Response<Void> execute = launchApiService.fire(tubesToStart).execute();

                execute.isSuccess();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // TODO
        }

        refreshStatus();
    }

    public void moveUp(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.startUpCommand());
    }

    public void moveDown(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.startDownCommand());
    }

    public void moveCCW(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.startCCWCommand());
    }

    public void moveCW(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.startCWCommand());
    }

    public void moveReset(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.resetCommand());
    }

    public void moveTest(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.testCommand());
    }

    public void moveService(ActionEvent actionEvent) {
        moveApiService.move(CommandFactory.serviceCommand());
    }


    private void refreshStatus() {
        Call<Status> status = launchApiService.status();
        try {
            Response<Status> execute = status.execute();
            status_lbl.setText(execute.body().isArmed() ? "ARMED" : "DISARMED");

            List<TubeStatus> tubes = execute.body().getTubes();

            statuses.setAll(tubes);

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
