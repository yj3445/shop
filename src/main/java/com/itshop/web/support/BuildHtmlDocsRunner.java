package com.itshop.web.support;

import com.itshop.web.ItShopApplication;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

/**
 * 记录SQL
 *
 * @author liufenglong
 * @date 2020/5/25
 */
@Order(1)
@Component
public class BuildHtmlDocsRunner implements ApplicationRunner {
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ("dev".equals(active)) {
            buildHtmlDocs();
        }
    }

    private void buildHtmlDocs(){
        File file = new File(ItShopApplication.class.getSimpleName());
        String projectPath = Paths.get(file.getAbsolutePath())
                .getParent().toAbsolutePath().toString();
        DocsConfig config = new DocsConfig();
        config.setProjectPath(projectPath); // 项目根目录
        config.setProjectName("ProjectName"); // 项目名称
        config.setApiVersion("V1.0");       // 声明该API的版本
        config.setDocsPath("D:\\privateProject\\it-shop\\doc\\apiDocs");
//        config.setDocsPath(projectPath+"/target/apiDocs");// 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        Docs.buildHtmlDocs(config); // 执行生成文档
    }
}
