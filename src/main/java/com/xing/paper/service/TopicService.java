package com.xing.paper.service;

import java.util.List;

import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;
import com.xing.paper.po.TopicVo;

public interface TopicService {
	
	public void addTopic(TopicPo topic);
	public void deleteTopic(TopicPo topic);
	public void upgradeTopic(TopicPo topic);
	public List<TopicPo> queryByTeacher(TeacherPo teacher);
	public TopicPo queryByTopicId(String id);
	public List<TopicPo> getAllTopic();
	public List<TopicVo> getTopicVoByTeacher(TeacherPo teacher);
	public List<TopicPo> getNotSelectedTopic();
}
