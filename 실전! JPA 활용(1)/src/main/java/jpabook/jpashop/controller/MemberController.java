package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) { // form 에서 오류가 생길 경우 원래는 컨트롤러에서 안넘어가고 튕겨나가는데 bindingResult 가 있을 경우 여기에 담겨 아래의 코드가 실행된다.
        // memberForm 에서 문제가 생기면 result 에 뭔가 담긴다.

        if(result.hasErrors()) {
            return "members/createMemberForm"; // 이때 입력 폼으로 보내도록 설정해주는데 spring 이 BindingResult 를 끌고와서 form 에서 쓸 수 있게 도와준다.
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }




}
