package com.final_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
//    UNKNOWN_ERROR(9999, "Unknown error", HttpStatusCode.INTERNAL_SERVER_ERROR),
    USERNAME_EXISTED(1001, "Username already existed", HttpStatus.BAD_REQUEST),
    USERNAME_NULL(1002, "Username cannot be null",HttpStatus.BAD_REQUEST),
    PASSWORD_AT_LEAST_8(1003, "Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    EMAIL_NULL(1004, "Email cannot be null",HttpStatus.BAD_REQUEST),
    FIRST_NAME_NULL(1005, "First name cannot be null",HttpStatus.BAD_REQUEST),
    LAST_NAME_NULL(1006, "Last name cannot be null",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1007, "User not existed",HttpStatus.NOT_FOUND),
    //CATEGORY
    CATEGORYNAME_NULL(1008,"Category name cannot be null",HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1009,"Category already existed",HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1010,"Category not existed",HttpStatus.NOT_FOUND),
    //PRODUCT
    PRODUCTNAME_NULL(1011,"Product name cannot be null",HttpStatus.BAD_REQUEST),
    CATEGORYID_NULL(1012,"Category id cannot be null",HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1013,"Product not existed",HttpStatus.NOT_FOUND),

    CART_ITEM_NOT_EXISTED(1014, "Cart item not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1015, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1016, "You do not have permission",HttpStatus.FORBIDDEN)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
