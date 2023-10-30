package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class HandController {

    @GetMapping("/hand")
    public Result operate(@RequestParam String value) {
        log.info("你好" + value);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < value.length(); i++) {
            if(value.charAt(i) == '_') sb.append(' ');
            else sb.append(value.charAt(i));
        }
        return Result.success(sb.toString());
    }
    // ["option1"]     ["option1","option2"]
    @GetMapping("/other")
    public Result operateGroup(@RequestParam("value") String[] values) {
        String[] ope = new String[values.length];
        StringBuilder sb= new StringBuilder();
        for(String value : values) {
            sb.append(value);
            sb.append(",");
        }
        String s = sb.toString();
        s = s.substring(1,s.length() - 2);
        String[] str = s.split(",");
        for(int i = 0; i < values.length; i++) {
            str[i] = str[i].substring(1, str[i].length() - 1);
            System.out.println(str[i]);
            if("option1".equals(str[i])) ope[i] = "伸展";
            if("option2".equals(str[i])) ope[i] = "握住";
            if("option3".equals(str[i])) ope[i] = "夹取";
        }

        return Result.success(ope);
    }


}
/*

*/