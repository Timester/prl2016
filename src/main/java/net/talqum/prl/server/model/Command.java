package net.talqum.prl.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.talqum.prl.server.model.enums.CommandAction;
import net.talqum.prl.server.model.enums.CommandDirection;
import net.talqum.prl.server.model.enums.CommandType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private CommandType type;
    private CommandDirection direction;
    private CommandAction action;
}
