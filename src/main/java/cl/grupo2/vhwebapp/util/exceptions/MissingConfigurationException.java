package com.gjaras.util.exceptions;

import java.util.ArrayList;
import java.util.List;

public class MissingConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 2947527943266306784L;
    
    private final List<String> fields_;
    
    public MissingConfigurationException(final String field) {
        this.fields_ = new ArrayList<>();
        this.fields_.add(field);
    }
    
    public MissingConfigurationException(final List<String> fields) {
        this.fields_ = fields;
    }
    
    @Override
    public String getMessage() {
        return "The following fields were missing in configuration: " +  String.join(", ", fields_);
    }
}
