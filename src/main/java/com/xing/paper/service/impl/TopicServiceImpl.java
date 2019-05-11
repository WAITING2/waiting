package com.xing.paper.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xing.paper.dao.SelectionDao;
import com.xing.paper.dao.TopicDao;
import com.xing.paper.po.SelectionPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;
import com.xing.paper.po.TopicVo;
import com.xing.paper.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

	@Resource
	TopicDao topicDao;
	@Resource
	SelectionDao selectionDao;

	@Override
	public void addTopic(TopicPo topic) {
		topicDao.addTopic(topic);
	}

	@Override
	public void deleteTopic(TopicPo topic) {
		topicDao.deleteTopic(topic);
	}

	@Override
	public void upgradeTopic(TopicPo topic) {
		topicDao.upgradeTopic(topic);
	}

	@Override
	public List<TopicPo> queryByTeacher(TeacherPo teacher) {
		return topicDao.queryByTeacher(teacher);
	}

	@Override
	public TopicPo queryByTopicId(String id) {
		return topicDao.queryByTopicId(id);
	}

	@Override
	public List<TopicPo> getAllTopic() {
		return topicDao.getAllTopic();
	}

	@Override
	public List<TopicVo> getTopicVoByTeacher(TeacherPo teacher) {
		List<TopicPo> topicList = topicDao.queryByTeacher(teacher);
		List<TopicVo> voList = new ArrayList<TopicVo>();
		for(TopicPo topic : topicList){
			TopicVo topicVo = new TopicVo();
			SelectionPo selection = selectionDao.queryByTopic(topic);
			topicVo.setId(topic.getId());
			topicVo.setTopic(topic.getTopic());
			if(selection!=null){
				topicVo.setSelect(true);
				topicVo.setStatus(selection.getProcessing());
				topicVo.setStudent(selection.getStudent());
				topicVo.setGrade(selection.getGrade());
			}else{
				topicVo.setStatus("未被选题");
				topicVo.setSelect(false);
			}
			voList.add(topicVo);
		}
		return voList;
	}

	@Override
	public List<TopicPo> getNotSelectedTopic() {
		return topicDao.getNotSelectedTopic();
	}

}
