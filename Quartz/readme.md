# 定时任务Quartz框架

## Quartz-quickstart 

三个基本概念  
1、调度器（Scheduler）：负责定时任务的启动和关闭  
2、触发器（Trigger）：负责制定执行周期，也就是什么时候执行定时任务  
3、Job：具体执行什么样的任务，具体执行的内容是由JobDetail来负责，Job本身没有什么属性，内容属性都是在JobDetail  


一句话总结三者的关系：调度器按照触发器的规则来执行jobDetail  

一个JobDetai任务可以被多个触发器指定执行, 也就是 JobDetai和触发器的关系是 一对多  
 
一个触发器可以指定多个任务吗？答案是不可以的，一个触发器只能指定一个任务JobDetail

总结：
1、一个调度器可以控制多个触发器  
2、一个触发器只能指定一个JobDetail  
3、一个JobDetail可以被多个触发器指定  
4、一个Job中可以有多个JobDetail  


## quartz-springboot

整合SpringBoot 

