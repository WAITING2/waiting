package com.xing.paper.utils;

import java.util.ArrayList;
import java.util.List;

public class GenerateTeacherTitleUtil {
	
	public static List<String> generateTeacherTitle(){
		List<String> res = new ArrayList<String>();
		res.add("助理讲师");
		res.add("讲师");
		res.add("高级讲师");
		res.add("副教授");
		res.add("教授");
		return res;
	}

}
