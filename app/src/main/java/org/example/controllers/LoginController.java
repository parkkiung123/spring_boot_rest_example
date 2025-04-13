package org.example.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.example.dto.LoginForm;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.services.DBService;

@Getter
@Setter
class ResultVO {

	private int resultCode = 0;
	
	private String resultMessage = "OK";
	private Map<String, Object> result = new HashMap<String, Object>();

	public void putResult(String key, Object value) {
		result.put(key, value);
	}

	public Object getResult(String key) {
		return this.result.get(key);
	}
}

enum ResponseCode {

	SUCCESS(200, "성공했습니다."),
	AUTH_ERROR(403, "인가된 사용자가 아닙니다."),
	DELETE_ERROR(700, "삭제 중 내부 오류가 발생했습니다."),
	SAVE_ERROR(800, "저장시 내부 오류가 발생했습니다."),
	INPUT_CHECK_ERROR(900, "입력값 무결성 오류 입니다.");

	private int code;
	private String message;

	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    private final DBService dbService;

    public LoginController(DBService dbService) {
        this.dbService = dbService;
    }

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody LoginForm form) {
        log.info("로그인 시도 : " + form.getId() + ", " + form.getName() + ", " + form.getRole());
        String role = form.getRole();
		Object found = null;
		ResultVO resultVO = new ResultVO();
		try {
			switch (role) {
				case "admin":
					found = dbService.findTeacherById(Long.parseLong(form.getId()))
						.orElseThrow(() -> new RuntimeException("해당 ID의 교사가 존재하지 않습니다."));
					break;
				case "user":
					found = dbService.findStudentById(Long.parseLong(form.getId()))
						.orElseThrow(() -> new RuntimeException("해당 ID의 학생이 존재하지 않습니다."));
					break;
				default:
					new IllegalArgumentException("유저 타입이 유효하지 않습니다 (user 및 admin 만 가능) : " + role);
			}
		} catch (Exception ex) {
			log.info("로그인 실패: " + form.getId() + ", " + form.getName());
			resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
		}

        boolean isNameCorrect = found instanceof Student student && student.getName().equals(form.getName()) ||
                                found instanceof Teacher teacher && teacher.getName().equals(form.getName());

        if (isNameCorrect) {
            log.info("로그인 성공: " + form.getId() + ", " + form.getName());
            resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		    resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
        } else {
            log.info("로그인 실패: " + form.getId() + ", " + form.getName());
            resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
		    resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
        }
        return resultVO;
    }
}
