package com.xiyi.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.xiyi.util.config.WebAppConfig;

public class ImageUploadTool{
	public static final Logger logger = LoggerFactory.getLogger(ImageUploadTool.class);
	   /**
     * 多文件上传
     * 
     * @param request
     *            当前上传的请求
     * @param basePath
     *            保存文件的路径
     * @param exclude
     *            排除文件名字符串,以逗号分隔的,默认无可传null
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static Map<String, String> multiFileUpload(HttpServletRequest request, String basePath, String exclude) throws IllegalStateException, IOException {
        if (!(new File(basePath).isDirectory())) {
            throw new IllegalArgumentException("basePath 参数必须是文件夹路径");
        }

        return multifileUploadAssist(request, basePath, exclude);
    }

 
    /**
     * 多文件上传辅助
     * 
     * @param request
     *            当前上传的请求
     * @param basePath
     *            保存文件的路径
     * @param exclude
     *            排除文件名字符串,以逗号分隔的,默认无可传null
     * @return
     * @throws IOException
     */
    private static Map<String, String> multifileUploadAssist(HttpServletRequest request, String basePath, String exclude) throws IOException {
        exclude = exclude == null ? "" : exclude;
        
        Map<String, String> filePaths = new HashMap<String, String>();
        File file = null;
        //获取当天时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(date);
		time = time.substring(0, 10);
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //拼接图片key
        String imgName=" ";
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // get the parameter names of the MULTIPART files contained in this request
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                List<MultipartFile> multipartFiles = multiRequest.getFiles(iter.next());
                int i=0;
                for (MultipartFile multipartFile : multipartFiles) {
                    String fileName = multipartFile.getOriginalFilename();
                    if (StringUtils.isNotEmpty(fileName) && (!exclude.contains(fileName))) {
                    	String fileBaseName = changeFilename(fileName);
                        file = new File(basePath + fileBaseName);
                        filePaths.put(fileName, file.getPath());
                        multipartFile.transferTo(file);
                        imgName+=time+file.separator+fileBaseName;
                        if((i+1)!=multipartFiles.size()){
                        	imgName+=",";
                        }
                    }
                }
            }
        }
        filePaths.put("imgName", imgName.trim());
        return filePaths;
    }
    /**
     * 将文件名转变为时间戳 ,保留文件后缀
     * 
     * @param filename
     * @return
     */
    public static String changeFilename(String filename) {
    	// 取时间戳
    	Random rand = new Random();
		int num = rand.nextInt(10000);
		String time = num+String.valueOf(new Date().getTime()).substring(0, 10);
        return time + "." + FilenameUtils.getExtension(filename);
    }
    /**
     * 删除文件
     * 
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public String uploadImg(MultipartFile file,String path) throws Exception {	
		String twoPath = "imgs/";
		try { 
			if (file.isEmpty()) {
				return "";
			}
			// 取1到10000之间的随机整数
			Random rand = new Random();
			int num = rand.nextInt(10000);
			// 取时间戳
			Date date = new Date();
			int time = Integer.valueOf(String.valueOf(date.getTime()).substring(0, 10));
			// 文件名
			String nameNum = String.valueOf(num) + String.valueOf(time);
			// 文件重命名
			String fileName = nameNum + ".jpg";

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// TODO
     		// 按日期创建文件夹
			File uidFile = new File(WebAppConfig.getProperty("upload.path") + twoPath + File.separatorChar );
			uidFile.mkdirs();
			if (uidFile.exists()) {
				logger.info("文件夹创建成功");
			} else {
				logger.error("文件夹创建失败");
			}
			// 新建图片文件
			File img = new File(WebAppConfig.getProperty("upload.path") + twoPath + dateFormat.format(date) + File.separatorChar + fileName);
			if (!img.exists()) {
				// 读取上传文件流
				InputStream fileInputStream = null;
				fileInputStream = file.getInputStream();

				int input = 0;
				byte[] buffer = new byte[1024];
				// 输出流
				FileOutputStream out = new FileOutputStream(img);
				// 写文件
				while ((input = fileInputStream.read(buffer)) != -1) {
					for (int i = 0; i < input; i++) {
						out.write(buffer[i]);
					}
				}

				// 关闭输出流
				out.close();
			}
			return twoPath + dateFormat.format(date) + "/" + fileName;

		} catch (Exception e) {
			logger.error("上传失败", e);
			throw e;
		}
	}
}