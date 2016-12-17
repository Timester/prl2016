package net.talqum.prl.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TubeStatus {
    private int id;
    private boolean loaded;

    public String toString() {
        return id + " - " + loaded;
    }
}
