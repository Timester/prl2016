package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Controller {

    public CheckBox tube1_chkbx;
    public CheckBox tube2_chkbx;

    public ProgressIndicator tube1_indic;
    public ProgressIndicator tube2_indic;

    public ProgressBar progress;

    public void launch(ActionEvent actionEvent) {
        if (tube1_chkbx.isSelected()) {
            for (int i = 0; i < 100; i++) {
                tube1_indic.setProgress(i/100.0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        }

        if (tube2_chkbx.isSelected()) {
            for (int i = 0; i < 100; i++) {
                tube2_indic.setProgress(i/100.0);
            }
        }
    }
}
