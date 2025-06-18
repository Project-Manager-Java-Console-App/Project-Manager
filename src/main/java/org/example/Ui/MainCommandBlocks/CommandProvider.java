package org.example.Ui.MainCommandBlocks;

import org.example.Ui.Command;

public interface CommandProvider {
    Command getCommand(int choice);
}
