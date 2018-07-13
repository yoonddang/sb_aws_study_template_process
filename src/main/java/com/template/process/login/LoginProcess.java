package com.template.process.login;

import com.template.common.model.user.TemplateUser;
import com.template.common.model.common.ResultInfo;
import com.template.common.reststub.login.LoginStubBO;
import com.template.common.util.JsonUtils;
import com.template.service.templateUser.TemplateUserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginProcess implements LoginStubBO {

	private static final Logger logger = LoggerFactory.getLogger(LoginProcess.class);

	@Autowired
	private TemplateUserBO templateUserBO;

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	public String userLogin(@RequestBody TemplateUser hanteoUser) {
		logger.info("call /login/userLogin  email:{}, password:{}", hanteoUser.getEmail(),	hanteoUser.getPass_word());

		//이메일로 로그인하기
		ResultInfo resultInfo = templateUserBO.checkUserByUsername(hanteoUser.getEmail(),	hanteoUser.getPass_word());

		return JsonUtils.toJson(resultInfo);
	}


}
