
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class InfectStatistic
{
    public static Map<String, List<Integer>> province_map=new HashMap<>();
    public static Map<String, List<String>>  cmd_map=new HashMap<>();
    public static List<String> type=new ArrayList<>();
    public static List<String> cmd_province=new ArrayList<>();
    public static String[] province =new String[]{
                                     "安徽","北京","重庆","福建","甘肃",
                                     "广东","广西","贵州","海南","河北",
                                     "河南","黑龙江","湖北","湖南","吉林",
                                     "江苏","江西","辽宁","内蒙古","宁夏",
                                     "青海","山东","山西","陕西","上海",
                                     "四川","天津","西藏","新疆","云南","浙江"};
    public static void main(String[] args)
    {
         String logfile_path ="",outfile_path="",data="";
        List<Integer> put_list=new ArrayList<>();
        for(int i=0;i<args.length;i++)
        {
            if(args[i].equals("-log"))
                logfile_path=args[i+1];
           else if(args[i].equals("-out"))
                outfile_path=args[i+1];
           else if(args[i].equals("-date"))
                data=args[i+1];
            else if(args[i].equals("-type"))
            {
                while (!args[i+1].equals("-log")&&!args[i+1].equals("-out")&&!args[i+1].equals("-date")
                        &&!args[i+1].equals("-province"))
                {
                    type.add(args[i+1]);
                    i++;
                    if(i==args.length-1)
                        break;
                }
                i--;
            }
            else if(args[i].equals("-province"))
            {
                while (!args[i+1].equals("-log")&&!args[i+1].equals("-out")&&!args[i+1].equals("-date")
                        &&!args[i+1].equals("-type"))
                {
                    cmd_province.add(args[i+1]);
                    i++;
                    if(i==args.length-1)
                        break;
                }
                i--;
            }
        }
        for(int i=0;i<4;i++)
            put_list.add(0);//4数字个顺序表示感染患者、疑似患者、治愈、死亡
        for(int i=0;i<province.length;i++ )
            province_map.put(province[i],put_list);
        ReadFile(logfile_path,data);
        WriteFile(outfile_path);
    }

    private static void ReadFile(String file_path,String date)
    {
        File dir_file = new File(file_path);
        if (!dir_file.exists())
        {
            System.out.println("do not exit");
            return;
        }
        File[] file_list = dir_file.listFiles();
        if(file_list==null)
            return;
        for (int i = 0; i < file_list.length; i++) {
            File log_file = file_list[i];
            String filename = log_file.getName();
            String[] log_name=filename.trim().split("\\.");
            if (date.equals("")||CompareDate(date, log_name[0])) {
                try {
                    FileReader reader = new FileReader(log_file);
                    BufferedReader br = new BufferedReader(reader);
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] arr = line.trim().split("\\s+");//字符串按空格分割开
                        String[] str = arr[arr.length - 1].trim().split("人");
                        int people_num = Integer.parseInt(str[0].trim());//获得人数
                        if (arr[1].equals("新增")) {
                            if (arr[2].equals("感染患者")) {
                                List<Integer> list = new ArrayList<>();
                                list.addAll(province_map.get(arr[0]));
                                int value = list.get(0).intValue();
                                list.set(0, value + people_num);
                                province_map.put(arr[0], list);
                            } else if (arr[2].equals("疑似患者")) {
                                List<Integer> list = new ArrayList<>();
                                list.addAll(province_map.get(arr[0]));
                                int value = list.get(1).intValue();
                                list.set(1, value + people_num);
                                province_map.put(arr[0], list);
                            }
                        }
                        if (arr[2].equals("流入")) {
                            if (arr[1].equals("感染患者")) {
                                List<Integer> list = new ArrayList<>();
                                list.addAll(province_map.get(arr[0]));
                                int value = list.get(0).intValue();
                                list.set(0, value - people_num);
                                province_map.put(arr[0], list);
                                List<Integer> list1 = new ArrayList<>();
                                list.addAll(province_map.get(arr[3]));
                                int value1 = list1.get(0).intValue();
                                list1.set(0, value1 + people_num);
                                province_map.put(arr[3], list1);
                            }
                            if (arr[1].equals("疑似患者")) {
                                List<Integer> list = new ArrayList<>();
                                list.addAll(province_map.get(arr[0]));
                                int value = list.get(1).intValue();
                                list.set(1, value - people_num);
                                province_map.put(arr[0], list);
                                List<Integer> list1 = new ArrayList<>();
                                list.addAll(province_map.get(arr[3]));
                                int value1 = list1.get(1).intValue();
                                list1.set(1, value1 + people_num);
                                province_map.put(arr[3], list1);
                            }
                        }
                        if (arr[1].equals("死亡")) {
                            List<Integer> list = new ArrayList<>();
                            list.addAll(province_map.get(arr[0]));
                            int value = list.get(3).intValue();
                            int value1 = list.get(0).intValue();
                            list.set(3, value + people_num);
                            list.set(0, value1 - people_num);
                            province_map.put(arr[0], list);
                        }
                        if (arr[1].equals("治愈")) {
                            List<Integer> list = new ArrayList<>();
                            list.addAll(province_map.get(arr[0]));
                            int value = list.get(2).intValue();
                            int value1 = list.get(0).intValue();
                            list.set(2, value + people_num);
                            list.set(0, value1 - people_num);
                            province_map.put(arr[0], list);
                        }
                        if (arr[1].equals("排除")) {
                            List<Integer> list = new ArrayList<>();
                            list.addAll(province_map.get(arr[0]));
                            int value = list.get(1).intValue();
                            list.set(1, value + people_num);
                            province_map.put(arr[0], list);
                        }
                        if (arr[1].equals("疑似患者")) {
                            List<Integer> list = new ArrayList<>();
                            list.addAll(province_map.get(arr[0]));
                            int value = list.get(1).intValue();
                            int value1 = list.get(0).intValue();
                            list.set(1, value - people_num);
                            list.set(1, value1 + people_num);
                            province_map.put(arr[0], list);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static boolean CompareDate(String data1, String date2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            Date dt1 = df.parse(data1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime())
                return true;
             else if (dt1.getTime() < dt2.getTime())
                return false;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return true;

    }
    private static void WriteFile(String file_path)
    {
        int infected_num=0,suspected_num=0,cure_num=0,death_num=0;
        for(int i=0;i<province.length;i++)
        {
            List<Integer> list = province_map.get(province[i]);
            infected_num = infected_num + list.get(0);
            suspected_num = suspected_num + list.get(1);
            cure_num = cure_num + list.get(2);
            death_num = death_num + list.get(3);
        }
        if(cmd_province.contains("全国")||cmd_province.size()==0)
        {
            String name="全国";
            if(type.size()==0)
               System.out.println("全国"+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                        +"死亡"+death_num+"人 ");
            else
            {
                for (int n = 0; n < type.size(); n++) {
                    if (type.get(n).equals("ip"))
                        name = name + " 感染患者" + infected_num + "人";
                    if (type.get(n).equals("sp"))
                        name = name + " 疑似患者" + suspected_num + "人";
                    if (type.get(n).equals("cure"))
                        name = name + " 治愈" + cure_num + "人";
                    if (type.get(n).equals("dead"))
                        name = name + " 死亡" + death_num + "人";
                }
                System.out.println(name);
            }
        }
        for(int i=0;i<province.length;i++)
        {
            List<Integer> list=province_map.get(province[i]);
            infected_num=list.get(0);
            suspected_num=list.get(1);
            cure_num=list.get(2);
            death_num=list.get(3);
            if(cmd_province.size()==0&&((list.get(0)!=0)||(list.get(1)!=0)||(list.get(2)!=0)||(list.get(3)!=0)))
            {
                if(type.size()==0)
                    System.out.println(province[i]+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                            +"死亡"+death_num+"人 ");
                else
                {
                    String name=province[i];
                    for(int n=0;n<type.size();n++)
                    {
                        if(type.get(n).equals("ip"))
                            name=name+" 感染患者"+infected_num+"人";
                        if(type.get(n).equals("sp"))
                            name=name+" 疑似患者"+suspected_num+"人";
                        if(type.get(n).equals("cure"))
                            name=name+" 治愈"+cure_num+"人";
                        if(type.get(n).equals("dead"))
                            name=name+" 死亡"+death_num+"人";
                    }
                    System.out.println(name);
                }
            }
            else if(cmd_province.contains(province[i]))
            {
                if(type.size()==0)
                    System.out.println(province[i]+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                            +"死亡"+death_num+"人 ");
                else
                {
                    String name=province[i];
                    for(int n=0;n<type.size();n++)
                    {
                        if(type.get(n).equals("ip"))
                            name=name+" 感染患者"+infected_num+"人";
                        if(type.get(n).equals("sp"))
                            name=name+" 疑似患者"+suspected_num+"人";
                        if(type.get(n).equals("cure"))
                            name=name+" 治愈"+cure_num+"人";
                        if(type.get(n).equals("dead"))
                            name=name+" 死亡"+death_num+"人";
                    }
                    System.out.println(name+"\n");
                }
            }
        }
        try {
            File writeName = new File(file_path); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for(int i=0;i<province.length;i++)
                {
                    List<Integer> list = province_map.get(province[i]);
                    infected_num = infected_num + list.get(0);
                    suspected_num = suspected_num + list.get(1);
                    cure_num = cure_num + list.get(2);
                    death_num = death_num + list.get(3);
                }
                if(cmd_province.contains("全国")||cmd_province.size()==0)
                {
                    String name="全国";
                    if(type.size()==0)
                        out.write("全国"+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                                +"死亡"+death_num+"人 "+"\n");
                    else
                    {
                        for (int n = 0; n < type.size(); n++) {
                            if (type.get(n).equals("ip"))
                                name = name + " 感染患者" + infected_num + "人";
                            if (type.get(n).equals("sp"))
                                name = name + " 疑似患者" + suspected_num + "人";
                            if (type.get(n).equals("cure"))
                                name = name + " 治愈" + cure_num + "人";
                            if (type.get(n).equals("dead"))
                                name = name + " 死亡" + death_num + "人";
                        }
                        out.write(name+"\n");
                    }
                }
                for(int i=0;i<province.length;i++)
                {
                    List<Integer> list=province_map.get(province[i]);
                    infected_num=list.get(0);
                    suspected_num=list.get(1);
                    cure_num=list.get(2);
                    death_num=list.get(3);
                    if(cmd_province.size()==0&&((list.get(0)!=0)||(list.get(1)!=0)||(list.get(2)!=0)||(list.get(3)!=0)))
                    {
                        if(type.size()==0)
                            out.write(province[i]+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                                    +"死亡"+death_num+"人 "+"\n");
                        else
                        {
                            String name=province[i];
                            for(int n=0;n<type.size();n++)
                            {
                                if(type.get(n).equals("ip"))
                                    name=name+" 感染患者"+infected_num+"人";
                                if(type.get(n).equals("sp"))
                                    name=name+" 疑似患者"+suspected_num+"人";
                                if(type.get(n).equals("cure"))
                                    name=name+" 治愈"+cure_num+"人";
                                if(type.get(n).equals("dead"))
                                    name=name+" 死亡"+death_num+"人";
                            }
                            out.write(name+"\n");
                        }
                    }
                    else if(cmd_province.contains(province[i]))
                    {
                        if(type.size()==0)
                            out.write(province[i]+" "+"感染患者"+infected_num+"人 "+"疑似患者"+suspected_num+"人 "+"治愈"+cure_num+"人 "
                                    +"死亡"+death_num+"人 "+"\n");
                        else
                        {
                            String name=province[i];
                            for(int n=0;n<type.size();n++)
                            {
                                if(type.get(n).equals("ip"))
                                    name=name+" 感染患者"+infected_num+"人";
                                if(type.get(n).equals("sp"))
                                    name=name+" 疑似患者"+suspected_num+"人";
                                if(type.get(n).equals("cure"))
                                    name=name+" 治愈"+cure_num+"人";
                                if(type.get(n).equals("dead"))
                                    name=name+" 死亡"+death_num+"人";
                            }
                            out.write(name+"\n");
                        }
                    }
                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
