# 定时任务Quartz框架

## Quartz-quickstart 

三个基本概念
1、调度器（Scheduler）：负责定时任务的启动和关闭
2、触发器（Trigger）：负责制定执行周期，也就是什么时候执行定时任务
3、Job：具体执行什么样的任务，具体执行的内容是由JobDetai来负责，Job本身没有什么属性，内容属性都是在JobDetail

