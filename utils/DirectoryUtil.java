package com.grd.online.paper.utils;

import lombok.NonNull;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class DirectoryUtil implements EnvironmentAware {

    private static Environment env;

    public static String createDir(String dirName) throws IOException {
        final String parentDir = env.getProperty("app.file.base-path");
        System.out.println("parentDir = " + parentDir);
        final String directoryName = parentDir + "/" + dirName;
        System.out.println("directoryName " + directoryName);
        if (parentDir != null) {
            // convert string to directory path...
            Path dirPath = Paths.get(directoryName);

            // create directory only if doesn't exists...
            if (!dirPath.toFile().exists())
                Files.createDirectories(dirPath);

            // return the directory's absolute path...
            return dirPath.toFile().getAbsolutePath();
        }

        return null;
    }

    /**
     * This will create new file against the given directory and content
     *
     * @return {filePath : String}
     */
    public static String createFile(String filePath, MultipartFile file, String fileName) {
        try {
            String _filePath = createDir((filePath == null) ? "" : filePath);
            String _extension = FilenameUtils.getExtension(file.getOriginalFilename());
            // String _fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
            String _fileName = null;
            if (fileName != null)
                _fileName = _fileName + "-" + fileName;
            System.out.println("22222 " + _fileName);
            Path path = Paths.get(_filePath, _fileName + "." + _extension);

            InputStream _inputStream = file.getInputStream();
            Files.copy(_inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            return path.toFile().getAbsolutePath();
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    @SuppressWarnings("null")
    @Override
    public void setEnvironment(@NonNull Environment environment) {
        env = environment;
    }

}