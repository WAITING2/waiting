package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.SelectionPo;
import com.xing.paper.po.StudentPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;

public interface SelectionDao {
	
	public void addSelection(SelectionPo selection);
	public void deleteSelection(SelectionPo selection);
	public void upgradeSelection(SelectionPo selection);
	public SelectionPo queryByTopic(TopicPo topic);
	public List<SelectionPo> queryByTeacher(TeacherPo teacher);
	public SelectionPo queryByStudent(StudentPo student);
	public List<SelectionPo> getAllSelection();
	public List<SelectionPo> getAllFinishSelection();
}
