class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length; 
        int n = nums2.length;
        int start = 0;
        int end = m;
		
		if(m > n)
        {
			return findMedianSortedArrays(nums2, nums1);
		}    
        while(start <= end)
		{
            int i = start + (end - start) / 2;
            int j = (m + n + 1) / 2 - i;
            
			if(i > start && nums1[i - 1] > nums2[j])
			{
                end = i - 1;
            }
			else if(i < end && nums1[i] < nums2[j - 1])
			{
                start = i + 1;
            }
			else
			{
                int maxLeft = Integer.MIN_VALUE;
                int minRight = Integer.MAX_VALUE;
				
				if(i - 1 >= 0) maxLeft  = Math.max(maxLeft, nums1[i - 1]);
                if(j - 1 >= 0) maxLeft  = Math.max(maxLeft, nums2[j - 1]);
                if(i <= m - 1) minRight = Math.min(minRight, nums1[i]);
                if(j <= n - 1) minRight = Math.min(minRight, nums2[j]);

                return (m + n) % 2 == 0 ? (maxLeft + minRight) / 2.0 : maxLeft;
            }
        }
        return 0.0;
    }
}

