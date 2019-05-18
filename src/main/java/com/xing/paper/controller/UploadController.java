package com.xing.paper.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xing.paper.service.WordService;
import com.xing.paper.utils.UUIDTool;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xing.paper.po.PaperPO;
import com.xing.paper.service.StudentService;
import com.xing.paper.utils.FileUtils;
import com.xing.paper.utils.ResponseReslut;


@Controller
public class UploadController {
	
	@Autowired
	private StudentService studentService;

	@Resource(name = "asposeWordService")
	private WordService wordService;
	
    @RequestMapping("/student/uploadPaper")
    @ResponseBody
    public ResponseReslut uploadPaper(HttpServletRequest request,
    	@RequestParam("paper_file") MultipartFile file,MultipartHttpServletRequest mulRequest,String teacherNumber,String studentNumber){
    	File f = null; //生成一个file
		try {
            if(file.equals("")||file.getSize()<=0){  
                file = null;  
            }else{

                wordService.word2pdf(
                        "C:\\Users\\admin\\Documents\\Tencent Files\\1590184\\FileRecv\\《医疗管理系统》需求 分析.docx","D:/test2222.pdf");


                InputStream ins = file.getInputStream();

                String uuid = UUIDTool.getUUID();

                String name = file.getName();

                String originalFilename = file.getOriginalFilename();

                String[] split = originalFilename.split("\\.");

                f=new File(uuid +"."+ split[1]);

                FileUtils.inputStreamToFile(ins, f);

//                String content = FileUtils.reaFile(f);//文件中的内容

                PaperPO paper = new PaperPO();

                paper.setF_student_number(studentNumber);

                paper.setF_teacher_number(teacherNumber);

                paper.setPaper_content("");

                paper.setPaper_status(2);//正在审核的状态

                paper.setPosted_time(new Date());

                paper.setUu_file_name(uuid +"."+ split[1]);

                paper.setFile_name(file.getOriginalFilename());

                studentService.savePaper(paper,f);
            }  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != f){
			    File del = new File(f.toURI());  
			    del.delete();  
			}
		}
        return ResponseReslut.SUCCESS;
    }

    public static void main(String[] args) {

    }
}
