package com.xing.paper.utils;

import java.util.ArrayList;
import java.util.List;

public class GenerateStatusUtil {
	
	public static List<String> generateStatus(){
		List<String> res = new ArrayList<String>();
		res.add("选题通过");
		res.add("开题报告通过");
		res.add("中期检查通过");
		res.add("预答辩通过");
		res.add("二次答辩准备");
		res.add("格式问题退回修改");
		res.add("终期答辩通过");
		return res;
	}

}
