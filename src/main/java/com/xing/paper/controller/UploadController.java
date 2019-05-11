package com.xing.paper.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
	
	
    @RequestMapping("/student/uploadPaper")
    @ResponseBody
    public ResponseReslut uploadPaper(HttpServletRequest request,
    	@RequestParam("paper_file") MultipartFile file,MultipartHttpServletRequest mulRequest,String teacherNumber,String studentNumber){
    	File f = null; //生成一个file
		try {
            if(file.equals("")||file.getSize()<=0){  
                file = null;  
            }else{  
                InputStream ins = file.getInputStream();  
                f=new File(file.getOriginalFilename());  
                FileUtils.inputStreamToFile(ins, f); 
                String content = FileUtils.reaFile(f);//文件中的内容
                PaperPO paper = new PaperPO();
                paper.setF_student_number(studentNumber);
                paper.setF_teacher_number(teacherNumber);
                paper.setPaper_content(content);
                paper.setPaper_status(2);//正在审核的状态
                paper.setPosted_time(new Date());
                studentService.savePaper(paper);
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
}
