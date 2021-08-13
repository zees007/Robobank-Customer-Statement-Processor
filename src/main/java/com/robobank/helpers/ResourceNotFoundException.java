package com.robobank.helpers;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 12, 2021 - 11:49 AM
 */
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = -9079454849611061074L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

}
