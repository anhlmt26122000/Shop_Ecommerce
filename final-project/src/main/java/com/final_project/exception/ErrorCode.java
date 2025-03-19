package com.final_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
//    UNKNOWN_ERROR(9999, "Unknown error"),
    USERNAME_EXISTED(1001, "Username already existed"),
    USERNAME_NULL(1002, "Username cannot be null"),
    PASSWORD_AT_LEAST_8(1003, "Password must be at least 8 characters"),
    EMAIL_NULL(1004, "Email cannot be null"),
    FIRST_NAME_NULL(1005, "First name cannot be null"),
    LAST_NAME_NULL(1006, "Last name cannot be null"),
    USER_NOT_EXISTED(1007, "User not existed"),
    //CATEGORY
    CATEGORYNAME_NULL(1008,"Category name cannot be null"),
    CATEGORY_EXISTED(1009,"Category already existed"),
    CATEGORY_NOT_EXISTED(1010,"Category not existed"),
    //PRODUCT
    PRODUCTNAME_NULL(1011,"Product name cannot be null"),
    CATEGORYID_NULL(1012,"Category id cannot be null"),
    PRODUCT_NOT_EXISTED(1013,"Product not existed"),

    CART_ITEM_NOT_EXISTED(1014, "Cart item not existed"),
    WRONG_PASSWORD(1015, "Wrong password")
    ;

    private int code;
    private String message;

}
