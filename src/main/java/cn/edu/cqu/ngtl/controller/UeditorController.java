package cn.edu.cqu.ngtl.controller;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by hp on 2016/10/18.
 */
@Controller
@RequestMapping("/editor")
public class UeditorController {

    @RequestMapping("/submit")
    public void uploadimage(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("content")String content){
        System.out.println(content);


    }


}
