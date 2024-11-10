package com.util;

import java.util.*;

public class UserCFDemo{

	 //系统用户
    public static String[] users={"小明","小花","小美","小张","小李"};
    //和这些用户相关的商品
    public static String[] movies={"商品1","商品2","商品3","商品4","商品5","商品6","商品7"};
    //用户点评商品打星数据,是users对应用户针对movies对应商品的评分
    public static int[][] allUserMovieStarList={
            {0,0,8},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,5},
            {0,0,10},
            {0,0,10}

    };
    public static int membernum = 0; //会员数  5
    public static int mvnum = 0;//影片数  7
    
    //相似用户集合
    private static List<List<Object>> similarityUsers=null;
    //推荐所有商品集合
    private static List<String> targetRecommendMovies=null;
    //点评过商品集合
    private static List<String> commentedMovies=null;
    //用户在商品打星集合中的位置
    private static Integer targetUserIndex=null;
    
    public static List mvlist(String realname){
//    	for(int i=0;i<users.length;i++){  
//            System.out.println("aaaaaaa="+users[i]);  
//        }  
//    	for(int i=0;i<movies.length;i++){  
//            System.out.println("bbbbbbb="+movies[i]);  
//        }  
//    	
//    	System.out.println("realname=="+realname);
    	ArrayList rtnlist = new ArrayList();
            targetUserIndex=getUserIndex(realname);
            if(targetUserIndex==null){
                System.out.println("没有搜索到此用户，请重新输入：");
            }else{
                //计算用户相似度
                calcUserSimilarity();
                //计算商品推荐度，排序
                calcRecommendMovie();
                //处理推荐商品列表
                handleRecommendMovies();
                //输出推荐商品
                //System.out.print("推荐商品列表：");
                System.out.println("targetRecommendMovies=="+targetRecommendMovies);
                for (String item:targetRecommendMovies){
                	//System.out.println("item=="+item);
                	System.out.println("commentedMovies=="+commentedMovies);
                    if(!commentedMovies.contains(item)){
                    	//System.out.println("item=="+item);
                        //System.out.print(item+"  ");
                        rtnlist.add(item);
                    }
                }
            }

            targetRecommendMovies=null;
            //System.out.println("rtnlist="+rtnlist.size());
        return rtnlist;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String user=scanner.nextLine();
        while (user!=null && !"exit".equals(user)){
            targetUserIndex=getUserIndex(user);
            if(targetUserIndex==null){
                System.out.println("没有搜索到此用户，请重新输入：");
            }else{
                //计算用户相似度
                calcUserSimilarity();
                //计算商品推荐度，排序
                calcRecommendMovie();
                //处理推荐商品列表
                handleRecommendMovies();
                //输出推荐商品
                System.out.print("推荐商品列表：");
                for (String item:targetRecommendMovies){
                    if(!commentedMovies.contains(item)){
                        System.out.print(item+"  ");
                    }
                }
                System.out.println();
            }

            user=scanner.nextLine();
            targetRecommendMovies=null;
        }

    }

    /**
     * 把推荐列表中用户已经点评过的商品剔除
     */
    private static void handleRecommendMovies(){
        commentedMovies=(List<String>) new ArrayList<String>();
        for (int i=0;i<allUserMovieStarList[targetUserIndex].length;i++){
            if(allUserMovieStarList[targetUserIndex][i]!=0){
                commentedMovies.add(movies[i]);
            }
        }
    }


    /**
     * 获取全部推荐商品,计算平均商品推荐度
     */
    private static void calcRecommendMovie(){
        targetRecommendMovies=new ArrayList<String>();
        List<List<Object>> recommendMovies=new ArrayList<List<Object>>();
        List<Object> recommendMovie=null;
        double recommdRate=0,sumRate=0;
        for (int i=0;i<mvnum;i++){
            recommendMovie=new ArrayList<Object>();
            recommendMovie.add(i);
            recommdRate=allUserMovieStarList[Integer.parseInt(similarityUsers.get(0).get(0).toString())][i]*Double.parseDouble(similarityUsers.get(0).get(1).toString())
                    +allUserMovieStarList[Integer.parseInt(similarityUsers.get(1).get(0).toString())][i]*Double.parseDouble(similarityUsers.get(1).get(1).toString());
            recommendMovie.add(recommdRate);
            recommendMovies.add(recommendMovie);
            sumRate+=recommdRate;
        }

        sortCollection(recommendMovies,-1);

        for (List<Object> item:recommendMovies){
            if(Double.parseDouble(item.get(1).toString()) > sumRate/mvnum){ //大于平均推荐度的商品才有可能被推荐
                targetRecommendMovies.add(movies[Integer.parseInt(item.get(0).toString())]);
            }
        }
    }

    /**
     * 获取两个最相似的用户
     */
    private static void calcUserSimilarity(){
        similarityUsers=new ArrayList<List<Object>>();
        List<List<Object>> userSimilaritys=new ArrayList<List<Object>>();
        for (int i=0;i<membernum;i++){
            if(i==targetUserIndex){
                continue;
            }
            List<Object> userSimilarity=new ArrayList<Object>();
            userSimilarity.add(i);
            userSimilarity.add(calcTwoUserSimilarity(allUserMovieStarList[i],allUserMovieStarList[targetUserIndex]));
            userSimilaritys.add(userSimilarity);
        }

        sortCollection(userSimilaritys,1);

        similarityUsers.add(userSimilaritys.get(0));
        similarityUsers.add(userSimilaritys.get(1));
    }

    /**
     * 根据用户数据，计算用户相似度
     * @param user1Stars
     * @param user2Starts
     * @return
     */
    private static double calcTwoUserSimilarity(int[] user1Stars,int[] user2Starts){
        float sum=0;
        for(int i=0;i<mvnum;i++){
            sum+=Math.pow(user1Stars[i]-user2Starts[i],2);
        }
        return Math.sqrt(sum);
    }

    /**
     * 查找用户所在的位置
     * @param user
     * @return
     */
    private static Integer getUserIndex(String user){
        if(user==null || "".contains(user)){
            return null;
        }

        for(int i=0;i<users.length;i++){
            if(user.equals(users[i])){
                return i;
            }
        }

        return null;
    }

    /**
     * 集合排序
     * @param list
     * @param order  1正序 -1倒序
     */
    private static void sortCollection(List<List<Object>> list,final int order){
        Collections.sort(list, new Comparator<List<Object>>() {
            public int compare(List<Object> o1, List<Object> o2) {
                if(Double.valueOf(o1.get(1).toString()) > Double.valueOf(o2.get(1).toString())){
                    return order;
                }else if(Double.valueOf(o1.get(1).toString()) < Double.valueOf(o2.get(1).toString())){
                    return -order;
                }else{
                    return 0;
                }
            }
        });
    }
}

