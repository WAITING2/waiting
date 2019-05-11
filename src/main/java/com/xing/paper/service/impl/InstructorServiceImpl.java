package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.xing.paper.dao.InstructorDao;
import com.xing.paper.po.ClassPO;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.Instructor;
import com.xing.paper.service.InstructorService;
import com.xing.paper.utils.ExportExcelUtil;
import com.xing.paper.vo.PaperVO;

@Service
public class InstructorServiceImpl implements InstructorService{
	@Resource
	private InstructorDao instructorDao;

	@Override
	public boolean comfirmLogin(String username, String password) {
		return instructorDao.getInstrucorByUsernameAndPassword(username,password).size()>0;
	}

	@Override
	public Instructor queryByInstructorNumber(String username) {
		return instructorDao.queryByInstructorNumber(username);
	}

	@Override
	public List<ClassPO> findAllClass() {
		return instructorDao.findAllClass();
	}

	@Override
	public List<DepartmentPo> findAllDepartment() {
		return instructorDao.findAllDepartment();
	}

	@Override
	public Workbook exportExcle(List<PaperVO> paperVoList, String[] titles, ServletOutputStream outputStream) {
		Workbook workbook=new SXSSFWorkbook();
		// 创建工作表实例
		Sheet sheet = workbook.createSheet("PAPERVOLIST");
		// 设置列宽
		ExportExcelUtil.setSheetColumnWidth(sheet);
		// 获取样式
		CellStyle style = ExportExcelUtil.createTitleStyle(workbook);
		if (paperVoList!= null&&paperVoList.size()>0) {
			//创建表头
			Row row = sheet.createRow(0);// 建立新行
				if(titles!=null&&titles.length>0){
					for(int i=0;i<titles.length;i++){
						ExportExcelUtil.createCell(row, i, style, Cell.CELL_TYPE_STRING,titles[i]);
					}
				}
			//填充数据
			for (int j = 0; j < paperVoList.size(); j++) {
				PaperVO paperVO = paperVoList.get(j);
				Row row1 = sheet.createRow(j + 1);// 建立新行
//				"选题","	学生","	院系",	班级",	"老师",	"论文状态"
				ExportExcelUtil.createCell(row1, 0, style,Cell.CELL_TYPE_STRING,StringUtils.isNoneBlank(paperVO.getTopic()) ? paperVO.getTopic():"");
				ExportExcelUtil.createCell(row1, 1, style,Cell.CELL_TYPE_STRING,StringUtils.isNoneBlank(paperVO.getStudentName()) ? paperVO.getStudentName():"");
				ExportExcelUtil.createCell(row1, 2, style,Cell.CELL_TYPE_STRING,StringUtils.isNoneBlank(paperVO.getDepartment())?paperVO.getDepartment():"");
				ExportExcelUtil.createCell(row1, 3, style,Cell.CELL_TYPE_STRING,StringUtils.isNoneBlank(paperVO.getClassName())?paperVO.getClassName():"");
				ExportExcelUtil.createCell(row1, 4, style,Cell.CELL_TYPE_STRING,StringUtils.isNoneBlank(paperVO.getTeacherName())?paperVO.getTeacherName():"");
				String state_txt = "";
				Integer paper_status = paperVO.getPaperStatus();
				if(paper_status == 0){
					state_txt = "通过";
				}else if(paper_status == 1){
					state_txt = "未通过";
				}else if(paper_status == 2){
					state_txt = "待审核";
				}
				ExportExcelUtil.createCell(row1, 5, style,Cell.CELL_TYPE_STRING,state_txt);
			}
		}else{
			ExportExcelUtil.createCell(sheet.createRow(0), 0, style,
					Cell.CELL_TYPE_STRING, "查无数据");

		}
		return workbook;
	}
}
