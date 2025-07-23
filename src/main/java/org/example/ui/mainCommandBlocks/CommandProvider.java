package org.example.ui.mainCommandBlocks;

import org.example.ui.Command;

public interface CommandProvider {
    Command getCommand(int choice);
}
