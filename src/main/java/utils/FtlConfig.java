package utils;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.util.Optional;

/**
 * Author: Shubham Kumar
 * Date: 4/11/2022
 * Description: This class is for accessibility utilities.
 */
public class FtlConfig {

    private static FtlConfig instance;

    private Configuration cfg;
    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This function creates the configurations for FTL templates.
     */
    public FtlConfig() {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "ftl");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setSharedVariable("random",
                new BeansWrapperBuilder(Configuration.VERSION_2_3_30).build().getStaticModels());
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    public static FtlConfig getInstance() {
        return Optional.ofNullable(instance).orElseGet(FtlConfig::new);
    }

    public Template getTemplate(String name) throws IOException {
        return getInstance().cfg.getTemplate(name, "UTF-8");
    }
}
