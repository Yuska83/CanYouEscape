package com.project.yuliya.canyouescape.EventBus;

import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;

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

    public ToolChangeEvent(int numTool, boolean toolEnabled)
    {
        this.nameTool = null;
        this.numTool = numTool;
        this.action = toolEnabled?Action.Found:Action.Used;
    }


}
