package org.example.exception;


import lombok.extern.slf4j.Slf4j;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){
        if(e instanceof BizException){
            BizException bizException = (BizException) e;
            log.error("[业务类异常 {}]",e);
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }else {
            log.error("[非业务类异常 {}]",e);
            return JsonData.buildError("全局错误，未知异常");
        }
    }
}
