package com.project.yuliya.canyouescape.eventBus;

import com.project.yuliya.canyouescape.constans.Action;
import com.project.yuliya.canyouescape.constans.ToolName;

public class ToolChangeEvent {

    private ToolName nameTool;
    private int numTool;
    private Action action;

    public ToolName getNameTool() {
        return nameTool;
    }
    public int getNumTool() {
        return numTool;
    }
    public Action getAction() {
        return action;
    }

    public ToolChangeEvent(ToolName nameTool, int numTool, Action action)
    {
        this.nameTool = nameTool ;
        this.numTool = numTool;
        this.action = action;
    }


}
