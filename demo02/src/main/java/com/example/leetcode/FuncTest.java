package com.example.leetcode;

import java.util.*;

public class FuncTest  {

    public static void main(String[] args) {
       int[] nums={2,2,1,1,1,2,2};
        FuncTest ft = new FuncTest();

      int[] nums1={4,0,0,0,0,0};
      int[] nums2= {1,2,3,5,6,7};

       func( nums1, nums2);
        Arrays.stream(nums1).forEach(System.out::println);

    }

    private static void func(int[] nums1, int[] nums2) {

        nums1[2]=5;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        if(len <3) {
            return new ArrayList<>();
        }
        List<List<Integer>> result=new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < len; i++) {
            if(nums[i]>0) {
                break;
            }
            if(i>1&&nums[i]==nums[i-1]) {
                continue;
            }
            int l=i+1;
            int r=len-1;
            while(l<r){
                int sum=nums[l]+nums[i]+nums[r];
                if(sum==0){
                    result.add(Arrays.asList(nums[l],nums[i],nums[r]));
                    l--;
                    r++;
                }else if(sum>0) {l--;}
                else {r++;}
            }

        }

        return result;
    }
    public ListNode detectCycle(ListNode head) {
        ListNode fast=head,slow=head;
        while(true){
            if(fast==null||fast.next==null) {
                return null;
            }
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow) {
                break;
            }
        }

        fast=head;
        while (fast!=slow){
            fast=fast.next;
            slow=slow.next;
        }
        return fast;
    }
    public List<List<Integer>> levelOrder1(TreeNode root) {
        if(root==null) {
            return null;
        }
        List<List<Integer>> result=new ArrayList<>();
        level(root,0,result);
        return result;
    }

    public void level(TreeNode root,int le,List<List<Integer>> result){
        if(result.size()<le) {
            result.add(new ArrayList<>());
        }


        result.get(le-1).add(root.val);
        if(root.left!=null) {
            level(root.left,le+1,result);
        }
        if(root.right!=null) {
            level(root.right,le+1,result);
        }
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue=new PriorityQueue<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            queue.add(nums[i]);
            if(queue.size()>k){
                queue.poll();
            }
        }
        return queue.peek();

    }

    public int lengthOfLongestSubstring(String s) {
        int res=0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int fast=i;
            Set<Character> temp=new HashSet<>();
            while (fast < chars.length){
                if(temp.contains(chars[fast])){
                    break;
                }
                temp.add(chars[fast]);
                fast++;
            }

            res=Math.max(temp.size(),res);


        }


        return  res;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast=head,slow=head;
        for (int i = 0; i < n; i++) {
            fast=fast.next;
        }
        while (fast!=null){
            fast=fast.next;
            slow=slow.next;
        }

        ListNode temp = slow.next.next;
        slow.next.next=null;
        slow.next=temp;

        return head;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null) {
            return null;
        }
        System.out.println();
        List<List<Integer>> result=new ArrayList<>();
        level(root,0,result);
        for (int i = 0; i < result.size(); i++) {
            if(i%2==0){
               Collections.reverse(result.get(i));
            }
        }
        return result;
    }

   public void stringJudge(){
        Scanner sc=new Scanner(System.in);
       int n = sc.nextInt();
       sc.nextLine();
       Set<String> res=new HashSet<>();
       for(int i=0;i<n;i++) {
           String s = sc.nextLine();
           System.out.println("s:"+s);
           char[] a = s.toCharArray();
           System.out.println(a.length);
           if(res.contains(s)){
               System.out.println("existed");
               continue;
           }
           if(a.length>12||a.length<6){
               System.out.println("illegal length");
               continue;
           }
           for (int j = 0; j < a.length; j++) {
               if(!Character.isLetter(a[j])){
                   System.out.println("illegal charactor");
                   break;

               }
               if(j==a.length-1){
                   System.out.println("registration");
                   res.add(s);
               }
           }
       }
   }
    public void fun3(){
        Scanner sc = new Scanner(System.in);
        Map<Double,Integer> res = new HashMap<>();
        int n = sc.nextInt();
        for(int i =0;i<n;i++){
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();
            double k = (double)y/(double)x;
            if(!res.containsKey(k)){
                res.put(k,0);
            }else{
                res.put(k,res.get(k) + 1);
            }
        }
        int ret = 0;
        for(Map.Entry<Double,Integer> entry: res.entrySet()){
            ret += entry.getValue();
        }
        Set<Double> doubles = res.keySet();
        int tmp = doubles.size() - 1;
        int t = tmp * (tmp + 1) / 2;
        ret += t;
        System.out.println(ret);}
    public int search(int[] arr, int target) {
        int len = arr.length-1;
        if(arr[0]==target) {
            return 0;
        }
        int head=0,mid=0;
        while(head<=len){
            mid=(head+len)>>1;
            if(arr[mid]==target) {
                while(mid>0&&arr[mid-1]==arr[mid]){
                    mid--;
                }
                return mid;
            }
            if (arr[mid]<arr[len]) {
                if(arr[mid]<target&&arr[len] >= target) {
                    head=mid+1;
                } else {
                    len=mid-1;
                }
            } else if (arr[mid] >arr[len]) {
                if(arr[head]<=target&&arr[mid]>target) {
                    len=mid-1;
                } else {
                    head=mid+1;
                }
            }else {
                len--;
            }
        }
        return -1;
    }

    public void fastSortArray(int[] nums,int l,int r) {
        if(l<r){
            int key=nums[l];
            while(l<r){
                while(key<nums[r]&&l<r) {
                    r--;
                }
                while(l<r&&nums[l]<key) {
                    l++;
                }
                if(l<r) {
                   int temp=nums[l];
                   nums[l]=nums[r];
                   nums[r]=temp;
                }
            }

            nums[l]=key;
            fastSortArray(nums,0,l-1);
            fastSortArray(nums,l+1,r);
        }
    }

    public int compareVersion(String version1, String version2) {
        String[] s1 = version1.split("\\."),s2=version2.split("\\.");
        int i=0,j=0;
        int a=0,b=0;
        while(i<s1.length||j<s2.length){
            if(i<s1.length) {
                a=Integer.parseInt(s1[i]);
            }
            if(j<s2.length) {
                b=Integer.parseInt(s2[i]);
            }
            if(a!=b) {
                return a>b?1:-1;
            }
        }
        return 0;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head==null) {
            return head;
        }
        ListNode dummy=new ListNode(-1);
        dummy.next=head;
        ListNode temp=dummy;
        while(temp.next!=null&&temp.next.next!=null){
            if(temp.next.next.val==temp.next.val){
                int t=temp.next.val;
                while(temp.next!=null&&t==temp.next.val) {
                    temp.next=temp.next.next;
                }
            }else {
               temp=temp.next;
            }

        }
        return dummy.next;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        HashMap<Integer,Integer> me=new HashMap<>();
        int[] post=postorder;
        for (int i = 0; i < inorder.length; i++) {
            me.put(inorder[i],i);
        }
        return buildT(me,post,0,inorder.length-1,0,post.length-1 );
    }
    public TreeNode buildT(HashMap<Integer,Integer> me,int[] inorder,int is,int ie,int ps,int pe){
        if(is>ie||ps>pe) {
            return null;
        }
        TreeNode root=new TreeNode(inorder[pe]);
        int mid=me.get(inorder[pe]);
        root.left=buildT(me,inorder,is,mid-1,ps,ps+mid-is-1);
        root.right=buildT(me,inorder,mid+1,ie,ps+mid-is,pe-1);
        return  root;
    }


    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int[] dp=new int[len];
        for (int i = 1; i < len; i++) {
            if(dp[i-1]>0) {
                dp[i]=dp[i-1]+nums[i];
            }
            //另起炉灶
            else {
                dp[i]=nums[i];
            }
        }
        int max=0;
        for (int i = 0; i < len; i++) {
            max=Math.max(dp[i],max);
        }
        return max;
    }
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy=new ListNode(-1);
        PriorityQueue<ListNode> queue=new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
              if(o1.val-o2.val>0) {
                  return 1;
              } else if (o1.val-o2.val<0) {
                  return -1;
              } else {
                  return 0;
              }
            }
        });

        for (ListNode p : lists) {
            if(p!=null) {
                queue.add(p);
            }
        }
        ListNode head = dummy;
        while (!queue.isEmpty()){
            ListNode temp = queue.poll();
            if(temp.next!=null) {
                queue.add(temp.next);
            }
            head.next=temp;
            head=temp;

        }

        return dummy.next;
    }



    public String longestPalindrome(String s) {
        if(s.length()<2) {
            return s;
        }
        String res = null;
       int max=-1;
        char[] arr = s.toCharArray();
        int len=s.length();
        for (int i = 1; i < len; i++) {
            int l=i-1,r=i+1;
            while(l>0&&r<len-1){
                if(arr[l]==arr[r]){
                    l--;r++;
                }else {
                    break;
                }
            }
            int le=r-l+1;
            if(len>max){
                res = s.substring(l,r);

            }
        }

        return  res;
    }
    public String longestPalindrome1(String s) {

        if (s == null || s.length() == 0) {
            return "";
        }
        int strLen = s.length();
        int left = 0;
        int right = 0;
        int len = 1;
        int maxStart = 0;
        int maxLen = 0;

        for (int i = 0; i < strLen; i++) {
            left = i - 1;
            right = i + 1;
            while (left >= 0 && s.charAt(left) == s.charAt(i)) {
                len++;
                left--;
            }
            while (right < strLen && s.charAt(right) == s.charAt(i)) {
                len++;
                right++;
            }
            while (left >= 0 && right < strLen && s.charAt(right) == s.charAt(left)) {
                len = len + 2;
                left--;
                right++;
            }
            if (len > maxLen) {
                maxLen = len;
                maxStart = left;
            }
            len = 1;
        }
        return s.substring(maxStart + 1, maxStart + maxLen + 1);

    }

    public int numIslands(char[][] grid) {
        int res=0;
        int row=grid.length,col=grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
               if(grid[i][j]=='1'){
                   dfs(grid,i,j);
                   ++res;
               }
            }
        }

        return res;
    }

    private void dfs(char[][] grid, int i, int j) {
        if(i<0||i>grid.length||j<0||j>grid[0].length||grid[i][j]==0){
            return;
        }
        grid[i][j]=0;
        dfs(grid,i-1,j);
        dfs(grid,i+1,j);
        dfs(grid,i,j-1);
        dfs(grid,i,j+1);
    }

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        if(len ==0){
            return new ArrayList<>();
        }
        List<List<Integer>> res=new ArrayList<>();
        boolean[] used=new boolean[len];
        numsDfs(nums,res,used,0,new ArrayList<Integer>());


        return res;
    }

    private void numsDfs(int[] nums, List<List<Integer>> res,boolean[] used,int depth, ArrayList<Integer> temp) {
        if(depth==nums.length){
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if(!used[i]){
                used[i]=true;
                temp.add(nums[i]);
                numsDfs(nums,res,used,depth+1,temp);
                used[i]=false;
                temp.remove(temp.size()-1);

            }

        }

    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();

        zigLevel(res,1,root);
        for (int i = 0; i < res.size(); i++) {
            if(i%2==1){
                Collections.reverse(res.get(i));
            }
        }

        return  res;
    }

    private void zigLevel(List<List<Integer>> res, int depth, TreeNode root) {
        if(root==null) {
            return;
        }

        if(res.size()<depth){
            res.add(new ArrayList<Integer>());
        }
        res.get(depth-1).add(root.val);
        zigLevel(res,depth+1,root.left);
        zigLevel(res,depth+1,root.right);
    }


    public ListNode reverseList(ListNode head) {
        ListNode dummy=new ListNode(-1);
        ListNode tail=null;


        while(head!=null||head.next!=null){
            ListNode flag=head;
            head=head.next;
           flag.next=tail;
            dummy.next=flag;
           tail=flag;
        }
        return dummy.next;
    }

    public int[] twoSum(int[] nums, int target) {

        List<Integer> res=new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int tem=target-nums[i];
            int j=i+1;
            while(j<nums.length){
                if(tem==nums[j]){
                    res.add(i);
                    res.add(j);
                    break;
                }
            }
            if(res.size()==2){
                break;
            }
        }

        return  res.stream().mapToInt(Integer::valueOf).toArray();
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        indfs(res,root);
        return res;
    }

    private void indfs(List<Integer> res, TreeNode root) {
        if(root==null){
            return;
        }
        indfs(res,root.left);
        res.add(root.val);
        indfs(res,root.right);

    }

    public boolean hasCycle(ListNode head) {

        ListNode fast=head;
        ListNode slow=head;
        while (fast!=null&&fast.next!=null){
            if(fast==slow){
                return true;
            }
            slow=slow.next;
            fast=fast.next.next;
        }
        return false;
        }


    public int majorityElement(int[] nums) {

        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i])){
                int n = map.get(nums[i]) + 1;
                if(n>nums.length>>1){
                    return nums[i];
                }
                map.put(nums[i],n);

            }else {

            map.put(nums[i],1);
            }
        }
        return nums[0];
    }

    public boolean isValid(String s) {
        int len = s.length();
        Stack<Character> stack=new Stack<>();
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(s.charAt(i)=='('||s.charAt(i)=='['||s.charAt(i)=='{'){
                stack.push(s.charAt(i));
            }else if(s.charAt(i)==')'||s.charAt(i)==']'||s.charAt(i)=='}'){
                if(stack.peek().equals(pairs.get(s.charAt(i)))){
                    stack.pop();
                }
            }
        }
        if(stack.size()==0){
            return true;
        }

        return false;
    }

    public String addStrings(String num1, String num2) {
      StringBuilder res=new StringBuilder();
      int i=num1.length()-1;
      int j=num2.length()-1;
      int flag=0;
      while (i>=0||j>=0){
          int a=i>=0?num1.charAt(i)-'0':0;
          int b=j>=0?num2.charAt(j)-'0':0;
          int temp=a+b+flag;
          res.append(temp%10);
          flag=temp/10;
          j--;
          i--;
      }
       if(flag>0){
           res.append(flag);
       }
        return res.reverse().toString();
    }

    public String longestCommonPrefix(String[] strs) {
        StringBuilder res=new StringBuilder("");
       Arrays.sort(strs);
       String f=strs[0];
       String l=strs[strs.length-1];
       int i=0,j=0;
        while(i<f.length()&&j<l.length()){
            if(f.charAt(i)==l.charAt(j)){
                res.append(f.charAt(i));
            }else {
                break;
            }

        }

        return  res.toString();
    }

    public int lengthOfLIS(int[] nums) {
        int res=0;
        int len = nums.length;
        int[] dp=new int[len];
        Arrays.fill(dp,1);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[i]>nums[j]){

                    dp[i]=Math.max(dp[j]+1,dp[i]);
                }
            }
            res=Math.max(res,dp[i]);
        }

        return res;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(),n=text2.length();
        int[][] dp=new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            char a=text1.charAt(i-1);
            for (int j = 1; j <= n; j++) {
                char b=text2.charAt(j-1);
                if(a==b){
                    dp[i][j]=dp[i-1][j-1]+1;
                }else {
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int car=0;
        ListNode dummy=new ListNode(-1);
        ListNode temp=dummy;
        while (l1!=null||l2!=null){
            int a=l1!=null?l1.val:0;
            int b=l2!=null?l2.val:0;
            int t=a+b+car;
            car=t/10;
            t=t%10;
            temp.next=new ListNode(t);
            temp=temp.next;
            if(l1!=null){
                l1=l1.next;
            }
            if(l2!=null){
                l2=l2.next;
            }

        }
        if(car>0){
            temp.next=new ListNode(car);

        }
        return dummy.next;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if(root==null||root==p||root==q){
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left==null){
            return right;
        }
        if(right==null){
            return left;
        }
        if(left==null&&right==null){
            return null;
        }
        return root;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res=new ArrayList<>();
    int s=0,l=0;
    int row=matrix.length-1;
    int col=matrix[0].length-1;
       while (true){
           //从左向右遍历
           for (int i=l;i<=col;i++){
               res.add(matrix[s][i]);
           }
           //设置上边界
           if(++s>row){
               break;
           }
           //从上向下遍历
           for(int i=s;i<=row;i++){
               res.add(matrix[i][col]);
           }
           //设置右边界
           if(--col<l){
               break;
           }
           //从右向左遍历
           for (int i=col;i>=l;i--){
               res.add(matrix[row][i]);
           }
           //设置下边界
           if(--row<s){
               break;
           }
           //从下向上遍历
           for (int i=row;i>=s;i--){
               res.add(matrix[i][l]);
           }
           //设置做边界
           if (++l>col){
               break;
           }
       }



        return res;
    }

    public int maxProfit(int[] prices) {
        int[] dp=new int[prices.length];
        int min=prices[0];
        for (int i = 1; i < prices.length; i++) {
            min=Math.min(min,prices[i]);
            dp[i] = Math.max(prices[i] -min, dp[i-1]);

        }

        return dp[prices.length-1];
    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a=headA,b=headB;
        while(a!=b){
            a=a==null?headB:a.next;
            b=b==null?headA:b.next;
        }
        return a;
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(m==0){
          System.arraycopy(nums2,0,nums1,0,n);
        }
        if(n==0){
            return;
        }
        List<Integer> list =new ArrayList<>();
       int i=0,j=0;
       while(i<m||j<n) {
           if (i == m) {
               list.add(nums2[j++]);
           } else if (j == n) {
               list.add(nums1[i++]);
           } else if (nums1[i] <= nums2[j]) {
               list.add(nums1[i++]);
           } else {
               list.add(nums2[j++]);
           }
       }
        int[] res = list.stream().mapToInt(Integer::valueOf).toArray();
        for (int k = 0; k < nums1.length; k++) {
            if(nums1[k]!=res[k]){
                nums1[k]=res[k];
            }
        }
    }



}


  class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }


    class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


    class DNode {
        int val;
        int key;
        DNode pre;
        DNode next;
        public DNode(){};
        public DNode(int val, int key) {
            this.val = val;
            this.key = key;
        }
    }
