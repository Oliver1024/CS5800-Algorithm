public class Median {
    private  PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    private  PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((o1,o2)-> o2-o1);

    public void build (int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            insert(num);
        }
    }
    
    public void insert(int element) {
        float median = peek();
        if (element > median) {
            minHeap.offer(element);
        } else {
            maxHeap.offer(element);
        }
        balanceHeap();
    }

    public float peek() {
        int minSize = minHeap.size();
        int maxSize = maxHeap.size();
        if (minSize == 0 && maxSize == 0) {
            return 0;
        }
        if (minSize > maxSize) {
            return minHeap.peek();
        }
        if (minSize < maxSize) {
            return maxHeap.peek();
        }
        return maxHeap.peek();
    }

    public int extract() {
        int minSize = minHeap.size();
        int maxSize = maxHeap.size();
        if (minSize >= maxSize) {
            return minHeap.poll();
        }
        if (minSize < maxSize) {
            return maxHeap.peek();
        }
        return 0;
    }
    // this is a helper function to balance heap
    private void balanceHeap() {
        int minSize = minHeap.size();
        int maxSize = maxHeap.size();
        int temp = 0;
        if (minSize > maxSize + 1) {
            temp = minHeap.poll();
            maxHeap.offer(temp);
        }
        if (maxSize > minSize + 1) {
            temp = maxHeap.poll();
            minHeap.offer(temp);
        }
    }
}