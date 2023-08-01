package com.hmwl.c2service;

import com.alibaba.fastjson.JSON;
import com.hmwl.c2service.utils.WebserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.ws.WebServiceClient;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@SpringBootTest(classes = {C2serviceApplication.class})
class C2serviceApplicationTests {

    @Test
    void contextLoads() {
    }
}
