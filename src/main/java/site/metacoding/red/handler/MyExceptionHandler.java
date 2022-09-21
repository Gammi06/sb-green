package site.metacoding.red.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import site.metacoding.red.handler.ex.MyApiException;
import site.metacoding.red.handler.ex.MyException;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.response.CMRespDto;

//에러만 전담처리하는 컨트롤러
@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(MyApiException.class)
	public @ResponseBody CMRespDto<?> apiError(Exception e){
		return new CMRespDto<>(-1, e.getMessage(), null);
	}
	
	@ExceptionHandler(MyException.class)
	public @ResponseBody String m1(Exception e){
		return Script.back(e.getMessage());
	}
}
