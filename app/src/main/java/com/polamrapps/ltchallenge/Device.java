package com.polamrapps.ltchallenge;

/**
 * Created by Rajareddy on 06/07/16.
 */
public class Device {
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private String type;

    public String getModel() {
        return model;
    }

    private String model;
    private String name;

    /** default constructor  **/
    public Device() {

    }

    /** Constructor with input parameters  **/
    public Device (String _type, String _model, String _name) {
        this.type = _type;
        this.model = _model;
        this.name = _name;
    }

}
