package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;

public interface TopicDao {
	
	public void addTopic(TopicPo topic);
	public void deleteTopic(TopicPo topic);
	public void upgradeTopic(TopicPo topic);
	public List<TopicPo> queryByTeacher(TeacherPo teacher);
	public TopicPo queryByTopicId(String id);
	public List<TopicPo> getAllTopic();
	public List<TopicPo> getNotSelectedTopic();
}
