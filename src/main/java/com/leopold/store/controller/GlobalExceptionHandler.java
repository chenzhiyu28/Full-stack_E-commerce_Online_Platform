package com.leopold.store.controller;

import com.leopold.store.controller.ex.*;
import com.leopold.store.service.ex.*;
import com.leopold.store.util.JsonResponse;
import com.leopold.store.util.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/*
// 相当于 @ControllerAdvice + @ResponseBody, 由 Spring 框架自动调用
@RestControllerAdvice
// 所有的exception，都统一在这里生成http response
public class GlobalExceptionHandler {
    protected final String STATUS_ERROR = "ERROR";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public JsonResponse<Object> handleException(Exception e, HttpServletRequest request) {
        logger.error("application error, address:{}, error message:", request.getRequestURL(), e);

        JsonResponse<Object> response = new JsonResponse<>();
        response.setStatus(STATUS_ERROR);

        if (e instanceof NoHandlerFoundException) {
            // url找不到对应handler
            response.setCode(ResponseCode.CODE_404.getCode());
            response.setMessage(ResponseCode.CODE_404.getMessage());
        } else if (e instanceof AccessDeniedException) {
            // 没有权限
            response.setCode(ResponseCode.CODE_403.getCode());
            response.setMessage(ResponseCode.CODE_403.getMessage());
        } else if (e instanceof BindException
                || e instanceof MethodArgumentTypeMismatchException
                || e instanceof MethodArgumentNotValidException
                || e instanceof IllegalArgumentException) {
            // 参数错误
            response.setCode(ResponseCode.CODE_600.getCode());
            response.setMessage(ResponseCode.CODE_600.getMessage());
        } else if (e instanceof UserNameDuplicatedException) {
            // 注册—— 重复用户名
            response.setCode(ResponseCode.CODE_601.getCode());
            response.setMessage(ResponseCode.CODE_601.getMessage());
        } else if (e instanceof DatabaseConnectionException || e instanceof DataAccessException) {
            // 数据库异常
            response.setCode(ResponseCode.CODE_400.getCode());
            response.setMessage("Failed to create new user!");
        } else if (e instanceof UserNotFoundException) {
            response.setCode(ResponseCode.USER_NOT_EXIST.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof PasswordNotMatchException) {
            response.setCode(ResponseCode.PASSWORD_ERROR.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof UpdateException) {
            response.setCode(ResponseCode.Update_Error.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof FileOverSizeException) {
            response.setCode(ResponseCode.FILE_OVERSIZE_ERROR.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof FileUploadIOException) {
            response.setCode(ResponseCode.FILE_UPLOAD_ERROR.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof FileDamagedException) {
            response.setCode(ResponseCode.FILE_DAMAGED_ERROR.getCode());
            response.setMessage(e.getMessage());
        } else if (e instanceof FileTypeException) {
            response.setCode(ResponseCode.FILE_TYPE_ERROR.getCode());
            response.setMessage(e.getMessage());
        } else {
            response.setCode(ResponseCode.CODE_500.getCode());
            response.setMessage(e.getMessage());
        }


        return response;
    }
}
*/
// 思路: 所有exception全在这处理, errorCode & message 都由exception 预先定义, 业务逻辑不负责throw/handle exception
// 目前自定义的exception还需要 explicitly throw 出来, 将来可以refactor 成AOP
@RestControllerAdvice
public class GlobalExceptionHandler {
    protected final String STATUS_ERROR = "ERROR";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public JsonResponse<Object> handleServiceException(ServiceException e, HttpServletRequest request) {
        logger.error("Service exception, address:{}, error message:", request.getRequestURL(), e);
        JsonResponse<Object> response = new JsonResponse<>();
        response.setStatus(STATUS_ERROR);

        if (e instanceof UserNameDuplicatedException) {
            response.setCode(ResponseCode.DUPLICATE_USER_NAME.getCode());
            response.setMessage(ResponseCode.DUPLICATE_USER_NAME.getMessage());
        } else if (e instanceof DatabaseConnectionException /* || e instanceof DataAccessException*/) {
            response.setCode(ResponseCode.DATABASE_CONNECTION_ERROR.getCode());
            response.setMessage(ResponseCode.DATABASE_CONNECTION_ERROR.getMessage());
        } else if (e instanceof UserNotFoundException) {
            response.setCode(ResponseCode.USER_NOT_EXIST.getCode());
            response.setMessage(ResponseCode.USER_NOT_EXIST.getMessage());
        } else if (e instanceof PasswordNotMatchException) {
            response.setCode(ResponseCode.PASSWORD_ERROR.getCode());
            response.setMessage(ResponseCode.PASSWORD_ERROR.getMessage());
        } else if (e instanceof UpdateInfoException) {
            response.setCode(ResponseCode.UPDATE_INFO_ERROR.getCode());
            response.setMessage(ResponseCode.UPDATE_INFO_ERROR.getMessage());
        } else if (e instanceof SessionExpiredException) {
            response.setCode(ResponseCode.SESSION_EXPIRED.getCode());
            response.setMessage(ResponseCode.SESSION_EXPIRED.getMessage());
        } else if (e instanceof AddressCommitLimitException) {
            response.setCode(ResponseCode.USER_ADDRESS_EXCEED_LIMIT.getCode());
            response.setMessage(ResponseCode.USER_ADDRESS_EXCEED_LIMIT.getMessage());
        } else if (e instanceof AddressNotExistException) {
            response.setCode(ResponseCode.USER_ADDRESS_NOT_EXIST.getCode());
            response.setMessage(ResponseCode.USER_ADDRESS_NOT_EXIST.getMessage());
        } else if (e instanceof IllegalRequestException) {
            response.setCode(ResponseCode.ILLEGAL_REQUEST_ERROR.getCode());
            response.setMessage(ResponseCode.ILLEGAL_REQUEST_ERROR.getMessage());
        } else if (e instanceof ProductNotFoundException) {
            response.setCode(ResponseCode.PRODUCT_NOT_EXIST.getCode());
            response.setMessage(ResponseCode.PRODUCT_NOT_EXIST.getMessage());
        } else if (e instanceof CartNotFoundException) {
            response.setCode(ResponseCode.CART_NOT_EXIST.getCode());
            response.setMessage(ResponseCode.CART_NOT_EXIST.getMessage());
        } else {
            response.setCode(ResponseCode.UNCLASSIFIED_SERVICE_ERROR.getCode());
            response.setMessage(ResponseCode.UNCLASSIFIED_SERVICE_ERROR.getMessage());
        }

        // if more specific info is given when throwing
        if (e.getMessage() != null) {
            response.setMessage(e.getMessage());
        }

        return response;
    }


    @ExceptionHandler(FileUploadException.class)
    public JsonResponse<Object> handleFileUploadException(FileUploadException e, HttpServletRequest request) {
        logger.error("File upload exception, address:{}, error message:", request.getRequestURL(), e);
        JsonResponse<Object> response = new JsonResponse<>();
        response.setStatus(STATUS_ERROR);

        if (e instanceof FileOverSizeException) {
            response.setCode(ResponseCode.FILE_OVERSIZE_ERROR.getCode());
            response.setMessage(ResponseCode.FILE_OVERSIZE_ERROR.getMessage());
        } else if (e instanceof FileUploadIOException) {
            response.setCode(ResponseCode.FILE_UPLOAD_ERROR.getCode());
            response.setMessage(ResponseCode.FILE_UPLOAD_ERROR.getMessage());
        } else if (e instanceof FileDamagedException) {
            response.setCode(ResponseCode.FILE_DAMAGED_ERROR.getCode());
            response.setMessage(ResponseCode.FILE_DAMAGED_ERROR.getMessage());
        } else if (e instanceof FileTypeException) {
            response.setCode(ResponseCode.FILE_TYPE_ERROR.getCode());
            response.setMessage(ResponseCode.FILE_TYPE_ERROR.getMessage());
        } else {
            response.setCode(ResponseCode.FILE_UPLOAD_ERROR.getCode());
            response.setMessage(ResponseCode.FILE_UPLOAD_ERROR.getMessage());
        }

        if (!e.getMessage().isEmpty()) {
            response.setMessage(e.getMessage());
        }

        return response;
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public JsonResponse<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {

        logger.error("No handler found, address:{}, error message:", request.getRequestURL(), e);
        JsonResponse<Object> response = new JsonResponse<>();

        response.setCode(ResponseCode.NO_HANDLER_ERROR.getCode());
        response.setMessage(ResponseCode.NO_HANDLER_ERROR.getMessage());

        if (!e.getMessage().isEmpty()) {
            response.setMessage(e.getMessage());
        }

        return response;
    }


    @ExceptionHandler(Exception.class)
    public JsonResponse<Object> handleGeneralException(Exception e, HttpServletRequest request) {
        logger.error("Unexpected error, address:{}, error message:", request.getRequestURL(), e);

        JsonResponse<Object> response = new JsonResponse<>();
        response.setCode(ResponseCode.UNEXPECTED_ERROR.getCode());
        response.setMessage(ResponseCode.UNEXPECTED_ERROR.getMessage());

        if (!e.getMessage().isEmpty()) {
            response.setMessage(e.getMessage());
        }

        return response;
    }
}

/*
在 Spring Boot 应用程序中，全局异常处理器（例如 GlobalExceptionHandlerController）
主要是用于捕获未被捕获的异常并处理它们。

全局异常处理器通过 @ExceptionHandler 注解和 @RestControllerAdvice 自动注册，
当控制器方法或其他地方未捕获的异常被抛出时，Spring 会自动调用相应的处理方法

某个地方抛出异常（例如 throw new Exception），会触发 handleException 方法。

只要设置了general exception handler 就不需要显式抛出异常:

 */