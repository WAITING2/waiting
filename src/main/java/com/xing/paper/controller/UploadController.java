package com.xing.paper.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xing.paper.service.WordService;
import com.xing.paper.utils.UUIDTool;
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
        File pdf_f = null;
		try {
            if(file.equals("")||file.getSize()<=0){

                System.out.print("上传文件为空...");

            }else{

                InputStream ins = file.getInputStream();

                String uuid = UUIDTool.getUUID();

                String originalFilename = file.getOriginalFilename();

                String[] split = originalFilename.split("\\.");

                f = new File(uuid +"."+ split[1]);

                FileUtils.inputStreamToFile(ins, f);

                if(file.getName().endsWith(".dox") || file.getName().endsWith(".doxc")){

                    pdf_f = new File("pdf_f.pdf");

                    try {
                        wordService.word2pdf(
                                f.getPath(),pdf_f.getPath());
                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }


                if(null != pdf_f){
                    File del = new File(f.toURI());

                    del.delete();

                    f = pdf_f;
                }

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

			if(null != pdf_f){
                File del = new File(pdf_f.toURI());
                del.delete();
            }

		}
        return ResponseReslut.SUCCESS;
    }


    @RequestMapping("/teacher/uploadPaper")
    @ResponseBody
    public ResponseReslut uploadPaper2(HttpServletRequest request,
                                      @RequestParam("paper_file") MultipartFile file,MultipartHttpServletRequest mulRequest,PaperPO po){

        if(null == po.getId()){
            return ResponseReslut.FAILD;
        }

        File f = null; //生成一个file
        File pdf_f = null;
        try {
            if(file.equals("")||file.getSize()<=0){

                System.out.print("上传文件为空...");

            }else{

                InputStream ins = file.getInputStream();

                String uuid = UUIDTool.getUUID();

                String originalFilename = file.getOriginalFilename();

                String[] split = originalFilename.split("\\.");

                f = new File(uuid +"."+ split[1]);

                FileUtils.inputStreamToFile(ins, f);

                if(file.getName().endsWith(".dox") || file.getName().endsWith(".doxc")){

                    pdf_f = new File("pdf_f.pdf");

                    wordService.word2pdf(
                            f.getPath(),pdf_f.getPath());
                }


                if(null != pdf_f){
                    File del = new File(f.toURI());

                    del.delete();

                    f = pdf_f;
                }

                po.setUu_t_reply_name(uuid +"."+ split[1]);

                po.setT_reply_name(file.getOriginalFilename());

                studentService.savePaper(po,f);
            }
        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if(null != f){
                File del = new File(f.toURI());
                del.delete();
            }

            if(null != pdf_f){
                File del = new File(pdf_f.toURI());
                del.delete();
            }
        }
        return ResponseReslut.SUCCESS;
    }




    public static void main(String[] args) {

    }
}
