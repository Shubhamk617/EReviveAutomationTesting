package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import pagefactory.accessibility.ftlModal.Issues;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;

public class CreateReport {

    static Logger log = Logger.getLogger(CreateReport.class);

    public static void createHTMLReport(String brand) throws IOException {
        List<?> issuesList = jsonReports(Issues.class, brand);

        Template tmplIndex = FtlConfig.getInstance().getTemplate("axe/index.ftl");
        Template tmplPage = FtlConfig.getInstance().getTemplate("axe/page.ftl");

        issuesList.forEach(issues -> {
            save(tmplPage, issues, ((Issues) issues).getId(), "AXE", brand);
        });

        Map<String, Object> root = new HashMap<>();
        root.put("list", issuesList);
        root.put("title", "Accessibility Report");
        save(tmplIndex, root, "index", "AXE", brand);
    }

    public static List<?> jsonReports(Class<?> clazz, String brand) throws IOException {
        return walk(get("./Output/json/" + brand + "/")).filter(Files::isRegularFile).map(Path::toFile)
                .filter(file -> FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")).map(file -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        return mapper.readValue(file, clazz);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("Failed to read json file");
                    }
                    return null;
                }).collect(Collectors.toList());
    }



    public static void save(Template tmpl, Object map, String name, String engine, String brand) {
        Path path = null;
        File report = null;
        try {
            path = get("./Output/HTML/"+ brand + "/" );
            createDirectories(path);
            log.info("Path =>" + path.toString());
            if (name.equalsIgnoreCase("index"))
                report = new File(path + File.separator + name + ".html");
            else
                report = new File(path + File.separator + name + ".html");
            log.info("Name =>" + name.toString());
            log.info("Report =>" + report.toString());
            Writer file = new FileWriter(report);
            if (tmpl == null) {
                throw new IOException();
            }
            tmpl.process(map, file);
            file.flush();
            file.close();
            String loggerMsg = name.equalsIgnoreCase("index") ? "Consoliated " : "Page ";
            log.info(loggerMsg + "report generated at " + report.getAbsolutePath());
        } catch (IOException  e) {
            log.error("unable to write file: " + path + File.separator + name);
            e.printStackTrace();
        }catch (TemplateException e) {
            log.error("unable to find template: " + tmpl + " for " + name);
            e.printStackTrace();
        }
    }

    public String outPutPath = System.getProperty("user.dir") + "/Output/json/";
    public String htmlPath = System.getProperty("user.dir") + "/Output/HTML/";

    public String createOutputFolder(String brand) {
        outPutPath = createFolder(outPutPath + brand + "/");
        htmlPath=createFolder(htmlPath + brand + "/");

        deleteFolder(new File(outPutPath));
        deleteFolder(new File(htmlPath));

        return outPutPath;
    }
    public String createFolder(String folderPath) {
        // TODO Auto-generated method stub
        try {
            Path path = Paths.get(folderPath);
            log.info("creating :: " + path.toString());
            createDirectories(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return folderPath;
    }

    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    continue;
                } else {
                    log.info("deleting :: " + f.getAbsolutePath());
                    f.delete();
                }
            }
        }
    }
}