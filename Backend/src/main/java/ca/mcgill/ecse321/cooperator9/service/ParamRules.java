package ca.mcgill.ecse321.cooperator9.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ParamRules {
	@Around("execution(* *(..)) && @annotation(Fuck)")
	public boolean ParamRule(ProceedingJoinPoint point) {
		System.out.println(point.getTarget().toString());
		return false;
		
	}
}


