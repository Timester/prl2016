package net.talqum.prl.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.talqum.prl.server.model.enums.CommandDirection;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TowerStatus {
    private List<CommandDirection> movingDirections;
}
