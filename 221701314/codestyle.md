# 代码规范
+ ###缩进&每行最多字符
   使用8个空格作为缩进。
   控制每行的长度不超过80个字符，以便阅读和维护。 

 当语句过长，应按以下规则进行断行： 
 * 在一个标点符后进行断行
 * 在一个操作符前进行断行
 * 按操作优先级由高到低进行断行
 * 同级别的表达式断行后左对齐
 * 如果以上规则导致代码混乱或代码太过靠向右侧，仅使用8个空格进行一次缩进即可
 


----------


+ ###变量命名
   所有字母均小写，每个单词间以下划线分割，样子像 “nginx_vip”
   my_data是一个下划线命名的示例


----------


+ ###函数最大行数
 代码行最多不会超过50行
通常来说，在阅读一个函数的时候，如果视需要跨过很长的垂直距离会非常影响代码的阅读体验。如果需要来回滚动眼球或代码才能看全一个方法，就会很影响思维的连贯性，对阅读代码的速度造成比较大的影响。最好的情况是在不滚动眼球或代码的情况下一眼就能将该方法的全部代码映入眼帘。


----------


+ ###函数和类命名
 * 类名：
 首字母大写，每个单词首字母大写（大驼峰命名法）
尽量使用能够反映类功能的名词短语
eg：UserManage ,UserData等
 * 方法名：
 首字母小写，之后每个单词首字母都大写(小驼峰法命名法)
方法名使用动词短语


----------


+ ###常量
常量的名字应该都使用大写字母，并且指出该常量完整含义。如果一个常量名称由多个单词组成，则应该用下划线来分割这些单词。 


----------


+ ###空行规则
规则一：定义变量后要空行。尽可能在定义变量的同时初始化该变量，即遵循就近原则。如果变量的引用和定义相隔比较远，那么变量的初始化就很容易被忘记。若引用了未被初始化的变量，就会导致程序出错。
规则二：每个函数定义结束之后都要加空行。
总规则：两个相对独立的程序块、变量说明之后必须要加空行。比如上面几行代码完成的是一个功能，下面几行代码完成的是另一个功能，那么它们中间就要加空行。这样看起来更清晰。


----------


+ ###注释
文件注释：文件注释写入文件头部。
例如：
/\*
\* 文件名：[文件名]
\* 作者：〈版权〉
\* 描述：〈描述〉
\* 修改人：〈修改人〉
\* 修改时间：YYYY-MM-DD
 \* 修改内容：〈修改内容〉
 \*/
类和接口的注释：该注释放在class定义之前，using或package关键字之后。
例如：
package com. websmap.comm;
/\*\*
 \* 注释内容
 */
 public class CommManager

类属性、公有和保护方法注释：写在类属性、公有和保护方法上面。用//来注释,需要对齐被注释代码。
示例：
/ /注释内容
private  String logType


----------

