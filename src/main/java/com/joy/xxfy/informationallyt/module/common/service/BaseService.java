package com.joy.xxfy.informationallyt.module.common.service;

import com.joy.xxfy.informationallyt.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationallyt.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.FileUtil;
import com.joy.xxfy.informationallyt.publish.utils.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Transactional
@Service
public class BaseService {
    // 所有文件存储的顶层目录
    @Value("${system.upload.store-path}")
    private String storePath;

    @Value("${system.upload.image-format}")
    private String imageFormat;

    // 配置文件中配置的顶层目录的分隔符
    private final static String FILE_SPLIT = "&&";

    // 获取登陆账户的信息
    public UserEntity getLoginUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return TokenUtil.getUser(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new JoyException(Notice.USER_NOT_LOGIN);
        }
    }

    /**
     * 存储文件
     */
    public JoyResult saveModuleFile(MultipartFile file, String moduleName, boolean isImage){
        String name = file.getOriginalFilename();
        String[] names = name.split("\\.");
        if (null == names || names.length == 0) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
        // 验证图片类型
        if (isImage && !FileUtil.fileNamePostfixCheck(imageFormat, names[names.length - 1])) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_TYPE_ERROR);
        }
        // uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 保存文件名 uuid.ext.
        String filename = uuid + "." + names[1];
        // 替换为符合当前操作系统的路径分隔符顶层路径
        String[] filePaths = storePath.split(FILE_SPLIT);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : filePaths) {
            stringBuffer.append(s + File.separator);
        }
        // 配置路径/StoreFilePathConstant静态配置
        String fileFinalPath = stringBuffer.toString() + moduleName;
        // 保存上传文件
        try {
            FileUtil.save(file.getBytes(), fileFinalPath, filename);
            // 设置返回数据
            FileInfoRes res = new FileInfoRes();
            res.setFilename(filename);
            res.setFilePath(fileFinalPath);
            return JoyResult.buildSuccessResultWithData(res);
        } catch (IOException e) {
            e.printStackTrace();
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
    }
}
