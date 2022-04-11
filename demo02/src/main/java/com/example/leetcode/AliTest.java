package com.example.leetcode;

import java.util.*;

public class AliTest {
//    public static void main(String[] args) {
//        Scanner sc=new Scanner(System.in);
//        int n = sc.nextInt();
//        List<Integer> dis=new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//             int d = sc.nextInt();
//             dis.add(d);
//        }
//        sc.close();
//        if(dis.size()==2) System.out.println(Math.abs(dis.get(0)-dis.get(1)));
//        else{
//            int l=dis.get(0);
//            int r=dis.get(dis.size()-1);
//            int max=Integer.MAX_VALUE;
//            for (int i = 0; i < dis.size(); i++) {
//                int le=Math.abs(l-dis.get(i));
//                int ri=Math.abs(r-dis.get(i));
//                int res= Math.abs(ri-le);
//                max=Math.min(max,res);
//            }
//            System.out.println(max);
//        }
//
//
//    }
//public static void main(String[] args) {
//    Scanner sc=new Scanner(System.in);
//    int n = sc.nextInt();
//    sc.nextLine();
//    List<Integer> nums=new ArrayList<>();
//   int[][] res=new int[n+1][7];
//
//    for (int i = 0; i < n; i++) {
//       nums.add(sc.nextInt());
//    }
//    sc.close();
//    res[0][0]=Integer.MIN_VALUE;
//    res[0][1]=Integer.MIN_VALUE;
//    for (int i = 1; i < n; i++) {
//        int x=nums.get(i-1)%7;
//        res[i][0]=Math.max(res[i-1][0],res[i-1][(7-x)%7]+nums.get(i-1));
//        res[i][1]=Math.max(res[i-1][1],res[i-1][(7-x+1)%7]+nums.get(i-1));
//        res[i][2]=Math.max(res[i-1][2],res[i-1][(7-x+2)%7]+nums.get(i-1));
//        res[i][3]=Math.max(res[i-1][3],res[i-1][(7-x+3)%7]+nums.get(i-1));
//        res[i][4]=Math.max(res[i-1][4],res[i-1][(7-x+4)%7]+nums.get(i-1));
//        res[i][5]=Math.max(res[i-1][5],res[i-1][(7-x+5)%7]+nums.get(i-1));
//        res[i][6]=Math.max(res[i-1][6],res[i-1][(7-x+6)%7]+nums.get(i-1));
//    }
//    System.out.println(res[n][0]);
//
//}
//public static void main(String[] args) {
//    Scanner sc=new Scanner(System.in);
//        int n = sc.nextInt();
//        sc.nextLine();
//        List<Integer> nums=new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//             nums.add(sc.nextInt());
//        }
//        sc.close();
//        Collections.sort(nums);
//        int max=Integer.MIN_VALUE;
//    for (int i = nums.size() - 1; i >= 0; i--) {
//       int l=i-1;
//       if(max>nums.get(i)) break;
//        while(l>0){
//            int sum = nums.get(i) + nums.get(l);
//            if(sum%7==0){
//                max=Math.max(max,sum);
//                break;
//            }
//            else l--;
//        }
//    }
//    System.out.println(max);
//}
//
//    public static void main(String[] args) {
//            Scanner sc=new Scanner(System.in);
//            int n = sc.nextInt();
//        sc.nextLine();
//        int res=0;
//        List<Integer> nums=new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            int d = sc.nextInt();
//            nums.add(d);
//            res+=d;
//
//        }
//        sc.close();
//        int l=3;
//        while (l<nums.size()){
//            for (int i = 0; i < n-l+1; i++) {
//                List<Integer> temp=new ArrayList<>();
//                for (int j = l; j > 0; j--) {
//                    temp.set(j,nums.get(i+j));
//                }
//                Collections.sort(temp);
//                res+=temp.get((l-1)>>1);
//            }
//            l+=2;
//        }
//        System.out.println(res);
//    }
public static TreeNode createBiTree(TreeNode root, int[] a, int i) {
    if (i < a.length) {
        if (a[i] == 0) {
            root = null;
        } else {
            TreeNode left = new TreeNode();
            TreeNode right = new TreeNode();
            root.val = a[i];
            root.left = createBiTree(left, a, ++i);
            root.right = createBiTree(right, a, ++i);
        }
    }
    return root;
}

    public static void main(String[] args) {
        AliTest al=new AliTest();
        al.findx();
        Scanner sc=new Scanner(System.in);
        int n = sc.nextInt();
        int res=0;
        for (int i = 0; i < n; i++) {
            int l=sc.nextInt();
            sc.nextLine();
            String s = sc.nextLine();
            Map<Character, Integer> map=new HashMap<>();
            for (int j = 0; j < 3*l; j++) {
                char c = s.charAt(j);
                if(map.containsKey(c)){
                    map.put(c,map.get(c)+1);
                }
                map.put(c,1);
            }
            int la=map.get('A');
            int lb=map.get('B');
            int lc=map.get('C');
            if(la==lb&&la==lc){
                res=0;
            }
            if(la==0||lb==0||lc==0){
                res=2;
            }
            List<Integer> lista=new ArrayList<>();
            List<Integer> listb=new ArrayList<>();
            List<Integer> listc=new ArrayList<>();
                for (int j = 0; j < 3*l; j++) {
                    char c = s.charAt(j);
                    if(c=='A'){
                        int t=j;
                        while (j<3*l&&c==s.charAt(j+1)){
                            j++;
                        }
                        lista.add(j-t);
                    }else if(c=='B'){
                        int t=j;
                        while (j<3*l&&c==s.charAt(j+1)){
                            j++;
                        }
                        listb.add(j-t);
                    }else{
                        int t=j;
                        while (j<3*l&&c==s.charAt(j+1)){
                            j++;
                        }
                        listc.add(j-t);
                    }
                }
                Collections.sort(lista, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2-o1;
                    }
                });
                Collections.sort(listb, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2-o1;
                    }
                });
                Collections.sort(listc, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2-o1;
                    }
                });
                if(la>n){
                    int tb=n-lb;
                    int tc=n-lc;
                    for (int k = 0; k < lista.size(); k++) {
                        int t=lista.get(k);
                        if(t>Math.max(tb,tc)){

                        }
                    }

                }

            System.out.println(res);
        }

    }

    public void findx(){
        Scanner sc=new Scanner(System.in);
        int n = sc.nextInt();
        long start=sc.nextLong();
        sc.nextLine();
        String s = sc.nextLine();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c=='U'){
                if(start==1){
                    continue;
                }
                start=start/2;
            }else if(c=='R'){
                start=start*2+1;
            }else {
                start=start*2;
            }
        }
        System.out.println(start);
    }

    public void color() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int l = sc.nextInt();
            sc.nextLine();
            String s = sc.nextLine();
            Map<Character, Integer> map = new HashMap<>();
            for (int j = 0; j < 3 * l; j++) {
                char c = s.charAt(j);
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                }
                map.put(c, 1);
            }
            int la =(map.get('A')-l)>0?(map.get('A')-l):0;
            int lb =(map.get('B')-l)>0?(map.get('b')-l):0;
            int lc = (map.get('C')-l)>0?(map.get('C')-l):0;

            if (la == 0 && lb == 0 && lc == 0) {
                System.out.println(0);
            } else if ((la > 0 && lb > 0) || (la > 0 && lc > 0) || (lc > 0 && lb > 0)) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }
        }
    }
}
