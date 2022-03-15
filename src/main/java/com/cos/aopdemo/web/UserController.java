package com.cos.aopdemo.web;

import com.cos.aopdemo.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
@RestController
public class UserController {
    // DI
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // http://localhost:8000/user
    @GetMapping("/user")
    public CommonDto<List<User>> findAll() {
        System.out.println("findAll()");
        // MessageConverter (JavaObject -> Json String)
        return new CommonDto<>(HttpStatus.OK.value(), userRepository.findAll());
    }

    @GetMapping("/user/{id}")
    public CommonDto<User> finalById(@PathVariable int id) {
        System.out.println("finalById() id: " + id);
        return new CommonDto<User>(HttpStatus.OK.value(), userRepository.findById(id));
    }

    // cors 접근 가능, javascript 로 http 요청 가능
    @CrossOrigin
    @PostMapping("/user")
    // x-www-form-urlencoded => request.getParameter()
    // text/plain => @RequestBody
    // application/json => @RequestBody 어노테이션 + 오브젝트
    // BindingResult 에 validation 후 결과를 담아준다.
    public CommonDto<?> save(@Valid @RequestBody JoinReqDto dto, BindingResult bindingResult) {
        System.out.println("save()");
        System.out.println("dto: " + dto);

        userRepository.save(dto);

        return new CommonDto<String>(HttpStatus.CREATED.value(), "ok");
    }

    @DeleteMapping("/user/{id}")
    public CommonDto delete(@PathVariable int id) {
        System.out.println("delete()");
        userRepository.delete(id);
        return new CommonDto(HttpStatus.OK.value());
    }

    @PutMapping("/user/{id}")
    public CommonDto<?> update(@PathVariable int id, @Valid @RequestBody UpdateReqDto dto
            , BindingResult bindingResult) {
        System.out.println("update()");
        userRepository.update(id, dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }

}
