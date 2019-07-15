package com.lirong.servicehi.error;

import com.lirong.servicehi.filter.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Title: ControllerException <br>
 * Description: ControllerException <br>
 * Date: 2019年02月01日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private HttpErrorRecord httpErrorRecord;

    private static final String CACHE_ERROR = "系统错误(1001)";
    private static final String NOT_FOUND = "not found";
    private static final String QUERY_ERROR = "系统错误(1002)";
    private static final String JSON_ERROR = "请求错误，json无法反序列化";

    /**
     * 输入校验异常处理
     *
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<ResultBean>
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResultBean> validError(MethodArgumentNotValidException e) {
        List<ArgumentInvalidResult> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult result = new ArgumentInvalidResult(error.getField(), error.getRejectedValue(),
                    error.getDefaultMessage());
            errors.add(result);
        }
        String msg = StringUtils.collectionToDelimitedString(errors, ",");
        return ResponseEntity.badRequest().body(
                new ResultBean(GlobalCodeEnum.INVALID_PARAMS.getCode(), msg));
    }

    /**
     * 输出校验异常处理
     *
     * @param e ConstraintViolationException
     * @return ResponseEntity
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResultBean> validError(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> msg = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            msg.add(constraintViolation.getMessage());
        }
        return ResponseEntity.badRequest().body(
                new ResultBean(GlobalCodeEnum.INVALID_PARAMS.getCode(), StringUtils.collectionToDelimitedString(msg, ",")));
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResultBean> cacheError(Exception e, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        log.info(request.getRequestURI());
        log.info(request.getPathInfo());
        log.info(request.getMethod());
        request.getParameter("id");
        log.info("请求参数：{}",request.getParameterMap());
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>();
        for (String s : parameterMap.keySet()) {
            String tem = s +"="+StringUtils.collectionToDelimitedString(Arrays.asList(parameterMap.get(s)),",") ;
            list.add(tem);
        }
        String s1 = StringUtils.collectionToDelimitedString(list, "&");
        log.info("完整请求：{}",request.getRequestURI()+"?"+s1);
        if (request instanceof BodyReaderHttpServletRequestWrapper) {
            byte[] s = ((BodyReaderHttpServletRequestWrapper) request).getBody();
            log.info(new String(s));
        }
        System.out.println(e.getLocalizedMessage());
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        System.out.println("打印异常："+sw.toString());
        log.error("系统异常", e);
        httpErrorRecord.record(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResultBean(GlobalCodeEnum.SYSTEM_ERROR.getCode(), GlobalCodeEnum.SYSTEM_ERROR.getMsg()));
    }

}