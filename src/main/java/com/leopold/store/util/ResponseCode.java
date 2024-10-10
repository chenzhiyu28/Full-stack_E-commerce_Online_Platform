package com.leopold.store.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    // General Exceptions
    UNEXPECTED_ERROR(100, "An unexpected error has occurred! Try again later!"),
    CODE_200(200, "OK"),
    CODE_400(400, "Bad Request"),
    CODE_403(403, "Forbidden"),
    CODE_404(404, "Not Found"),
    CODE_600(600, "Parameter Error"),
    CODE_601(601, "Duplicate Key Error"),
    CODE_500(500, "Bad Gateway"),


    // Service Exceptions: 4000
    UNCLASSIFIED_SERVICE_ERROR(4000, "An unexpected error has occurred! Try again later!"),
    ILLEGAL_REQUEST_ERROR(4010, "Illegal request!"),
        // User Exceptions: 4200
    USER_NOT_EXIST(4200, "User does not exist!"),
    PASSWORD_ERROR(4210, "In correct password or userName!"),
    DUPLICATE_USER_NAME(4220, "Duplicate user name!"),

        // Repository Exceptions: 4300
    DATABASE_CONNECTION_ERROR(4300, "Failed to save changes, try again later!"),
    UPDATE_INFO_ERROR(4310, "Failed to update password, try again later!"),

        // Session Exceptions: 4400
    SESSION_EXPIRED(4400, "Your session has expired, try login again!"),

        // User_Address Exceptions: 4500
    USER_ADDRESS_EXCEED_LIMIT(4500, "User address exceed limit!"),
    USER_ADDRESS_NOT_EXIST(4510, "User address does not exist!"),

        // Product Exceptions: 4600
    PRODUCT_NOT_EXIST(4610, "Product does not exist!"),

        // Cart Exceptions: 4700
    CART_NOT_EXIST(4710, "Cart does not exist!"),


    // Controller Exceptions: 5000
        // handler exceptions
    NO_HANDLER_ERROR(5010, "Invalid action!"),
        // File upload Errors: 5100
    FILE_OVERSIZE_ERROR(5100, "The file you uploaded is oversize!"),
    FILE_TYPE_ERROR(5200, "The file you uploaded is not allowed!"),
    FILE_UPLOAD_ERROR(5300, "Failed to upload file, try again later!"),
    FILE_DAMAGED_ERROR(5400, "File is damaged or empty!");

    private final Integer code;
    private final String message;
}
