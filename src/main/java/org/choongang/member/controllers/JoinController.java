package org.choongang.member.controllers;

import org.choongang.global.AbstractController;
import org.choongang.global.Router;
import org.choongang.global.Service;
import org.choongang.global.constants.Menu;
import org.choongang.main.controllers.MainRouter;
import org.choongang.main.services.MemberServiceLocator;
import org.choongang.template.Templates;

/**
 * 회원 가입 컨트롤러
 *
 */

public class JoinController extends AbstractController {
    @Override
    public void show() {
        Templates.getInstance().render(Menu.JOIN);
    }

    @Override
    public void prompt() {
        String userId = promptWithValidation("아이디(6자리 이상): ", s -> s.length() >= 6);
        String userPw = promptWithValidation("비밀번호(8자리 이상): ", s -> s.length() >= 8);
        String confirmPw = promptWithValidation("비밀번호 확인: ", s -> {
            boolean match = s.equals(userPw);
            if(!match) {
            System.err.println("\n비밀번호가 일치하지 않습니다.");
            }
            return match;
        });

        String userNm = promptWithValidation("회원명: ", s -> !s.isBlank());

       RequestJoin form = RequestJoin.builder()
               .userId(userId)
               .userPw(userPw)
               .confirmPw(confirmPw)
               .userNm(userNm)
               .build();

       Router router = MainRouter.getInstance();
       try {
           // 회원 가입 처리..
           Service service = MemberServiceLocator.getInstance().find(Menu.JOIN);
           service.process(form);


           // 회원 가입 성공 시 -> 로그인 화면 이동
           router.change(Menu.LOGIN);
       } catch (RuntimeException e) {
           // 회원가입 실패 시
           System.err.println(e.getMessage());
           router.change(Menu.JOIN);
       }
    }
}
