package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    //get방식(GetMapping)
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!");
        //컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버('viewResolver')가 화면을 찾아서 처리한다.
        //스프링 부트 템블릿 엔진 기본 viewName매핑
        //resources:tamplates/ + (ViewName) + .html
        return "hello";
    }

    @GetMapping("hello-mvc")
    //외부에서 파라미터를 받을때는 @RequestParam를 사용한다.(?=박수현)
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name",name);
        //컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버('viewResolver')가 화면을 찾아서 처리한다.
        //스프링 부트 템블릿 엔진 기본 viewName매핑
        //resources:tamplates/ + (ViewName) + .html
        return "hello-template";
    }

    //api방식(예제1)
    @GetMapping("hello-string")
    @ResponseBody
    //http의 body부분에 데이터를 직접 부분에 넣어주겠다는 의미임. -> html코드없이!
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    //api방식(예제2)
    //return 객체 + @ResponseBody로 하면 -> json을 리턴함
    //api방식은 viewResolver를 거치는 과정이 없음(viewResolver대신에 HttpMessageConverter가 동작함)
    @GetMapping("hello-api")
    @ResponseBody //return 객체 + @ResponseBody로 하면 -> json을 리턴함(객체는 json형태로 리턴하게됨)
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //반환값 [Json - key/value로 이루어진 구조]
    }

    //getter/setter단축키 [alt+insert]
    static class Hello{
        private String name;

        //property접근방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
