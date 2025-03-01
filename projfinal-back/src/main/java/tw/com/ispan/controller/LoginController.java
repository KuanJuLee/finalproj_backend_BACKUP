package tw.com.ispan.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.jwt.JsonWebTokenUtility;
import tw.com.ispan.service.MemberService;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    @PostMapping("/api/ajax/secure/login")
    public String login(@RequestBody String entity) {
        JSONObject responseJson = new JSONObject();

        System.out.println("接收到登入請求");
        // 接收資料
        JSONObject obj = new JSONObject(entity);
        String email = obj.isNull("email") ? null : obj.getString("email");
        String password = obj.isNull("password") ? null : obj.getString("password");

        // 驗證資料
        if (email == null || email.length() == 0 || password == null || password.length() == 0) {
            responseJson.put("success", false);
            responseJson.put("message", "請輸入帳號/密碼");
            return responseJson.toString();
        }
        // 呼叫 Service 層的 login 方法，使用email和密碼登入
        Member bean = memberService.login(email, password);

        // 根據Model執行結果決定要呼叫的View
        if (bean == null) {
            responseJson.put("success", false);
            responseJson.put("message", "登入失敗");
            System.out.println("查無此會員: " + email);
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "登入成功");

            // 登入時有將memberid和email放進token中，
            JSONObject user = new JSONObject()
                    .put("memberId", bean.getMemberId()) // 把小朱原本custid改成memberId
                    .put("email", bean.getEmail())
                    .put("role", "member"); // 這裡新增角色資訊，給前台做權限驗證
            String token = jsonWebTokenUtility.createToken(user.toString()); // 產生一個帶有memberid和email的token字串
            responseJson.put("token", token); // Token 返回給前端，用於後續請求的身份驗證
            responseJson.put("user", bean.getEmail());
        }
        return responseJson.toString();
    }
}