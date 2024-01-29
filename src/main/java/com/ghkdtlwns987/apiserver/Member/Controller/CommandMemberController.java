package com.ghkdtlwns987.apiserver.Member.Controller;

import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommandMemberController {
    private final CommandMemberService commandMemberService;
    @GetMapping("/member/{loginId}")
    public EntityModel<ResultResponse> getMemberInfo(@PathVariable String loginId) throws Exception{
        MemberGetInformationResponseDto response = commandMemberService.getMemberInfo(loginId);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.MEMBER_INFORMATION_READ_SUCCESS, response);
        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandMemberController.class).withSelfRel());

        return entityModel;
    }
    @PostMapping("/member")
    public EntityModel<ResultResponse> create(@RequestBody @Valid MemberCreateRequestDto member) throws Exception{
        MemberCreateResponseDto response =  commandMemberService.signup(member);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.REGISTER_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandMemberController.class).withSelfRel());

        return entityModel;
    }

    @PutMapping("/member/nickname/{loginId}")
    public EntityModel<ResultResponse> updateNickname(@PathVariable String loginId, @Valid @RequestBody MemberUpdateNicknameRequestDto nickname) throws Exception{
        final String newNickname = nickname.getNewNickname();
        MemberUpdateNicknameResponseDto response = commandMemberService.updateNickname(loginId, newNickname);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.MEMBER_NICKNAME_UPDATE_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandMemberController.class).withSelfRel());
        return entityModel;
    }
    @PutMapping("/member/password/{loginId}")
    public EntityModel<ResultResponse> updatePassword(@PathVariable String loginId, @Valid @RequestBody MemberUpdatePasswordRequestDto request) throws Exception{
        MemberUpdatePasswordResponseDto response = commandMemberService.updatePassword(loginId, request);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.MEMBER_PASSWORD_UPDATE_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandMemberController.class).withSelfRel());
        return entityModel;
    }

    @DeleteMapping("/member/{loginId}")
    public EntityModel<ResultResponse> withdrawMember(@PathVariable String loginId) throws Exception{
        MemberWithdrawalResponseDto response = commandMemberService.witrawalMember(loginId);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.MEMBER_WITHDRAWAL_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandMemberController.class).withSelfRel());
        return entityModel;
    }
}
