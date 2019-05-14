package com.xing.paper.utils;

import java.util.ArrayList;
import java.util.List;

public class GenerateGradeUtil {
	
	public static List<String> generateGrade(){
		List<String> res = new ArrayList<String>();
		res.add("优秀");
		res.add("良好");
//		res.add("中等");
		res.add("及格");
		res.add("不及格");
		return res;
	}

}
