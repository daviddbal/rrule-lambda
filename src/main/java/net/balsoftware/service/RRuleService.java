package net.balsoftware.service;

import java.util.List;

import net.balsoftware.bean.RRule;
import net.balsoftware.dao.RRuleDao;

public class RRuleService
{
	private RRuleDao dao = new RRuleDao();
	
	public RRule getRRule(int uid)
	{
		return dao.getRRule(uid);
	}
	
	public List<RRule> getAllRRule()
	{
		return dao.getAllRRules();
	}
	
	public RRule addRRule(RRule rrule)
	{
		return dao.addRRule(rrule);
	}
}
