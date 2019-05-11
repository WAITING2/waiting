package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xing.paper.dao.SelectionDao;
import com.xing.paper.po.SelectionPo;
import com.xing.paper.po.StudentPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;
import com.xing.paper.service.SelectionService;

@Service
public class SelectionServiceImpl implements SelectionService {

	@Resource
	SelectionDao selectionDao;

	@Override
	public boolean addSelection(SelectionPo selection) {
		if (selectionDao.queryByStudent(selection.getStudent()) == null
				&& selectionDao.queryByTopic(selection.getTopic()) == null) {
			selectionDao.addSelection(selection);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void deleteSelection(SelectionPo selection) {
		selectionDao.deleteSelection(selection);
	}

	@Override
	public void upgradeSelection(SelectionPo selection) {
		selectionDao.upgradeSelection(selection);
	}

	@Override
	public SelectionPo queryByTopic(TopicPo topic) {
		return selectionDao.queryByTopic(topic);
	}

	@Override
	public List<SelectionPo> queryByTeacher(TeacherPo teacher) {
		return selectionDao.queryByTeacher(teacher);
	}

	@Override
	public SelectionPo queryByStudent(StudentPo student) {
		return selectionDao.queryByStudent(student);
	}

	@Override
	public List<SelectionPo> getAllSelection() {
		return selectionDao.getAllSelection();
	}

	@Override
	public List<SelectionPo> getAllFinishSelection() {
		return selectionDao.getAllFinishSelection();
	}

}
